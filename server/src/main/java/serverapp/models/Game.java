package serverapp.models;

import serverapp.models.maprelated.Hex;

public class Game {
    public Game(String name, Hex[][] hexes) {
        this.name = name;
        this.hexes = hexes;
    }
    private String name;
    private Hex[][] hexes;
}
