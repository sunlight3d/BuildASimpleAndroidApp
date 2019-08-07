package webservices.database;

import webservices.models.User;
/*
CREATE TABLE IF NOT EXISTS tblUser (
    userId INT AUTO_INCREMENT PRIMARY KEY ,
    name varchar(500),
    email varchar(500) NOT NULL,
    password varchar(500) NOT NULL,
    imageUrl varchar(500),
    userType varchar(255) DEFAULT 'default'
);
 */
public interface IUserModel {
    public static String sqlInsert = "INSERT INTO tblUser(email,name, password, imageUrl, userType) \n" +
                                               " VALUES(?,?,?,?,?) \n";//SQL injection
    public static String sqlLogin = "SELECT * FROM tblUser WHERE email= '%s' and password = '%s'";
    public static String sqlLoginFacebook = "SELECT * FROM tblUser WHERE email= '%s' and userType = 'facebook'";
    public User register(String email, String name, String password, String imageUrl, String userType);
    public User login(String email, String password, String userType);
}
