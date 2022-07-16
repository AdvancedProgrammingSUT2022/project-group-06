package serverapp.controllers;

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
        if (!attacker.isInPossibleCombatRange(x, y, 0, attacker.getX(), attacker.getY())) {
            return "out of sight range";
        }
        return handelCombatType(defenderCity, x, y);
    }

    private static String handelCombatType(City defenderCity, int x, int y) {
        if (UnitController.getSelectedUnit() instanceof Ranged) {
            if (UnitController.getSelectedUnit() instanceof Siege) {
                if (!((Siege) UnitController.getSelectedUnit()).isReadyToAttack()) return "siege unit is not ready";
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

    private static String meleeCivilianCombat(int x, int y) {
        return null;
    }

    private static String RangedToCivilianCombat(int x, int y) {
        return null;
    }

    private static String meleeCityCombat(City city) {
        int unitStrength = UnitController.getSelectedUnit().calculateCombatModifier(city);
        int cityStrength = city.getMeleeCombatStrength();
        //todo : saze defaie divar
        if (city.getCapital().getMilitaryUnit() != null) {
            cityStrength += city.getCapital().getMilitaryUnit().calculateCombatModifier(attacker);
        }
        city.decreaseHitPoint(unitStrength);
        UnitController.getSelectedUnit().decreaseHealth(cityStrength);
        if (UnitController.getSelectedUnit().getHealth() <= 0 && city.getHitPoint() > 0) {
            UnitController.deleteMilitaryUnit(UnitController.getSelectedUnit());
            return "you lose the battle unit is death";
        }
/*        if (city.getHitPoint() <= 0 && UnitController.getSelectedUnit().getHealth() > 0) {
            //todo : move unit to city coordinates
            GameMenu.cityCombatMenu(city, UnitController.getSelectedUnit().getOwner());
            return "done";
        }
        if (UnitController.getSelectedUnit().getHealth() <= 0 && city.getHitPoint() <= 0) {
            GameMenu.cityCombatMenu(city, UnitController.getSelectedUnit().getOwner());
            UnitController.deleteMilitaryUnit(UnitController.getSelectedUnit());
            return "unit and city are death";
        }*/
        UnitController.getSelectedUnit().setMP(0);
        return "attack is done";
    }

    private static String rangedCityCombat(City city) {
        if (city.getHitPoint() == 1) return "you can not attack to this city hit point is 1";
        int unitStrength = ((Ranged) UnitController.getSelectedUnit()).calculateRangedAttackStrength();
        //todo :saze defaie divar
        city.decreaseHitPoint(unitStrength);
        if (city.getHitPoint() < 1) {
            city.setHitPoint(1);
        }
        UnitController.getSelectedUnit().setMP(0);
        return "attack is done";
    }

    private static String meleeUnitCombat(int x, int y) {
        return "";
    }

    private static String rangedUnitCombat(int x, int y) {
        return "";
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
}
