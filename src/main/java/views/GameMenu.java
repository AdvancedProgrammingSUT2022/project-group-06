package views;

import controllers.*;
import models.Player;
import models.maprelated.City;
import models.units.Civilian;
import models.units.Military;
import models.units.Settler;
import models.units.Worker;


import java.util.Scanner;
import java.util.regex.Matcher;



public class GameMenu extends Menu {
    private static Scanner scanner;

    public void run(Scanner gameScanner) {
        scanner = gameScanner;
        InitializeGameInfo.run();
        GameController.initializeGameController();
        System.out.println(GameController.printWorld());
        if(startGame()) {return;}
        String command= scanner.nextLine();
        Matcher matcher;


        while (true) {
            boolean validCommand = true;
            if(command.equals("construction delete")){
                System.out.println(GameController.deleteConstruction());
            }else if(command.equals("demographic screen")){
                System.out.println(GameController.demographicScreen());
            }else if(command.equals("unit activate")){
                System.out.println(GameController.activateUnit());
            } else if (command.equals("military panel")) {
                System.out.println(GameController.militaryPanel());
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
            } else if ((matcher = getMatcher("increase (--movepoint|-mp) (?<amount>\\d+) (?<x>\\d+) (?<y>\\d+) (?<type>[a-zA-Z]+)", command)) != null) {
                System.out.println(GameController.cheatMP((Integer.parseInt(matcher.group("amount"))),Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y")),matcher.group("type")));
            } else if((matcher = getMatcher("increase (--happiness|-h) (?<amount>\\d+)", command)) != null){ 
                System.out.println(GameController.cheatHappiness((Integer.parseInt(matcher.group("amount")))));
            } else if((matcher = getMatcher("increase (--population|-p) (?<amount>\\d+)", command)) != null){ 
                System.out.println(GameController.cheatPopulation((Integer.parseInt(matcher.group("amount")))));
            } else if((matcher = getMatcher("increase (--score|-s) (?<amount>\\d+)", command)) != null){ 
                System.out.println(GameController.cheatScore((Integer.parseInt(matcher.group("amount")))));
            } else if((matcher = getMatcher("increase (--production|-p) (?<amount>\\d+)", command)) != null){ 
                System.out.println(GameController.cheatProduction((Integer.parseInt(matcher.group("amount")))));
            } else if((matcher = getMatcher("increase (--cityMeleeCombatStrength|-cmcs) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null){
                System.out.println(GameController.cheatMeleeCombatStrength(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
            } else if((matcher = getMatcher("increase (--cityRangedCombatStrength|-crcs) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null){
                System.out.println(GameController.cheatRangedCombatStrength(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
            } else if((matcher = getMatcher("increase (--cityhitpoint|-chp) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null){
                System.out.println(GameController.cheatCityHitPoint(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
            } else if((matcher = getMatcher("increase (--cityfood|-cf) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null){
                System.out.println(GameController.cheatCityFood(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
            } else if((matcher = getMatcher("increase (--trophy|-t) (?<amount>\\d+)", command)) != null){
                System.out.println(GameController.cheatTrophy(Integer.parseInt(matcher.group("amount"))));
            } else if (command.equals("city show trophies")) {
                System.out.println(CityController.showTrophies());
            } else if (command.equals("city show resources")) {
                System.out.println(CityController.showResources());
            } else if ((matcher = getMatcher("tile select (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                System.out.println(CityController.selectHex(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if ((matcher = getMatcher("city select (--cityname|-cn) (?<cityname>[a-zA-Z_ ]+)", command)) != null) {
                System.out.println(CityController.selectCity(matcher.group("cityname")));
            } else if ((matcher = getMatcher("city attack (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                System.out.println(CombatController.attackCity(Integer.parseInt(matcher.group("x")),Integer.parseInt(matcher.group("y"))));
            } else if (command.equals("next turn")) {
                System.out.println(GameController.changeTurn());
                GameController.checkTimeVariantProcesses();
                if(GameController.getTurn() == 1) GameMenu.startGame();
            } else if ((matcher = getMatcher("buy tile", command)) != null) {
                System.out.println(buyTile(matcher));
            } else if ((matcher = getMatcher("unit make (--unitname|-un) (?<unitname>[a-zA-Z]+)", command)) != null) {
                System.out.println(CityController.startMakingUnit(matcher.group("unitname")));
            } else if ((matcher = getMatcher("select combat (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                selectMilitary(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                orderToSelectedUnit(scanner);
            } else if ((matcher = getMatcher("select noncombat (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                selectCivilian(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
                orderToSelectedUnit(scanner);
            }else if ((matcher = getMatcher("Remove citizen at (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+) from work", command)) != null) {
                System.out.println(CityController.removeCitizenFromWork(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if ((matcher = getMatcher("lock an citizen to (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                System.out.println(CityController.lockCitizenTo(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            } else if (command.equals("show unemployed citizens")) {
                System.out.println(CityController.showUnEmployedCitizen());
            }else if (command.equals("exit menu"))
                break;
            else if(!mapCommands(command)){
                System.out.println("invalid command!");
                validCommand = false;
            }
            if(validCommand) System.out.println(GameController.printWorld());
            command = scanner.nextLine();
            //TODO:
            GameController.showHexState();
        }
    }

    private static boolean mapCommands(String command) {
        Matcher matcher;
        if (command.equals("show all map")) {
            System.out.println(GameController.printAllWorld());
            return true;
        } else if ((matcher = getMatcher("map show details (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
            System.out.println(GameController.showHexDetails(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            return true;
        } else if ((matcher = getMatcher("map move (?<direction>(right|left|up|down)) (-c)(?<amount>\\d+)", command)) != null) {
            System.out.println(GameController.moveMap(matcher.group("direction"), Integer.parseInt(matcher.group("amount"))));
            return true;
        } else if ((matcher = getMatcher("map show (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
            System.out.println(GameController.showPosition(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
            return true;
        } else if ((matcher = getMatcher("map show (--cityName|-c) (?<cityName>.+)", command)) != null) {
            System.out.println(GameController.showCity(matcher.group("cityName")));
            return true;
        }
        return false;
    }

    public static boolean startGame() {
        String command;
        System.out.println("hi "+GameController.getCurrentPlayer().getName()+" ,welcome to civilization");
        System.out.println("one of the most pointless games ever made that has\nbeen created by a company that most probably prides itself");
        System.out.println("on its ability to create a game with just enough \nmeaningless tasks and ridiculous functions to ensure that in a");
        System.out.println("comparison between actually finishing the game and \ndeath, death would seem like the only option you have left");
        System.out.println("we in advance offer our heartfelt condolences and \npray to god that you are smart enough to exit the game right now");
        System.out.println("and save yourself from this endless torture.\n in order to do that, please type -Exit-\n");
        System.out.println("if you hate your life and have decided to end it by continuing to play this game,\nthen please select a tile and then choose a name for your very first city");
        //System.out.println("a name for your very first city");

        Matcher matcher;
        while(true)
        {
            command=scanner.nextLine();
            if(command.equals("Exit"))
            {
                return true;
            } else if ((matcher = getMatcher("tile select (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
                String result;
                System.out.println(result = CityController.selectHex(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y"))));
                if(result.equals("tile selected")){
                    if(!GameController.getSelectedHex().getTerrain().getName().matches("Mountain|Ocean")) break;
                    else System.out.println("but this hex is mounted or ocean");
                }
            }else if(!mapCommands(command))
                System.out.println("invalid command");
        }

        System.out.println("enter your city's name in camelCase");
        command=scanner.nextLine();
        GameController.getSelectedHex().setOwner(GameController.getCurrentPlayer());
        UnitController.makeUnit("Settler", GameController.getSelectedHex());
        UnitController.setSelectedUnit(GameController.getCiviliansByLocation(GameController.getSelectedHex().getX(), GameController.getSelectedHex().getY()));
        System.out.println(CityController.buildCity(command));
        return false;
    }
    private void orderToSelectedUnit(Scanner scanner) {
        if(UnitController.getSelectedUnit() instanceof Worker)orderToWorker(scanner);
        else if(UnitController.getSelectedUnit() instanceof Settler)orderToSettler(scanner);
        else if(UnitController.getSelectedUnit() instanceof Military)orderToMilitary(scanner);
    }
    private void orderToMilitary(Scanner scanner) {
        boolean isSelect = true;
        String command;
        Matcher matcher;
        while(isSelect){
            command = scanner.nextLine();
        if(command.equals("sleep")){
            System.out.println(UnitController.sleepUnit());
        }else if(command.equals("wake")){
            System.out.println(UnitController.wakeUpUnit());
        } else if(command.equals("delete")){
            System.out.println(UnitController.deleteUnit(UnitController.getSelectedUnit()));
        } else if(command.equals("alert")){
            System.out.println(UnitController.alert());
        }else if(command.equals("garrison")){
            System.out.println(UnitController.garrison());
        }else if(command.equals("fortify")){
            System.out.println(UnitController.fortify());
        }else if(command.equals("ranged attack setup")){
            System.out.println(UnitController.setUpSiegeForRangeAttack());
        }else if(command.equals("pillage")){
            System.out.println(UnitController.pillage());
        }else if ((matcher = getMatcher("attack (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
            attackUnitView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
        }  else {
            isSelect = orderToAllUnits(isSelect, command);
        }
        }
    }

    private boolean orderToAllUnits(boolean isSelect, String command) {
        Matcher matcher;
        if ((matcher = getMatcher("move to (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null) {
            moveUnitView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
        }else if(command.equals("unselect unit")){
                UnitController.setSelectedUnit(null);
                isSelect=false;
        } else if(mapCommands(command)){
            return isSelect;
        }else{
            System.out.println("invalid command");
        }
        return isSelect;
    }

    private void orderToSettler(Scanner scanner) {
        boolean isSelect = true;
        Matcher matcher;
        String command;
        while(isSelect){
            command = scanner.nextLine();
            if ((matcher = getMatcher("city build (--cityname|-cn) (?<name>[a-zA-Z_ ]+)", command)) != null) {
                System.out.println(CityController.buildCity(matcher.group("name")));
            } else if(command.equals("sleep")){
                System.out.println(UnitController.sleepUnit());
            }else if(command.equals("wake")){
                System.out.println(UnitController.wakeUpUnit());
            } else if(command.equals("delete")){
                System.out.println(UnitController.deleteUnit(UnitController.getSelectedUnit()));
            } else isSelect = orderToAllUnits(isSelect, command);
        }
    }
    private void orderToWorker(Scanner scanner) {
        boolean isSelect = true;
        String command;
        Matcher matcher;
        while(isSelect){
            command = scanner.nextLine();
            if(command.equals("sleep")){
                System.out.println(UnitController.sleepUnit());
            } else if ((matcher = getMatcher("construct road (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null)
                constructRoadView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            else if ((matcher = getMatcher("construct railroad (--coordinates|-c) (?<x>-?\\d+) (?<y>-?\\d+)", command)) != null)
                constructRoadView(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")));
            else if(command.equals("wake")) {
                System.out.println(UnitController.wakeUpUnit());
            } else if(command.equals("delete")){
                System.out.println(UnitController.deleteUnit(UnitController.getSelectedUnit()));
            }else if(command.equals("quarry build")){
                System.out.println(GameController.makeQuarry());
            } else if(command.equals("plantation build")){
                System.out.println(GameController.makePlantation());
            } else if(command.equals("camp build")){
                System.out.println(GameController.makingCamp());
            } else if(command.equals("pasture build")){
                System.out.println(GameController.makingPasture());
            } else if(command.equals("lumber mill build")){
                System.out.println(GameController.makingLumberMill());
            } else if(command.equals("post build")){
                System.out.println(GameController.startMakeingTradingPost());
            } else if (command.equals("farm build")) {
                System.out.println(GameController.startBuildFarm());
            } else if (command.equals("mine build")) {
                System.out.println(GameController.startBuildMine());
            } else if(command.equals("remove jungle")){
                System.out.println(GameController.removeJungle());
            } else if(command.equals("remove forest")){
                System.out.println(GameController.removeForest());
            } else if(command.equals("remove marsh")){
                System.out.println(GameController.removeMarsh());
            } else if(command.equals("remove way")){
                System.out.println(GameController.removeRailRoad());
            }else if(command.equals("repair")){
                System.out.println(GameController.repair());
            } else isSelect = orderToAllUnits(isSelect, command);
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
        else if (GameController.getWorld().getHex()[x][y].getMilitaryUnit().getOwner() != GameController.getCurrentPlayer())
            System.out.println("You don't own this unit");
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
        else if (GameController.getWorld().getHex()[x][y].getCivilianUnit().getOwner() != GameController.getCurrentPlayer())
            System.out.println("You don't own this unit");
        else {
            UnitController.setSelectedUnit(GameController.getCiviliansByLocation(x, y));
            System.out.println("The Civilian unit was selected successfully");
        }
    }


    private static void moveUnitView(int x, int y) {
        System.out.println(UnitController.startMovement(x, y));
    }


    private void attackUnitView(int x, int y) {
        if (UnitController.getSelectedUnit() == null) {
            System.out.println("You should choose a unit first");
            return;
        }
        if (UnitController.getSelectedUnit() instanceof Civilian) {
            System.out.println("you can not attack with a civilian unit");
            return;
        }
        if (UnitController.getSelectedUnit().getMP() == 0){
            System.out.println("you do not have move point");
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