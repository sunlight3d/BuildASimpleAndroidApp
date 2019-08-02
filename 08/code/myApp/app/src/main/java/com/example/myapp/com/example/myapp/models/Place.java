package com.example.myapp.com.example.myapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Place implements Serializable{
    private Integer placeId;
    private Integer userId;
    private String placeName, address;
    private Double lat, lon;

    public Place(Integer placeId, Integer userId, String placeName, String address, Double lat, Double lon) {
        this.placeId = placeId;
        this.userId = userId;
        this.placeName = placeName;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    //convert JSONObject to User object ?
    public static Place createPlaceFromJsonObject(JSONObject jsonObject) {
        try {
            Integer userId = jsonObject.getInt("userId");
            Integer placeId = jsonObject.getInt("placeId");
            String placeName = jsonObject.getString("placeName");
            String address = jsonObject.getString("address");
            Double lat = jsonObject.getDouble("lat");
            Double lon = jsonObject.getDouble("lon");
            Place place = new Place(placeId, userId, placeName, address, lat, lon);
            return place;
        }catch (JSONException e) {
            return null;
        }

    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
