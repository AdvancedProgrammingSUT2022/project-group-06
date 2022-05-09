package controllers;

import enums.HexState;
import enums.TerrainNames;
import models.Player;
import models.maprelated.Hex;
import models.units.Civilian;
import models.units.Military;
import models.units.Siege;
import models.units.Unit;

import static controllers.GameController.*;

public class UnitController {

    private static Unit selectedUnit;
    private static Hex[][] hex = getWorld().getHex();
    private static Player currentPlayer = GameController.getCurrentPlayer();


    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static boolean hasMilitary(int x, int y) {
        // TODO: ask traneh why??
        return GameController.getWorld().getHex()[x][y].getMilitaryUnit() != null;
/*
        return GameController.getMilitaryByLocation(x, y) != null;
*/
    }

    public static boolean hasCivilian(int x, int y) {
        return GameController.getCiviliansByLocation(x, y) != null;
    }

    public static boolean canMove(Unit unit, Hex destination) {
        return Math.abs(unit.getCurrentHex().getX() - destination.getX() + unit.getCurrentHex().getY() - destination.getY()) == 1;
    }


    private static int[] getDirectionIndex(int[][] direction, int dx, int dy, Unit unit) {
        if (dx != 0) dx = dx / Math.abs(dx);
        if (dy != 0) dy = dy / Math.abs(dy);
        for (int[] ints : direction) {
            if (ints[0] == dx && ints[1] == dy && !isOutOfBounds(unit.getCurrentHex().getX() + ints[0], unit.getCurrentHex().getY() + ints[1]))
                return ints;
        }
        return null;
    }

    public static Hex getNextHex(int finalX, int finalY) {
        Unit unit = selectedUnit;
        int[] direction;
        int[][] oddDirection = new int[][]{{-1, 0}, {0, -1}, {1, -1}, {1, 0}, {0, 1}, {1, 1}};
        int[][] evenDirection = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, 0}, {-1, 1}, {0, 1}};
        int deltaX = finalX - unit.getCurrentHex().getX();
        int deltaY = finalY - unit.getCurrentHex().getY();

        if (unit.getCurrentHex().getY() % 2 == 0) {
            direction = getDirectionIndex(evenDirection, deltaX, deltaY, unit);
        } else {
            direction = getDirectionIndex(oddDirection, deltaX, deltaY, unit);
        }
        return hex[unit.getCurrentHex().getX() + direction[0]][unit.getCurrentHex().getY() + direction[1]];

    }

    public static boolean canMoveThrough(int x, int y) {
        return !hex[x][y].getTerrain().getName().equals("mountain") && !hex[x][y].getTerrain().getName().equals("ocean");
    }

    public static boolean isHexOccupied(int destinationX, int destinationY) {
        Unit unit = selectedUnit;
        return (unit instanceof Military && hex[destinationX][destinationY].getMilitaryUnit() != null)
                || (unit instanceof Civilian && hex[destinationX][destinationY].getCivilianUnit() != null);
    }

    public static Unit getSelectedUnit() {
        return selectedUnit;
    }

    public static void setSelectedUnit(Unit selectedUnit) {
        UnitController.selectedUnit = selectedUnit;
    }

    public static void moveUnit(Unit unit, int x, int y) {
        changeView(x, y);
        unit.changeCurrentHex(hex[x][y]);
        unit.decreaseMP(hex[x][y].getTerrain().getMovePoint());
    }

    private static void changeView(int x, int y) {
        int[][] oddDirection = new int[][]{{0, 0}, {-1, 0}, {0, -1}, {1, -1}, {1, 0}, {0, 1}, {1, 1}};
        int[][] evenDirection = new int[][]{{0, 0}, {-1, 0}, {-1, -1}, {0, -1}, {1, 0}, {-1, 1}, {0, 1}};
        int[][] direction;

        if (y % 2 == 0) direction = evenDirection;
        else direction = oddDirection;

        for (int j = 0; j < 7; j++) {
            x = x + direction[j][0];
            y = y + direction[j][0];
            for (int i = 0; i < 7; i++) {
                if (!GameController.isOutOfBounds(x + direction[i][0], y + direction[i][1]))
                    hex[x + direction[i][0]][y + direction[i][1]].setState(HexState.Visible, currentPlayer);
            }
        }
    }

    public static String constructRoad(int x, int y) {
        if (GameController.isOutOfBounds(x, y))
            return "chosen position is not valid";
        if (selectedUnit == null)
            return "you should choose a unit first";
        if (!selectedUnit.getName().equals("Worker"))
            return "you should choose a worker unit";
        if (!currentPlayer.getAchievedTechnologies().get("TheWheel"))
            return "you don't have required technology for building roads";
        if (hex[x][y].hasRoad())
            return "this hex already has road";
        if (hex[x][y].getTerrain().getName().equals(TerrainNames.Mountain))
            return "you can't construct road on mountain";
        if (hex[x][y].getTerrain().equals(TerrainNames.Ocean))
            return "you can't construct road on ocean";
        //Todo: can't build roads on ice
        //todo: construct road in 3 turns
        return "the road will be constructed in 3 turns";
    }

    public static String constructRailRoad(int x, int y) {
        if (GameController.isOutOfBounds(x, y))
            return "chosen position is not valid";
        if (!selectedUnit.getName().equals("Worker"))
            return "you should choose a worker unit";
        if (!currentPlayer.getAchievedTechnologies().get("Road"))
            return "you don't have required technology for building roads";
        if (hex[x][y].hasRailRoad())
            return "this hex already has railroad";
        if (hex[x][y].getTerrain().getName().equals(TerrainNames.Mountain))
            return "you can't construct railroad on mountain";
        if (hex[x][y].getTerrain().equals(TerrainNames.Ocean))
            return "you can't construct railroad on ocean";
        //Todo: can't build roads on ice
        //todo: construct road in 3 turns
        return "the railroad will be constructed in 3 turns";
    }
    public static String setUpSiegeForRangeAttack(){
        if(selectedUnit == null) return "you did not select a unit";
        if(! (selectedUnit instanceof Siege)) return "selected unit is not a siege";
        ((Siege)selectedUnit).setReadyToAttack(true);
        return "siege is ready now";
    }
    public static String fortify(){
        if(selectedUnit == null) return "you did not select a unit";
        if(selectedUnit.getCombatType() == "Mounted") return "a Mounted unit can not fortify";
        if(selectedUnit.getCombatType() == "Armored") return "a Armored unit can not fortify";

        return "fortified successfully";
    }
    public static String garrison(int x, int y){
        if(hex[x][y].getCapital() == null){
            System.out.println("there is no capital");
        }if(hex[x][y].getOwner() != currentPlayer){
            System.out.println("this is not your city");
        }
        return "garrisoned successfully";
    }
    public static String alert(){
        return "alerted successfully";
    }
    public static void deleteUnit(int x, int y){
        //todo: ask is any list of unit to remove
        hex[x][y].setMilitaryUnit(null);
        selectedUnit = null;
    }
    public static String sleepUnit(){
        if(selectedUnit.isSleep()){
            return "unit is already sleep";
        }
        selectedUnit.setSleep(false);
        return "successfully sleep";
    }
    public static String wakeUpUnit(){
        if(selectedUnit.isSleep()){
            selectedUnit.setSleep(false);
            return "successfully waked up";
        }
        return "unit is already awake";
    }
    public static String pillage(){
        return "pillaged successfully";
    }
}
