package project.civilization.models.maprelated;


import com.google.gson.annotations.Expose;
import project.civilization.enums.Color;
import project.civilization.enums.HexState;
import project.civilization.models.Player;

import java.util.*;

public class World {
    public World(){}
    private transient int worldWidth;

    private transient int worldHeight;


    private transient String[][] string;

    private int hexInWidth;

    private int hexInHeight;

    private Hex[][] hex;

    public World(int hexInHeight, int hexInWidth) {
        worldWidth = 12 * hexInWidth;
        worldHeight = 7 * hexInHeight;
        string = new String[worldHeight][worldWidth];
        this.hexInHeight = hexInHeight;
        this.hexInWidth = hexInWidth;
        hex = new Hex[hexInHeight][hexInWidth];
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public String[][] getString() {
        return string;
    }

    public Hex[][] getHex() {
        return hex;
    }

    public int getHexInHeight() {
        return hexInHeight;
    }

    public int getHexInWidth() {
        return hexInWidth;
    }
    public void setWorldWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }

    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }

    public void setString(String[][] string) {
        this.string = string;
    }

    public void setHexInWidth(int hexInWidth) {
        this.hexInWidth = hexInWidth;
    }

    public void setHexInHeight(int hexInHeight) {
        this.hexInHeight = hexInHeight;
    }

    public void setHex(Hex[][] hex) {
        this.hex = hex;
    }


}