package project.civilization.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Actions;
import project.civilization.enums.MenuCategory;


import java.io.IOException;

public class CombatController {

    public static String attackCity(int x, int y) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.CITYATTACKTOUNIT.getCharacter());
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

    public static String startWar(int i, int j) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.STARTWAR.getCharacter());
            json.put("i",i);
            json.put("j",j);
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
