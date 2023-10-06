package com.example.assignment2parta;

public class ContactData {
    private String name;
    private String phoneNumber;
    private int pictureId;
    private String email;

    public ContactData(String name, String phoneNumber, String email, int pictureId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pictureId = pictureId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
