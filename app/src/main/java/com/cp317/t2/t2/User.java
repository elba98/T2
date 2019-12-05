package com.cp317.t2.t2;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Arrays;
import java.util.ArrayList;


public class User {
    private String userFirstName;
    private String userLastName;
    private String userType;
    private String userPostalCode;
    private String userEMail;
    private String userPhoneNumber;
    private String uId;

    // Optional parameters entered later
    private String program, sex, bio;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    private String courses;

    public User() {
    }

    public User(String uId, String userFirstName, String userLastName, String program) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.program = program;
        this.uId = uId;
    }

    public User(String uId, String userEMail, String userFirstName, String userLastName, String userType, String userPhoneNumber, String userPostalCode) {
        this.uId = uId;
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

    public String getProgram() {
        return program;
    }

    public String getSex() {
        return sex;
    }

    public String getBio() {
        return bio;
    }

    public String getCourses() {
        return courses;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }
}

