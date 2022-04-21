package controllers;

import models.maprelated.World;

import java.util.ArrayList;

public class GameController {
    public static ArrayList<String> players = new ArrayList<String>();
    private int turn;
    private static final World world = new World();

    public static String printWorld() {
        StringBuilder stringWorld = new StringBuilder();
        for (int i = 0; i < world.getWorldHeight(); i++) {
            for (int j = 0; j < world.getWorldWidth(); j++) {
                stringWorld.append(world.getString()[i][j]);
            }
            stringWorld.append("\n");
        }
        return (stringWorld.toString());
    }

}
