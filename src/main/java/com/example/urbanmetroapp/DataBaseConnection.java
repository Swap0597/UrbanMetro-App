package com.example.urbanmetroapp;

import java.sql.*;
public class DataBaseConnection {
    String dbURL = "jdbc:mysql://localhost:3306/urbanmetro";
    String userName = "root";
    String password = "123456";


    private Statement getStatement() {
        try {
            Connection conn = DriverManager.getConnection(dbURL, userName, password);
            return conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getQueryTable(String query) {
        Statement statement = getStatement();
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean insertUpdate(String query) {
        Statement statement = getStatement();
        try {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
