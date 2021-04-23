package com.example.mypetstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypetstore.Room.Pet;
import com.example.mypetstore.ViewModel.AddEditViewModel;
import com.example.mypetstore.Views.PetStoreList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Random;

public class AddEditPet extends AppCompatActivity {

    private AddEditViewModel mAddEditViewModel;

    public static final int LOADER_PETS = 0;
    private EditText mPetName;
    private EditText mPetBreed;
    private Spinner mPetType;
    private Spinner mPetColor;
    private FloatingActionButton mAddPet;
    private Button mFetchPet;
    private TextView mPetText;
    private ProgressBar mAddingProgress;
    private ArrayAdapter<CharSequence> mTypeAdapter;
    private ArrayAdapter<CharSequence> mColorAdapter;
    private boolean isCancelling;

    private int spinnerPosition1=0;
    private int spinnerPosition2 = 0;


    public static final String EXTRA_ID = "com.example.mypetstore.EXTRA_ID";
    public static final String EXTRA_PET_NAME = "com.example.mypetstore.EXTRA_PET_NAME";
    public static final String EXTRA_PET_ID = "com.example.mypetstore.EXTRA_PET_ID";
    public static final String EXTRA_PET_TYPE = "com.example.mypetstore.EXTRA_PET_TYPE";
    public static final String EXTRA_PET_COLOR = "com.example.mypetstore.EXTRA_PET_COLOR";
    public static final String EXTRA_PET_BREED = "com.example.mypetstore.EXTRA_PET_BREED";
    private int mExtra_id;
    private String mAutoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

        //map the widgets
        mPetName = (EditText) findViewById(R.id.pet_name);
        mPetBreed = (EditText) findViewById(R.id.pet_breed);
        mPetType = (Spinner) findViewById(R.id.spinner_typOfPet);
        mPetColor = (Spinner) findViewById(R.id.spinner_pet_color);
        mAddPet = (FloatingActionButton) findViewById(R.id.add_pet_action);
        mAddingProgress = (ProgressBar) findViewById(R.id.adding_progress);


        mTypeAdapter = ArrayAdapter.createFromResource(this, R.array.pet_type, android.R.layout.simple_spinner_item);
        mTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPetType.setAdapter(mTypeAdapter);
        //mPetType.setSelection(2);

        mColorAdapter = ArrayAdapter.createFromResource(this, R.array.pet_color, android.R.layout.simple_spinner_item);
        mColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPetColor.setAdapter(mColorAdapter);

        mAddEditViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(AddEditViewModel.class);


        //check if pet is being added or edited
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit pet details");
            mExtra_id = intent.getIntExtra(EXTRA_ID , 1);
            mAutoId = intent.getStringExtra(EXTRA_PET_ID);
            mPetName.setText(intent.getStringExtra(EXTRA_PET_NAME));
            mPetBreed.setText(intent.getStringExtra(EXTRA_PET_BREED));

            String myColor = intent.getStringExtra(EXTRA_PET_COLOR);
            spinnerPosition1 = mColorAdapter.getPosition(myColor);
            mPetColor.setSelection(spinnerPosition1);

            String myType = intent.getStringExtra(EXTRA_PET_TYPE);
            spinnerPosition2 = mTypeAdapter.getPosition(myType);
            mPetType.setSelection(spinnerPosition2);
        }else{
            setTitle("Add new Pet");
        }



        mAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mExtra_id!=0){

                    updatePet();
                    startActivity(new Intent(AddEditPet.this , PetStoreList.class));

                } else {

                    savePet();
                    startActivity(new Intent(AddEditPet.this , PetStoreList.class));

                }

            }
        });


    }

    private void updatePet() {

        mAddingProgress.setVisibility(View.VISIBLE);
        String name = mPetName.getText().toString();
        String breed = mPetBreed.getText().toString();
        String type = selectedPetType();
        String color = selectedPetColor();
        String id = mAutoId;

        Toast.makeText(AddEditPet.this, "Values:" + type + color, Toast.LENGTH_SHORT).show();

        //send to database;
        updateDatabase(name ,type  ,breed , color , id);
    }


    public void savePet(){

        mAddingProgress.setVisibility(View.VISIBLE);
        String name = mPetName.getText().toString();
        String breed = mPetBreed.getText().toString();
        String type = selectedPetType();
        String color = selectedPetColor();
        String id = generateRandomId();

        Toast.makeText(AddEditPet.this, "Values:" + type + color, Toast.LENGTH_SHORT).show();

        //send to database;
        sendToDatabase(name ,type  ,breed , color , id);

    }

    private void sendToDatabase( String name, String type ,String breed,  String color ,String id) {

        //Pet pet = new Pet(name , type  , breed, color ,id);
        //mAddEditViewModel.insert(pet);
        HashMap<String , String> pet = new HashMap<>();
        pet.put("petname" , name);
        pet.put("pettype" , type);
        pet.put("petcolor" , color);
        pet.put("petbreed" , breed);
        pet.put("petid" , id);

        mAddEditViewModel.insertOnline(pet);

    }

    private void updateDatabase( String name, String type ,String breed,  String color ,String id) {

        Pet pet = new Pet(name , type  , breed, color ,id);
        pet.setId(mExtra_id);
        mAddEditViewModel.update(pet);



    }



    private String selectedPetColor() {
        String selectedColor = (String) mPetColor.getSelectedItem();
        return selectedColor;
    }

    private String selectedPetType() {
        String selectedType = (String) mPetType.getSelectedItem();
        return selectedType;
    }





    private String generateRandomId() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel_upload:
             //isCancelling = true;
              startActivity(new Intent(AddEditPet.this , PetStoreList.class));
                return true;

            case R.id.delete_note:


            default:

                return super.onOptionsItemSelected(item);


        }
    }


}