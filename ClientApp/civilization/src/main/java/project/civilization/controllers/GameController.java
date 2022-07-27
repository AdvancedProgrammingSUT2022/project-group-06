package project.civilization.controllers;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.*;


import java.io.IOException;
import java.util.*;

public class GameController {
    public static void setIsEnemy(String name){
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.setIsEnemy.getCharacter());
            json.put("name",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        
    }
    public static String getIsEnemy(String name)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getIsEnemy.getCharacter());
            json.put("name",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }
    public static void setSelectedCityByName(String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.setSelectedCity.getCharacter());
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static String changeTurn() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.changeTurn.getCharacter());
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


    public static String cheatCityProduction(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatCityProduction.getCharacter());
            json.put("amount", amount);
            json.put("cityName", cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatGold(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatGold.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatHappiness(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatHappiness.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatPopulation(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatPopulation.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatProduction(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatProduction.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatScore(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatScore.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // public static String cheatTurn(int amount) {
    //     turn += amount-1;
    //     changeTurn();
    //     return "turn increased successfully";
    // }
    public static String cheatMP(int amount, int x, int y, String type) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatMP.getCharacter());
            json.put("amount", amount);
            json.put("x",x);
            json.put("y",y);
            json.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatMeleeCombatStrength(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatMeleeCombatStrength.getCharacter());
            json.put("amount", amount);
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatRangedCombatStrength(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatRangedCombatStrength.getCharacter());
            json.put("amount", amount);
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatCityFood(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatCityFood.getCharacter());
            json.put("amount", amount);
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatCityHitPoint(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatCityHitPoint.getCharacter());
            json.put("amount", amount);
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatTrophy(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatTrophy.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String militaryPanel() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.militaryPanel.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public static String citiesPanel() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.citiesPanel.getCharacter());
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

    public static String unitsPanel() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.UNITLISTPANEL.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }


    public static String demographicScreen() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.demographicScreen.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public static String isAchieved(String name) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.ISANACHIVEDTECK.getCharacter());
            json.put("techName", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return null;
    }
    public static String getLastTechnology() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getLastTechnology.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }
    public static String getPlayerMainInfo()
    {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.playerMainInfo.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public static ArrayList<String> getAvailableTechs() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getAvailableTechsArray.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
        }
        return null;
    }

    public static String getTechInfo(String name) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getTechnologyInfo.getCharacter());
            json.put("name", name);
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getBuildingInfo(String name) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getBuildingInfo.getCharacter());
            json.put("name", name);
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String changeResearch(String techName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.changeResearch.getCharacter());
            json.put("techName", techName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public static String cityScreen(String cityName)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.cityScreen.getCharacter());
            json.put("cityName", cityName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";

    }

    public static ArrayList<String> getAvailableWorks() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETAVAILABLEWORKS.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }
    public static String  orderToWorker(String temp) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.orderToWorker.getCharacter());
            json.put("order", temp);
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

    public static String getPaneDetails(int mapBoundary0, int mapBoundary1, int mapBoundary2, int mapBoundary3) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETPANEDETAILS.getCharacter());
            json.put("mapBoundary0",mapBoundary0);
            json.put("mapBoundary1",mapBoundary1);
            json.put("mapBoundary2",mapBoundary2);
            json.put("mapBoundary3",mapBoundary3);
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

    public static int getHexInHeight() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETHexInHeight.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return Integer.parseInt(CivilizationApplication.dataInputStream.readUTF());
        } catch (IOException x) {
            x.printStackTrace();
            return 0;
        }
    }

    public static int getHexInWidth() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETHexInWidth.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return Integer.parseInt(CivilizationApplication.dataInputStream.readUTF());
        } catch (IOException x) {
            x.printStackTrace();
            return 0;
        }
    }

    public static String getTerrainNames() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GetTerrainNames.getCharacter());
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

    public static String getHexDetails(int i, int j) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GetHexDetails.getCharacter());
            json.put("i", i);
            json.put("j", j);
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
    public static String unitBuy(String name)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.buyUnit.getCharacter());
            json.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }
    public static String unitMake(String name)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.makeUnit.getCharacter());
            json.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
   
    }
    public static void setSelectedHex(int i, int j) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.SELECTILE.getCharacter());
            json.put("i", i);
            json.put("j",j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static String getUnitInformation(int i, int j,String type) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETUNITINFORMATION.getCharacter());
            json.put("i", i);
            json.put("j",j);
            json.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }


    public static ArrayList<String> getNotifications() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getNotifications.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Integer> getNotificationsTurns() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getNotificationsTurns.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getPlayerCitiesNames() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getPlayerCitiesNames.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }

    public static String handelFogOfWarRemoverButton() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.handelFogOfWarRemoverButton.getCharacter());
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

    public static void unlockTechnology(String tecName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.unlockTechnology.getCharacter());
            json.put("tecName", tecName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
             CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static String getImprovementNameOfWoorker(int i, int j) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getImprovementNameOfWoorker.getCharacter());
            json.put("i", i);
            json.put("j",j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return null;
    }
}