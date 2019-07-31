package models;

public class Place {
    private Integer userId;
    private String placeName, address;
    private Double lat, lon;

    public Place(Integer userId, String placeName, String address, Double lat, Double lon) {
        this.userId = userId;
        this.placeName = placeName;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
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
