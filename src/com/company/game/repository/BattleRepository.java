package com.company.game.repository;

import com.company.game.model.Battle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleRepository extends Repository{
    public boolean save(Battle battle){
        if(!checkBattleExists(battle)){
            try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO battle (id, user1, user2, result, log) VALUES (?, ?, ?, ?, ?); "
                )
            ){
                statement.setString(1, battle.getId());
                statement.setString(2, battle.getPlayer1().getUsername());
                statement.setString(3, battle.getPlayer2().getUsername());
                statement.setInt(4, battle.getResult());
                statement.setString(5, battle.getLog().toString());
                statement.execute();
                return true;
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean checkBattleExists(Battle battle){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM battle WHERE id = ?; "
            )
        ){
            statement.setString(1, battle.getId());
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
