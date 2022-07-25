package project.civilization.controllers;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.*;
import project.civilization.models.Game;
import project.civilization.models.Player;
import project.civilization.models.gainable.Construction;
import project.civilization.models.gainable.Technology;
import project.civilization.models.maprelated.*;
import project.civilization.models.units.*;


import java.io.IOException;
import java.util.*;

public class GameController {

    private static ArrayList<Player> players;
    private static int turn;
    private static  World world ;
    private static int[] mapBoundaries;
    private static Hex[][] hex;
    private static Player currentPlayer;
    private static  ArrayList<Civilian> allCivilians;
    private static ArrayList<Military> allMilitaries;
    private static Hex selectedHex;
    private static City selectedCity;
    private static int playerCount;
    private static ArrayList<Game> allGames ;

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
    public static void setSelectedCityByName(String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.setSelectedCity.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    public static City getSelectedCity() {
        return selectedCity;
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
        /*hex[0][0] = new Hex(0,0,new Terrain("Plain"),null);
        hex[0][0].setState(HexState.Visible,players.get(0));*/
    }
    public static void startGame(){
        for (int i = 0; i < world.getHexInHeight(); i++) {
            for (int j = 0; j < world.getHexInWidth(); j++) {
                if(hex[i][j].getState(currentPlayer).equals(HexState.Visible) &&
                        !hex[i][j].getTerrain().getName().matches("Mountain|Ocean")){
                    UnitController.makeUnit("Worker", hex[i][j], "gold");
                    City newCity = new City(GameController.getCurrentPlayer(), "fuck",
                            hex[i][j]);

                    UnitController.makeUnit("Warrior", hex[i][j], "gold");
                    return;
                }
            }
        }
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
        string[hexHeight * x + 2 + align][(hexWidth - 2) * y + 4] = color + hex.getX() % 10 + Color.ANSI_RESET.getCharacter();
        string[hexHeight * x + 2 + align][(hexWidth - 2) * y + 8] = color + hex.getY() % 10 + Color.ANSI_RESET.getCharacter();
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
        if (isOutOfBounds(x, y)) {
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
        if (hex.hasRailRoad()) detail.append(" has railroad");
        if (hex.hasRoad()) detail.append(" has road");
        if (hex.isPillaged()) detail.append((" is pillaged"));
        if (hex.getCapital() != null) detail.append(" Capital of: ").append(hex.getCapital().getName());
        if (hex.getCity() != null) detail.append("tile of").append(hex.getCity().getName());
        detail.append("\n");
        detail.append(hex.getState(currentPlayer));

        if (hex.getHasCitizen()) detail.append("has a working citizen");
        if (hex.getOwner() != null) {
            detail.append(" Owner: " + hex.getOwner().getName());
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

    private static void resetOrdersAndOrdered() {
        for (Military military : currentPlayer.getMilitaries()) {
            military.setOrdered(false);
        }
        for (Civilian civilian : currentPlayer.getCivilians()) {
            civilian.setOrdered(false);
        }
        for (Construction construction : currentPlayer.getUnfinishedProjects()) {
            if (!(construction instanceof Technology))
                construction.getWorker().setOrdered(true);
        }
        for (Movement movement : UnitController.getUnfinishedMovements()) {
            movement.getUnit().setOrdered(true);
        }
    }

    private static void heal() {
        for (Military military : currentPlayer.getMilitaries()) {
            if (military.getHealth() < military.getMaxHealth()) {
                military.increaseHealth(1);
            }
            if (military.getState() == UnitState.FortifiedUntilHeal) {
                military.increaseHealth(2);
            }
        }

        for (City city : currentPlayer.getCities()) {
            if (city.getHitPoint() < city.getMaxHitPoint()) {
                city.increaseHitPoint(1);
            }
        }
    }

    public static void feedCitizens() {
        for (City city : currentPlayer.getCities()) {
            city.decreaseChangingFood(city.getPopulation());
            city.increaseChangingFood(city.getNumberOfUnemployedCitizen());
        }
    }

    public static void addGoldFromWorkersActivities() {
        //working on tiles with river, beach and oasis increases gold
        for (City city : currentPlayer.getCities()) {
            for (Hex hex : city.getHexs()) {
                if (hex.getHasCitizen() && (hex.isNextToAnyRiver() || hex.getTerrain().getName().equals(TerrainNames.Coast.getCharacter())
                        || (hex.getFeature() != null && hex.getFeature().getName().equals(FeatureNames.Oasis.getCharacter()))))
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

    public static void productionFromTerrains() {
        for (City city : currentPlayer.getCities()) {
            currentPlayer.increaseProduction(city.getProduction());
        }
    }

    public static void goldFromTerrains() {
        for (City city : currentPlayer.getCities()) {
            currentPlayer.increaseGold(city.getGold());
        }
    }

    public static String changeTurn() {
        // TODO: 7/14/2022 : 
/*        String unitOrders = unitActions();
        if (unitOrders != null) return unitOrders;*/
        int goldPerTurn = 0;
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            goldPerTurn += temp.getGold();
        }
        currentPlayer.increaseGold(goldPerTurn);
        goldFromTerrains();
        productionFromTerrains();
        addFoodFromTiles();
        feedCitizens();
        growCity();
        //healUnits and cities(1hit point)//handel tarmim asib
        heal();
        // TODO: 7/14/2022 : 
        //resetOrdersAndOrdered();
        UnitController.changeTurn();
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
        if (playerCount == players.size() - 1) {
            playerCount = 0;
            turn++;
        } else {
            playerCount++;
        }
        currentPlayer = players.get(playerCount);
        UnitController.setCurrentPlayer(currentPlayer);
        return "Turn changed successfully \n player:" + currentPlayer.getName();
    }

    public static void addFoodFromTiles() {
        currentPlayer.decreaseFood(currentPlayer.getFood());
        for (City city : currentPlayer.getCities()) {
            city.increaseChangingFood(city.getFood());
            currentPlayer.increaseFood(city.getFood());
        } //reset civilization food each turn
    }

    public static void growCity() {
        for (Construction project : currentPlayer.getUnfinishedProjects()) {
            if (project instanceof Unit && project.getName().equals("Settler"))
                ((Unit) project).getCurrentHex().getCity().decreaseFood(((Unit) project).getCurrentHex().getTerrain().getFood());
        }//food production will stop if a settler unit is being implemented

        for (City city : currentPlayer.getCities()) {
            if (currentPlayer.getHappiness() >= 0) { //city growth stops if people are unhappy
                if (city.getChangingFood() / currentPlayer.getFoodForNewCitizen() > 5)
                    currentPlayer.setFoodForNewCitizen(currentPlayer.getFoodForNewCitizen() * 2);
                implementNewCitizens(city, city.getChangingFood() / currentPlayer.getFoodForNewCitizen());
            }
        }
    }

    public static void implementNewCitizens(City city, int amount) {
        city.increasePopulation(city.getFood() / currentPlayer.getFoodForNewCitizen());//city growth
        for (Hex hex : city.getHexs()) {
            if (!hex.getHasCitizen() && amount > 0) {
                hex.setHasCitizen(true);
                city.increaseFood(hex.getTerrain().getFood());
                city.increaseGold(hex.getTerrain().getGold());
                city.increaseProduction(hex.getTerrain().getProduction());
                amount--;
            }
        }
        city.increaseNumberOfUnemployedCitizen(amount);
    }

    private static String unitActions() {
        for (Military military : currentPlayer.getMilitaries()) {
            if (military.getState() == UnitState.Alert && !military.isOrdered()) {
                if (enemyIsNear(getDirection(military.getY()), military.getX(), military.getY())) {
                    return "unit in " + military.getX() + "," + military.getY() + "coordinates needs order";
                }
            }
            if ((military.getState() == UnitState.Active) && !military.isOrdered()) {
                return "unit in " + military.getX() + "," + military.getY() + "coordinates needs order";
            }
        }
        for (Civilian civilian : currentPlayer.getCivilians()) {
            if ((civilian.getState() == UnitState.Active) && !civilian.isOrdered()) {
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
            if (hex[x + tempDirection[i][0]][y + tempDirection[i][1]].getMilitaryUnit() != null &&
                    hex[x + tempDirection[i][0]][y + tempDirection[i][1]].getMilitaryUnit().getOwner() != currentPlayer) {
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

    public static String cheatCityProduction(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatCityProduction.getCharacter());
            json.put("amount", amount);
            json.put("cityName", cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatGold(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatGold.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatHappiness(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatHappiness.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatPopulation(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatPopulation.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatProduction(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatProduction.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatScore(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatScore.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // public static String cheatTurn(int amount) {
    //     turn += amount-1;
    //     changeTurn();
    //     return "turn increased successfully";
    // }
    public static String cheatMP(int amount, int x, int y, String type) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatMP.getCharacter());
            json.put("amount", amount);
            json.put("x",x);
            json.put("y",y);
            json.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatMeleeCombatStrength(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatMeleeCombatStrength.getCharacter());
            json.put("amount", amount);
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatRangedCombatStrength(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatRangedCombatStrength.getCharacter());
            json.put("amount", amount);
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatCityFood(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatCityFood.getCharacter());
            json.put("amount", amount);
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatCityHitPoint(int amount, String cityName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatCityHitPoint.getCharacter());
            json.put("amount", amount);
            json.put("cityName",cityName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String cheatTrophy(int amount) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.CHEAT.getCharacter());
            json.put("action", Actions.cheatTrophy.getCharacter());
            json.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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

    public static String militaryPanel() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.militaryPanel.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public static String citiesPanel() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.citiesPanel.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }

    public static String unitsPanel() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.UNITLISTPANEL.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
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

            ArrayList<Construction> technologies = new ArrayList<Construction>();

            economicInfo.append(count + ") cityname: " + temp.getName() + "\n");
            economicInfo.append("\t\tpoplulation: " + temp.getPopulation() + "\n");
            economicInfo.append("\t\tmelee defensive power: " + temp.getMeleeCombatStrength() + "\n");
            economicInfo.append("\t\tranged defensive power: " + temp.getRangedCombatStrength() + "\n");
            economicInfo.append("\t\tfood: " + temp.getFood() + "\n");
            economicInfo.append("\t\tgold " + temp.getGold() + "\n");
            economicInfo.append("\t\ttrophy: " + temp.getTrophy() + "\n");
            economicInfo.append("\t\tproduction: " + temp.getProduction() + "\n");

            for (Construction construction : currentPlayer.getUnfinishedProjects()) {
                if (!(construction instanceof Technology) && construction.getHex().getCity().getName().equals(temp.getName())) {
                    economicInfo.append("\t\tpending project: " + construction.getName() + "-> turn left: " + construction.getLeftTurns() + "\n");
                }
                if (construction instanceof Technology) {
                    technologies.add(construction);
                }

            }

            if (!technologies.isEmpty()) {
                for (Construction tech : technologies) {
                    economicInfo.append("pending technology: " + tech.getName());
                }
            }
        }


        return economicInfo.toString();

    }

    public static void checkTimeVariantProcesses() {


        ArrayList<Construction> deleteConstruction = new ArrayList<Construction>();


        for (Construction process : currentPlayer.getUnfinishedProjects()) {


            if (process.getLeftTurns() == 0) {


                if (process instanceof Unit) {
                    UnitController.makeUnit(process.getName(), process.getHex(), "production");
                    deleteConstruction.add(process);
                    String temp = "the process of " + process.getName() + " on the hex: x=" + process.getHex().getX() + " y=" + process.getHex().getY() + " finished successfullly";
                    currentPlayer.addNotifications(temp);
                    currentPlayer.setNotificationsTurns(turn);
                    continue;
                }

                if (!(process instanceof Technology))
                    process.getWorker().setOrdered(false);

                String temp;
                process.build(null);
                if (process instanceof Technology) {
                    temp = process.getName() + " technology has been achieved successfully";
                } else {
                    temp = "the process of " + process.getName() + "on the hex: x=" + process.getHex().getX() + " y=" + process.getHex().getY() + " finished successfully";
                }
                currentPlayer.addNotifications(temp);
                currentPlayer.setNotificationsTurns(turn);
                deleteConstruction.add(process);
            } else if (process instanceof Unit) {
                Unit previewUnit = new Unit(process.getName(), process.getHex(), currentPlayer);
                currentPlayer.decreaseProduction(previewUnit.getNeededProduction() / process.getLeftTurns());
                process.decreaseLeftTurns();
            } else {
                process.decreaseLeftTurns();
            }


        }

        for (Construction temp : deleteConstruction) {
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
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.demographicScreen.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
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
            if (UnitController.getSelectedUnit().getCurrentHex().equals(temp.getHex()) && !(temp instanceof Unit)) {
                return false;
            }
        }


        return true;
    }

    public static void stopWorkingOnTechnology() {
        currentPlayer.addArchivedTechnology(currentPlayer.getCurrentResearch());
        currentPlayer.getUnfinishedProjects().remove(currentPlayer.getCurrentResearch());
        currentPlayer.setCurrentResearch(null);
    }

    public static String startWorkingOnTechnology(String name) {
        if (!currentPlayer.getAchievedTechnologies().containsKey(name))
            return "there is no such technology";
        HashMap<String, Boolean> achievedTech = currentPlayer.getAchievedTechnologies();
        Technology technology = new Technology(name, currentPlayer);
        ArrayList<String> neededTech = technology.getNeededPreviousTechnologies();

        if (currentPlayer.getCurrentResearch() != null)
            return "you are already working on a technology";

        for (String tech : neededTech) {
            if (!achievedTech.get(tech))
                return "you haven't achieved required technologies for this tech";
        }

        if (currentPlayer.getProduction() == 0)
            return "your civilization production is zero at the moment";

        int turns = technology.getCost() / currentPlayer.getProduction();
        technology.setLeftTurns(turns);
        for (int i = 0; i < currentPlayer.getArchivedTechnologies().size(); i++) {
            if (currentPlayer.getArchivedTechnologies().get(i).getName().equals(name)) {
                technology.setLeftTurns(currentPlayer.getArchivedTechnologies().get(i).getLeftTurns());
                currentPlayer.getArchivedTechnologies().remove(i);
                break;
            }
        }
        currentPlayer.addUnfinishedProject(technology);
        StringBuilder output = new StringBuilder("started working on this technology");
        output.append("turns required: ");
        output.append(turns);
        currentPlayer.getNotifications().add(output.toString());
        return output.toString();
    }

    public static String buyUnit(String name) {
        if (GameController.getSelectedHex() == null) {
            return "select a tile first";
        }
        if (GameController.getSelectedHex().getOwner() != GameController.getCurrentPlayer()) {
            return "this tile does not belong to you";
        }
        if (GameController.getSelectedHex().getCapital() == null) {
            return "this is not capital";
        }

        if (name.equals("Settler") && GameController.getCurrentPlayer().getHappiness() < 0)
            return "your civilization is unhappy";

        if (!InitializeGameInfo.unitInfo.containsKey(name)) {
            return "invalid unit name";
        }
        String type = InitializeGameInfo.unitInfo.get(name).split(" ")[7];
        GameController.setSelectedCity(GameController.getSelectedHex().getCity());


        if (type.matches("Settler||Worker")) {
            for (Construction temp : currentPlayer.getUnfinishedProjects()) {
                if (temp instanceof Unit && temp.getName().matches("Worker||Settler")) {
                    return "you have already started the process of another civilian unit on this tile\nyou can not have two civilian units on a tile";
                }
            }

            if (GameController.getSelectedHex().getCivilianUnit() != null)
                return "you can't have two Civilian units in a city";
        } else if ((!type.matches("Settler||Worker"))) {

            for (Construction temp : currentPlayer.getUnfinishedProjects()) {
                if (temp instanceof Unit && !temp.getName().matches("Worker||Settler")) {
                    return "you have already started the process of another military unit on this tile\nyou can not have two civilian units on a tile";
                }
            }
            if (GameController.getSelectedHex().getMilitaryUnit() != null) {
                return "you can't have two Military units in a city";
            }
        }
        if (name.equals("Settler") && GameController.getCurrentPlayer().getHappiness() < 0)
            return "your civilization is unhappy";

        if (!InitializeGameInfo.unitInfo.containsKey(name)) {
            return "invalid unit name";
        }
        Unit newUnit = new Unit(name, GameController.getSelectedHex(), GameController.getCurrentPlayer());
        if (newUnit.getNeededTech() != null && !GameController.getCurrentPlayer().getAchievedTechnologies().get(newUnit.getNeededTech())) {
            return "you have not achieved the needed technology to make this unit";
        }

        boolean check = false;
        for (Hex hex : GameController.getSelectedHex().getCity().getHexs()) {
            if (newUnit.getNeededResource() != null && hex.getResource().getName().equals(newUnit.getNeededResource())) {
                check = true;
            }
        }

        if (!check && newUnit.getNeededResource() != null) {
            return "you don't have the needed resource to make this unit";
        }

        if (newUnit.getCost() > currentPlayer.getGold()) {
            return "you don't have enough money";
        }

        GameController.getCurrentPlayer().decreaseGold(newUnit.getCost());
        UnitController.makeUnit(name, GameController.getSelectedHex(), "gold");
        return "Unit created successfully";

    }
    public static String isAchieved(String name) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.ISANACHIVEDTECK.getCharacter());
            json.put("techName", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return null;
    }
    public static String getLastTechnology() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getLastTechnology.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }
    public static ArrayList<String> getAvailableTechs() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getAvailableTechsArray.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
        }
        return null;
    }

    public static String changeResearch(String techName) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.changeResearch.getCharacter());
            json.put("techName", techName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public static String cityScreen(String cityName)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.cityScreen.getCharacter());
            json.put("cityName", cityName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";

    }

    public static ArrayList<String> getAvailableWorks() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETAVAILABLEWORKS.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }
    public static void orderToWorker(String temp) {
    }

    public static String getPaneDetails(int mapBoundary0, int mapBoundary1, int mapBoundary2, int mapBoundary3) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETPANEDETAILS.getCharacter());
            json.put("mapBoundary0",mapBoundary0);
            json.put("mapBoundary1",mapBoundary1);
            json.put("mapBoundary2",mapBoundary2);
            json.put("mapBoundary3",mapBoundary3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return "something is wrong";
        }
    }

    public static int getHexInHeight() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETHexInHeight.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return Integer.parseInt(CivilizationApplication.dataInputStream.readUTF());
        } catch (IOException x) {
            x.printStackTrace();
            return 0;
        }
    }

    public static int getHexInWidth() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETHexInWidth.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return Integer.parseInt(CivilizationApplication.dataInputStream.readUTF());
        } catch (IOException x) {
            x.printStackTrace();
            return 0;
        }
    }

    public static String getTerrainNames() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GetTerrainNames.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return "something is wrong";
        }
    }

    public static String getHexDetails(int i, int j) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GetHexDetails.getCharacter());
            json.put("i", i);
            json.put("j", j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
            return "something is wrong";
        }
    }
    public static void setSelectedHex(int i, int j) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.SELECTILE.getCharacter());
            json.put("i", i);
            json.put("j",j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static String getUnitInformation(int i, int j,String type) {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.GETUNITINFORMATION.getCharacter());
            json.put("i", i);
            json.put("j",j);
            json.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            return CivilizationApplication.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }


    public static ArrayList<String> getNotifications() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getNotifications.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Integer> getNotificationsTurns() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getNotificationsTurns.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getPlayerCitiesNames() {
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.GAMEMenu.getCharacter());
            json.put("action", Actions.getPlayerCitiesNames.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CivilizationApplication.dataOutputStream.writeUTF(json.toString());
            CivilizationApplication.dataOutputStream.flush();
            String res = CivilizationApplication.dataInputStream.readUTF();
            return new Gson().fromJson(res, new TypeToken<ArrayList<String>>() {
            }.getType());
        } catch (IOException x) {
            x.printStackTrace();
            return null;
        }
    }
}