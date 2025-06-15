package com.example.pilifitproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO<T> {
    // Abstract methods (enforce implementation)
    public abstract void add(T item) throws SQLException;
    public abstract void delete(int id) throws SQLException;

    // Shared concrete implementation
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

}