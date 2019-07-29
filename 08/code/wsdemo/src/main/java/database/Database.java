package database;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Properties;
import com.mysql.jdbc.Driver;
import models.User;

public class Database implements IUserModel{

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
    public User login(String name, String email, String password, String userType) throws MyException {
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
            throw new MyException(String.format("Cannot login user.Error: %s", e.toString()));
        } finally {
            return user;
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
            preparedStatement.setString(3, password);
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
}
