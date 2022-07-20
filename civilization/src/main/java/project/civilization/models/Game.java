package project.civilization.models;

import project.civilization.models.maprelated.Hex;
import project.civilization.models.maprelated.World;

import java.util.ArrayList;

public class Game {
    public Game(){

    }
    private String name;

    private  World world;
    private ArrayList<Player> players;
    private int playerCount;
    private int turn;


    public Game(String name, World world, ArrayList<Player> players,int playerCount, int turn) {
        this.name = name;
        this.world = world;
        this.players = players;
        this.turn = turn;
        this.playerCount = playerCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getTurn() {
        return turn;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
