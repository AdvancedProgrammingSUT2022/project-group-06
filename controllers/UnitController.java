package controllers;

import models.maprelated.Hex;
import models.units.Unit;

public class UnitController {
    public static boolean hasMilitary(int x, int y) {
        return GameController.getMilitaryByLocation(x, y) != null;
    }

    public static boolean hasCivilian(int x, int y) {
        return GameController.getCiviliansByLocation(x, y) != null;
    }

    public static boolean canMove(Unit unit, Hex destination) {
        return Math.abs(unit.getCurrentHex().getX() - destination.getX() + unit.getCurrentHex().getY() - destination.getY()) == 1;
    }

}
