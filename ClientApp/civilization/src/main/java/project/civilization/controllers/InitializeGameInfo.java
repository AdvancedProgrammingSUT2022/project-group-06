package project.civilization.controllers;


import project.civilization.CivilizationApplication;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class InitializeGameInfo {

    private static int numberOFPlayers;

    public static int getNumberOFPlayers() {
        return numberOFPlayers;
    }

    public static void setNumberOFPlayers(int numberOFPlayers) {
        InitializeGameInfo.numberOFPlayers = numberOFPlayers;
    }

    public static int hexInHeight;
    public static int hexInWidth;

    public static void setMapSize(String mapSize) {
        String[] size = mapSize.split("\\*");
        hexInHeight = Integer.parseInt(size[0]);
        hexInWidth = Integer.parseInt(size[1]);
    }

}
