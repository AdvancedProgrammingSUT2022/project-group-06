package controllers;

import models.Player;
import models.maprelated.City;
import models.maprelated.Hex;
import models.units.*;
import views.GameMenu;

public class CombatController {
    private static final Hex[][] hex = GameController.getWorld().getHex();
    private static Combatable attacker;
    private static Combatable defender;

    public static Hex[][] getHex() {
        return hex;
    }

    public static String attackCity(int x, int y) {
        if( GameController.getSelectedCity() == null) return "first select a city";
        if (hex[x][y].getMilitaryUnit() == null){
            return ("there is not a military unit");
        }
        if (hex[x][y].getMilitaryUnit().getOwner() == GameController.getCurrentPlayer()){
            return ("am i joke to you? attack our self?");
        }
        if(! GameController.getSelectedCity().isInPossibleCombatRange(x, y, 0, GameController.getSelectedCity().getX(), GameController.getSelectedCity().getY())) {
            return "out of range";
        }
        //System.out.println( GameController.getSelectedCity().getHitPoint()+" "+  GameController.getSelectedCity().getRangedCombatStrength());
        //System.out.println(hex[x][y].getMilitaryUnit().getHealth());
        int temp =  GameController.getSelectedCity().getRangedCombatStrength();
        temp -=  hex[x][y].getMilitaryUnit().getDefenciveBounes();
        hex[x][y].getMilitaryUnit().decreaseHealth(temp);
        //System.out.println(hex[x][y].getMilitaryUnit().getHealth());
        if(hex[x][y].getMilitaryUnit().getHealth()<=0){
            UnitController.deleteMilitaryUnit(hex[x][y].getMilitaryUnit());
            return "city win";
        }
        return "attacked successfully";
    }
    public static String attackUnit(int x, int y) {
        City defenderCity = hex[x][y].getCapital();
        attacker = UnitController.getSelectedUnit();
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
        if (UnitController.getSelectedUnit() instanceof Ranged) {
            if(UnitController.getSelectedUnit() instanceof Siege){
                if(!((Siege) UnitController.getSelectedUnit()).isReadyToAttack()) return "siege unit is not ready";
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
        //Military temp = new Military("Scout",hex[0][0],GameController.getCurrentPlayer());
        //hex[0][0].setMilitaryUnit(temp);
        //System.out.println("meleCombat"+UnitController.getSelectedUnit().getHealth()+" "+city.getHitPoint() );
        //handel tile train and feature
        int unitStrength = UnitController.getSelectedUnit().calculateCombatModifier(city);
        int cityStrength = city.getMeleeCombatStrength();
        //todo : saze defaie divar
        if(city.getCapital().getMilitaryUnit() != null){
            //System.out.println("ll"+city.getCapital().getMilitaryUnit().calculateCombatModifier(attacker));
            cityStrength += city.getCapital().getMilitaryUnit().calculateCombatModifier(attacker);
        }
        //System.out.println(cityStrength+ " "+unitStrength);
        city.decreaseHitPoint(unitStrength);
        UnitController.getSelectedUnit().decreaseHealth(cityStrength);
        //System.out.println(UnitController.getSelectedUnit().getHealth()+" "+city.getHitPoint() );
        if (UnitController.getSelectedUnit().getHealth() <= 0) {
            UnitController.deleteMilitaryUnit(UnitController.getSelectedUnit());
            return "you lose the battle unit is death";
        }
        if (city.getHitPoint() <= 0) {
            //todo : move unit to city coordinates
            GameMenu.cityCombatMenu(city, UnitController.getSelectedUnit().getOwner());
            return "done";
        }
        UnitController.getSelectedUnit().setMP(0);
        return "attack is done";
    }

    private static String rangedCityCombat(City city) {
        //System.out.println(UnitController.getSelectedUnit().getHealth()+" "+city.getHitPoint() );
        if (city.getHitPoint() == 1) return "you can not attack to this city hit point is 1";
        // handel tile train and feature
        int unitStrength = ((Ranged)UnitController.getSelectedUnit()).calculateRangedAttackStrength();
         //System.out.println(unitStrength);
        //todo :saze defaie divar
        city.decreaseHitPoint(unitStrength);
        if (city.getHitPoint() < 1) {
            city.setHitPoint(1);
        }
        //System.out.println(UnitController.getSelectedUnit().getHealth()+" "+city.getHitPoint() );
        UnitController.getSelectedUnit().setMP(0);
        return "attack is done";
    }

    private static String meleeUnitCombat(int x, int y) {
        return "";
    }

    private static String rangedUnitCombat(int x, int y) {
        return "";
    }

    public static void addCityToTerritory(City city, Player player) {
        //todo: check correction : yanni nemikad dige kar dige ba azafe kardan be teritory kard?
        Player looser = city.getOwner();
        city.setOwner(player);
        looser.removeCity(city);
        player.addCity(city);
        city.setHitPoint(0);
        GameController.getCurrentPlayer().decreaseHappiness(3);//happiness decreases due to annexed cities
    }
}
