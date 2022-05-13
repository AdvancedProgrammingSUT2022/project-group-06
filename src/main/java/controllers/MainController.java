package controllers;

import java.util.ArrayList;

import models.Player;

public class MainController
{
    public int startGame(String input)
    {
        ArrayList<String> usernames=new ArrayList<String>();

        StringBuilder playerNum=new StringBuilder();
        playerNum.append("--player1");
        int user=0;
        int num=2;
        while(true)
        {
            if((user=input.indexOf(playerNum.toString()))!=-1)
            {
                String getUsername=input.substring(user+10);
                String username=getUsername.split("[ \\t]+")[0];

                usernames.add(username);

            }
            else
            {
                break;
            }

            playerNum.deleteCharAt(playerNum.length()-1);
            playerNum.append(num);
            num++;
        }

        StringBuilder playerNum2=new StringBuilder();
        playerNum2.append("-p1");
        int user2=0;
        int num2=2;
        while(true)
        {
            if((user2=input.indexOf(playerNum2.toString()))!=-1)
            {
                String getUsername=input.substring(user2+4);
                String username=getUsername.split("[ \\t]+")[0];
                usernames.add(username);

            }
            else
            {
                break;
            }

            playerNum2.deleteCharAt(playerNum2.length()-1);
            playerNum2.append(num2);
            num2++;
        }

        if(usernames.isEmpty())
        {
            return 0;
        }

        for(String temp:usernames)
        {
            if(!UserController.users.containsKey(temp))
            {
                return 1;
            }
            new Player(temp);
        }
        InitializeGameInfo.setNumberOFPlayers(InitializeGameInfo.getPlayers().size());
        return 2;


    }
}
