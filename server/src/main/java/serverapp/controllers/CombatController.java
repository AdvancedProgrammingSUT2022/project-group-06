package serverapp.controllers;

import org.json.JSONObject;
import serverapp.models.Player;
import serverapp.models.maprelated.City;
import serverapp.models.maprelated.Hex;
import serverapp.models.units.Combatable;
import serverapp.models.units.Ranged;
import serverapp.models.units.Siege;


public class CombatController {
    private static final Hex[][] hex = GameController.getWorld().getHex();
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
        JSONObject jsonObject = new JSONObject();
        City defenderCity = hex[x][y].getCapital();
        attacker = UnitController.getSelectedUnit();
        if (defenderCity != null) {
            defender = defenderCity;
        } else if (hex[x][y].getMilitaryUnit() != null) {
            defender = hex[x][y].getMilitaryUnit();
        } else if (hex[x][y].getCivilianUnit() != null) {
            defender = hex[x][y].getCivilianUnit();
        } else{
            jsonObject.put("result","there is no city or unit to attack");
            return jsonObject.toString();
        }
        if (defender.getOwner() == attacker.getOwner()){
            jsonObject.put("result","you can not attack to your self");
            return jsonObject.toString();
        }
        if (!attacker.isInPossibleCombatRange(x, y, 0, attacker.getX(), attacker.getY())) {
            jsonObject.put("result","out of sight range");
            return jsonObject.toString();
        }
        return handelCombatType(defenderCity, x, y);
    }

    private static String handelCombatType(City defenderCity, int x, int y) {
        JSONObject jsonObject = new JSONObject();
        if (UnitController.getSelectedUnit() instanceof Ranged) {
            if (UnitController.getSelectedUnit() instanceof Siege) {
                if (!((Siege) UnitController.getSelectedUnit()).isReadyToAttack()) {
                    jsonObject.put("result","siege unit is not ready");
                    return jsonObject.toString();
                }
            }
            if (defenderCity != null) {
                return rangedCityCombat(defenderCity);
            } else if (hex[x][y].getMilitaryUnit() != null) {
                return rangedUnitCombat(x, y);
            } else if (hex[x][y].getCivilianUnit() != null) {
                return RangedToCivilianCombat(x, y);
            }
        } else {
            if (defenderCity != null) {
                return meleeCityCombat(defenderCity);
            } else if (hex[x][y].getMilitaryUnit() != null) {
                return meleeUnitCombat(x, y);
            } else if (hex[x][y].getCivilianUnit() != null) {
                return meleeCivilianCombat(x, y);
            }
        }
        return null;
    }


    private static String meleeCityCombat(City city) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("combatType","meleeToCity");
        int unitStrength = UnitController.getSelectedUnit().calculateCombatModifier();
        int cityStrength = city.getMeleeCombatStrength();
        //todo : saze defaie divar
        if (city.getCapital().getMilitaryUnit() != null) {
            cityStrength += city.getCapital().getMilitaryUnit().calculateCombatModifier();
        }
        city.decreaseHitPoint(unitStrength);
        UnitController.getSelectedUnit().decreaseHealth(cityStrength);
        if (UnitController.getSelectedUnit().getHealth() <= 0 && city.getHitPoint() > 0) {
            UnitController.deleteMilitaryUnit(UnitController.getSelectedUnit());
             jsonObject.put("result","in melee to city combat you lose the battle unit is death");
            return jsonObject.toString();
        }
        if (city.getHitPoint() <= 0 && UnitController.getSelectedUnit().getHealth() > 0) {
            //todo : move unit to city coordinates
            jsonObject.put("cityName",city.getName());
            jsonObject.put("playerName",UnitController.getSelectedUnit().getOwner().getName());
            jsonObject.put("result","in melee to city combat city is death");
            return jsonObject.toString();
        }
        if (UnitController.getSelectedUnit().getHealth() <= 0
                && city.getHitPoint() <= 0) {
            jsonObject.put("cityName",city.getName());
            jsonObject.put("playerName",UnitController.getSelectedUnit().getOwner().getName());
            jsonObject.put("result","in melee to city combat unit and city are death");
            UnitController.deleteMilitaryUnit(UnitController.getSelectedUnit());
            return jsonObject.toString();
        }
        UnitController.getSelectedUnit().setMP(0);
        jsonObject.put("result","in melee to city combat  is done");
        return jsonObject.toString();
    }

    private static String rangedCityCombat(City city) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("combatType","rangedCityCombat");
        if (city.getHitPoint() == 1) {
            jsonObject.put("result","you can not attack to this city hit point is 1");
            return jsonObject.toString();
        }
        int unitStrength = ((Ranged) UnitController.getSelectedUnit()).calculateRangedAttackStrength();
        //todo :saze defaie divar
        city.decreaseHitPoint(unitStrength);
        if (city.getHitPoint() < 1) {
            city.setHitPoint(1);
        }
        UnitController.getSelectedUnit().setMP(0);
        jsonObject.put("result","attack is done");
        return jsonObject.toString();
    }

    private static String meleeUnitCombat(int x, int y) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("warAlert", true);
        return jsonObject.toString();
    }
    public static String startWar(int x, int y){
        GameController.setIsEnemy(hex[x][y].getMilitaryUnit().getName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("combatType","meleeToMilitary");
        int defenderStrength = hex[x][y].getMilitaryUnit().calculateCombatModifier();
        int attackerStrength = UnitController.getSelectedUnit().calculateCombatModifier();
        UnitController.getSelectedUnit().decreaseHealth(defenderStrength);
        hex[x][y].getMilitaryUnit().decreaseHealth(attackerStrength);

        if( UnitController.getSelectedUnit().getHealth() > 0 && hex[x][y].getMilitaryUnit().getHealth() <= 0){
            //win
            UnitController.deleteMilitaryUnit(hex[x][y].getMilitaryUnit());
            jsonObject.put("result","in melee to military combat you win the battle");
            return jsonObject.toString();
        }
        else if(UnitController.getSelectedUnit().getHealth() <= 0 && hex[x][y].getMilitaryUnit().getHealth() > 0){
            //loose
            UnitController.deleteMilitaryUnit(UnitController.getSelectedUnit());
            jsonObject.put("result","in melee to military combat you loose the battle unit is death");
            return jsonObject.toString();
        }
        else if(UnitController.getSelectedUnit().getHealth() <= 0 && hex[x][y].getMilitaryUnit().getHealth() <= 0){
            //equal
            System.out.println(x+" "+ y+" "+hex[x][y]);
            System.out.println(hex[x][y].getMilitaryUnit().getName()+" "+hex[x][y].getMilitaryUnit().getOwner().getName());
            UnitController.deleteMilitaryUnit(hex[x][y].getMilitaryUnit());
            UnitController.deleteMilitaryUnit(UnitController.getSelectedUnit());
            jsonObject.put("result","in melee to military combat both units are death");
            return jsonObject.toString();
        }
        //nothing just damage
        jsonObject.put("result","in melee to military combat no winner both are damage");
        return jsonObject.toString();
    }
    private static String rangedUnitCombat(int x, int y) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("combatType","rangedToMilitary");
        int attackerStrength = UnitController.getSelectedUnit().calculateCombatModifier();
        hex[x][y].getMilitaryUnit().decreaseHealth(attackerStrength);
        if(hex[x][y].getMilitaryUnit().getHealth() <= 0){
            //win
            UnitController.deleteMilitaryUnit(hex[x][y].getMilitaryUnit());
            jsonObject.put("result","in ranged to military combat you win the battle");
            return jsonObject.toString();
        }
        //nothing just damage
        jsonObject.put("result","in ranged to military combat no winner both are damage");
        return jsonObject.toString();
    }

    private static String meleeCivilianCombat(int x, int y) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","oooooooo");
        return jsonObject.toString();
    }

    private static String RangedToCivilianCombat(int x, int y) {
        return null;
    }
    public static String addCityToTerritory(City city, Player player) {
        //todo: check correction : yanni nemikad dige kar dige ba azafe kardan be teritory kard?
        Player looser = city.getOwner();
        city.getCapital().setOwner(player);
        for (Hex hex : city.getHexs()) {
            hex.setOwner(player);
        }
        city.setOwner(player);
        looser.removeCity(city);
        player.addCity(city);
        city.setHitPoint(0);
        GameController.getCurrentPlayer().decreaseHappiness(3);//happiness decreases due to annexed cities
        return "added successfully";
    }

    public static String addCityToTerritoryByName(String player, String city) {
        return addCityToTerritory(CityController.getCityWithName(city),
                InitializeGameInfo.getPlayerByName(player));
    }

    public static String deleteCity(String cityName) {
        City city = CityController.getCityWithName(cityName);
        assert city != null;
        return City.deleteCity(city);
    }

}
