package webservices.database;

import webservices.models.User;

import java.sql.*;
import java.util.Properties;

public class Database implements IUserModel {
    private final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/wstutorials";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String MAX_POOL = "250";

    private Connection connection;
    private Database() {
        connection = getConnection();
    }
    //How to call constructor ? => Use Singleton pattern
    private static Database instance;
    public static final Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Connection getConnection() {
        try {
            Class.forName(DATABASE_DRIVER);
            Properties properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
            connection = DriverManager.getConnection(DATABASE_URL, properties);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Cannot connect to Database");
            disconnect();
        } finally {
            return connection;
        }
    }
    private void disconnect() {
        try {
            connection.close();
        }catch (Exception e) {

        } finally {
            connection = null;
        }
    }
    //Insert, update, delete, query for User object here ?

    @Override
    public User register(String email, String name, String password, String imageUrl, String userType) {
        User newUser = null;
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                    IUserModel.sqlInsert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);//password will be hashed later
            preparedStatement.setString(4, imageUrl);
            preparedStatement.setString(5, userType);
            preparedStatement.executeUpdate();
            Integer userId = -1;
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
            newUser = new User(name, email, password, imageUrl, userType, userId);
        }catch (SQLException e) {
            System.out.println("Cannot insert user. Error: "+e.toString());
        } finally {
            return newUser;
        }
    }

    @Override
    public User login(String email, String password, String userType) {
        return null;
    }
}
