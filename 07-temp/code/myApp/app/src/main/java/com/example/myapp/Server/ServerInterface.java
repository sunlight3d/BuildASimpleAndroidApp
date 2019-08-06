package com.example.myapp.Server;

public interface ServerInterface {
    public void loginWithEmailAndPassword(String email, String password);
    public void queryPlaces(Integer userId, Integer offset, Integer limit);
}
