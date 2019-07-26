package database;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Properties;
import com.mysql.jdbc.Driver;

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
    public void login(String email, String password, String userType) {

    }

    @Override
    public void register(String email, String name, String password, String userType) {

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
