package project.civilization.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Actions;
import project.civilization.enums.HexState;
import project.civilization.enums.MenuCategory;
import project.civilization.enums.UnitState;
import project.civilization.models.Player;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.units.Settler;
import project.civilization.models.units.Unit;


public class CityController {
    private static final ArrayList<Hex> toBuyTiles = new ArrayList<Hex>();
    private static Hex[][] hex;


    public static ArrayList<Hex> getToBuyTiles() {
        return toBuyTiles;
    }

    public static Player getCurrentPlayer() {
        return GameController.getCurrentPlayer();
    }

    public static void finalizeTrophy(City theCity) {
        int count = 0;
        for (Hex tempHex : theCity.getHexs()) {
            if (tempHex.getHasCitizen()) {
                count++;
            }
        }

        theCity.setTrophy(count + 3);

    }

    public static String showTrophies() {
        StringBuilder trophy = new StringBuilder();
        if (GameController.getSelectedCity() == null) {
            return "select a city first";
        }

        finalizeTrophy(GameController.getSelectedCity());
        trophy.append(GameController.getSelectedCity().getTrophy());
        GameController.setSelectedCity(null);
        return trophy.toString();
    }

    public static String showResources() {
        if (GameController.getSelectedCity() == null) {
            return "select a city first";
        }
        StringBuilder cityResources = new StringBuilder();

        for (Hex hex : GameController.getSelectedCity().getHexs()) {
            if (hex.getResource().getType().equals("Strategic")) {
                cityResources.append("hex: " + "x- " + hex.getX() + " y- " + hex.getY() + " --> " + hex.getResource().getName() + "\n");
            }

        }

        if (cityResources.toString().isEmpty()) {
            return "this city has no resources";
        }


        GameController.setSelectedCity(null);
        return cityResources.toString();


    }


    public static String selectHex(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "invalid x or y";
        }
        if (GameController.getWorld().getHex()[x][y].getState(GameController.getCurrentPlayer()) == HexState.FogOfWar) {
            return "he zerangi inja fog of ware";
        }
        GameController.setSelectedHex(GameController.getWorld().getHex()[x][y]);
        return "tile selected";
    }


    public static String selectCity(String name) {
        for (City temp : City.getCities()) {
            if (temp.getName().equals(name)) {
                GameController.setSelectedCity(temp);
                return "city selected successfully";
            }
        }

        return "no city with this name exists";
    }


    public static String startMakingUnit(String name) {
        if (GameController.getSelectedHex() == null) {
            return "select a tile first";
        }
        if (GameController.getSelectedHex().getOwner() != GameController.getCurrentPlayer()) {
            return "this tile does not belong to you";
        }
        if (GameController.getSelectedHex().getCapital() == null) {
            return "this is not capital";
        }

        if (name.equals("Settler") && GameController.getCurrentPlayer().getHappiness() < 0)
            return "your civilization is unhappy";

        if (!InitializeGameInfo.unitInfo.containsKey(name)) {
            return "invalid unit name";
        }

        String type = InitializeGameInfo.unitInfo.get(name).split(" ")[7];
        GameController.setSelectedCity(GameController.getSelectedHex().getCity());


        if (type.matches("Settler||Worker") && GameController.getSelectedHex().getCivilianUnit() != null) {
            return "you can't have two Civilian units in a city";
        } else if ((!type.matches("Settler||Worker")) && (GameController.getSelectedHex().getMilitaryUnit() != null)) {
            return "you can't have two Military units in a city";
        }
        if (name.equals("Settler") && GameController.getCurrentPlayer().getHappiness() < 0)
            return "your civilization is unhappy";

        if (!InitializeGameInfo.unitInfo.containsKey(name)) {
            return "invalid unit name";
        }

        Unit newUnit = new Unit(name, GameController.getSelectedHex(), GameController.getCurrentPlayer());

        if (newUnit.getNeededTech() != null && !GameController.getCurrentPlayer().getAchievedTechnologies().get(newUnit.getNeededTech())) {
            return "you have not achieved the needed technology to make this unit";
        }

        boolean check = false;
        for (Hex hex : GameController.getSelectedHex().getCity().getHexs()) {
            if (newUnit.getNeededResource() != null && hex.getResource() != null && hex.getResource().getName().equals(newUnit.getNeededResource())) {
                check = true;
            }
        }

        if (!check && newUnit.getNeededResource() != null) {
            return "you don't have the needed resource to make this unit";
        }

        if (GameController.getCurrentPlayer().getProduction() >= newUnit.getNeededProduction()) {
            UnitController.makeUnit(name, GameController.getSelectedHex(), "production");
            return "Unit created successfully";
        }

        int productionPerTurn = 0;
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            productionPerTurn += temp.getProduction();

        }

        Unit unit = new Unit(name, GameController.getSelectedHex(), GameController.getCurrentPlayer());

        if (newUnit.getNeededProduction() < productionPerTurn) {
            unit.setLeftTurns(1);

        } else if (productionPerTurn <= 0) {
            return "you have no production per turn so it's impossible to start making a unit right now";
        } else {
            unit.setLeftTurns((newUnit.getNeededProduction() / productionPerTurn) + 1);
        }


        GameController.getCurrentPlayer().addUnfinishedProject(unit);

        String temp = "the process of " + "making a " + name + " unit" + " on the hex: x=" + GameController.getSelectedHex().getX() + " y=" + GameController.getSelectedHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for builing an unit started successfully";
    }


    public static String buildCity() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.BUILDCITY.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return "something is wrong";
        }
    }


    public static String presaleTiles() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.presaleTiles.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }

    public static String buyHex(int count) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.buyHex.getCharacter());
            json.put("count", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }

    }


    public static String removeCitizenFromWork(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "out of bounds";
        }
        if (hex[x][y].getOwner() != GameController.getCurrentPlayer()) {
            return "this tile is not yours";
        }
        if (!hex[x][y].getHasCitizen()) return "there is no citizen";
        City city = hex[x][y].getCity();
        city.decreaseFood(hex[x][y].getTerrain().getFood());
        city.decreaseGold(hex[x][y].getTerrain().getGold());
        city.decreaseProduction(hex[x][y].getTerrain().getProduction());
        hex[x][y].setHasCitizen(false);
        GameController.getSelectedCity().increaseNumberOfUnemployedCitizen(1);
        return "citizen removed successfully";
    }

    public static String lockCitizenTo(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "out of bounds";
        }
        if (hex[x][y].getOwner() != GameController.getCurrentPlayer()) {
            return "this tile is not yours";
        }
        if (hex[x][y].getHasCitizen()) return "there is already a citizen";
        if (GameController.getSelectedCity().getNumberOfUnemployedCitizen() > 0) {
            GameController.getSelectedCity().increaseNumberOfUnemployedCitizen(1);
            City city = hex[x][y].getCity();
            city.increaseFood(hex[x][y].getTerrain().getFood());
            city.increaseGold(hex[x][y].getTerrain().getGold());
            city.increaseProduction(hex[x][y].getTerrain().getProduction());
            hex[x][y].setHasCitizen(true);
            return "citizen locked";
        } else return "unemployed a citizen first";
    }

    public static String showUnEmployedCitizen() {
        if (GameController.getSelectedCity() == null) return "please select a city first";
        return String.valueOf(GameController.getSelectedCity().getNumberOfUnemployedCitizen());
    }

    public static String cityBanner() {
        if (GameController.getSelectedCity() == null) {
            return "please select a city first";
        }

        StringBuilder cityBanner = new StringBuilder();
        cityBanner.append(GameController.getSelectedCity().getName() + " hitpoint: " + GameController.getSelectedCity().getHitPoint());
        GameController.setSelectedCity(null);
        return cityBanner.toString();
    }

    public static City getCityWithName(String name) {
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            if (temp.getName().equals(name)) {
                return temp;
            }
        }
        return null;
    }

    public static ArrayList<String> getAvailableBuildings(String cityName) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.getAvailableBuildings.getCharacter());
        object.put("menu", MenuCategory.GAMEMenu.getCharacter());
        object.put("cityName", cityName);
        Gson gson = new Gson();
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            String buildingsJson = CivilizationApplication.dataInputStream.readUTF();
            ArrayList<String> names = new Gson().fromJson(buildingsJson, new TypeToken<ArrayList<String>>() {
            }.getType());
            return names;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void buildABuilding(String buildingName, String cityName) {
        JSONObject object = new JSONObject();
        object.put("action", Actions.buildBuilding.getCharacter());
        object.put("menu", MenuCategory.GAMEMenu.getCharacter());
        object.put("cityName", cityName);
        object.put("buildingName", buildingName);
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
