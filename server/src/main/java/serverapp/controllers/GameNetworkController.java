package serverapp.controllers;

import serverapp.enums.HexState;
import serverapp.models.Game;
import serverapp.models.Player;
import serverapp.models.PlayingGame;
import serverapp.models.User;
import serverapp.models.maprelated.City;

import java.util.ArrayList;

public class GameNetworkController {
    private static ArrayList<PlayingGame> allPlayingGames = new ArrayList<>();
    public static void startGame(String gameUuid, ArrayList<User> usersWhoWantToPlay) {
        //todo: inilize initialize game
/*        for (int i = 0; i < usersWhoWantToPlay.size(); i++) {
            Player player = new Player(usersWhoWantToPlay.get(i).getUsername());
        }
       PlayingGame playingGame =  new PlayingGame(gameUuid, InitializeGameInfo.getWorld().getHex(),InitializeGameInfo.getPlayers())
        allPlayingGames.add(playingGame);*/
    }
}
