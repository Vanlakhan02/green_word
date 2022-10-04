package com.example.greenlife.Model;

public class UserAccount {
    String userId;
    String userName;
    String userImage;
    String phone;
    String userEmail;
    public UserAccount(){

    }
    public UserAccount(String userId, String userName, String userImage, String phone,String userEmail){
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.phone = phone;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
