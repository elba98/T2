package com.cp317.t2.t2;

import com.google.firebase.database.IgnoreExtraProperties;


public class User {
    private String userFirstName;
    private String userLastName;
    private String userType;
    private String userPostalCode;
    private String userEMail;
    private String userPhoneNumber;
    public User() {
    }

    public User(String userEMail, String userFirstName, String userLastName, String userType, String userPhoneNumber, String userPostalCode) {
        this.userEMail = userEMail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userType = userType;
        this.userPostalCode = userPostalCode;
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }

    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    public String getUserEMail() {
        return userEMail;
    }

    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

