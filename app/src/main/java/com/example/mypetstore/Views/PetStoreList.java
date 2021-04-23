package com.example.mypetstore.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mypetstore.Adapters.PetRecyClerAdapter;
import com.example.mypetstore.R;
import com.example.mypetstore.AddEditPet;
import com.example.mypetstore.Room.Pet;
import com.example.mypetstore.SettingsActivity;
import com.example.mypetstore.ViewModel.PetViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PetStoreList extends AppCompatActivity {

    private PetViewModel mPetViewModel;
    private RecyclerView mPetRecyclerView;
    private ProgressBar mLoadItems;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mFloatingActionButton;
    public static final int EDIT_PET_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_store_list);


        mPetRecyclerView = (RecyclerView) findViewById(R.id.pets_rv);
        mLoadItems = (ProgressBar) findViewById(R.id.load_pet_list);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_new_pet_btn);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(PetStoreList.this, AddEditPet.class));
                //finish();

                //Intent intent = new Intent(PetStoreList.this , RegisterPet.class);
                //  intent.putExtra("IS_NEW_NOTE" ,mIsNewNote);
                // startActivity(intent);
            }
        });


        initializeDisplayContent();

    }


    private void initializeDisplayContent() {

        //load the list of pets into the recycler adapter
        mLinearLayoutManager = new LinearLayoutManager(this);
        mPetRecyclerView.setLayoutManager(mLinearLayoutManager);
        mPetRecyclerView.setHasFixedSize(true);
      PetRecyClerAdapter adapter = new PetRecyClerAdapter();
        mPetRecyclerView.setAdapter(adapter);

        mPetViewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(PetViewModel.class);
        mPetViewModel.getAllPets().observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(List<Pet> pets) {
                adapter.submitList(pets);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mPetViewModel.delete(adapter.getPetAt(viewHolder.getAdapterPosition()));
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentLayout, "pet deleted", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }).attachToRecyclerView(mPetRecyclerView);

        adapter.setOnClickListener(new PetRecyClerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pet pet) {

                Intent intent = new Intent(PetStoreList.this , AddEditPet.class);
                intent.putExtra(AddEditPet.EXTRA_ID , pet.getId());
                intent.putExtra(AddEditPet.EXTRA_PET_ID , pet.getPetId());
                intent.putExtra(AddEditPet.EXTRA_PET_NAME , pet.getPetName());
                intent.putExtra(AddEditPet.EXTRA_PET_TYPE , pet.getPetType());
                intent.putExtra(AddEditPet.EXTRA_PET_COLOR , pet.getPetColor());
                intent.putExtra(AddEditPet.EXTRA_PET_BREED , pet.getPetBreed());
                startActivityForResult(intent , EDIT_PET_REQUEST);


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pet_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                deleteAllRecords();
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this , SettingsActivity.class));
                return true;

            default:

                return super.onOptionsItemSelected(item);


        }
    }

    private void deleteAllRecords() {

        mPetViewModel.deleteAllPets();
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, "all pets deleted", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

}