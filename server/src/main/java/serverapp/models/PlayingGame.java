package serverapp.models;

import serverapp.models.maprelated.Hex;

import java.util.ArrayList;

public class PlayingGame {
    private ArrayList<Player> players;
    private String name;
    private Hex[][] hexes;
    public PlayingGame(String name, Hex[][] hexes,ArrayList<Player> players) {
        this.name = name;
        this.hexes = hexes;
        this.players = players;
    }
}
