package com.company.game.repository;

import com.company.game.model.Card;
import com.company.game.model.MonsterCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeckRepository extends Repository{
    public String save(String cardId, String userId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE cardholder SET deck = true WHERE user_id = ? AND card_id = ? AND deck != true"
            )
        ){
            statement.setString(1, userId);
            statement.setString(2, cardId);
            statement.execute();
            return "OK";
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
