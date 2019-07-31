package database;

import models.Place;

/*
CREATE TABLE IF NOT EXISTS tblPlace (
    placeId INT AUTO_INCREMENT PRIMARY KEY ,
    userId INT,
    placeName varchar(500),
    address TEXT,
    lat float,
    lon float
);
 */
public interface IPlaceModel {
    public static final String sqlCreateUserTable = "CREATE TABLE IF NOT EXISTS tblPlace (\n" +
            "    placeId INT AUTO_INCREMENT PRIMARY KEY ,\n" +
            "    userId INT,\n" +
            "    placeName varchar(500),\n" +
            "    address TEXT,\n" +
            "    lat float,\n" +
            "    lon float\n" +
            ")";
    public static final String sqlInsertUser = "INSERT INTO tblPlace(userId,placeName, address, lat, lon) \n" +
            "    VALUES(?,?,?,?,?) \n";//SQL injection
    public static final String sqlFindPlaces = "SELECT * FROM tblPlace WHERE userId= '%d'";
    public void createTablePlace();
    public Place insertPlace(Integer userId, String placeName, String address, double lat, double lon);
}
