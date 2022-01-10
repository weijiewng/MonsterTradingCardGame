package com.company.game.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Repository {
    protected Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection(
                //Need to change if database is build different
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "postgres"
        );
        return connection;
    }
}
