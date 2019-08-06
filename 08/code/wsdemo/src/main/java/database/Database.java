package database;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import models.Place;
import models.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Database implements IUserModel, IPlaceModel{

    private final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/wstutorials";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String MAX_POOL = "250";
    Connection connection;
    private static Database instance;
    private Database() {
        connection = getConnection();
    }
    public static final Database getInstance() {
        if(instance == null) {
            instance = new Database();
            instance.createTableUser();
        }
        return instance;
    }
    public Connection getConnection() {
        try {
            Class.forName(DATABASE_DRIVER);
            Properties properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
            connection = DriverManager.getConnection(DATABASE_URL, properties);
            return connection;
        } catch (Exception e) {
            System.out.println("Cannot connect to DB");
            return null;
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

    @Override
    public User login(String name, String email, String password, String userType) {
        User user = null;
        String sqlCommand = "";
        try {
            if(userType.equals("facebook")) {
                sqlCommand = String.format(IUserModel.sqlLoginFacebook, email, password);
            }else if(userType.equals("default")) {
                sqlCommand = String.format(IUserModel.sqlLoginUser, email, password);
            }
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                //String password = resultSet.getString("password");
                String imageUrl = resultSet.getString("imageUrl");
                user = new User(name, email, password, imageUrl, userType,userId);
                user.setPassword("not show");
            }
            if(user == null && userType.equals("facebook")) {
                user = register(email, name,"","", userType);
            }
        }catch (Exception e) {
            System.out.println("Error login user. Error: "+e.toString());
        } finally {
            return user;
        }
    }
    private String generatePassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        String hashPassword = "";
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            hashPassword = new String(hash);
        }catch (NoSuchAlgorithmException e) {

        } finally {
            return hashPassword;
        }
    }
    @Override
    public User register(String email, String name, String password, String imageUrl, String userType) {
        User newUser = null;
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                    IUserModel.sqlInsertUser, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, generatePassword(password));
            preparedStatement.setString(4, imageUrl);
            preparedStatement.setString(5, userType);
            preparedStatement.executeUpdate();
            Integer userId = -1;
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                userId=rs.getInt(1);
            }
            newUser = new User(name, email, password,imageUrl,userType, userId);
        }catch (SQLException e) {
            System.out.println(String.format("Cannot insert user.Error: %s", e.toString()));
        } finally {
            return newUser;
        }

    }

    @Override
    public void createTableUser() {
        try {
            String sql = IUserModel.sqlCreateUserTable;
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(String.format("Cannot create table User. Error: %s", e.toString()));
        }
    }

    @Override
    public void createTablePlace() {
        try {
            String sql = IPlaceModel.sqlCreatePlaceTable;
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(String.format("Cannot create table Place. Error: %s", e.toString()));
        }
    }

    @Override
    public Place insertPlace(Integer userId, String placeName, String address, double lat, double lon) {
        Place newPlace = null;
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                    IPlaceModel.sqlInsertPlace, Statement.RETURN_GENERATED_KEYS);
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
            System.out.println("Error login user. Error: "+e.toString());
        } finally {
            return places;
        }
    }
}
