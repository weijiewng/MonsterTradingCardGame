package com.company.game.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleRepository extends Repository{
    public void win(String userId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE \"user\" SET win = win + 1 WHERE id = ?"
            )
        ){
            statement.setString(1, userId);
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void lose(String userId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE \"user\" SET lose = lose + 1 WHERE id = ?"
            )
        ){
            statement.setString(1, userId);
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void draw(String userId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE \"user\" SET draw = draw + 1 WHERE id = ?"
            )
        ){
            statement.setString(1, userId);
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
