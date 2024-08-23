package com.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
	
	private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
	
	private String jdbcURL = "jdbc:postgresql://localhost:5432/productdb"; // PostgreSQLのURLを確認
    private String jdbcUsername = "postgres"; // 正しいユーザー名
    private String jdbcPassword = "password"; // 正しいパスワード
    
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE userid = ?";
    private static final String SELECT_ALL_USER_SORTED = "SELECT * FROM users ORDER BY userid";
    
    
    private static final String DELETE_USER_SQL = "UPDATE users SET deleteflag = ?, updated_date = ? WHERE userid = ?";
    private static final String UPDATE_USER_SQL = "UPDATE users SET username = ?, adminflag = ?, mail = ?, updated_date = ? WHERE userid = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password = ?, updated_date = ? WHERE userid = ? AND password = ?";
    
	
	 protected Connection getConnection() throws SQLException {
	        Connection connection = null;
	        try {
	            Class.forName("org.postgresql.Driver");
	            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
	            LOGGER.info("Database connection established.");
	        } catch (ClassNotFoundException e) {
	            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
	        } catch (SQLException e) {
	            LOGGER.log(Level.SEVERE, "SQL Error: " + e.getMessage(), e);
	            throw e;
	        }
	        return connection;
	    }
	 
    public User getUser(int userid, String username, String password) throws ClassNotFoundException {
        String query = "SELECT * FROM users WHERE userid = ? username = ? AND password = ?";
        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

        	statement.setInt(1, userid);
            statement.setString(2, username);
            statement.setString(3, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	userid = resultSet.getInt("userid");
            	username = resultSet.getString("username");
                password = resultSet.getString("password");
                int adminflag = resultSet.getInt("adminflag");
                String mail = resultSet.getString("mail");
                Date registered_date = resultSet.getDate("registered_date");
                Date updated_date = resultSet.getDate("updated_date");
                Date last_login_date = resultSet.getDate("last_login_date");
                user = new User(userid, username, password, adminflag,mail,registered_date,updated_date,last_login_date);
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void addUser(User user) throws ClassNotFoundException {
        String query = "INSERT INTO users (userid,username, password,adminflag,mail,registered_date,updated_date,last_login_date,deleteflag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

        	statement.setInt(1, user.getUserid());
            statement.setString(2, user.getusername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getAdminflag());
            statement.setString(5, user.getMail());
            statement.setDate(6, (java.sql.Date) user.getRegistered_date());
            statement.setDate(7, (java.sql.Date) user.getUpdated_date());
            statement.setDate(8, (java.sql.Date) user.getLast_login_date());
            statement.setBoolean(9, false);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	int userid = rs.getInt("userid");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int adminflag = rs.getInt("adminflag");
                String mail = rs.getString("mail");
                Date registered_date = rs.getDate("registered_date");
                Date updated_date = rs.getDate("updated_date");
                Date last_login_date = rs.getDate("last_login_date");
                boolean deleteflag = rs.getBoolean("deleteflag");
                users.add(new User(userid, username, password, adminflag,mail,registered_date,updated_date,last_login_date,deleteflag));
            }
            LOGGER.info("Users retrieved successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error: " + e.getMessage(), e);
            throw e;
        }
        return users;
    }
    
    public User getUserById(int id) throws SQLException {
    	User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
            	int userid = rs.getInt("userid");
            	String username = rs.getString("username");
                String password = rs.getString("password");
                int adminflag = rs.getInt("adminflag");
                String mail = rs.getString("mail");
                Date registered_date = rs.getDate("registered_date");
                Date updated_date = rs.getDate("updated_date");
                Date last_login_date = rs.getDate("last_login_date");
                user = new User(userid, username, password, adminflag,mail,registered_date,updated_date,last_login_date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return user;
    }
    
    public List<User> getAllUsersSorted(String sortBy, String sortOrder) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY " + sortBy + " " + sortOrder;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	int userid = rs.getInt("userid");
            	String username = rs.getString("username");
                String password = rs.getString("password");
                int adminflag = rs.getInt("adminflag");
                String mail = rs.getString("mail");
                Date registered_date = rs.getDate("registered_date");
                Date updated_date = rs.getDate("updated_date");
                Date last_login_date = rs.getDate("last_login_date");
                users.add(new User(userid, username, password, adminflag,mail,registered_date,updated_date,last_login_date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }
    
    public void deleteUser(int userid) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
        	long millis = System.currentTimeMillis();
        	preparedStatement.setBoolean(1, true);
        	preparedStatement.setDate(2, new Date(millis));
            preparedStatement.setInt(3, userid);
            
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                LOGGER.info("user deleted successfully.");
            } else {
                LOGGER.warning("No rows deleted.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error: " + e.getMessage(), e);
            throw e;
        }
        
        
    }
    
    public void releaseUser(int userid) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
        	long millis = System.currentTimeMillis();
        	preparedStatement.setBoolean(1, false);
        	preparedStatement.setDate(2, new Date(millis));
            preparedStatement.setInt(3, userid);
            
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                LOGGER.info("user deleted successfully.");
            } else {
                LOGGER.warning("No rows deleted.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error: " + e.getMessage(), e);
            throw e;
        }
        
    }

    public void updateUser(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
            long millis = System.currentTimeMillis();
        	preparedStatement.setInt(5, user.getUserid());
            preparedStatement.setString(1, user.getusername());
            preparedStatement.setInt(2, user.getAdminflag());
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setDate(4, new Date(millis));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void updatePassword(int userid,String password,String newPassword) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_SQL)) {
            long millis = System.currentTimeMillis();
        	preparedStatement.setString(1, newPassword);
        	 preparedStatement.setDate(2, new Date(millis));
            preparedStatement.setInt(3, userid);
            preparedStatement.setString(4, password);
         
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
   
}
