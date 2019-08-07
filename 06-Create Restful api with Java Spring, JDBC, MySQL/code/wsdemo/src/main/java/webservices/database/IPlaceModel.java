package webservices.database;

import webservices.models.Place;

import java.util.ArrayList;


/*
CREATE TABLE IF NOT EXISTS tblPlace (
    placeId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    placeName varchar(500),
    address TEXT,
    lat float(15, 10),
    lon float(15, 10)
);
 */

public interface IPlaceModel {
    public static String sqlInsert = "INSERT INTO tblPlace(userId,placeName, address, lat, lon) \n" +
            "    VALUES(?,?,?,?,?) \n";//SQL injection
    public static String sqlFindPlaces = "SELECT * FROM tblPlace WHERE userId= '%d' LIMIT %d OFFSET %d";
    public Place insertPlace(Integer userId, String placeName, String address, double lat, double lon);
    public ArrayList<Place> queryPlaces(Integer userId, Integer offset, Integer limit);
}
