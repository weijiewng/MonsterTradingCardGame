package com.company.game.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends Repository{
    //TODO Hashing
    public String registration(String username, String password){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE username = ? AND password = ?"
            )
        ){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                //TODO Dont know how to handle yet
                return "ERROR";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        //TODO Dont know how to handle yet maybe just succesfull registration menu
        return "SUCCESS";
    }

    public String login(String username, String password){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE username = ? AND password = ?"
            )
        ){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                //TODO TOKEN handling | maybe already create user 
                return "TOKEN";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        //TODO Maybe error code dont know how to handle yet
        return "ERROR";
    }
}
