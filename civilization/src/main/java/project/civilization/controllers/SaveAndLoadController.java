package project.civilization.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import project.civilization.models.Game;
import project.civilization.models.Player;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.maprelated.Terrain;
import project.civilization.models.units.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveAndLoadController {
    public static String saveGameWithJackson(String gameName) {
        try {
            Game game = new Game(gameName, GameController.getWorld(), InitializeGameInfo.getPlayers(),
                    GameController.getPlayerCount(),GameController.getTurn());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.writeValue(new File(gameName+".json"), game);
        }catch (IOException e){
            e.printStackTrace();
        }
        loadGameWithJackson(gameName);
        return null;
    }

    public static String loadGameWithJackson(String gameName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String json = new String(Files.readAllBytes(Paths.get(gameName+".json")));
            Game game = objectMapper.readValue(json, Game.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static String saveGameWithJson(String gameName) {
        FileWriter fileWriter;
        try {
            Game game = new Game(gameName, GameController.getWorld(), InitializeGameInfo.getPlayers(),
                    GameController.getPlayerCount(),GameController.getTurn());
            fileWriter = new FileWriter(gameName+".json");
            Gson gson = new GsonBuilder().create();
            fileWriter.write(gson.toJson(game));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadGameWithJson(gameName);
        return null;
    }
    public static String loadGameWithJson(String gameName) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(gameName+".json")));
            Game game = new Gson().fromJson(json, Game.class);
            FillCityClass(game);
            fillHexClass(game);
            InitializeGameInfo.runAsLoadGame(game.getWorld(), game.getPlayers());
            GameController.initializeGameControllerForLoadGame(game.getPlayerCount(),game.getTurn());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void fillHexClass(Game game) {
        Hex[][] hexes = game.getWorld().getHex();
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                for (Player player: game.getPlayers()) {
                    if(hexes[i][j].getOwnerUserName() != null &&
                            hexes[i][j].getOwnerUserName().equals(player.getName())){
                        hexes[i][j].setOwner(player);
                    }
                    for (Unit unit: player.getUnits()) {
                        if (unit instanceof  Civilian) hexes[i][j].setCivilianUnit((Civilian) unit);
                        else if (unit instanceof  Military) hexes[i][j].setMilitaryUnit((Military) unit);
                    }
                }
            }
        }
    }

    private static void FillCityClass(Game game) {
        Hex[][] hexes = game.getWorld().getHex();
        for (Player player: game.getPlayers()) {
            for (City city : player.getCities()) {
                City.getCities().add(city);
                city.setOwner(player);
                city.setCapital(game.getWorld().getHex()[city.getBeginningX()][city.getBeginningY()]);
                hexes[city.getBeginningX()][city.getBeginningY()].setCapital(city);
                hexes[city.getBeginningX()][city.getBeginningY()].setCity(city);
                ArrayList<Hex> allHexes = new ArrayList<>();
                allHexes.add(city.getCapital());
                for (int[] ints:city.getOtherTilesCoordinates()) {
                    allHexes.add(game.getWorld().getHex()[ints[0]][ints[1]]);
                    hexes[ints[0]][ints[1]].setCity(city);
                }
                city.setHexs(allHexes);
            }
        }
    }

    public static void makeUnit(String name, Hex hex) {
        String theType = InitializeGameInfo.unitInfo.get(name).split(" ")[7];
        if (theType.equals("Settler")) {
            Settler newSettler = new Settler(name, hex, GameController.getCurrentPlayer());
        } else if (theType.equals("Worker")) {
            Worker newWorker = new Worker(name, hex, GameController.getCurrentPlayer());
        } else if (theType.equals("Ranged")) {
            Ranged newRanged = new Ranged(name, hex, GameController.getCurrentPlayer());
        } else if (theType.equals("Siege")) {
            Siege newSiege = new Siege(name, hex, GameController.getCurrentPlayer());
        } else if (theType.equals("Melee")) {
            Melee newMelee = new Melee(name, hex, GameController.getCurrentPlayer());
        }
    }
}
