package com.example.myapp.com.example.myapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String name;
    private String email;
    private String userType; //facebook / default
    private String password;//You should save encrypted password
    private String userId;

    public User(String name, String email, String userType, String password, String userId) {
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.password = password;
        this.userId = userId;
    }
    //convert JSONObject to User object ?
    public static User createUserFromJsonObject(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            String userType = jsonObject.getString("userType");
            String password = jsonObject.getString("password");
            String userId = jsonObject.getString("userId");
            return new User(name, email, userType, password, userId);
        }catch (JSONException e) {
            return null;
        }

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
