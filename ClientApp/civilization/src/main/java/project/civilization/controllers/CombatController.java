package project.civilization.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Actions;
import project.civilization.enums.MenuCategory;
import project.civilization.models.Player;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.units.Combatable;
import project.civilization.models.units.Ranged;
import project.civilization.models.units.Siege;

import java.io.IOException;

public class CombatController {
    private static  Hex[][] hex ;
    private static Combatable attacker;
    private static Combatable defender;

    public static Hex[][] getHex() {
        return hex;
    }

    public static String attackCity(int x, int y) {
        if (GameController.getSelectedCity() == null) return "first select a city";
        if (hex[x][y].getMilitaryUnit() == null) {
            return ("there is not a military unit");
        }
        if (hex[x][y].getMilitaryUnit().getOwner() == GameController.getCurrentPlayer()) {
            return ("am i joke to you? attack our self?");
        }
        if (!GameController.getSelectedCity().isInPossibleCombatRange(x, y, 0, GameController.getSelectedCity().getX(), GameController.getSelectedCity().getY())) {
            return "out of range";
        }
        int temp = GameController.getSelectedCity().getRangedCombatStrength();
        temp -= hex[x][y].getMilitaryUnit().getDefenciveBounes();
        hex[x][y].getMilitaryUnit().decreaseHealth(temp);
        if (hex[x][y].getMilitaryUnit().getHealth() <= 0) {
            UnitController.deleteMilitaryUnit(hex[x][y].getMilitaryUnit());
            return "city win";
        }
        return "attacked successfully";
    }

    public static String attackUnit(int x, int y) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.ATTACKUNIT.getCharacter());
            json.put("i", x);
            json.put("j", y);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return "something is wrong";
        }
    }

    public static String addCityToTerritory(String cityName, String playerName ){
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.ADDCITYTOTERRITORY.getCharacter());
            json.put("player",playerName );
            json.put("city", cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return "something is wrong";
        }
    }
}
