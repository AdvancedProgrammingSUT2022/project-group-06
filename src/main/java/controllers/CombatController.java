package controllers;

import models.Player;
import models.maprelated.City;
import models.maprelated.Hex;
import models.units.Combatable;
import models.units.Ranged;
import models.units.Siege;
import models.units.Unit;
import views.GameMenu;

public class CombatController {
    private static Unit selectedUnit = UnitController.getSelectedUnit();
    private static Hex[][] hex = GameController.getWorld().getHex();
    private static Combatable attacker;
    private static Combatable defender;

    public static Hex[][] getHex() {
        return hex;
    }

    public static String attackUnit(int x, int y) {
        City defenderCity = hex[x][y].getCapital();
        attacker = selectedUnit;
        if (defenderCity != null) {
            defender = defenderCity;
        } else if (hex[x][y].getMilitaryUnit() != null) {
            defender = hex[x][y].getMilitaryUnit();
        } else if (hex[x][y].getCivilianUnit() != null) {
            defender = hex[x][y].getCivilianUnit();
        } else
            return "there is no city or unit to attack";
        if (defender.getOwner() == attacker.getOwner()) return "you can not attack to your self";

        if(!attacker.isInPossibleCombatRange(x, y,0, attacker.getX(), attacker.getY())){
            return "out of sight range";
        }
/*        attacker.attack(calculateCombatModifier());
        defender.defend(calculateCombatModifier());*/
        return  handelCombatType(defenderCity, x, y);
    }

    private static String handelCombatType(City defenderCity, int x, int y){
        if (selectedUnit instanceof Ranged) {
            if(selectedUnit instanceof Siege){
                if(!((Siege) selectedUnit).isReadyToAttack()) return "siege unit is not ready";
            }
            if (defenderCity != null) {
                return rangedCityCombat(defenderCity);
            } else if (hex[x][y].getMilitaryUnit() != null) {
                return rangedUnitCombat(x, y);
            } else if (hex[x][y].getCivilianUnit() != null) {
                return RangedToCivilianCombat(x, y);
            }
        } else {
            if (defenderCity!= null) {
                return meleeCityCombat(defenderCity);
            }
            else if(hex[x][y].getMilitaryUnit() != null) {
                return meleeUnitCombat(x, y);
            }else if(hex[x][y].getCivilianUnit() != null) {
                return meleeCivilianCombat(x, y);
            }
        }
        return null;
    }
    private static String meleeCivilianCombat(int x, int y) {
        return null;
    }

    private static String RangedToCivilianCombat(int x, int y) {
        return null;
    }

    private static String meleeCityCombat(City city) {
//        System.out.println(selectedUnit.getHealth()+" "+city.getHitPoint() );
        //handel tile train and feature
        int unitStrength = selectedUnit.calculateCombatModifier(city);
        unitStrength = (selectedUnit.isFirstFortify()) ? unitStrength*125/100 :unitStrength*150/100;
        int cityStrength = city.getHitPoint();
        //todo: assarat asib
        //todo : saze defaie divar
        if(city.getCapital().getMilitaryUnit() != null){
            cityStrength += city.getCapital().getMilitaryUnit().calculateCombatModifier(attacker);
        }
//        System.out.println(unitStrength);
        city.decreaseHitPoint(unitStrength);
        selectedUnit.decreaseHealth(city.getMeleeDefensivePower());
//        System.out.println(selectedUnit.getHealth()+" "+city.getHitPoint() );
        if (selectedUnit.getHealth() <= 0) {
            UnitController.deleteUnit(selectedUnit.getX(),selectedUnit.getY());
            return "you lose the battle unit is death";
        }
        if (cityStrength <= 0) {
            GameMenu.cityCombatMenu(city, selectedUnit.getOwner());
            return "done";
        }
        selectedUnit.setMP(0);
        return "attack is done";
    }

    private static String rangedCityCombat(City city) {
/*        System.out.println(selectedUnit.getHealth()+" "+city.getHitPoint() );*/
        if (city.getHitPoint() == 1) return "you can not attack to this city hit point is 1";
        // handel tile train and feature
        int unitStrength = selectedUnit.calculateCombatModifier(city);
        unitStrength = (selectedUnit.isFirstFortify()) ? unitStrength*125/100 :unitStrength*150/100;
        /*        System.out.println(unitStrength);*/
        //todo: assarat asib
        //todo :saze defaie divar
        city.decreaseHitPoint(unitStrength);
        if (city.getHitPoint() < 1) {
            city.setHitPoint(1);
        }
/*        System.out.println(selectedUnit.getHealth()+" "+city.getHitPoint() );*/
        selectedUnit.setMP(0);
        return "attack is done";
    }

    private static String meleeUnitCombat(int x, int y) {
        return "";
    }

    private static String rangedUnitCombat(int x, int y) {
        return "";
    }

    public static void addCityToTerritory(City city, Player player) {
        city.setOwner(player);
        //todo: check correction : yanni nemikad dige kar dige ba azafe kardan be teritory kard?
        //todo : ask if any list of city for player
        city.setHitPoint(0);
    }
}
