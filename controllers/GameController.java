package controllers;

import models.Player;
import models.maprelated.Hex;
import models.maprelated.World;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class GameController {
    public static ArrayList<String> players = new ArrayList<String>();
    private int turn;
    private static final World world = new World();
    private static final Hex[][] hex = world.getHex();
    private static Player loggedInPlayer;
    private static ArrayList<Civilian> allCivilians;
    private static ArrayList<Military> allMilitaries;
    private static Unit selectedUnit;

    public static String printWorld() {
        StringBuilder stringWorld = new StringBuilder();
        for (int i = 0; i < world.getWorldHeight(); i++) {
            for (int j = 0; j < world.getWorldWidth(); j++) {
                stringWorld.append(world.getString()[i][j]);
            }
            stringWorld.append("\n");
        }
        return (stringWorld.toString());
    }

    public static boolean isPositionValid(int x, int y) {
        return x >= 0 && y >= 0 && x <= 10 && y <= 10;
    }

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player player) {
        loggedInPlayer = player;
    }

    public static Military getPlayerMilitaryByLocation(int x, int y) {
        List<Military> militaries = loggedInPlayer.getMilitaries();
        for (Military military : militaries) {
            if (military.getCurrentHex().getX() == x && military.getCurrentHex().getY() == y)
                return military;
        }
        return null;
    }

    public static Civilian getPlayerCiviliansByLocation(int x, int y) {
        List<Civilian> civilians = loggedInPlayer.getCivilians();
        for (Civilian civilian : civilians) {
            if (civilian.getCurrentHex().getX() == x && civilian.getCurrentHex().getY() == y)
                return civilian;
        }
        return null;
    }

    public static ArrayList<Civilian> getAllCivilians() {
        return allCivilians;
    }

    public static ArrayList<Military> getAllMilitaries() {
        return allMilitaries;
    }

    public static void addMilitary(Military military) {
        allMilitaries.add(military);
    }

    public static void addCivilian(Civilian civilian) {
        allCivilians.add(civilian);
    }

    public static void removeMilitary(Military military) {
        allMilitaries.remove(military);
    }

    public static void removeCivilian(Civilian civilian) {
        allCivilians.remove(civilian);
    }

    public static Military getMilitaryByLocation(int x, int y) {
        List<Military> militaries = allMilitaries;
        for (Military military : militaries) {
            if (military.getCurrentHex().getX() == x && military.getCurrentHex().getY() == y)
                return military;
        }
        return null;
    }

    public static Civilian getCiviliansByLocation(int x, int y) {
        List<Civilian> civilians = allCivilians;
        for (Civilian civilian : civilians) {
            if (civilian.getCurrentHex().getX() == x && civilian.getCurrentHex().getY() == y)
                return civilian;
        }
        return null;
    }

    public static boolean isDistanceValid(Unit unit, int x, int y) {
        return unit.getMaxDistance() > abs(unit.getCurrentHex().getX() - hex[x][y].getX()) + abs(unit.getCurrentHex().getY() - hex[x][y].getY());
    }

    public static boolean isHexOccupied(Unit unit, int destinationX, int destinationY) {
        return (unit instanceof Military && hex[destinationX][destinationY].getMilitaryUnit() != null)
                || (unit instanceof Civilian && hex[destinationX][destinationY].getCivilianUnit() != null);
    }

    public static Unit getSelectedUnit() {
        return selectedUnit;
    }

    public static void setSelectedUnit(Unit selectedUnit) {
        GameController.selectedUnit = selectedUnit;
    }

    public static void moveUnit(Unit unit, int x, int y) {
        unit.changeCurrentHex(hex[x][y]);
    }

}
