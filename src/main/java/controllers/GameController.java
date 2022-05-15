package controllers;

import enums.*;
import models.Player;
import models.gainable.Construction;
import models.gainable.Improvement;
import models.gainable.Technology;
import models.maprelated.*;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;
import models.units.Worker;


import java.util.*;

public class GameController {

    private static final ArrayList<Player> players = InitializeGameInfo.getPlayers();
    private static int turn ;
    private static final World world = InitializeGameInfo.getWorld();
    private static int[] mapBoundaries;
    private static final Hex[][] hex = world.getHex();
    private static Player currentPlayer;
    private static final ArrayList<Civilian> allCivilians = new ArrayList<Civilian>();
    private static final ArrayList<Military> allMilitaries = new ArrayList<Military>();
    private static Hex selectedHex;
    private static City selectedCity;
    private static int playerCount;

    public static int getTurn() {
        return turn;
    }
    public static void addALlCivilians(Civilian newCivilian) {
        allCivilians.add(newCivilian);
    }

    public static void addAllMilitary(Military newMilitary) {
        allMilitaries.add(newMilitary);
    }

    public static void setSelectedHex(Hex newHex) {
        selectedHex = newHex;
    }

    public static World getWorld() {
        return world;
    }

    public static void setSelectedCity(City newCity) {
        selectedCity = newCity;
    }

    public static City getSelectedCity() {
        return selectedCity;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    public static Hex getSelectedHex() {
        return selectedHex;
    }

    public static void initializeGameController() {
        playerCount = 0;
        currentPlayer = players.get(playerCount);
        turn = 1;
        mapBoundaries = new int[]{0, 3, 0, 6};
        removeOwnerOfHexes();
        hex[0][0] = new Hex(0,0 , new Terrain("Hills"),null);
        hex[0][0].setState(HexState.Visible,currentPlayer);
        hex[1][0] = new Hex(1,0 , new Terrain("Hills"),null);
        //UnitController.makeUnit("Archer",hex[0][0]);
        //hex[1][1].setState(HexState.Visible,currentPlayer);
        //Worker worker = new Worker("Worker", hex[1][1] ,GameController.currentPlayer);
        //hex[1][1].setPillaged(true);
/*        Improvement improvement = new Improvement("Mine",worker,hex[1][1]);
        City city2 =makeCityForTesting(5, 7,"aseman");
        hex[1][1].setCity(city2);
        improvement.build();*/
        //City city1 = makeCityForTesting(0, 0,"asy");
/*        city1.addHex(world.getHex()[0][1]);
        City city2 =makeCityForTesting(5, 7,"aseman");
        City city3 =makeCityForTesting(1, 2,"asemane");
        world.getHex()[0][1].setState(HexState.Visible, currentPlayer);*/
/*        Siege siege = new Siege("Catapult", world.getHex()[1][0], players.get(1));
        world.getHex()[1][0].setMilitaryUnit(siege);
        world.getHex()[1][0].setState(HexState.Visible, currentPlayer);*/
    }

    private static City makeCityForTesting(int x, int y, String name) {
        City city = new City(players.get(0), name, world.getHex()[x][y]);
        world.getHex()[x][y].setCity(city);
        world.getHex()[x][y].setState(HexState.Visible, currentPlayer);
        currentPlayer.addCity(city);
        City.addCities(city);
        return city;
    }

    private static void removeOwnerOfHexes() {
        for (int i = 0; i < world.getHexInHeight(); i++) {
            for (int j = 0; j < world.getHexInWidth(); j++) {
                hex[i][j].setOwner(null);
            }
        }
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

    public static String printAllWorld() {
        StringBuilder stringWorld = new StringBuilder();
        initializeString(new int[]{0, 10, 0, 10}, world.getString());
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
        //if(hex[x][y].getState(currentPlayer) == HexState.FogOfWar) return "he zerange inja fog of ware";
        return getString(x, y, y, x);
    }

    public static String showCity(String cityName) {
        City city;
        if ((city = isCityValid(cityName)) == null) return "oops invalid city name!";
        if (city.getCapital().getState(currentPlayer) == HexState.FogOfWar) return "you have not seen the city";
        return getString(city.getX(), city.getY(), city.getY(), city.getX());
    }

    private static String getString(int maxX, int maxY, int minY, int minX) {
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

    private static City isCityValid(String cityName) {
        for (Player player : players) {
            for (City city : player.getCities()) {
                if (Objects.equals(city.getName(), cityName)) return city;
            }
        }
        return null;
    }

    private static void drawHexDetails(int align, int minI, int minJ, String[][] string, Hex hex, String color) {
        //string[minI + 1 + align][minJ + 6] = color + "\033[0;33m" + "A" + Color.ANSI_RESET.getCharacter();
        if (hex.getOwner() != null) {
            Color playerColor = InitializeGameInfo.getPlayerColor().get(hex.getOwner().getName());
            char cityName;
            if (hex.getCapital() != null) {
                cityName = hex.getCapital().getName().toUpperCase().charAt(0);
            } else {
                cityName = hex.getCity().getName().toLowerCase().charAt(0);
            }
            string[minI + 1 + align][minJ + 6] = "\033[0;33m" + playerColor.getCharacter() + cityName + Color.ANSI_RESET.getCharacter();
        }
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
                if (hex[n][m].getState(currentPlayer) == HexState.Visible) drawHex(hex[n][m]);
                else if (hex[n][m].getState(currentPlayer) == HexState.FogOfWar) drawFogOfWar(hex[n][m]);
                else {
                    drawHex(currentPlayer.getReveledHexes().get(hex[n][m]));
                }
            }
        }
    }

    public static String showHexDetails(int x, int y) {
        if (isOutOfBounds(x,y)) {
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
            detail.append(" Civilian Unit: ").append(hex.getCivilianUnit().getName());
        }
        if (hex.getMilitaryUnit() != null) {
            detail.append(" Military Unit: ").append(hex.getMilitaryUnit().getName());
        }
        detail.append("\n");
        if(hex.hasRailRoad())detail.append(" has railroad");
        if(hex.hasRoad())detail.append(" has road");
        if(hex.isPillaged())detail.append((" is pillaged"));
        if(hex.getCapital()!= null) detail.append(" Capital of: ").append(hex.getCapital().getName());
        if(hex.getCity() != null) detail.append("tile of").append(hex.getCity().getName());
        detail.append("\n");
        detail.append(hex.getState(currentPlayer));
        
        if(hex.getHasCitizen())detail.append("has a working citizen");
        if(hex.getOwner()!=null){
            detail.append(" Owner: "+ hex.getOwner().getName());
        }
        detail.append(" x,y:").append(x).append(" ").append(y);
        return detail.toString();
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static Military getPlayerMilitaryByLocation(int x, int y) {
        List<Military> militaries = currentPlayer.getMilitaries();
        for (Military military : militaries) {
            if (military.getCurrentHex().getX() == x && military.getCurrentHex().getY() == y)
                return military;
        }
        return null;
    }

    public static Civilian getPlayerCiviliansByLocation(int x, int y) {
        List<Civilian> civilians = currentPlayer.getCivilians();
        for (Civilian civilian : civilians) {
            if (civilian.getCurrentHex().getX() == x && civilian.getCurrentHex().getY() == y)
                return civilian;
        }
        return null;
    }

    public static Military getMilitaryByLocation(int x, int y) {
        return GameController.getWorld().getHex()[x][y].getMilitaryUnit();
    }

    public static Civilian getCiviliansByLocation(int x, int y) {
        return world.getHex()[x][y].getCivilianUnit();
    }
    private static void resetOrdersAndOrdered(){
        for (Military military : currentPlayer.getMilitaries()) {
            military.setOrdered(false);
            military.setMP(military.getBackUpMp());
        }
        for (Civilian civilian: currentPlayer.getCivilians()) {
            civilian.setOrdered(false);
        }
        for (Construction construction: currentPlayer.getUnfinishedProjects()) {
            construction.getWorker().setOrdered(true);
        }
        for (Movement movement:UnitController.getUnfinishedMovements()) {
            movement.getUnit().setOrdered(true);
        }
    }
    private static void heal() {
        for (Military military : currentPlayer.getMilitaries()) {
            if (military.getHealth() < military.getMaxHealth()) {
                military.increaseHealth(1);
            }
            military.setMP(military.getBackUpMp());
        }
        for (Civilian civilian: currentPlayer.getCivilians()){
            civilian.setMP(civilian.getBackUpMp());
        }
        for (City city : currentPlayer.getCities()) {
            if (city.getHitPoint() < city.getMaxHitPoint()) {
                city.increaseHitPoint(1);
            }
        }
    }

    public static void feedCitizens() {
        for (City city : currentPlayer.getCities()) {
            currentPlayer.decreaseFood(city.getPopulation());
            currentPlayer.increaseFood(city.getNumberOfUnemployedCitizen());
        }
    }

    public static void addGoldFromWorkersActivities() {
        //working on tiles with river, beach and oasis increases gold
        for (City city : currentPlayer.getCities()) {
            for (Hex hex : city.getHexs()) {
                if (hex.getHasCitizen() && (hex.isNextToAnyRiver() || hex.getTerrain().getName().equals(TerrainNames.Coast) || hex.getFeature().getName().equals(FeatureNames.Oasis)))
                    currentPlayer.increaseGold(2);
            }
        }
    }

    public static void resetMovePoints() {
        for (Civilian civilian : currentPlayer.getCivilians())
            civilian.setMP(civilian.getBackUpMp());
        for (Military military : currentPlayer.getMilitaries())
            military.setMP(military.getBackUpMp());
    }

    public static String changeTurn() {
        String unitOrders = unitActions();
        if (unitOrders != null) return unitOrders;
        int goldPerTurn = 0;
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            goldPerTurn += temp.getGold();
        }
        currentPlayer.increaseGold(goldPerTurn);///////////////////////////////////////////////////
        //todo: complete followings
        feedCitizens();
        growCity();
        //healUnits and cities(1hit point)//handel tarmim asib
        heal();
        resetOrdersAndOrdered();
        UnitController.changeTurn();
        //handle siege units
        //hazine tamir O negahdari buldings
        currentPlayer.decreaseHappiness(1);//happiness decrease as the population grows
        if (currentPlayer.getHappiness() < 0) unhappinessEffects();
        for (int i = 0; i < currentPlayer.getCities().size(); i++) {
            for (int j = 0; j < currentPlayer.getCities().get(i).getHexs().size(); j++) {
                if (currentPlayer.getCities().get(j).getHexs().get(j).hasRoad())
                    currentPlayer.decreaseGold(2);//gold to maintain roads
                if (currentPlayer.getCities().get(j).getHexs().get(j).hasRailRoad())
                    currentPlayer.decreaseGold(3);//gold to maintain railroads
            }
        }
        addGoldFromWorkersActivities();
        //improvements
        for (Player player : players)
            player.setTrophies(player.getTrophies() + player.getPopulation() + 3); //one trophy for each citizen & 3 for capital
        resetMovePoints();

        if (playerCount == GameController.getPlayers().size() - 1) {
            playerCount = 0;
            turn++;
        } else {
            playerCount++;
        }
        currentPlayer = GameController.getPlayers().get(playerCount);
        UnitController.setCurrentPlayer(currentPlayer);
/*        System.out.println(currentPlayer);
        System.out.println(CityController.getCurrentPlayer());*/
        return "Turn changed successfully \n player:" + currentPlayer.getName();
    }

    public static void addFoodFromTiles() {
        for (Player player : players) {
            for (City city : player.getCities()) {
                for (Hex hex : city.getHexs()) {
                    city.increaseFood(hex.getTerrain().getFood());
                }
            }
        }
    }

    public static void growCity() {
        addFoodFromTiles();

        for (Player player : players) {
            for (Construction project : player.getUnfinishedProjects()) {
                if (project instanceof Unit && project.getName().equals("Settler"))
                    ((Unit) project).getCurrentHex().getCity().decreaseFood(((Unit) project).getCurrentHex().getTerrain().getFood());
            }
        }//food production will stop if a settler unit is being implemented

        for (Player player : players) {
            for (City city : player.getCities()) {
                if (player.getHappiness() >= 0) { //city growth stops if people are unhappy
                    if (city.getFood() / player.getFoodForNewCitizen() > 5)
                        player.setFoodForNewCitizen(player.getFoodForNewCitizen() * 2);
                    city.increasePopulation(city.getFood() / player.getFoodForNewCitizen());//city growth
                    city.decreaseFood(city.getFood());
                }
            }
        }
    }

    private static String unitActions() {
        for (Military military : currentPlayer.getMilitaries()) {
            if (!(military.getState() == UnitState.Sleep) && !military.isOrdered()) {
                if (military.getState() == UnitState.Alert) {
                    if (enemyIsNear(getDirection(military.getY()), military.getX(), military.getY())){
                        return "unit in " + military.getX() + "," + military.getY() + "coordinates needs order";
                    }
                } else
                    return "unit in " + military.getX() + "," + military.getY() + "coordinates needs order";
            }
        }
        for (Civilian civilian : currentPlayer.getCivilians()) {
            if (!(civilian.getState() == UnitState.Sleep) &&  !civilian.isOrdered()) {
                return "unit in " + civilian.getX() + "," + civilian.getY() + "coordinates needs order";
            }
        }
        return null;
    }

    private static boolean enemyIsNear(int[][] direction, int x, int y) {
        for (int j = 0; j < direction.length; j++) {
            if (checkExistOfEnemy(x, y, direction, j)) return true;
            int[][] tempDirection = getDirection(y + direction[j][1]);
            for (int i = 0; i < tempDirection.length; i++) {
                if (checkExistOfEnemy(x + direction[j][0], y + direction[j][1], tempDirection, i)) return true;
            }
        }
        return false;
    }

    private static boolean checkExistOfEnemy(int x, int y, int[][] tempDirection, int i) {
        if (!isOutOfBounds(x + tempDirection[i][0], y + tempDirection[i][1])) {
            if(hex[x + tempDirection[i][0]][y + tempDirection[i][1]].getMilitaryUnit()!=null&&
               hex[x + tempDirection[i][0]][y + tempDirection[i][1]].getMilitaryUnit().getOwner()!= currentPlayer){
                return true;
            }
        }
        return false;
    }

    public static boolean isOutOfBounds(int x, int y) {
        return y >= world.getHexInWidth() || x >= world.getHexInHeight() || x < 0 || y < 0;
    }

    public static int[][] getDirection(int y) {
        int[][] oddDirection = {{-1, 0}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}};
        int[][] evenDirection = {{-1, 0}, {-1, -1}, {0, -1}, {1, 0}, {0, 1}, {-1, 1}};
        return y % 2 == 1 ? oddDirection : evenDirection;
    }

    public static void unhappinessEffects() {
        for (int i = 0; i < currentPlayer.getMilitaries().size(); i++) {
            currentPlayer.getMilitaries().get(i).setCombatStrength((int) (currentPlayer.getMilitaries().get(i).getCombatStrength() * 0.75));
        }
        //combat strength for military units decrease 25%
    }

    public static void happinessDueToLuxuries(String name) {
        for (int i = 0; i < currentPlayer.getCities().size(); i++) {
            for (int j = 0; j < currentPlayer.getCities().get(i).getHexs().size(); j++) {
                if (currentPlayer.getCities().get(i).getHexs().get(j).getResource().getName().equals(name))
                    return;
            }
        }
        currentPlayer.increaseHappiness(4); //new luxuries add to happiness
    }

    public static String cheatCityProduction(int amount,String cityName)
    {
        for(City temp:currentPlayer.getCities())
        {
            if(temp.getName().equals(cityName))
            {
                temp.increaseProduction(amount);
                return "city's production increased successfully";
            }   
        }

        return "no city with this name exists";
    }

    public static String cheatGold(int amount) {
        currentPlayer.increaseGold(amount);
        return "gold increased successfully";
    }

    public static String cheatHappiness(int amount) {
        currentPlayer.increaseHappiness(amount);
        return "happiness increased successfully";
    }

    public static String cheatPopulation(int amount) {
        currentPlayer.increasePopulation(amount);
        return "population increased successfully";
    }

    public static String cheatProduction(int amount) {
        currentPlayer.increaseProduction(amount);
        return "production increased successfully";
    }

    public static String cheatScore(int amount) {
        currentPlayer.increaseScore(amount);
        return "score increased successfully";
    }

    // public static String cheatTurn(int amount) {
    //     turn += amount-1;
    //     changeTurn();
    //     return "turn increased successfully";
    // }
    public static String cheatMP(int amount, int x, int y, String type) {
        if (isOutOfBounds(x, y)) {
            return "invalid x and y";
        }

        if (!type.matches("Civilian||Military")) {
            return "invalid unit type";
        }

        if (type.equals("Military")) {
            if (GameController.getWorld().getHex()[x][y].getMilitaryUnit() != null) {
                GameController.getWorld().getHex()[x][y].getMilitaryUnit().increaseMP(amount);
                return "move point increased successfully";
            }
            return "no military unit exists in this tile";
        }
        if (type.equals("Civilian")) {
            if (GameController.getWorld().getHex()[x][y].getCivilianUnit() != null) {
                GameController.getWorld().getHex()[x][y].getCivilianUnit().increaseMP(amount);
                return "move point increased successfully";
            }
            return "no civilian unit exists in this tile";
        }

        return "";

    }

    public static String cheatMeleeCombatStrength(int amount, String cityName) {
        for (City city : currentPlayer.getCities()) {
            if (city.getName().equals(cityName)) {
                city.increaseMeleeDefensivePower(amount);
                return "Melee combat strength increased successfully";
            }
        }

        return "no city with this name exists";
    }

    public static String cheatRangedCombatStrength(int amount, String cityName) {
        for (City city : currentPlayer.getCities()) {
            if (city.getName().equals(cityName)) {
                city.increaseRangedDefencePower(amount);
                return "Ranged combat strength increased successfully";
            }
        }

        return "no city with this name exists";
    }

    public static String cheatCityFood(int amount, String cityName) {
        for (City city : currentPlayer.getCities()) {
            if (city.getName().equals(cityName)) {
                city.increaseFood(amount);
                return "food increased successfully";
            }
        }

        return "no city with this name exists";
    }

    public static String cheatCityHitPoint(int amount, String cityName) {
        for (City city : currentPlayer.getCities()) {
            if (city.getName().equals(cityName)) {
                city.increaseHitPoint(amount);
                return "hit point increased successfully";
            }
        }

        return "no city with this name exists";
    }

    public static String cheatTrophy(int amount) {
        currentPlayer.increaseTrophies(amount);
        return "trophy increased successfuly";
    }

    public static String showResearchMenu() {
        StringBuilder research = new StringBuilder("");

        currentPlayer.getAchievedTechnologies().forEach((key, value) -> {
            research.append(key + " status: ");
            if (value == false) {
                research.append("not achieved\n");
            } else {
                research.append("achieved\n");
            }

        });


        return research.toString();
    }
    public static String militaryPanel()
    {
        StringBuilder militaryList=new StringBuilder();
        int count=1;
        for(Military military:currentPlayer.getMilitaries())
        {
            militaryList.append(count+") "+military.getName()+" hex: x="+military.getCurrentHex().getX()+" y="+military.getHex().getY()+"\n");
            count++;
        }
        return militaryList.toString();

    }

    public static String citiesPanel() {
        StringBuilder citiesList = new StringBuilder();
        int count = 1;
        for (City temp : currentPlayer.getCities()) {
            citiesList.append(count + ") " + temp.getName() + "\n");
            count++;
        }
        return citiesList.toString();
    }

    public static String unitsPanel() {
        StringBuilder unitsList = new StringBuilder();
        int count = 1;
        for (Unit temp : currentPlayer.getUnits()) {
            unitsList.append(count + ") " + temp.getName() + ": " + "state: " + temp.getState() + " x: " + temp.getX() + " y: " + temp.getY() + "\n");
            count++;
        }
        return unitsList.toString();
    }

    public static String notificationHistory() {
        StringBuilder notificationsList = new StringBuilder();
        
        int count = 0;
        for (int temp : currentPlayer.getNotificationsTurns()) {
            notificationsList.append("turn: " + temp + " notification: " + currentPlayer.getNotifications().get(count) + "\n");
            count++;
        }
        return notificationsList.toString();
    }

    public static String economicOverview() {
        StringBuilder economicInfo = new StringBuilder();
        int count = 1;
        for (City temp : currentPlayer.getCities()) {
            economicInfo.append(count + ") cityname: " + temp.getName() + "\n");
            economicInfo.append("\t\tpoplulation: " + temp.getPopulation() + "\n");
            economicInfo.append("\t\tmelee defensive power: " + temp.getMeleeCombatStrength() + "\n");
            economicInfo.append("\t\tranged defensive power: " + temp.getRangedCombatStrength() + "\n");
            economicInfo.append("\t\tfood: " + temp.getFood() + "\n");
            economicInfo.append("\t\tgold " + temp.getGold() + "\n");
            economicInfo.append("\t\ttrophy: " + temp.getTrophy() + "\n");
            economicInfo.append("\t\tproduction: " + temp.getProduction() + "\n");

            for (Construction construction : currentPlayer.getUnfinishedProjects()) {
                if (construction.getHex().getCity().getName().equals(temp.getName())) {
                    economicInfo.append("\t\tpending project: " + construction.getName() + "-> turn left: " + construction.getLeftTurns() + "\n");
                }

            }

        }

        return economicInfo.toString();

    }

    private static String isMakingMinePossible() {
        Hex hex = UnitController.getSelectedUnit().getCurrentHex();
        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a Worker first";
        }
        if(!hex.getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }
        if (!currentPlayer.getAchievedTechnologies().get("Mining")) {
            return "you have not achieve the Mining technology yet";
        }
        if (hex.getResource() == null || (hex.getResource().getName().equals("Ice||FoodPlains")) || (hex.getTerrain().getName().equals("Mountain||Ocean"))) {
            return "you can not make a Mine on this tile";
        }
        return null;
    }

    public static String startBuildMine() {

        String isPossible;
        if ((isPossible = isMakingMinePossible()) != null) {
            return isPossible;
        }
        Improvement Mine = new Improvement("Mine", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        String type = UnitController.getSelectedUnit().getCurrentHex().getFeature().getName();


        if (type.equals("Jungle")) {
            Mine.setLeftTurns(13);
        } else if (type.equals("Forest")) {
            Mine.setLeftTurns(10);
        } else if (type.equals("Marsh")) {
            Mine.setLeftTurns(12);
        } else {
            Mine.setLeftTurns(6);
        }

        currentPlayer.addUnfinishedProject(Mine);
        String temp = "the process of " + "making a "+"Mine"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a Mine successfully started";
    }

    private static String isMakingFarmPossible() {

        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a Worker first";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }

        if (!isConstructionPossible()) {
            return "you can not have two Improvements in one tile";
        }

        if (UnitController.getSelectedUnit().getCurrentHex().getFeature().getName().equals("Ice")) {
            return "you can not make a Farm on Ice";
        }

        return null;
    }


    public static String startBuildFarm() {
        String isPossible;
        if ((isPossible = isMakingFarmPossible()) != null) {
            return isPossible;
        }

        Improvement Farm = new Improvement("Farm", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());


        String type = UnitController.getSelectedUnit().getCurrentHex().getFeature().getName();

        if (type.equals("Jungle")) {
            Farm.setLeftTurns(10);
        } else if (type.equals("Forest")) {
            Farm.setLeftTurns(13);
        } else if (type.equals("Marsh")) {
            Farm.setLeftTurns(12);
        } else {
            Farm.setLeftTurns(6);
        }


        currentPlayer.addUnfinishedProject(Farm);
        String temp = "the process of " + "making a "+"Farm"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a farm successfully started";

    }

    public static String removeError(String name) {
        if (!(UnitController.getSelectedUnit() instanceof Worker)) {
            return "choose a worker";
        }
        if (!Objects.equals(UnitController.getSelectedUnit().getCurrentHex().getFeature().getName(), name))
            return "this tile dont have" + name;
        return null;
    }

    public static String removeJungle() {
        String error;
        if ((error = removeError("Jungle")) != null) {
            return error;
        }
        Improvement delete = new Improvement("remove jungle", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        delete.setLeftTurns(7);
        currentPlayer.addUnfinishedProject(delete);
        return "process for deleting a jungle successfully started";
    }

    public static String removeForest() {
        String error;
        if ((error = removeError("forest")) != null) {
            return error;
        }
        Improvement delete = new Improvement("remove forest", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        delete.setLeftTurns(4);
        currentPlayer.addUnfinishedProject(delete);
        return "process for deleting a forest successfully started";
    }

    public static String removeMarsh() {
        String error;
        if ((error = removeError("Marsh")) != null) {
            return error;
        }
        Improvement delete = new Improvement("remove marsh", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        delete.setLeftTurns(6);
        currentPlayer.addUnfinishedProject(delete);
        return "process for deleting a marsh successfully started";
    }

    public static String removeRailRoad() {
        if (!(UnitController.getSelectedUnit() instanceof Worker)) {
            return "choose a worker";
        }
        if (!UnitController.getSelectedUnit().getCurrentHex().hasRailRoad() || !UnitController.getSelectedUnit().getCurrentHex().hasRoad()) {
            return "this tile dont have road or railroad";
        }
        Improvement delete = new Improvement("remove road", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        delete.setLeftTurns(7);
        currentPlayer.addUnfinishedProject(delete);
        return "process for deleting a road successfully started";
    }

    public static String repair() {
        if (!(UnitController.getSelectedUnit() instanceof Worker)) {
            return "choose a worker";
        }
        if (!UnitController.getSelectedUnit().getCurrentHex().isPillaged()) {
            return "this tile is not pillaged";
        }
        Improvement repair = new Improvement("repair", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        repair.setLeftTurns(3);
        currentPlayer.addUnfinishedProject(repair);
        return "process repairing started";
    }

    public static String startMakeingTradingPost() {
        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a worker first";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }
        if (!isConstructionPossible()) {
            return "you can not have two Improvements in one tile";
        }
        if (!UnitController.getSelectedUnit().getCurrentHex().getTerrain().getName().matches("Plain||Desert||Grassland|||Tundra")) {
            return "you can not build a TradingPost on this tile";
        }

        Improvement post = new Improvement("post", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        post.setLeftTurns(5);
        currentPlayer.addUnfinishedProject(post);
        String temp = "the process of " + "making a "+"TradingPost"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a TradingPost started";
    }

    public static String makingLumberMill() {
        if (!currentPlayer.getAchievedTechnologies().get("Construction")) {
            return "you have not achieved the required technology to build a Lumber Mill";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }
        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a worker first";
        }
        if (!isConstructionPossible()) {
            return "you can not have two Improvements in one tile";
        }
        if (!UnitController.getSelectedUnit().getCurrentHex().getTerrain().getName().equals("Jungle")) {
            return "you can not build a Lumber Mill on this tile";
        }

        Improvement lumber = new Improvement("lumber", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        lumber.setLeftTurns(5);
        currentPlayer.addUnfinishedProject(lumber);
        String temp = "the process of " + "making a "+"LumberMill"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a Lumber Mill started";
    }

    public static String makingPasture() {
        if (!currentPlayer.getAchievedTechnologies().get("AnimalHusbandry")) {
            return "you have not achieved the required technology to build a Pasture";
        }
        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a worker first";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }
        if (!isConstructionPossible()) {
            return "you can not have two Improvements in one tile";
        }
        if (!UnitController.getSelectedUnit().getCurrentHex().getTerrain().getName().matches("Desert||Plain||Grassland||Tundra||Hills")) {
            return "you can not build a Pasture on this tile";
        }

        Improvement Pasture = new Improvement("Pasture", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        Pasture.setLeftTurns(5);
        currentPlayer.addUnfinishedProject(Pasture);
        String temp = "the process of " + "making a "+"Pasture"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a Pasture started";
    }

    public static String makingCamp() {
        if (!currentPlayer.getAchievedTechnologies().get("Trapping")) {
            return "you have not achieved the required technology to build a Camp";
        }
        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a worker first";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }
        if (!isConstructionPossible()) {
            return "you can not have two Improvements in one tile";
        }
        if (!UnitController.getSelectedUnit().getCurrentHex().getTerrain().getName().matches("Jungle||Tundra||Hills||Plain")) {
            return "you can not build a Camp on this tile";
        }

        Improvement camp = new Improvement("camp", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        camp.setLeftTurns(5);
        currentPlayer.addUnfinishedProject(camp);
        String temp = "the process of " + "making a "+"Camp"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a camp started";
    }

    public static String makePlantation() {
        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a worker first";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }
        if (!isConstructionPossible()) {
            return "you can not have two Improvements in one tile";
        }

        Improvement plantation = new Improvement("Plantation", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        plantation.setLeftTurns(5);
        currentPlayer.addUnfinishedProject(plantation);
        String temp = "the process of " + "making a "+"Plantation"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a plantation started";
    }

    public static String makeQuarry() {
        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a worker first";
        }
        if (!isConstructionPossible()) {
            return "you can not have two Improvements in one tile";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }
        Improvement quarry = new Improvement("Quarry", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        quarry.setLeftTurns(5);
        currentPlayer.addUnfinishedProject(quarry);
        String temp = "the process of " + "making a "+"Quarry"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a quarry started";
    }

    public static String makeFactory() {
        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Worker)) {
            return "select a worker first";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().getOwner().equals(currentPlayer))
        {
            return "this tile is not yours";
        }
        if (!isConstructionPossible()) {
            return "you can not have two Improvements in one tile";
        }
        Improvement factory = new Improvement("Factory", UnitController.getSelectedUnit(), UnitController.getSelectedUnit().getCurrentHex());
        factory.setLeftTurns(5);
        currentPlayer.addUnfinishedProject(factory);
        String temp = "the process of " + "making a "+"Factory"+" Improvement" + " on the hex: x=" + UnitController.getSelectedUnit().getCurrentHex().getX() + " y=" + UnitController.getSelectedUnit().getCurrentHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for building a Factory started";
    }

    public static void checkTimeVariantProcesses() {


        ArrayList<Construction> deleteConstruction=new ArrayList<Construction>();


        for (Construction process : currentPlayer.getUnfinishedProjects()) {


            if (process.getLeftTurns() == 0) {


                if (process instanceof Unit) {
                    UnitController.makeUnit(process.getName(), process.getHex(),"production");
                    deleteConstruction.add(process);
                    String temp = "the process of " + process.getName() + " on the hex: x=" + process.getHex().getX() + " y=" + process.getHex().getY() + " finished successfullly";
                    currentPlayer.addNotifications(temp);
                    currentPlayer.setNotificationsTurns(turn);
                    continue;
                }

                process.getWorker().setOrdered(false);


                process.build(null);
                String temp = "the process of " + process.getName() + "on the hex: x=" + process.getHex().getX() + " y=" + process.getHex().getY() + " finished successfullly";
                currentPlayer.addNotifications(temp);
                currentPlayer.setNotificationsTurns(turn);
                deleteConstruction.add(process);
            } else if (process instanceof Unit) {
                Unit previewUnit = new Unit(process.getName(), process.getHex(), currentPlayer);
                currentPlayer.decreaseProduction(previewUnit.getNeededProduction() / process.getLeftTurns());
                process.decreaseLeftTurns();
            }else {
                process.decreaseLeftTurns();
            }


        }

        for(Construction temp:deleteConstruction)
        {
            currentPlayer.getUnfinishedProjects().remove(temp);
        }

    }

    public static String activateUnit() {
        if (UnitController.getSelectedUnit() == null) {
            return "select a unit first";
        }

        UnitController.getSelectedUnit().changeUnitState(UnitState.Active);
        return "unit activated successfully";
    }

    public static String demographicScreen() {
        StringBuilder demographics = new StringBuilder();
        demographics.append("total wealth: " + currentPlayer.getGold() + "\n");
        demographics.append("total number of military units: " + currentPlayer.getMilitaries().size() + "\n");
        demographics.append("total number of civilian units: " + currentPlayer.getCivilians().size() + "\n");
        demographics.append("population: " + currentPlayer.getPopulation());


        return demographics.toString();
    }

    public static String deleteConstruction() {
        if (GameController.getSelectedHex() == null) {
            return "select a tile first";
        }


        for (Construction construction : currentPlayer.getUnfinishedProjects()) {


            if (construction.getHex().equals(GameController.getSelectedHex())) {
                currentPlayer.getUnfinishedProjects().remove(construction);
                return "construction was deleted successfully";
            }


        }


        return "this tile does not have an on going construction";
    }

    public static Boolean isConstructionPossible() {
        for (Construction temp : UnitController.getSelectedUnit().getCurrentHex().getImprovement()) {
            if (temp.getName().matches("Road||Railroad")) {
                return false;
            }
        }

        for (Construction temp : currentPlayer.getUnfinishedProjects()) {
            if (UnitController.getSelectedUnit().getCurrentHex().equals(temp.getHex())&&!(temp instanceof Unit)) {
                return false;
            }
        }


        return true;
    }

    public static String stopWorkingOnTechnology() {
        currentPlayer.addArchivedTechnology(currentPlayer.getCurrentResearch());
        currentPlayer.getUnfinishedProjects().remove(currentPlayer.getCurrentResearch());
        currentPlayer.setCurrentResearch(null);
        return "stopped working on this technology";
    }

    public static String startWorkingOnTechnology(String name) {
        if (!currentPlayer.getAchievedTechnologies().containsKey(name))
            return "there is no such technology";
        HashMap<String, Boolean> achievedTech = currentPlayer.getAchievedTechnologies();
        Technology technology = new Technology(name);
        ArrayList<String> neededTech = technology.getNeededPreviousTechnologies();

        if (currentPlayer.getCurrentResearch() != null)
            return "you are already working on a technology";

        for (String tech : neededTech) {
            if (!achievedTech.get(tech))
                return "you haven't achieved required technologies for this tech";
        }

        //TODO: set left turns eine adam
        technology.setLeftTurns(5);
        for (int i = 0; i < currentPlayer.getArchivedTechnologies().size(); i++) {
            if (currentPlayer.getArchivedTechnologies().get(i).getName().equals(name)) {
                technology.setLeftTurns(currentPlayer.getArchivedTechnologies().get(i).getLeftTurns());
                currentPlayer.getArchivedTechnologies().remove(i);
                break;
            }
        }
        currentPlayer.addUnfinishedProject(technology);
        return "started working on this technology";
    }

    public static String buyUnit(String name)
    {
        if (GameController.getSelectedHex() == null) {
            return "select a tile first";
        }
        if(GameController.getSelectedHex().getOwner() != GameController.getCurrentPlayer()) {
             return "this tile does not belong to you";
         }
         if(GameController.getSelectedHex().getCapital() == null){
             return "this is not capital";
         }

            if (name.equals("Settler") && GameController.getCurrentPlayer().getHappiness() < 0)
            return "your civilization is unhappy";

        if (!InitializeGameInfo.unitInfo.containsKey(name)) {
            return "invalid unit name";
        }
        String type=InitializeGameInfo.unitInfo.get(name).split(" ")[7];
        GameController.setSelectedCity(GameController.getSelectedHex().getCity());
        

        if(type.matches("Settler||Worker"))
        {
            for(Construction temp:currentPlayer.getUnfinishedProjects())
            {
                if(temp instanceof Unit&&temp.getName().matches("Worker||Settler"))
                {
                    return "you have already started the process of another civilian unit on this tile\nyou can not have two civilian units on a tile";
                }
            }

            if(GameController.getSelectedHex().getCivilianUnit()!=null)
                return "you can't have two Civilian units in a city";
        }else if((!type.matches("Settler||Worker"))) {
           
            for(Construction temp:currentPlayer.getUnfinishedProjects())
            {
                if(temp instanceof Unit&&!temp.getName().matches("Worker||Settler"))
                {
                    return "you have already started the process of another military unit on this tile\nyou can not have two civilian units on a tile";
                }
            }
            if(GameController.getSelectedHex().getMilitaryUnit()!=null)
            {
                 return "you can't have two Military units in a city";
            }
        }
            if (name.equals("Settler") && GameController.getCurrentPlayer().getHappiness() < 0)
            return "your civilization is unhappy";

        if (!InitializeGameInfo.unitInfo.containsKey(name)) {
            return "invalid unit name";
        }
        Unit newUnit = new Unit(name, GameController.getSelectedHex(), GameController.getCurrentPlayer());
        if(newUnit.getNeededTech()!=null&&!GameController.getCurrentPlayer().getAchievedTechnologies().get(newUnit.getNeededTech()))
        {
            return "you have not achieved the needed technology to make this unit";
        }

        boolean check=false;
        for(Hex hex: GameController.getSelectedHex().getCity().getHexs())
        {
            if(newUnit.getNeededResource()!=null&&hex.getResource().getName().equals(newUnit.getNeededResource()))
            {
                check=true;
            }
        }

        if(!check&&newUnit.getNeededResource()!=null)
        {
            return "you don't have the needed resource to make this unit";
        }

        if(newUnit.getCost()>currentPlayer.getGold())
        {
            return "you don't have enough money";
        }

        GameController.getCurrentPlayer().decreaseGold(newUnit.getCost());
        UnitController.makeUnit(name, GameController.getSelectedHex(),"gold");
        return "Unit created successfully";

    }

}