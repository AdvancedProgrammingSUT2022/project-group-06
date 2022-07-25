package serverapp.views;

import org.json.JSONObject;
import serverapp.controllers.CityController;
import serverapp.controllers.GameController;
import serverapp.controllers.UserController;
import serverapp.enums.Actions;
import serverapp.models.maprelated.City;

public class Cheat {
    public static String run(String command, JSONObject jsonObject) {
        if(command.equals(Actions.cheatCityProduction.getCharacter())){
            return GameController.cheatCityProduction(jsonObject.getInt("amount"),jsonObject.getString("cityName"));
        } if(command.equals(Actions.cheatGold.getCharacter())){
            return GameController.cheatGold(jsonObject.getInt("amount"));
        }if(command.equals(Actions.cheatPopulation.getCharacter())){
            return GameController.cheatPopulation(jsonObject.getInt("amount"));
        }if(command.equals(Actions.cheatHappiness.getCharacter())){
            return GameController.cheatHappiness(jsonObject.getInt("amount"));
        }if(command.equals(Actions.cheatProduction.getCharacter())){
            return GameController.cheatProduction(jsonObject.getInt("amount"));
        }if(command.equals(Actions.cheatScore.getCharacter())){
            return GameController.cheatScore(jsonObject.getInt("amount"));
        }if(command.equals(Actions.cheatMP.getCharacter())){
            return GameController.cheatMP(jsonObject.getInt("amount"),
                    jsonObject.getInt("x"),jsonObject.getInt("y"),jsonObject.getString("type"));
        }if(command.equals(Actions.cheatRangedCombatStrength.getCharacter())){
            return GameController.cheatRangedCombatStrength(jsonObject.getInt("amount"),jsonObject.getString("cityName"));
        }if(command.equals(Actions.cheatMeleeCombatStrength.getCharacter())){
            return GameController.cheatMeleeCombatStrength(jsonObject.getInt("amount"),jsonObject.getString("cityName"));
        }if(command.equals(Actions.cheatCityFood.getCharacter())){
            return GameController.cheatCityFood(jsonObject.getInt("amount"),jsonObject.getString("cityName"));
        }if(command.equals(Actions.cheatCityHitPoint.getCharacter())){
            return GameController.cheatCityHitPoint(jsonObject.getInt("amount"),jsonObject.getString("cityName"));
        }if(command.equals(Actions.cheatTrophy.getCharacter())){
            return GameController.cheatTrophy(jsonObject.getInt("amount"));
        }
        return "bad request format";
    }
}