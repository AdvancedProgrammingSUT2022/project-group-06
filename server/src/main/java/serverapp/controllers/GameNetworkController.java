package serverapp.controllers;

import serverapp.models.Game;
import serverapp.models.Player;
import serverapp.models.User;
import serverapp.models.maprelated.World;

import java.util.ArrayList;

public class GameNetworkController {
    private static ArrayList<Game> allPlayingGames = new ArrayList<>();

    public static void startGame(String gameUuid,Invitation invitation) {
        ArrayList<User> usersWhoWantToPlay = invitation.getAllUsers();
        InitializeGameInfo.setNumberOFPlayers(usersWhoWantToPlay.size());
        InitializeGameInfo.setMapSize(invitation.getHexInHeight(),invitation.getHexInHeight());
        for (int i = 0; i < usersWhoWantToPlay.size(); i++) {
            Player player = new Player(usersWhoWantToPlay.get(i).getUsername());
        }
        InitializeGameInfo.run();
        GameController.initializeGameController();
        Game playingGame = new Game(gameUuid, InitializeGameInfo.getWorld(), InitializeGameInfo.getPlayers(),
                GameController.getPlayerCount(),GameController.getTurn());
        allPlayingGames.add(playingGame);
    }
}
