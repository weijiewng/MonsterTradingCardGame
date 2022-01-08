package com.company.game.repository;

import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.model.SpellCard;
import com.company.game.enums.Element;
import com.company.game.enums.Rarity;
import com.company.game.enums.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CardRepository extends Repository{
    public void save(Card card){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Card (class, name, damage, element, rarity, type) VALUES (?, ?);"
            )
        ){
            statement.setString(1, card.getClass().getName());
            statement.setString(2, card.getName());
            statement.setInt(3, card.getDamage());
            statement.setString(4, card.getElement().toString());
            statement.setString(5, card.getRarity().toString());
            if(card instanceof MonsterCard){
                statement.setString(6, ((MonsterCard) card).getType().toString());
            }
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Optional<ArrayList<Card>> getAllCards(){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Card"
            )
        ){
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Card> cardList = new ArrayList<Card>();
            Card card;
            while(resultSet.next()){
                //TODO maybe create class to get card names and stuff
                if(resultSet.getString("class").equals("Monster")){
                    card = new MonsterCard(resultSet.getString("name"), resultSet.getInt("damage"),
                            Element.valueOf(resultSet.getString("Element")),
                            Rarity.valueOf(resultSet.getString("Rarity")), Type.valueOf(resultSet.getString("Type")));
                }
                else {
                    card = new SpellCard(resultSet.getString("name"), resultSet.getInt("damage"),
                            Element.valueOf(resultSet.getString("Element")),
                            Rarity.valueOf(resultSet.getString("Rarity")));
                }
                cardList.add(card);
            }
            return Optional.of(cardList);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
