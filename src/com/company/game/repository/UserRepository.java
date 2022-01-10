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
                    "INSERT INTO \"user\" (id, username, password, coins, elo) VALUES (?, ?, ?, ?, ?)"
            )
        ){
            String hash = Toolbox.createHash(user.getPassword());
            if(!hash.isEmpty()){
                user.setPassword(hash.toString());
                statement.setString(1, user.getToken());
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setInt(4, user.getCoins());
                statement.setInt(5, user.getElo());
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
        return null;
    }

    public User getUserData(String token){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM \"user\" WHERE id = ?"
            )
        ){
            User user = new User();
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                user.setUsername(resultSet.getString("username"));
                user.setCoins(resultSet.getInt("coins"));
                user.setName(resultSet.getString("name"));
                user.setBio(resultSet.getString("bio"));
                user.setImage(resultSet.getString("image"));
                return user;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public User getStats(String token) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM \"user\" WHERE id = ?"
             )
        ) {
            User user = new User();
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUsername(resultSet.getString("username"));
                user.setWin(resultSet.getInt("win"));
                user.setLose(resultSet.getInt("lose"));
                user.setDraw(resultSet.getInt("draw"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getScore(String token) {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM \"user\" WHERE id = ?"
             )
        ) {
            User user = new User();
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUsername(resultSet.getString("username"));
                user.setElo(resultSet.getInt("elo"));
                user.setWin(resultSet.getInt("win"));
                user.setLose(resultSet.getInt("lose"));
                user.setDraw(resultSet.getInt("draw"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setUserData(String token, String name, String bio, String image){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE \"user\" SET name = ?, bio = ?, image = ? WHERE id = ?"
            )
        ){
            statement.setString(1, name);
            statement.setString(2, bio);
            statement.setString(3, image);
            statement.setString(4, token);
            statement.execute();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
