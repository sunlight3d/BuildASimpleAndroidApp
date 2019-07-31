package database;

import models.User;

/*
CREATE TABLE IF NOT EXISTS tblUser (
    userId INT AUTO_INCREMENT PRIMARY KEY ,
    name varchar(500),
    email varchar(500) NOT NULL,,
    password varchar(500) NOT NULL,
    userType varchar(255) DEFAULT 'default'
);
 */
public interface IUserModel {
    public static final String sqlCreateUserTable = "CREATE TABLE IF NOT EXISTS tblUser (\n" +
            "    userId INT AUTO_INCREMENT PRIMARY KEY ,\n" +
            "    name varchar(500),\n" +
            "    email varchar(500) NOT NULL,\n" +
            "    password varchar(500) NOT NULL,\n" +
            "    imageUrl text,\n" +
            "    userType varchar(255) DEFAULT 'default'\n" +
            ")";
    public static final String sqlInsertUser = "INSERT INTO tblUser(email,name, password, imageUrl, userType) \n" +
            "    VALUES(?,?,?,?,?) \n";//SQL injection
    public static final String sqlLoginUser = "SELECT * FROM tblUser WHERE email= '%s' and password = '%s'";
    public static final String sqlLoginFacebook = "SELECT * FROM tblUser WHERE email= '%s' and userType = 'facebook'";

    public void createTableUser();
    public User register(String email, String name, String password, String imageUrl, String userType);
    public User login(String name, String email, String password, String userType);
}



