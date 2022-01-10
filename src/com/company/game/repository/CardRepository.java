package com.company.game.repository;

import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.model.SpellCard;
import com.company.game.enums.Element;
import com.company.game.enums.Rarity;
import com.company.game.enums.MonsterType;
import com.company.game.util.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CardRepository extends Repository{
    public Card save(Card card){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO card (id, name, damage, element, rarity, monstertype) VALUES (?, ?, ?, ?, ?, ?);"
            )
        ){
            statement.setString(1, card.getId());
            statement.setString(2, card.getName());
            statement.setInt(3, card.getDamage());
            statement.setString(4, card.getElement().toString());
            statement.setString(5, card.getRarity().toString());
            if(card instanceof MonsterCard){
                statement.setString(6, ((MonsterCard) card).getMonsterType().toString());
            }
            else{
                statement.setString(6, null);
            }
            statement.execute();
            return card;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Card> getAllCards(){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM card"
            )
        ){
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Card> cardList = new ArrayList<Card>();
            Card card;
            while(resultSet.next()){
                card = Toolbox.getCardFromResultSet(resultSet);
                cardList.add(card);
            }
            return cardList;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Card> getAllCardsFromUser(String token){
        ArrayList<String> cardIdList = getCardIdFromCardholder(token);
        if(cardIdList != null){
            ArrayList<Card> cardList = new ArrayList<Card>();
            Card card;
            for (int i = 0; i < cardIdList.size(); i++) {
                try(Connection connection = getConnection();
                    PreparedStatement statement = connection.prepareStatement(
                            "SELECT * FROM card WHERE id = ?"
                    )
                ){
                    statement.setString(1, cardIdList.get(i));
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next()){
                        card = Toolbox.getCardFromResultSet(resultSet);
                        cardList.add(card);
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            return cardList;
        }
        return null;

    }

    private ArrayList<String> getCardIdFromCardholder(String token){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM cardholder WHERE user_id = ?"
            )
        ){
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> cardIdList = new ArrayList<String>();
            while(resultSet.next()){
                cardIdList.add(resultSet.getString("card_id"));
            }
            return cardIdList;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
