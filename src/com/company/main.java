package com.company;

import com.company.game.GameApi;
import com.company.server.MyServer;

import java.io.IOException;

public class main {

    public static void main(String[] args) {

        MyServer server = new MyServer(new GameApi());
        try{
            server.start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
