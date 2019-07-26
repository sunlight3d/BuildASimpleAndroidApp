package database;
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
            "    userType varchar(255) DEFAULT 'default'\n" +
            ")";
    public void createTableUser();
    public void register(String email, String name, String password, String userType);
    public void login(String email,String password, String userType);
}
