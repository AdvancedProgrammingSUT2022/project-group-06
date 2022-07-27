package project.civilization.controllers;
import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.*;
import java.io.IOException;

public class UnitController {

    public static String getUnitHealth()
    {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getHealth.getCharacter());
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
    public static void increaseHealth(int amount)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.increaseHealth.getCharacter());
            json.put("amount", amount);

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
    public static String setUpSiegeForRangeAttack() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.SETUPFORRANGEATTACK.getCharacter());
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

    public static String fortify() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.FORTIFY.getCharacter());
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

    public static String fortifyUtilHeal() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.FORTIFYUNTILLHEAL.getCharacter());
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

    public static String garrison() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GARRISON.getCharacter());
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

    public static String alert() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.ALERT.getCharacter());
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

    public static String deleteUnitAction() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.DELETEUNIT.getCharacter());
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

    public static String sleepUnit() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.SLEEPUNIT.getCharacter());
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

    public static String wakeUpUnit() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.WAKEDUP.getCharacter());
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

    public static String pillage() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.PILLAGE.getCharacter());
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

    public static String startMovement(int i, int j) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.STARTMOVEMENT.getCharacter());
            json.put("i", i);
            json.put("j", j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            JSONObject jsonObject = new JSONObject(res);
            if(jsonObject.has("had ruins")){
                CivilizationApplication.mapPageController.activateRuin(
                        jsonObject.getInt("had ruins"),jsonObject);
            }
            return jsonObject.getString("movement result");
        } catch (IOException x) {
            x.printStackTrace();
            return "something is wrong";
        }
    }


    public static String isMyUnit(String type, int i , int j){
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.ISMYUNIT.getCharacter());
            json.put("i", i);
            json.put("j",j);
            json.put("type",type);
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

    

    public static void setSelectedUnit(String type, int i, int j) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.SELECTUNIT.getCharacter());
            json.put("i", i);
            json.put("j",j);
            json.put("type",type);
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
    
}