package com.company.game.repository;

import com.company.game.enums.Element;
import com.company.game.model.Card;
import com.company.game.model.TradeDeal;
import com.company.game.util.Toolbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TradingRepository extends Repository{
    public ArrayList<TradeDeal> getTradeDeals(String token){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM tradedeal WHERE user_id != ?"
             )
        ) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<TradeDeal> tradeDeals = new ArrayList<TradeDeal>();
            TradeDeal tradeDeal;
            while (resultSet.next()) {
                tradeDeal = new TradeDeal();
                tradeDeal.setId(resultSet.getString("id"));
                tradeDeal.setCardId(resultSet.getString("card_id"));
                tradeDeal.setMinDamage(resultSet.getInt("minDamage"));
                tradeDeal.setElement(Element.valueOf(resultSet.getString("element")));
                tradeDeal.setUserId(resultSet.getString("user_id"));
                tradeDeals.add(tradeDeal);
            }
            return tradeDeals;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TradeDeal createTradeDeal(String token, TradeDeal tradeDeal){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO tradedeal (id, card_id, minDamage, element, user_id) VALUES (?,?,?,?,?)"
             )
        ) {
            statement.setString(1, tradeDeal.getId());
            statement.setString(2, tradeDeal.getCardId());
            statement.setInt(3, tradeDeal.getMinDamage());
            statement.setString(4, tradeDeal.getElement().toString());
            statement.setString(5, token);
            statement.execute();
            return tradeDeal;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteTradeDeal(String token, String tradeId){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM tradedeal WHERE id = ? AND user_id = ?"
             )
        ) {
            statement.setString(1, tradeId);
            statement.setString(2, token);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean trade(String token, String tradeId, String cardId){
        TradeDeal tradeDeal = checkTradeExists(tradeId, token);
        if(tradeDeal != null){
            Card card = getCard(cardId, token);
            if(card != null){
                if(checkCondition(card, tradeDeal)){
                    addCardToUser(token,tradeDeal.getCardId());
                    addCardToUser(tradeDeal.getUserId(), cardId);
                    removeCardFromUser(token,tradeDeal.getCardId());
                    removeCardFromUser(tradeDeal.getUserId(), cardId);
                    deleteTradeDeal(tradeDeal.getUserId(), tradeId);
                    return true;
                }
            }
        }
        return false;
    }

    private TradeDeal checkTradeExists(String tradeId, String token){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM tradedeal WHERE id = ? AND user_id != ?"
             )
        ) {
            statement.setString(1, tradeId);
            statement.setString(2, token);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                TradeDeal tradeDeal = new TradeDeal();
                tradeDeal.setId(resultSet.getString("id"));
                tradeDeal.setCardId(resultSet.getString("card_id"));
                tradeDeal.setMinDamage(resultSet.getInt("minDamage"));
                tradeDeal.setElement(Element.valueOf(resultSet.getString("element")));
                tradeDeal.setUserId(resultSet.getString("user_id"));
                return tradeDeal;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Card getCard(String cardId, String token){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT card.id, card.name, card.damage, card.element, card.rarity, card.monstertype FROM card " +
                             "LEFT JOIN cardholder ON card_id = cardholder.card_id " +
                             "WHERE card.id = ? AND  cardholder.user_id = ?"
             )
        ) {
            statement.setString(1, cardId);
            statement.setString(2, token);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Card card = Toolbox.getCardFromResultSet(resultSet);
                return card;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addCardToUser(String userId, String cardId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cardholder (user_id, card_id) VALUES (?, ?); "
            )
        ){
            statement.setString(1, userId);
            statement.setString(2, cardId);
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void removeCardFromUser(String userId, String cardId){
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM cardholder USING " +
                            "(SELECT id FROM cardholder WHERE user_id = ? AND card_id = ? LIMIT 1) AS tmp " +
                            "WHERE cardholder.id = tmp.id "
            )
        ){
            statement.setString(1, userId);
            statement.setString(2, cardId);
            statement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private boolean checkCondition(Card card, TradeDeal tradeDeal){
        if(card.getDamage() < tradeDeal.getMinDamage())
        {
            return false;
        }
        if(!card.getElement().equals(tradeDeal.getElement())){
            return false;
        }
        return true;
    }
}
