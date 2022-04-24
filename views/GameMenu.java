package views;

import controllers.InitializeGameInfo;
import controllers.GameController;
import controllers.UnitController;
import models.maprelated.Hex;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {

    public void run(Scanner scanner) {
        InitializeGameInfo.run();
        GameController.GameControllerset();
        System.out.println(GameController.printWorld());
        String command = scanner.nextLine();
        Matcher matcher;
        while (true) {
            if (command.equals("show map")) {
                System.out.println(GameController.printWorld());
            } else if ((matcher = getMatcher("select combat (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                selectMilitary(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            }
            else if ((matcher = getMatcher("select noncombat (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                selectCivilian(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            }
            else if ((matcher = getMatcher("move to (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                moveUnitView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            }
            else if (command.equals("exit menu"))
                break;
            else
                System.out.println("invalid command!");
            command = scanner.nextLine();
        }
    }

    private static void selectMilitary(int x, int y) {
        if (!GameController.isPositionValid(x, y))
            System.out.println("Entered position is not valid");
        else if (!UnitController.hasMilitary(x, y))
            System.out.println("There is no military unit in this hex");
        else {
            GameController.setSelectedUnit(GameController.getMilitaryByLocation(x, y));
            System.out.println("The military unit was selected successfully");
        }
    }

    private static void selectCivilian(int x, int y) {
        if (!GameController.isPositionValid(x, y))
            System.out.println("Entered position is not valid");
        else if (!UnitController.hasCivilian(x, y))
            System.out.println("There is no civilian unit in this hex");
        else {
            GameController.setSelectedUnit(GameController.getCiviliansByLocation(x, y));
            System.out.println("The Civilian unit was selected successfully");
        }
    }


    private static void moveUnitView(int x, int y) {
        if (GameController.getSelectedUnit() == null) {
            System.out.println("You should choose a unit first");
        } else if (GameController.getPlayerCiviliansByLocation(GameController.getSelectedUnit().getCurrentHex().getX(), GameController.getSelectedUnit().getCurrentHex().getY()) == null
                && GameController.getPlayerMilitaryByLocation(GameController.getSelectedUnit().getCurrentHex().getX(), GameController.getSelectedUnit().getCurrentHex().getY()) == null)
            System.out.println("You don't own this unit");
        else if (GameController.isHexOccupied(x, y))
            System.out.println("This hex already has a unit of this type");
        else {
            Hex nextHex = GameController.getNextHex(x, y);
            for (int i = 0; i < GameController.getSelectedUnit().getMaxDistance(); i++) {
                if (nextHex.getX() == x && nextHex.getY() == y) {
                    System.out.println("The unit reached chosen destination");
                    break;
                }
                if (!GameController.canMoveThrough(nextHex.getX(), nextHex.getY())) {
                    System.out.print("The unit can't move through this hex");
                    break;
                }
                GameController.moveUnit(GameController.getSelectedUnit(), x, y);
                nextHex = GameController.getNextHex(x, y);
            }

        }

    }
}
