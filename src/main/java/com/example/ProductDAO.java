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

public class ProductDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

    private String jdbcURL = "jdbc:postgresql://localhost:5432/productdb"; // PostgreSQLのURLを確認
    private String jdbcUsername = "postgres"; // 正しいユーザー名
    private String jdbcPassword = "password"; // 正しいパスワード

    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (name, price, category, description, updated_date) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE id = ?";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE products SET name = ?, price = ?, category = ?, description = ?, updated_date = ? WHERE id = ?";
    private static final String SELECT_ALL_PRODUCTS_SORTED = "SELECT * FROM products ORDER BY price ";

    public ProductDAO() {}

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

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                String description = rs.getString("description");
                Date updatedDate = rs.getDate("updated_date");
                products.add(new Product(id, name, price, category, description, updatedDate));
            }
            LOGGER.info("Products retrieved successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error: " + e.getMessage(), e);
            throw e;
        }
        return products;
    }

    public Product getProductById(int id) throws SQLException {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                String description = rs.getString("description");
                Date updatedDate = rs.getDate("updated_date");
                product = new Product(id, name, price, category, description, updatedDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return product;
    }

    public List<Product> getAllProductsSorted(String sortOrder) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = SELECT_ALL_PRODUCTS_SORTED + (sortOrder.equals("desc") ? "DESC" : "ASC");
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                String description = rs.getString("description");
                Date updatedDate = rs.getDate("updated_date");
                products.add(new Product(id, name, price, category, description, updatedDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return products;
    }

    public void addProduct(Product product) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setDate(5, product.getUpdatedDate());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                LOGGER.info("Product inserted successfully.");
            } else {
                LOGGER.warning("No rows inserted.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error: " + e.getMessage(), e);
            throw e;
        }
    }

    public void deleteProduct(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                LOGGER.info("Product deleted successfully.");
            } else {
                LOGGER.warning("No rows deleted.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Error: " + e.getMessage(), e);
            throw e;
        }
    }

    public void updateProduct(Product product) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setDate(5, product.getUpdatedDate());
            preparedStatement.setInt(6, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
