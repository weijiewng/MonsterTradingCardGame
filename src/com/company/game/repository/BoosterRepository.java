package com.company.game.repository;

import com.company.game.enums.BoosterName;
import com.company.game.enums.Element;
import com.company.game.enums.Rarity;
import com.company.game.enums.MonsterType;
import com.company.game.model.Booster;
import com.company.game.model.Card;
import com.company.game.model.MonsterCard;
import com.company.game.model.SpellCard;
import com.company.game.util.Toolbox;

import java.sql.*;
import java.util.ArrayList;

public class BoosterRepository extends Repository{

    public boolean saveBooster(ArrayList<Card> booster, String boosterName, int cost){
        String uuid = Toolbox.createUUID();
        for (int i = 0; i < booster.size(); i++) {
            try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO booster (id, name, card_id, cost) VALUES (?, ?, ?, ?); "
                )
            ){
                statement.setString(1, uuid);
                statement.setString(2, boosterName);
                statement.setString(3, booster.get(i).getId());
                statement.setInt(4, cost);
                statement.execute();
            }
            catch(SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean saveBooster(ArrayList<String> cardIdList){
        String uuid = Toolbox.createUUID();
        for (int i = 0; i < cardIdList.size(); i++) {
            try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO booster (id, name, card_id, cost) VALUES (?, ?, ?, ?); "
                )
            ){
                statement.setString(1, uuid);
                statement.setString(2, BoosterName.DEFAULT.name);
                statement.setString(3, cardIdList.get(i));
                statement.setInt(4, 5);
                statement.execute();
            }
            catch(SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}

