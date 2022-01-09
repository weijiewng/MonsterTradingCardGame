package com.company.game.repository;

import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.model.SpellCard;
import com.company.game.enums.Element;
import com.company.game.enums.Rarity;
import com.company.game.enums.MonsterType;

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
            statement.execute();
            return card;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Optional<ArrayList<Card>> getAllCards(){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM card"
            )
        ){
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Card> cardList = new ArrayList<Card>();
            Card card;
            while(resultSet.next()){
                //TODO maybe create class to get card names and stuff
                if(resultSet.getString("monsterType") != null){
                    card = new MonsterCard(resultSet.getString("id"), resultSet.getString("name"), resultSet.getInt("damage"),
                            Element.valueOf(resultSet.getString("element")),
                            Rarity.valueOf(resultSet.getString("rarity")), MonsterType.valueOf(resultSet.getString("monsterTypeype")));
                }
                else {
                    card = new SpellCard(resultSet.getString("id"), resultSet.getString("name"), resultSet.getInt("damage"),
                            Element.valueOf(resultSet.getString("element")),
                            Rarity.valueOf(resultSet.getString("rarity")));
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
