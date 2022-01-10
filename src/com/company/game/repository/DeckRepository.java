package com.company.game.repository;

import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.util.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class DeckRepository extends Repository{
    public boolean save(String userId, String cardId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE cardholder SET deck = true WHERE user_id = ? AND card_id = ? AND deck != true"
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

    public LinkedList<Card> getDeck(String userId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM card LEFT JOIN cardholder on card.id = cardholder.card_id WHERE deck = true AND user_id = ?"
            )
        ){
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            LinkedList<Card> cardList = new LinkedList<Card>();
            Card card;
            while(resultSet.next()){
                card = Toolbox.getCardFromResultSet(resultSet);
                cardList.add(card);
            }
            return cardList;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkDeckFull(String userId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) as counter FROM cardholder WHERE user_id = ? AND deck = true"
            )
        ){
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getInt("counter") == 4){
                    return true;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
