package com.example.pilifitproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:database/pilifit.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()){
            try{
                connection = DriverManager.getConnection(URL);
            }catch(SQLException e){
                System.out.print("Error connecting to database: "+ e.getMessage());
                throw e;
            }
        }

    return connection;
}

    public static void testConnection() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connection to SQLite has been established.");
                System.out.println("Database name: " + conn.getMetaData().getURL());
            }
        } catch (SQLException e) {
            System.out.println(" Failed to connect: " + e.getMessage());
        }
    }

}
