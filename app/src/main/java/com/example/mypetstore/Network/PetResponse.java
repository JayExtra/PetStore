package com.example.mypetstore.Network;

import com.google.gson.annotations.SerializedName;

public class PetResponse {

    @SerializedName("pet_name")
    private String petName;

    @SerializedName("pet_breed")
    private String petBreed;

    @SerializedName("pet_color")
    private String petColor;

    @SerializedName("pet_type")
    private String petType;

    @SerializedName("pet_id")
    private String petId;


    public String getPetName() {
        return petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public String getPetColor() {
        return petColor;
    }

    public String getPetType() {
        return petType;
    }

    public String getPetId() {
        return petId;
    }


    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }
}
