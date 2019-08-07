package webservices.database;

import webservices.models.Place;
import webservices.models.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.Properties;

public class Database implements IUserModel, IPlaceModel {
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
    private String hashPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
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
            preparedStatement.setString(3, hashPassword(password));//password will be hashed !
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
        User user = null;
        String sqlCommand = "";
        try {
            if(userType.equals("facebook")) {
                sqlCommand = String.format(IUserModel.sqlLoginFacebook, email);
            } else {
                String hashedPassword = hashPassword(password);
                sqlCommand = String.format(IUserModel.sqlLogin, email, hashedPassword);
            }
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                String imageUrl = resultSet.getString("imageUrl");
                String name = resultSet.getString("name");
                user = new User(name, email, password, imageUrl, userType, userId);
                user.setPassword("now show");
            }
            if(user == null && userType.equals("facebook")) {
                user = register(email, "","","", "facebook");
            }
        } catch (Exception e) {
            System.out.println("Error login user. Error: "+e.toString());
        } finally {
            return user;
        }
    }

    @Override
    public Place insertPlace(Integer userId, String placeName, String address, double lat, double lon) {
        //like "register/insert user"
        Place newPlace = null;
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                    IPlaceModel.sqlInsert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, placeName);
            preparedStatement.setString(3, address);
            preparedStatement.setDouble(4, lat);
            preparedStatement.setDouble(5, lon);
            preparedStatement.executeUpdate();
            Integer placeId = -1;
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                placeId=rs.getInt(1);
            }
            newPlace = new Place(placeId, userId, placeName, address,lat,lon);
        }catch (SQLException e) {
            System.out.println(String.format("Cannot insert place.Error: %s", e.toString()));
        } finally {
            return newPlace;
        }
    }

    @Override
    public ArrayList<Place> queryPlaces(Integer userId, Integer offset, Integer limit) {
        String sqlCommand = "";
        ArrayList<Place> places = new ArrayList<Place>();
        try {
            sqlCommand = String.format(IPlaceModel.sqlFindPlaces, userId, limit, offset);
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                Integer placeId = resultSet.getInt("placeId");
                String placeName = resultSet.getString("placeName");
                String address = resultSet.getString("address");
                Double lat = resultSet.getDouble("lat");
                Double lon = resultSet.getDouble("lon");
                Place place = new Place(placeId, userId, placeName, address, lat, lon);
                places.add(place);
            }
        }catch (Exception e) {
            System.out.println("Error query Places. Error: "+e.toString());
        } finally {
            return places;
        }
    }
}
















