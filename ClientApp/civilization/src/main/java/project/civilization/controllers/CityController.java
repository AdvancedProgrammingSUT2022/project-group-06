package project.civilization.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Actions;
import project.civilization.enums.MenuCategory;



public class CityController {

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
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.REMOVECITIZENFROMWORK.getCharacter());
            json.put("i",x);
            json.put("j",y);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e ){
            e.printStackTrace();
            return "something is wrong";
        }
    }

    public static String lockCitizenTo(int x, int y) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.LOCKINGCITIZENTOTILE.getCharacter());
            json.put("i",x);
            json.put("j",y);
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

    public static String  showUnEmployedCitizen() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.showUnEmployedCitizen.getCharacter());
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

    public static String deleteCity(String cityName) {
        JSONObject object = new JSONObject();
        object.put("menu", MenuCategory.GAMEMenu.getCharacter());
        object.put("action", Actions.DELETECITY.getCharacter());
        object.put("cityName", cityName);
        try {
            CivilizationApplication.dataOutputStream.writeUTF(object.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
