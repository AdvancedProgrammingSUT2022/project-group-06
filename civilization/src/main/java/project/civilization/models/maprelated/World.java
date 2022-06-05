package project.civilization.models.maprelated;

import com.google.gson.annotations.Expose;
import project.civilization.enums.Color;
import project.civilization.enums.HexState;
import project.civilization.models.Player;

import java.util.*;

public class World {
    @Expose
    private int worldWidth;
    @Expose
    private int worldHeight;
    @Expose
    private String[][] string;
    @Expose
    private int hexInWidth;
    @Expose
    private int hexInHeight;
    @Expose
    private Hex[][] hex;

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