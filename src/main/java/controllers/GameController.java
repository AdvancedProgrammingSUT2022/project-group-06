package controllers;

import enums.Color;
import enums.HexState;
import enums.UnitState;
import models.Player;
import models.gainable.Building;
import models.gainable.Construction;
import models.gainable.Improvement;
import models.gainable.Technology;
import models.maprelated.City;
import models.maprelated.Hex;
import models.maprelated.World;
import models.units.Civilian;
import models.units.Combatable;
import models.units.Military;
import models.units.Unit;
import models.units.Worker;
import models.units.*;

import java.net.URI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.util.*;

public class GameController {

    private static ArrayList<Player> players = InitializeGameInfo.getPlayers();
    private static int turn;
    private static World world;
    private static int[] mapBoundaries;
    private static Hex[][] hex;
    private static Player currentPlayer;
    private static ArrayList<Civilian> allCivilians = new ArrayList<Civilian>();
    private static ArrayList<Military> allMilitaries = new ArrayList<Military>();
    private static Hex selectedHex;
    private static City selectedCity;
    private static int playerCount;
    private static ArrayList<Combatable> hurtElements;


    public static int getTurn()
    {
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
        world = InitializeGameInfo.getWorld();
        turn = 0;
        hex = world.getHex();
        mapBoundaries = new int[]{0, 3, 0, 6};
        removeOwnerOfHexes();
       /* City city = makeCityForTesting(0, 0);
        city.addHex(world.getHex()[0][1]);
        makeCityForTesting(0, 4);
        makeCityForTesting(1, 2);
        world.getHex()[0][1].setState(HexState.Visible, currentPlayer);
        Ranged Archer = new Ranged("Archer", world.getHex()[1][0], players.get(0));
        world.getHex()[1][0].setMilitaryUnit(Archer);
        world.getHex()[1][0].setState(HexState.Visible, currentPlayer);
 */   }

    private static City makeCityForTesting(int x, int y) {
        City city = new City(players.get(1), "Asemaneh", world.getHex()[x][y]);
        world.getHex()[x][y].setCity(city);
        world.getHex()[x][y].setState(HexState.Visible, currentPlayer);
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
        // TODO: 5/8/2022 ask traneh why
/*        List<Military> militaries = allMilitaries;
        for (Military military : militaries) {
            if (military.getCurrentHex().getX() == x && military.getCurrentHex().getY() == y)
                return military;
        }
        return null;*/
    }

    public static Civilian getCiviliansByLocation(int x, int y) {
        return world.getHex()[x][y].getCivilianUnit();
        //todo : ask why?
/*        List<Civilian> civilians = allCivilians;
        for (Civilian civilian : civilians) {
            if (civilian.getCurrentHex().getX() == x && civilian.getCurrentHex().getY() == y)
                return civilian;
        }*/
    }

    private static void heal() {
        //todo: add harm elements to aaray list and add heal units
        for (Combatable combatable : hurtElements) {
            combatable.healPerTurn();
        }
    }

    public static void finishConstruction() {
        for (Construction unfinishedProject : currentPlayer.getUnfinishedProjects()) {
            unfinishedProject.decreaseLeftTurns();
            if (unfinishedProject.getLeftTurns() == 0) {
                if (unfinishedProject instanceof Building)
                    finishBuilding();
                else if (unfinishedProject instanceof Technology)
                    achieveTechnology();
                else if (unfinishedProject instanceof Improvement)
                    finishImprovement();
            }
        }
    }

    public static void finishBuilding() {
        //todo
    }

    public static void achieveTechnology(){
        //todo
    }

    public static void finishImprovement() {
        //todo
    }


    public static String changeTurn() {
        String unitOrders = unitActions();
        if(unitOrders != null)return unitOrders;
        if(playerCount==GameController.getPlayers().size()-1)
        {
            playerCount=0;
            turn ++;
        }else{
            playerCount++;
        }

        currentPlayer = GameController.getPlayers().get(playerCount);
        UnitController.setCurrentPlayer(currentPlayer);
        int goldPerTurn=0;
        for(City temp:GameController.getCurrentPlayer().getCities())
        {
            goldPerTurn+=temp.getGold();
        }
        currentPlayer.increaseGold(goldPerTurn);///////////////////////////////////////////////////
        //todo: complete followings
        //feedUnits and citizens(bikar ye mahsol baghie 2 food)
        //healUnits and cities(1hit point)//handel tarmim asib
        //heal();
        //increase gold food and since(3 capital 1 citizen)...//citizen productions
        //decrease turn of project kavosh, city (UNIT/BUILDING) produce
        finishConstruction();
        //manage harekat chand nobati ye nobat bere jelo
        //handle siege units
        //hazine tamir O negahdari buldings
        //roshd shar ezafe shodan sharvanda
        currentPlayer.decreaseHappiness(1);//happiness decrease as the population grows
        if (currentPlayer.getHappiness() < 0) unhappinessEffects();
        //improvements
        for (Player player : players)
            player.setTrophies(player.getTrophies() + player.getPopulation() + 3); //one trophy for each citizen & 3 for capital
        //reset unit MP
        return "Turn changed successfully";
    }

    private static String unitActions() {
        for (Military military : currentPlayer.getMilitaries()) {
            if (!(military.getState()== UnitState.Sleep) && military.getMP() != 0) {
                if(military.getState()== UnitState.Alert){
                    if(enemyIsNear(getDirection(military.getY()),military.getX(),military.getY()))return "unit in " + military.getX() + "," + military.getY() + "coordinates needs order";
                }else
                    return "unit in " + military.getX() + "," + military.getY() + "coordinates needs order";
            }
        }
        for (Civilian civilian : currentPlayer.getCivilians()) {
            if (!(civilian.getState()== UnitState.Sleep) && civilian.getMP() != 0) {
                return "unit in " + civilian.getX() + "," + civilian.getY() + "coordinates needs order";
            }
        }
        return null;
    }

    private static boolean enemyIsNear(int[][] direction, int x,int y) {
        for (int j = 0; j < direction.length; j++) {
            x = x + direction[j][0];
            y = y + direction[j][1];
            for (int i = 0; i < direction.length; i++) {
                if (!GameController.isOutOfBounds(x + direction[i][0], y + direction[i][1])) {
                    if(hex[x + direction[i][0]][y + direction[i][1]].getMilitaryUnit()!=null&&
                       hex[x + direction[i][0]][y + direction[i][1]].getMilitaryUnit().getOwner()!= currentPlayer);
                    return true;
                }
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
        //todo: stop city growth
        for (int i = 0; i < currentPlayer.getMilitaries().size(); i++) {
            currentPlayer.getMilitaries().get(i).setCombatStrength((int) (currentPlayer.getMilitaries().get(i).getCombatStrength() * 0.75));
        } //combat strength for military units decrease 25%
    }

    public static void happinessDueToLuxuries(String name) {
        for (int i = 0; i < currentPlayer.getCities().size(); i++) {
            for (int j = 0; j < currentPlayer.getCities().get(i).getHexs().size(); j++) {
                if (currentPlayer.getCities().get(i).getHexs().get(j).getResource().getName().equals(name))
                    return;
            }
        }

        currentPlayer.increaseHappiness(1); //new luxuries add to happiness
    }


    public static String cheatGold(int amount) {
        currentPlayer.increaseGold(amount);
        return "gold increased successfully";
    }

    public static String cheatTurn(int amount) {
        turn += amount;
        return "turn increased successfully";
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
    public static String citiesPanel()
    {
        StringBuilder citiesList=new StringBuilder();
        int count=1;
        for(City temp:currentPlayer.getCities())
        {
            citiesList.append(count+") "+temp.getName()+"\n");
            count++;
        }
        return citiesList.toString();
    }

    public static String unitsPanel()
    {
        StringBuilder unitsList=new StringBuilder();
        int count=1;
        for(Unit temp:currentPlayer.getUnits())
        {
            unitsList.append(count+") "+temp.getName()+": "+"state: "+temp.getState()+" x: "+temp.getX()+" y: "+temp.getY()+"\n");
            count++;
        }
        return unitsList.toString();
    }

    public static String notificationHistory()
    {
        StringBuilder notificationsList=new StringBuilder();
        int count=0;
        for(int temp:currentPlayer.getNotificationsTurns())
        {
            notificationsList.append("turn: "+temp+" notification: "+currentPlayer.getNotifications().get(count)+"\n");
            count++;
        }
        return notificationsList.toString();
    }
    public static String economicOverview()
    {
        StringBuilder economicInfo=new StringBuilder();
        int count=1;
        for(City temp: currentPlayer.getCities())
        {
            economicInfo.append(count+") cityname: "+temp.getName()+"\n");
            economicInfo.append("\\t\\tpoplulation: "+temp.getPopulation()+"\n");
            economicInfo.append("\\t\\tmelee defensive power: "+temp.getMeleeDefensivePower()+"\n");
            economicInfo.append("\\t\\tranged defensive power: "+temp.getRangedDefencePower()+"\n");
            economicInfo.append("\\t\\tfood: "+temp.getFood()+"\n");
            economicInfo.append("\\t\\tgold "+temp.getGold()+"\n");
            economicInfo.append("\\t\\ttrophy: "+temp.getTrophy()+"\n");
            //TODO add production

        }

        return economicInfo.toString();

    }

    private static String isMakingMinePossible()
    {
        Hex hex=UnitController.getSelectedUnit().getCurrentHex();
        if(UnitController.getSelectedUnit()==null||(UnitController.getSelectedUnit() instanceof Worker))
        {
            return "select a Worker first";
        }

        if(!currentPlayer.getAchievedTechnologies().get("Mining"))
        {
            return "you have not achieve the Mining technology yet";
        }
        if(hex.getResource()==null||(hex.getResource().getName().equals("Ice||FoodPlains"))||(hex.getTerrain().getName().equals("Mountain||Ocean")))
        {
            return "you can not make a Mine on this tile";
        }
        return null;
    }
    public static String startBuildMine()
    {
        Improvement Mine=new Improvement("Mine",UnitController.getSelectedUnit(),UnitController.getSelectedUnit().getCurrentHex());

        String isPossible;
        if((isPossible=isMakingMinePossible())!=null)
        {
            return isPossible;
        }
        switch(UnitController.getSelectedUnit().getCurrentHex().getFeature().getName())
        {
            case "Jungle":
                Mine.setLeftTurns(13);
                break;
            case "Forest":
                Mine.setLeftTurns(10);
                break;
            case "Marsh":
                Mine.setLeftTurns(12);
                break;
            default:
                Mine.setLeftTurns(6);
                break;
        }

        currentPlayer.addUnfinishedProject(Mine);
        return "process for building a Mine successfully started";
    }

    private static String isMakingFarmPossible(Hex hex)
    {

        if(UnitController.getSelectedUnit()==null||(UnitController.getSelectedUnit() instanceof Worker))
        {
            return "select a Worker first";
        }

        if(hex.getFeature().getName().equals("Ice"))
        {
            return "you can not make a Farm on Ice";
        }

        return null;
    }


    public static String startBuildFarm()
    {
        Improvement Farm=new Improvement("Farm",UnitController.getSelectedUnit(),UnitController.getSelectedUnit().getCurrentHex());

        String isPossible;
        if((isPossible=isMakingMinePossible())!=null)
        {
            return isPossible;
        }
        switch(UnitController.getSelectedUnit().getCurrentHex().getFeature().getName())
        {
            case "Jungle":
                Farm.setLeftTurns(10);
                break;
            case "Forest":
                Farm.setLeftTurns(13);
                break;
            case "Marsh":
                Farm.setLeftTurns(12);;
                break;
            default:
                Farm.setLeftTurns(6);;
                break;
        }

        currentPlayer.addUnfinishedProject(Farm);
        return "process for building a farm successfully started";

    }

    public static void checkTimeVariantProcesses()
    {

        for(Construction process: currentPlayer.getUnfinishedProjects())
        {

            if(process.getLeftTurns()==0)
            {
                if(process instanceof Unit)
                {
                    UnitController.makeUnit(process.getName(), process.getHex());
                    currentPlayer.getUnfinishedProjects().remove(process);
                    continue;
                }
                process.build();
                currentPlayer.getUnfinishedProjects().remove(process);
            }else{
                process.decreaseLeftTurns();
            }
        }

    }

    public static String removeError(String name) {
        if(!(UnitController.getSelectedUnit() instanceof Worker)){
            return "choose a worker";
        }
        if(!Objects.equals(UnitController.getSelectedUnit().getCurrentHex().getFeature().getName(), name)) return "this tile dont have"+name;
        return null;
    }

    public static String removeJungle() {
        String error;
        if((error = removeError("Jungle"))!= null){
            return error;
        }Improvement delete =new Improvement("remove jungle",UnitController.getSelectedUnit(),UnitController.getSelectedUnit().getCurrentHex());
        delete.setLeftTurns(7);
        currentPlayer.addUnfinishedProject(delete);
        return "process for deleting a jungle successfully started";
    }

    public static String removeForest() {
        String error;
        if((error = removeError("forest"))!= null){
            return error;
        }
        Improvement delete =new Improvement("remove forest",UnitController.getSelectedUnit(),UnitController.getSelectedUnit().getCurrentHex());
        delete.setLeftTurns(4);
        currentPlayer.addUnfinishedProject(delete);
        return "process for deleting a forest successfully started";
    }

    public static String removeMarsh() {
        String error;
        if((error = removeError("Marsh"))!= null){
            return error;
        }Improvement delete =new Improvement("remove marsh",UnitController.getSelectedUnit(),UnitController.getSelectedUnit().getCurrentHex());
        delete.setLeftTurns(6);
        currentPlayer.addUnfinishedProject(delete);
        return "process for deleting a marsh successfully started";
    }

    public static String removeRailRoad() {
        if(!(UnitController.getSelectedUnit() instanceof Worker)){
            return "choose a worker";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().hasRailRoad() || !UnitController.getSelectedUnit().getCurrentHex().hasRoad()) {
            return "this tile dont have road or railroad";
        }
        Improvement delete =new Improvement("remove road",UnitController.getSelectedUnit(),UnitController.getSelectedUnit().getCurrentHex());
        delete.setLeftTurns(7);
        currentPlayer.addUnfinishedProject(delete);
        return "process for deleting a road successfully started";
    }

    public static String repair() {
        if(!(UnitController.getSelectedUnit() instanceof Worker)){
            return "choose a worker";
        }
        if(!UnitController.getSelectedUnit().getCurrentHex().isPillaged()) {
            return "this tile is not pillaged";
        }
        Improvement repair =new Improvement("repair",UnitController.getSelectedUnit(),UnitController.getSelectedUnit().getCurrentHex());
        repair.setLeftTurns(3);
        currentPlayer.addUnfinishedProject(repair);
        return "process repairing started";
    }
}
