package com.company.game.repository;

import com.company.game.enums.Element;
import com.company.game.enums.Rarity;
import com.company.game.enums.Type;
import com.company.game.model.Booster;
import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.model.SpellCard;

import java.sql.*;
import java.util.ArrayList;

//TODO looking how booster are stored
public class BoosterRepository extends Repository{

    public void saveBooster(ArrayList<String> cardIdList){
        int id = 0;
        //Maybe without prepared
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) as rowcount FROM Booster; "
            )
        ){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                id = resultSet.getInt("rowcount");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        for (int i = 0; i < cardIdList.size() ; i++) {
            try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO Booster (id, cardID) VALUES (?,?); "
                )
            ){
                statement.setInt(1, id);
                statement.setString(2, cardIdList.get(i));
                statement.execute();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }


    }

    public Booster getBooster(){
        Booster booster = new Booster();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    //TODO Looking which is the first booster
                    "SELECT * FROM Booster WHERE id = 1"
            )
        ){
            ResultSet resultSet =  statement.executeQuery();
            ArrayList<String> cardIdList = new ArrayList<String>();
            while(resultSet.next()){
                cardIdList.add(resultSet.getString("card_id"));
            }
            Card card;
            for (int i = 0; i < cardIdList.size(); i++) {
                try (PreparedStatement secondStatement = connection.prepareStatement(
                        "SELECT class, name, damage, element, rarity, type FROM Card INNER JOIN Booster ON Card.id = Booster.cardID WHERE Booster.id = ?"
                )){
                    secondStatement.setInt(1, 1);
                    resultSet =  secondStatement.executeQuery();
                    if(resultSet.next()){
                        if(resultSet.getString("class").equals("Monster")){
                            card = new MonsterCard(resultSet.getString("name"), resultSet.getInt("damage"),
                                    Element.valueOf(resultSet.getString("Element")),
                                    Rarity.valueOf(resultSet.getString("Rarity")), Type.valueOf(resultSet.getString("Type")));
                        }
                        else{
                            card = new SpellCard(resultSet.getString("name"), resultSet.getInt("damage"),
                                    Element.valueOf(resultSet.getString("Element")),
                                    Rarity.valueOf(resultSet.getString("Rarity")));
                        }
                        booster.addCard(card);
                        //TODO after booster is generated
                    }
                }catch(SQLException e){
                        e.printStackTrace();
                    }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        removeBooster();
        return booster;
    }

    private void addBoosterToUser(){
        //TODO add booster to user dont know how to do yet
    }

    private void removeBooster(){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Booster WHERE id = 1"
            )
        ){
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}

