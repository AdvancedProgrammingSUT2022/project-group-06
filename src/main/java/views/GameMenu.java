package views;

import controllers.*;
import models.Player;
import models.gainable.TimeVariantProcess;
import models.maprelated.City;
import models.maprelated.Hex;
import models.units.Civilian;

import static org.mockito.ArgumentMatchers.matches;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {
    private static Scanner scanner;

    public void run(Scanner gameScanner) {
        scanner = gameScanner;

        InitializeGameInfo.run();
        GameController.initializeGameController();
        System.out.println(GameController.printWorld());
        String command = scanner.nextLine();
        Matcher matcher;

        while (true) {
            if ((matcher = getMatcher("city build (--cityname|-cn) (?<name>[a-zA-Z_ ]+)", command)) != null) {
                System.out.println(CityController.buildCity(matcher.group("name")));
            } else if (command.equals("shit")) {
                System.out.println(GameController.getCurrentPlayer().getName());
            } else if (command.equals("farm build")) {
                System.out.println(GameController.startBuildFarm());
            } else if (command.equals("mine build")) {
                System.out.println(GameController.startBuildMine());
            } else if (command.equals("economic overview")) {
                System.out.println(GameController.economicOverview());
            } else if (command.equals("notification history")) {
                System.out.println(GameController.notificationHistory());
            } else if (command.equals("unit list panel")) {
                System.out.println(GameController.unitsPanel());
            } else if (command.equals("city banner")) {
                System.out.println(CityController.cityBanner());
            } else if (command.equals("city list panel")) {
                System.out.println(GameController.citiesPanel());
            } else if (command.equals("show research menu")) {
                System.out.println(GameController.showResearchMenu());
            } else if ((matcher = getMatcher("increase (--gold|-g) (?<amount>\\d+)", command)) != null) {
                System.out.println(GameController.cheatGold(Integer.parseInt(matcher.group("amount"))));
            } else if ((matcher = getMatcher("increase (--turn|-t) (?<amount>\\d+)", command)) != null) {
                System.out.println(GameController.cheatTurn((Integer.parseInt(matcher.group("amount")))));
            } else if (command.equals("city show trophies")) {
                System.out.println(CityController.showTrophies());
            } else if (command.equals("city show resources")) {
                System.out.println(CityController.showResources());
            } else if ((matcher = getMatcher("tile select (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                System.out.println(CityController.selectHex(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if ((matcher = getMatcher("city select (--cityname|-cn) (?<cityname>[a-zA-Z_ ]+)", command)) != null) {
                System.out.println(CityController.selectCity(matcher.group("cityname")));
            } else if (command.equals("next turn")) {
                System.out.println(GameController.changeTurn());
                GameController.checkTimeVariantProcesses();
            } else if ((matcher = getMatcher("buy tile", command)) != null) {
                System.out.println(buyTile(matcher));
            } else if ((matcher = getMatcher("unit make (--unittype|-ut) (?<unittype>Civilian||Military) (--unitname|-un) (?<unitname>[a-zA-Z]+)", command)) != null) {
                System.out.println(CityController.startMakingUnit(matcher.group("unittype"), matcher.group("unitname")));
            } else if (command.equals("show all map")) {
                System.out.println(GameController.printAllWorld());
            } else if ((matcher = getMatcher("map show details (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                System.out.println(GameController.showHexDetails(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if ((matcher = getMatcher("map move (?<direction>(right|left|up|down)) (-c)(?<amount>\\d+)", command)) != null) {
                System.out.println(GameController.moveMap(matcher.group("direction"), Integer.parseInt(matcher.group("amount"))));
            } else if ((matcher = getMatcher("map show (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                System.out.println(GameController.showPosition(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if ((matcher = getMatcher("map show (--cityName|-c) (?<cityName>\\.+)", command)) != null) {
                System.out.println("city");
            } else if ((matcher = getMatcher("select combat (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                selectMilitary(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            } else if ((matcher = getMatcher("select noncombat (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                selectCivilian(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            } else if ((matcher = getMatcher("move to (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                moveUnitView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            } else if ((matcher = getMatcher("attack (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                attackUnitView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            } else if ((matcher = getMatcher("Remove citizen at (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+) from work", command)) != null) {
                System.out.println(CityController.removeCitizenFromWork(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if ((matcher = getMatcher("lock an citizen to (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                System.out.println(CityController.lockCitizenTo(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if (command.equals("show unemployed citizens")) {
                System.out.println(CityController.showUnEmployedCitizen());
            } else if ((matcher = getMatcher("construct road (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null)
                constructRoadView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            else if ((matcher = getMatcher("construct railroad (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null)
                constructRoadView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            else if (command.equals("exit menu"))
                break;
            else
                System.out.println("invalid command!");
            command = scanner.nextLine();
        }
    }


    private static String buyTile(Matcher matcher) {
        String result = CityController.presaleTiles();
        if (result == null) {
            return "select a city first";
        } else if (result.equals("there are no tiles around your city")) {
            return result;
        } else {
            System.out.println(result);
            String buyString = new String("");
            while (true) {
                buyString = scanner.nextLine();
                if (buyString.equals("exit")) {
                    return "exited sale menu";
                } else if ((matcher = getMatcher("\\d+", buyString)) != null) {
                    int number = Integer.parseInt(buyString);
                    if (number < 1 || number > CityController.getToBuyTiles().size()) {
                        System.out.println("invalid number");
                    }

                    GameController.setSelectedCity(null);
                    return CityController.buyHex(number);

                }

                System.out.println("invalid command, please enter a number or -exit- to leave this menu");
            }

        }
    }


    private static void selectMilitary(int x, int y) {
        if (GameController.isOutOfBounds(x, y))
            System.out.println("Entered position is not valid");
        else if (!UnitController.hasMilitary(x, y))
            System.out.println("There is no military unit in this hex");
        else {
            UnitController.setSelectedUnit(GameController.getMilitaryByLocation(x, y));
            System.out.println("The military unit was selected successfully");
        }
    }

    private static void selectCivilian(int x, int y) {
        if (GameController.isOutOfBounds(x, y))
            System.out.println("Entered position is not valid");
        else if (!UnitController.hasCivilian(x, y))
            System.out.println("There is no civilian unit in this hex");
        else {
            UnitController.setSelectedUnit(GameController.getCiviliansByLocation(x, y));
            System.out.println("The Civilian unit was selected successfully");
        }
    }


    private static void moveUnitView(int x, int y) {
        if (UnitController.getSelectedUnit() == null) {
            System.out.println("You should choose a unit first");
        } else if (GameController.getPlayerCiviliansByLocation(UnitController.getSelectedUnit().getCurrentHex().getX(), UnitController.getSelectedUnit().getCurrentHex().getY()) == null
                && GameController.getPlayerMilitaryByLocation(UnitController.getSelectedUnit().getCurrentHex().getX(), UnitController.getSelectedUnit().getCurrentHex().getY()) == null)
            System.out.println("You don't own this unit");
        else if (UnitController.isHexOccupied(x, y))
            System.out.println("This hex already has a unit of this type");
        else {
            Hex nextHex = UnitController.getNextHex(x, y);
            if (!UnitController.canMoveThrough(nextHex.getX(), nextHex.getY())) {
                System.out.print("The unit can't move through this hex");
            } else if (nextHex.getX() == x && nextHex.getY() == y) {
                System.out.println("The unit reached chosen destination");
                UnitController.moveUnit(UnitController.getSelectedUnit(), x, y);
            }
            // nextHex = UnitController.getNextHex(x, y);

        }

    }


    private void attackUnitView(int x, int y) {
        //todo : mp
        if (UnitController.getSelectedUnit() == null) {
            System.out.println("You should choose a unit first");
            return;
        }
        if (UnitController.getSelectedUnit() instanceof Civilian) {
            System.out.println("you can not attack with a civilian unit");
            return;
        }
        String combatResult = CombatController.attackUnit(x, y);
        System.out.println(combatResult);
    }

    public static void cityCombatMenu(City city, Player player) {
        System.out.println("you win the battle select a number: \n 1.delete it \n 2.add it to your territory");
        switch (scanner.nextInt()) {
            case 1:
                City.deleteCity(city);
                break;
            case 2:
                CombatController.addCityToTerritory(city, player);
        }
    }

    private static void constructRoadView(int x, int y) {
        System.out.println(UnitController.constructRoad(x, y));
    }

    private static void constructRailroadMenu(int x, int y) {
        System.out.println(UnitController.constructRailRoad(x, y));
    }
}