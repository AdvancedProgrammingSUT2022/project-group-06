package project.civilization.models;

import com.google.gson.annotations.Expose;
import project.civilization.models.maprelated.Hex;

public class Game {
    public Game(String name, Hex[][] hexes) {
        this.name = name;
        this.hexes = hexes;
    }
    @Expose
    private String name;
    @Expose
    private Hex[][] hexes;
}
