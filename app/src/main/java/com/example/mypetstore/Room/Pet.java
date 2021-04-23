package com.example.mypetstore.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pet_info")
public class Pet {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String petName;
    private String petType;
    private String petBreed;
    private String petColor;
    private String petId;

    public Pet(){}

    public Pet(String petName, String petType, String petBreed, String petColor , String petId) {
        this.petName = petName;
        this.petType = petType;
        this.petBreed = petBreed;
        this.petColor = petColor;
        this.petId = petId;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetName() {
        return petName;
    }


    public String getPetId() {
        return petId;
    }

    public int getId() {
        return id;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetname() {
        return petName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPetname(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }
}
