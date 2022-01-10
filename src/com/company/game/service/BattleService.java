package com.company.game.service;

import com.company.game.model.Battle;
import com.company.game.model.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BattleService {
    ArrayList<User> battleReadyUser;
    ArrayList<Battle> battleList;

    public BattleService() {
        this.battleReadyUser = new ArrayList<User>();
        this.battleList = new ArrayList<Battle>();
    }

    public void addUser(User user){
        battleReadyUser.add(user);
    }

    public void removeUser(User user){
        battleReadyUser.remove(user);
    }

    public Battle startLookingForBattle(User user){
        for (int i = 0; i < battleList.size(); i++) {
            if(battleList.get(i).getStatus() == false){
                battleList.get(i).setPlayer2(user);
                battleList.get(i).setDeck2(user.getDeck());
                battleList.get(i).setStatus(true);
                battleList.get(i).doBattle();
                removeUser(battleList.get(i).getPlayer1());
                removeUser(battleList.get(i).getPlayer2());
            }
            return battleList.get(i);
        }
        if(waitingForBattle(user)){
            return startLookingForBattle(user);
        }
        removeUser(user);
        return null;

    }

    public boolean waitingForBattle(User user){
        long start = System.currentTimeMillis();
        int seconds = 0;
        addUser(user);
        Battle battle = new Battle(user, user.getDeck());
        battleList.add(battle);
        while(battleReadyUser.size() < 2 || seconds <= 5){
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds = (int)((System.currentTimeMillis() - start) / 1000);
        }
        if(battleReadyUser.size() >= 2){
            return true;
        }
        return false;
    }
}
