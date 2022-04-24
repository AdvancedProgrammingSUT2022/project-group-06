package controllers;

import enums.Color;
import enums.HexState;
import models.Player;
import models.maprelated.Hex;
import models.maprelated.World;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;

public class GameController {
    public static ArrayList<String> players = new ArrayList<String>();
    private static int turn;
    private static World world;
    private static Hex[][] hex;
    private static Player loggedInPlayer;
    private static ArrayList<Civilian> allCivilians;
    private static ArrayList<Military> allMilitaries;
    private static Unit selectedUnit;
    public static void GameControllerset(){
       world = InitializeGameInfo.getWorld();
       turn = 0;
       hex = world.getHex();
    }
    public static String printWorld() {
        StringBuilder stringWorld = new StringBuilder();
        initializeString(world.getHexInHeight(), world.getHexInWidth(), world.getString());
        for (int i = 0; i < world.getWorldHeight(); i++) {
            for (int j = 0; j < world.getWorldWidth(); j++) {
                stringWorld.append(world.getString()[i][j]);
            }
            stringWorld.append("\n");
        }
        return (stringWorld.toString());
    }

    private static void drawHexDetails(int align, int minI, int minJ, String[][] string, Hex hex, String color) {
        //string[minI + 1 + align][minJ + 6] = color + "\033[0;33m" + "A" + Color.ANSI_RESET.getCharacter();
        if (hex.getOwner() != null)
            string[minI + 1 + align][minJ + 6] = color + "\033[0;33m" + hex.getOwner().getName() + Color.ANSI_RESET.getCharacter();
        string[minI + 3 + align][minJ + 4] = color + "s" + Color.ANSI_RESET.getCharacter();
        string[minI + 3 + align][minJ + 8] = color + "B" + Color.ANSI_RESET.getCharacter();
        string[minI + 2 + align][minJ + 4] = color + hex.getX() % 10 + Color.ANSI_RESET.getCharacter();
        string[minI + 2 + align][minJ + 8] = color + hex.getY() % 10 + Color.ANSI_RESET.getCharacter();
        if (hex.getFeature() != null) {
            if (Objects.equals(hex.getFeature().getName(), "FoodPlains")) {
                string[minI + 4 + align][minJ + 6] = color + "F" + Color.ANSI_RESET.getCharacter();
                string[minI + 4 + align][minJ + 7] = color + "P" + Color.ANSI_RESET.getCharacter();
            } else
                string[minI + 4 + align][minJ + 6] = color + hex.getFeature().getName().charAt(0) + Color.ANSI_RESET.getCharacter();
        }
    }

    public static void drawHex(Hex hex) {
        String[][] string = world.getString();
        String color = InitializeGameInfo.terrainColors.get(hex.getTerrain().getName()).getCharacter();
        int hexHeight = 6, hexWidth = 12;
        int x = hex.getX(), y = hex.getY();
        int align = y % 2 == 1 ? 3 : 0;
        for (int i = hexHeight * x; i < hexHeight * (x + 1); i++) {
            int k = i % hexHeight < 3 ? Math.abs((i % hexHeight) - 2) : Math.abs((i % hexHeight) - 3);
            for (int j = (hexWidth - 2) * y + k; j < (hexWidth - 2) * y + hexWidth - k + 1; j++) {
                if ((j == (hexWidth - 2) * y + k && i % hexHeight < 3)) {
                    string[i + align][j] = !hex.isRiver(0) ? "/" : Color.ANSI_Bright_BLUE_BACKGROUND.getCharacter() + "/" + Color.ANSI_RESET.getCharacter();
                } else if (j == (hexWidth - 2) * y + hexWidth - k && i % 6 >= 3) {
                    string[i + align][j] = !hex.isRiver(3) ? "/" : Color.ANSI_Bright_BLUE_BACKGROUND.getCharacter() + "/" + Color.ANSI_RESET.getCharacter();
                } else if (j == (hexWidth - 2) * y + k && i % 6 >= 3) {
                    string[i + align][j] = !hex.isRiver(1) ? "\\" : Color.ANSI_Bright_BLUE_BACKGROUND.getCharacter() + "\\" + Color.ANSI_RESET.getCharacter();
                } else if (j == (hexWidth - 2) * y + hexWidth - k & i % 6 < 3) {
                    string[i + align][j] = !hex.isRiver(2) ? "\\" : Color.ANSI_Bright_BLUE_BACKGROUND.getCharacter() + "\\" + Color.ANSI_RESET.getCharacter();
                } else if (i == hexHeight * (x + 1) - 1) {
                    string[i + align][j] = color + "_" + Color.ANSI_RESET.getCharacter();
                } else if (hex.getState() == HexState.Visible) {
                    string[i + align][j] = color + " " + Color.ANSI_RESET.getCharacter();
                    drawHexDetails(align, hexHeight * x, (hexWidth - 2) * y, string, hex, color);
                } else {
                    string[i + align][j] = color + " " + Color.ANSI_RESET.getCharacter();
                    drawHexDetails(align, hexHeight * x, (hexWidth - 2) * y, string, hex, color);
                    //string[i + align][j] = "\u001b[48;5;243m"+ " " + Color.ANSI_RESET.getCharacter();
                }
            }
        }
    }

    private static void initializeString(int hexInHeight, int hexInWidth, String[][] string) {
        for (int i = 0; i < world.getWorldHeight(); i++) {
            for (int j = 0; j < world.getWorldWidth(); j++) {
                string[i][j] = " ";
            }
        }
        for (int n = 0; n < hexInHeight; n++) {
            for (int m = 0; m < hexInWidth; m++) {
                drawHex(hex[n][m]);
            }
        }
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


    public static Hex getNextHex(int finalX, int finalY) {
        Unit unit = selectedUnit;
        int deltaX = finalX - unit.getCurrentHex().getX();
        int deltaY = finalY - unit.getCurrentHex().getY();
        if (deltaX < 0 && deltaY < 0 && isPositionValid(unit.getCurrentHex().getX() - 1, unit.getCurrentHex().getY() - 1))
            return hex[unit.getCurrentHex().getX() - 1][unit.getCurrentHex().getY() - 1];//bala chap
        else if (deltaX < 0 && deltaY > 0 && isPositionValid(unit.getCurrentHex().getX() - 1, unit.getCurrentHex().getY() + 1))
            return hex[unit.getCurrentHex().getX() - 1][unit.getCurrentHex().getY() + 1];//bala rast
        else if (deltaX < 0 && isPositionValid(unit.getCurrentHex().getX() - 1, unit.getCurrentHex().getY()))
            return hex[unit.getCurrentHex().getX() - 1][unit.getCurrentHex().getY()];//bala
        else if (deltaY == 0 && isPositionValid(unit.getCurrentHex().getX() + 1, unit.getCurrentHex().getY()))
            return hex[unit.getCurrentHex().getX() + 1][unit.getCurrentHex().getY()];//paeen
        else if (deltaY > 0 && isPositionValid(unit.getCurrentHex().getX(), unit.getCurrentHex().getY() + 1))
            return hex[unit.getCurrentHex().getX()][unit.getCurrentHex().getY() + 1];//paeen rast
        else
            return hex[unit.getCurrentHex().getX()][unit.getCurrentHex().getY() - 1];//paeen chap
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
        GameController.selectedUnit = selectedUnit;
    }

    public static void moveUnit(Unit unit, int x, int y) {
        unit.changeCurrentHex(hex[x][y]);
        unit.decreaseMP(hex[x][y].getTerrain().getMovePoint());
    }

}
