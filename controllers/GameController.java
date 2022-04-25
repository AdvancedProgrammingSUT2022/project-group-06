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

public class GameController {

    private static ArrayList<String> players = new ArrayList<String>();
    private static int turn;
    private static World world;
    private static int[] mapBoundaries;
    private static Hex[][] hex;
    private static Player loggedInPlayer;
    private static ArrayList<Civilian> allCivilians;
    private static ArrayList<Military> allMilitaries;
    private static Unit selectedUnit;

    public static ArrayList<String> getPlayers() {
        return players;
    }

    public static void initializeGameController() {
        world = InitializeGameInfo.getWorld();
        turn = 0;
        hex = world.getHex();
        mapBoundaries = new int[]{0, 3, 0, 6};
    }

    public static String printAllWorld() {
        StringBuilder stringWorld = new StringBuilder();
        initializeString(new int[]{0, 9, 0, 9}, world.getString());
        for (int i = 0; i < world.getWorldHeight(); i++) {
            for (int j = 0; j < world.getWorldWidth(); j++) {
                stringWorld.append(world.getString()[i][j]);
            }
            stringWorld.append("\n");
        }
        return (stringWorld.toString());
    }

    public static String printWorld() {
        StringBuilder stringWorld = new StringBuilder();
        initializeString(mapBoundaries, world.getString());
        for (int i = (mapBoundaries[0]) * 6; i < (mapBoundaries[1]) * 6 + 3; i++) {
            for (int j = (mapBoundaries[2]) * 10; j < (mapBoundaries[3]) * 11 + 1; j++) {
                stringWorld.append(world.getString()[i][j]);
            }
            stringWorld.append("\n");
        }
        return (stringWorld.toString());
    }

    public static String moveMap(String direction, int amount) {
        int[] directions = new int[4];
        switch (direction) {
            case "right":
                directions = new int[]{0, 0, amount, amount};
                break;
            case "left":
                directions = new int[]{0, 0, -amount, -amount};
                break;
            case "up":
                directions = new int[]{-amount, -amount, 0, 0};
                break;
            case "down":
                directions = new int[]{amount, amount, 0, 0};
                break;
        }
        if (mapBoundaries[0] + directions[0] < 0 || mapBoundaries[1] + directions[1] > world.getHexInHeight() ||
                mapBoundaries[2] + directions[2] < 0 || mapBoundaries[3] + directions[3] > world.getHexInWidth()) {
            return "you can not go out of bounds";
        }
        for (int i = 0; i < 4; i++) {
            mapBoundaries[i] += directions[i];
        }
        return printWorld();
    }

    public static String showPosition(int x, int y) {
        if (x < 0 || y < 0 || x >= world.getHexInHeight() || y >= world.getHexInWidth()) {
            return "oops invalid cell!";
        }
        int maxX = x, maxY = y, minY = y, minX = x;
        while (maxX - minX < 3) {
            if (maxX < world.getHexInHeight()) maxX++;
            if (minX > 0 && maxX - minX < 3) minX--;
        }
        while (maxY - minY < 6) {
            if (maxY < world.getHexInWidth()) maxY++;
            if (minY > 0) minY--;
        }
        mapBoundaries = new int[]{minX, maxX, minY, maxY};
        return printWorld();
    }

    private static void drawHexDetails(int align, int minI, int minJ, String[][] string, Hex hex, String color) {
        //string[minI + 1 + align][minJ + 6] = color + "\033[0;33m" + "A" + Color.ANSI_RESET.getCharacter();
        if (hex.getOwner() != null)
            string[minI + 1 + align][minJ + 6] = color + "\033[0;33m" + hex.getOwner().getName() + Color.ANSI_RESET.getCharacter();
        if (hex.getCivilianUnit() != null) {
            Color unitColor = InitializeGameInfo.getPlayerColor().get(hex.getCivilianUnit().getOwner().getName());
            string[minI + 3 + align][minJ + 4] = color + unitColor.getCharacter() + "C" + Color.ANSI_RESET.getCharacter();
        }
        if (hex.getMilitaryUnit() != null) {
            Color unitColor = InitializeGameInfo.getPlayerColor().get(hex.getMilitaryUnit().getOwner().getName());
            string[minI + 3 + align][minJ + 8] = color + unitColor.getCharacter() + "M" + Color.ANSI_RESET.getCharacter();
        }
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
                } else {
                    string[i + align][j] = color + " " + Color.ANSI_RESET.getCharacter();
                    drawHexDetails(align, hexHeight * x, (hexWidth - 2) * y, string, hex, color);
                }
            }
        }
    }

    private static void drawFogOfWar(Hex hex) {
        String[][] string = world.getString();
        int hexHeight = 6, hexWidth = 12;
        int x = hex.getX(), y = hex.getY();
        String color = "\u001b[48;5;243m";
        int align = y % 2 == 1 ? 3 : 0;
        for (int i = hexHeight * x; i < hexHeight * (x + 1); i++) {
            int k = i % hexHeight < 3 ? Math.abs((i % hexHeight) - 2) : Math.abs((i % hexHeight) - 3);
            for (int j = (hexWidth - 2) * y + k; j < (hexWidth - 2) * y + hexWidth - k + 1; j++) {
                if ((j == (hexWidth - 2) * y + k && i % hexHeight < 3) || (j == (hexWidth - 2) * y + hexWidth - k && i % 6 >= 3)) {
                    string[i + align][j] = "/";
                } else if ((j == (hexWidth - 2) * y + k && i % 6 >= 3) || (j == (hexWidth - 2) * y + hexWidth - k & i % 6 < 3)) {
                    string[i + align][j] = "\\";
                } else if (i == hexHeight * (x + 1) - 1) {
                    string[i + align][j] = color + "_" + Color.ANSI_RESET.getCharacter();
                } else string[i + align][j] = color + " " + Color.ANSI_RESET.getCharacter();
            }
        }
    }

    private static void initializeString(int[] mapBoundaries, String[][] string) {
        for (int i = 0; i < world.getWorldHeight(); i++) {
            for (int j = 0; j < world.getWorldWidth(); j++) {
                string[i][j] = " ";
            }
        }
        for (int n = mapBoundaries[0]; n < mapBoundaries[1]; n++) {
            for (int m = mapBoundaries[2]; m < mapBoundaries[3]; m++) {
                if (hex[n][m].getState() == HexState.Visible) drawHex(hex[n][m]);
                else drawFogOfWar(hex[n][m]);
            }
        }
    }

    public static String showHexDetails(int x, int y) {
        if (x < 0 || y < 0 || x > world.getWorldHeight() || y > world.getWorldWidth()) {
            return "invalid cell";
        }
        Hex hex = world.getHex()[x][y];
        StringBuilder detail = new StringBuilder();
        detail.append("terrain: ").append(hex.getTerrain().getName());
        if (hex.getResource() != null) {
            detail.append(" resource: ").append(hex.getResource().getName());
        }
        if (hex.getFeature() != null) {
            detail.append(" feature: ").append(hex.getFeature().getName());
        }
        if (hex.getImprovement() != null) {
            detail.append(" improvement: ").append(hex.getImprovement());
        }
        if (hex.getCivilianUnit() != null) {
            detail.append(" Civilian Unit: " + hex.getCivilianUnit().getName());
        }
        if (hex.getMilitaryUnit() != null) {
            detail.append(" Military Unit: " + hex.getMilitaryUnit().getName());
        }
        detail.append(" x,y:").append(x).append(" ").append(y);
        return detail.toString();
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
        GameController.selectedUnit = selectedUnit;
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
                    hex[x + direction[i][0]][y + direction[i][1]].setState(HexState.Visible);
            }
        }
    }
}
