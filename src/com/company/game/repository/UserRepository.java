package com.company.game.repository;

import com.company.game.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends Repository{
    //TODO Hashing
    public User registration(User user){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO \"user\" (id, username, password) VALUES (?, ?, ?)"
            )
        ){

            statement.setString(1, user.getToken());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();

        }
        //TODO Dont know how to handle yet maybe just successfull registration menu
        return user;
    }

    public User login(User user){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM \"user\" WHERE username = ? AND password = ?"
            )
        ){
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                //TODO TOKEN handling | maybe already create user
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        //TODO Maybe error code dont know how to handle yet
        return user;
    }
}
