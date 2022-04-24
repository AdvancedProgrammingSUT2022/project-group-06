package models.maprelated;

import enums.Color;
import enums.HexState;
import models.Player;

import java.util.*;

public class World {
    private final int worldWidth;
    private final int worldHeight;
    private final String[][] string;

    private final int hexInWidth;
    private final int hexInHeight;
    private final Hex[][] hex;
    //public static HashMap<String, Color> terrainColors = new HashMap<String, Color>();

    public World() {
        worldWidth = 120;
        worldHeight = 65;
        string = new String[worldHeight][worldWidth];
        hexInWidth = 10;
        hexInHeight = 10;
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

}