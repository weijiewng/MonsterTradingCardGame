package com.company.game.repository;

import com.company.game.model.User;
import com.company.game.util.Toolbox;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository extends Repository{
    //TODO Hashing
    public User registration(User user){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    //TODO set other values
                    "INSERT INTO \"user\" (id, username, password, coins) VALUES (?, ?, ?, ?)"
            )
        ){
            String hash = Toolbox.createHash(user.getPassword());
            if(!hash.isEmpty()){
                user.setPassword(hash.toString());
                statement.setString(1, user.getToken());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setInt(4, user.getCoins());
                statement.execute();
                return user;
            }
        }
        catch(SQLException  e){
            e.printStackTrace();

        }
        return null;
    }

    public User login(User user){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM \"user\" WHERE username = ? AND password = ?"
            )
        ){
            statement.setString(1, user.getUsername());
            statement.setString(2, Toolbox.createHash(user.getPassword()));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user.setToken(resultSet.getString("id"));
                user.setCoins(resultSet.getInt("coins"));
                return user;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        //TODO Maybe error code dont know how to handle yet
        return null;
    }

}
