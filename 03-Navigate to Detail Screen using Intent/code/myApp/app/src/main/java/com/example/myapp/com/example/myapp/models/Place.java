package com.example.myapp.com.example.myapp.models;

import java.io.Serializable;

public class Place implements Serializable {
    private String placeName;
    private String description;
    //constructor

    public Place(String placeName, String description) {
        this.placeName = placeName;
        this.description = description;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Place name: "+placeName+".Description: "+description;
    }
}
