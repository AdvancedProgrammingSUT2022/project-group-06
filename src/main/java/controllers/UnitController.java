package controllers;

import enums.HexState;
import enums.TerrainNames;
import models.Player;
import models.maprelated.Hex;
import models.units.Civilian;
import models.units.Military;
import models.units.Ranged;
import models.units.Settler;
import models.units.Unit;
import models.units.Worker;

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
        return GameController.getMilitaryByLocation(x, y) != null;
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
        return !hex[x][y].getTerrain().getName().equals("Mountain") && !hex[x][y].getTerrain().getName().equals("Ocean");
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
        setRevealedTiles();
        changeView(x, y);
        if (unit instanceof Civilian) {
            unit.getCurrentHex().setCivilianUnit(null);
            hex[x][y].setCivilianUnit((Civilian) unit);
        } else {
            unit.getCurrentHex().setMilitaryUnit(null);
            hex[x][y].setMilitaryUnit((Military) unit);
        }
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
                if (!GameController.isOutOfBounds(x + direction[i][0], y + direction[i][1])) {
                    hex[x + direction[i][0]][y + direction[i][1]].setState(HexState.Visible, currentPlayer);
                    currentPlayer.addToRevealedHexes(hex[x + direction[i][0]][y + direction[i][1]]);
                }
            }
        }
    }

    private static void setRevealedTiles() {
        for (int i = 0; i < getWorld().getHexInWidth(); i++) {
            for (int j = 0; j < getWorld().getHexInHeight(); j++) {
               // System.out.println(i + " " + j);
                if (currentPlayer == null)
                    System.out.println("cp is null");
                if (hex[i][j].getState(currentPlayer) == null)
                    System.out.println("is null");
                if (hex[i][j].getState(currentPlayer).equals(HexState.Visible)) {
                    hex[i][j].setState(HexState.Revealed, currentPlayer);
                    currentPlayer.addToRevealedHexes(hex[i][j]);
                }
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

    public static void makeUnit(String name, Hex hex) {

        switch(InitializeGameInfo.unitInfo.get(name).split(" ")[6])
        {
            case "Settler":
                Settler newSettler=new Settler(name, hex, currentPlayer);
                newSettler.build();
                break;
            case "Worker":
                Worker newWorker=new Worker(name, hex,currentPlayer);
                newWorker.build();
                break;
            case "Archery":
                Ranged newRanged=new Ranged(name, hex, currentPlayer);
                newRanged.build();    
        }
   
        
        
        
    }
}
