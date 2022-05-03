package controllers;

import enums.HexState;
import models.Player;
import models.maprelated.Hex;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;

import static controllers.GameController.getWorld;
import static controllers.GameController.isPositionValid;

public class UnitController {

    private static Unit selectedUnit;
    private static Hex[][] hex = getWorld().getHex();
    private static Player currentPlayer;


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
            if (ints[0] == dx && ints[1] == dy && isPositionValid(unit.getCurrentHex().getX() + ints[0], unit.getCurrentHex().getY() + ints[1]))
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
                if (isPositionValid(x + direction[i][0], y + direction[i][1]))
                    hex[x + direction[i][0]][y + direction[i][1]].setState(HexState.Visible, currentPlayer);
            }
        }
    }
}
