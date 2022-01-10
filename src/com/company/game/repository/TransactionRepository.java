package com.company.game.repository;

import com.company.game.enums.BoosterName;
import com.company.game.model.Booster;
import com.company.game.model.Card;
import com.company.game.util.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRepository extends Repository{

    public Booster getBooster(String boosterName){
        BoosterName name = BoosterName.valueOf(boosterName);
        String boosterId = getBoosterId(name);
        if(boosterId != null){
            try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * from card LEFT JOIN booster ON card.id = booster.card_id WHERE booster.id = ?"
                )
            ){
                statement.setString(1, boosterId);
                ResultSet resultSet = statement.executeQuery();
                Card card;
                Booster booster = new Booster();
                while(resultSet.next()){
                    card = Toolbox.getCardFromResultSet(resultSet);
                    booster.addCard(card);
                }
                booster.setId(boosterId);
                booster.setBoosterName(name);
                return booster;
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean addCardToUser(String userId, String cardId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cardholder (user_id, card_id) VALUES (?, ?); "
            )
        ){
            statement.setString(1, userId);
            statement.setString(2, cardId);
            statement.execute();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void removeBooster(String boosterId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Booster WHERE id = ?"
            )
        ){
            statement.setString(1, boosterId);
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void setCoins(int coins, String token){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE \"user\" SET coins = ? WHERE id = ?;"
            )
        ){
            statement.setInt(1, coins);
            statement.setString(2, token);
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private String getBoosterId(BoosterName boosterName){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from booster WHERE name = ?"
            )
        ){
            statement.setString(1, boosterName.name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("id");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
