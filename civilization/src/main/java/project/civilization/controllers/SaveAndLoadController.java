package project.civilization.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.image.Image;
import project.civilization.CivilizationApplication;
import project.civilization.models.Game;
import project.civilization.models.Player;
import project.civilization.models.User;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.maprelated.Terrain;
import project.civilization.models.units.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SaveAndLoadController {
    public static int autoSaveTurns;
    public static int autoSaveLeftTurns;

    public static String saveGameWithJson(String gameName) {
        FileWriter fileWriter;
        try {
            Game game = new Game(gameName, GameController.getWorld(), InitializeGameInfo.getPlayers(),
                    GameController.getPlayerCount(), GameController.getTurn());
            fileWriter = new FileWriter(gameName + ".json");
            Gson gson = new GsonBuilder().create();
            fileWriter.write(gson.toJson(game));
            fileWriter.close();
            addToGameNamesFile(gameName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void addToGameNamesFile(String newName) {
        ArrayList<String> allGameNames = loadSavedGamesNames();
        if (!allGameNames.contains(newName)) {
            allGameNames.add(newName);
            try {
                URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/GameNames.txt")).toExternalForm());
                File file = new File(Paths.get(address.toURI()).toString());
                PrintWriter user = new PrintWriter(file);
                for (String s : allGameNames) {
                    user.write(s + "\n");
                }
                user.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String loadGameWithJson(String gameName) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(gameName + ".json")));
            Game game = new Gson().fromJson(json, Game.class);
            fillUnitClass(game);
            FillCityClass(game);
            fillHexClass(game);
            InitializeGameInfo.runAsLoadGame(game.getWorld(), game.getPlayers());
            GameController.initializeGameControllerForLoadGame(game.getPlayerCount(), game.getTurn());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void fillHexClass(Game game) {
        Hex[][] hexes = game.getWorld().getHex();
        for (int i = 0; i < hexes.length; i++) {
            for (int j = 0; j < hexes[i].length; j++) {
                for (Player player : game.getPlayers()) {
                    if (hexes[i][j].getOwnerUserName() != null &&
                            hexes[i][j].getOwnerUserName().equals(player.getName())) {
                        hexes[i][j].setOwner(player);
                    }
                    for (Unit unit : player.getUnits()) {
                        if(unit.getXhex()==i && unit.getYhex() == j){
                            unit.setCurrentHex(hexes[i][j]);
                        if (unit instanceof Civilian ){
                            hexes[i][j].setCivilianUnit((Civilian) unit);
                        }
                        else if (unit instanceof Military) {
                            hexes[i][j].setMilitaryUnit((Military) unit);
                        }
                        }
                    }
                }
            }
        }
    }

    private static void FillCityClass(Game game) {
        Hex[][] hexes = game.getWorld().getHex();
        for (Player player : game.getPlayers()) {
            for (City city : player.getCities()) {
                City.getCities().add(city);
                city.setOwner(player);
                city.setCapital(game.getWorld().getHex()[city.getBeginningX()][city.getBeginningY()]);
                hexes[city.getBeginningX()][city.getBeginningY()].setCapital(city);
                hexes[city.getBeginningX()][city.getBeginningY()].setCity(city);
                ArrayList<Hex> allHexes = new ArrayList<>();
                allHexes.add(city.getCapital());
                for (int[] ints : city.getOtherTilesCoordinates()) {
                    allHexes.add(game.getWorld().getHex()[ints[0]][ints[1]]);
                    hexes[ints[0]][ints[1]].setCity(city);
                }
                city.setHexs(allHexes);
            }
        }
    }

    private static void fillUnitClass(Game game) {
        for (Player player : game.getPlayers()) {
            player.setMilitaries(new ArrayList<>());
            player.setCivilians(new ArrayList<>());
            for (Unit unit: player.getUnits()) {
               makeUnit(unit, player);
            }
            player.getUnits().clear();
            player.getUnits().addAll(player.getCivilians());
            player.getUnits().addAll(player.getMilitaries());
        }
    }

    public static void makeUnit(Unit unit, Player player) {
        InitializeGameInfo.initializeUnitInfo();
        String theType = InitializeGameInfo.unitInfo.get(unit.getName()).split(" ")[7];
        if (theType.equals("Settler")) {
            player.getCivilians().add(new Settler().CloneASettler(unit));
        } else if (theType.equals("Worker")) {
            player.getCivilians().add(new Worker().CloneAWorker(unit));
        } else if (theType.equals("Ranged")) {
            player.getMilitaries().add(new Ranged().CloneARanged(unit));
        } else if (theType.equals("Siege")) {
            player.getMilitaries().add(new Siege().CloneASiege(unit));
        } else if (theType.equals("Melee")) {
            player.getMilitaries().add(new Melee().CloneAMelee(unit));
        }
    }

    public static ArrayList<String> loadSavedGamesNames() {
        ArrayList<String> allGameNames = new ArrayList<>();
        try {
            URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/GameNames.txt")).toExternalForm());
            String games = new String(Files.readAllBytes(Paths.get(address.toURI())));
            if (games.equals("")) {
                return allGameNames;
            }
            String[] readUser = games.split("\n");
            allGameNames.addAll(Arrays.asList(readUser));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return allGameNames;
    }

    public static void AutoSave() {
        if (autoSaveLeftTurns < autoSaveTurns) {
            saveGameWithJson(String.valueOf(autoSaveLeftTurns + 1));
            autoSaveLeftTurns++;
        } else {
            autoSaveLeftTurns = 0;
            saveGameWithJson(String.valueOf(autoSaveLeftTurns + 1));
        }
    }

}
