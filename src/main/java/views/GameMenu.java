package views;

import controllers.InitializeGameInfo;
import controllers.CityController;
import controllers.GameController;
import controllers.UnitController;
import models.Player;
import models.maprelated.City;
import models.maprelated.Hex;

import static org.mockito.ArgumentMatchers.matches;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {

    public void run(Scanner scanner) {
        InitializeGameInfo.run();
        GameController.initializeGameController();
        System.out.println(GameController.printWorld());
        int playerCount=0;
        Player currentPlayer=GameController.getPlayers().get(playerCount);

        String command = scanner.nextLine();
        Matcher matcher;
        while (true) {

            if((matcher = getMatcher("city build (--cityname|-cn) (?<name>[a-zA-Z_ ]+)", command)) != null)
            {
                System.out.println(CityController.buildCity(currentPlayer,matcher.group("name")));
            } else if((matcher = getMatcher("unit select (--unitname|-un) (?<unitname>[a-zA-Z]+) (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null)
            {
                
            }else if(command.equals("city show resources"))
            {  
                System.out.println(CityController.showResources());
            } else if((matcher = getMatcher("show unemployed citizens", command)) != null)
            {
                System.out.println(CityController.showUnemployed());

            }else if((matcher = getMatcher("tile select (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null)
            {
                System.out.println(CityController.selectHex(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if((matcher = getMatcher("city select (?<cityname>[a-zA-Z_ ]+)", command))!=null)
            {
                System.out.println(CityController.selectCity(matcher.group("cityname")));
            } else if(command.equals("next turn"))
            {
                if(playerCount==GameController.getPlayers().size()-1)
                {
                    playerCount=0;
                    currentPlayer=GameController.getPlayers().get(playerCount);
                }
                else
                {
                    playerCount++;
                    currentPlayer=GameController.getPlayers().get(playerCount);
                }
            } else if((matcher = getMatcher("buy tile ", command)) != null)
            {
                String result=CityController.presaleTiles();
                if(result==null)
                {
                    System.out.println("select a city first");
                }else if(result.equals("there are no tiles around your city"))
                {
                    System.out.println(result);
                }else
                {
                    System.out.println(result);
                    String buyString=new String("");
                    while(true)
                    {
                        buyString=scanner.nextLine();
                        if(buyString.equals("exit"))
                        {
                            break;
                        }else if((matcher = getMatcher("\\d+", buyString)) != null)
                        {
                            int number=Integer.parseInt(buyString);
                            if(number<1||number>CityController.getToBuyTiles().size())
                            {
                                System.out.println("invalid number");
                            }
                            System.out.println(CityController.buyHex(currentPlayer,number));
                            break;
                        }

                        System.out.println("invalid command, please enter a number or -exit- to leave this menu");
                    }
                    GameController.setSelectedCity(null);
                }
            } else if((matcher = getMatcher("unit make (--unittype|-ut) (?<unittype>[a-zA-Z]+) (--unitname|-un) (?<unitname>[a-zA-Z]+)", command)) != null)
            {
                System.out.println(CityController.makeUnit(currentPlayer,matcher.group("unittype"),matcher.group("unitname")));
            } else if (command.equals("show all map")) {
                System.out.println(GameController.printAllWorld());
            } else if ((matcher = getMatcher("map show details (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                System.out.println(GameController.showHexDetails(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if ((matcher = getMatcher("map move (?<direction>(right|left|up|down)) (-c)(?<amount>\\d+)", command)) != null) {
                System.out.println(GameController.moveMap(matcher.group("direction"),Integer.parseInt(matcher.group("amount"))));
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
            } else if (command.equals("exit menu"))
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
            for (int i = 0; i < GameController.getSelectedUnit().getRange(); i++) {
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
