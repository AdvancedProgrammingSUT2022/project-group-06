package controllers;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.ArrayList;


import enums.HexState;
import models.Player;
import models.maprelated.City;
import models.maprelated.Hex;
import models.units.Settler;
import models.units.Unit;


public class CityController {
    private static final ArrayList<Hex> toBuyTiles = new ArrayList<Hex>();
    private static final Hex[][] hex = GameController.getWorld().getHex();


    public static Player getCurrentPlayer() {
        return GameController.getCurrentPlayer();
    }

    public static void finalizeTrophy(City theCity) {
        int count = 0;
        for (Hex tempHex : theCity.getHexs()) {
            if (tempHex.getHasCitizen()) {
                count++;
            }
        }

        theCity.setTrophy(count + 3);

    }

    public static String showTrophies() {
        StringBuilder trophy = new StringBuilder();
        if (GameController.getSelectedCity() == null) {
            return "select a city first";
        }

        finalizeTrophy(GameController.getSelectedCity());
        trophy.append(GameController.getSelectedCity().getTrophy());
        GameController.setSelectedCity(null);
        return trophy.toString();
    }

    public static String showResources() {
        if (GameController.getSelectedCity() == null) {
            return "select a city first";
        }
        StringBuilder cityResources = new StringBuilder();

        for (Hex hex : GameController.getSelectedCity().getHexs()) {
            if (hex.getResource().getType().equals("Strategic")) {
                cityResources.append("hex: " + "x- " + hex.getX() + " y- " + hex.getY() + " --> " + hex.getResource().getName() + "\n");
            }

        }

        if (cityResources.toString().isEmpty()) {
            return "this city has no resources";
        }


        GameController.setSelectedCity(null);
        return cityResources.toString();


    }


    public static String selectHex(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "invalid x or y";
        }
        if (GameController.getWorld().getHex()[x][y].getState(GameController.getCurrentPlayer())== HexState.FogOfWar) {
            return "he zerangi inja fog of ware";
        }
        GameController.setSelectedHex(GameController.getWorld().getHex()[x][y]);
        return "tile selected";
    }


    public static String selectCity(String name) {
        for (City temp : City.getCities()) {
            if (temp.getName().equals(name)) {
                GameController.setSelectedCity(temp);
                return "city selected successfuly";
            }
        }

        return "no city with this name exists";
    }


    public static String startMakingUnit(String name) {
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


        if(type.matches("Settler||Worker")&&GameController.getSelectedHex().getCivilianUnit()!=null)
        {
            return "you can't have two Civilian units in a city";
        }else if((!type.matches("Settler||Worker"))&&(GameController.getSelectedHex().getMilitaryUnit()!=null)) {
            return "you can't have two Military units in a city";
        }
            if (name.equals("Settler") && GameController.getCurrentPlayer().getHappiness() < 0)
            return "your civilization is unhappy";

        if (!InitializeGameInfo.unitInfo.containsKey(name)) {
            return "invalid unit name";
        }
        Unit newUnit = new Unit(name, GameController.getSelectedHex(), GameController.getCurrentPlayer());
        if(newUnit.getNeededTech()!=null&&GameController.getCurrentPlayer().getAchievedTechnologies().get(newUnit.getNeededTech()))
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

        if (GameController.getCurrentPlayer().getGold() >= newUnit.getCost()) {
            UnitController.makeUnit(name, GameController.getSelectedHex());
            return "Unit created successfully";
        }

        int goldPerTurn = 0;
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            goldPerTurn += temp.getGold();
        }

        Unit unit = new Unit(name, GameController.getSelectedHex(), GameController.getCurrentPlayer());

        if (newUnit.getCost() < goldPerTurn) {
            unit.setLeftTurns(1);
            ;
        } else if (goldPerTurn == 0) {
            return "you have no income so it's impossible to start making a unit right now";
        } else {
            unit.setLeftTurns((newUnit.getCost() / goldPerTurn) + 1);
        }


        GameController.getCurrentPlayer().addUnfinishedProject(unit);

        String temp = "the process of " + "making a "+name+" unit" + " on the hex: x=" + GameController.getSelectedHex().getX() + " y=" + GameController.getSelectedHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for builing an unit started successfully";
    }


    public static ArrayList<Hex> getToBuyTiles() {
        return toBuyTiles;
    }


    public static String buildCity(String name) {

        if (UnitController.getSelectedUnit() == null || !(UnitController.getSelectedUnit() instanceof Settler)) {
            return "choose a settler first";
        }
        if (UnitController.getSelectedUnit().getCurrentHex().getTerrain().getName().matches("Ocean||Mountain")) {
            return "you can not build a city on this tile";
        }
        for (City temp : City.getCities()) {
            if (temp.getHexs().contains(UnitController.getSelectedUnit().getCurrentHex())) {
                return "this hex is already part of a city";
            }
            if (temp.getName().equals(name)) {
                return "a city with this name already exists";
            }
        }

        City newCity = new City(GameController.getCurrentPlayer(), name, UnitController.getSelectedUnit().getCurrentHex());
        GameController.getCurrentPlayer().decreaseHappiness(1); //happiness decrease as num of cities increase
        if (GameController.getCurrentPlayer().getHappiness() < 0) GameController.unhappinessEffects();
        City.addCities(newCity);
        UnitController.getSelectedUnit().getCurrentHex().setCity(newCity);
        GameController.getCurrentPlayer().addCity(newCity);
        UnitController.getSelectedUnit().setMP(0);
        return "new city created successfully";
    }


    public static String presaleTiles() {

        if (GameController.getSelectedCity() == null) {
            return null;
        }

        StringBuilder availableHexs = new StringBuilder();

        int counter = 0;

        for (Hex temp : GameController.getSelectedCity().getHexs()) {
            Hex[][] hex = GameController.getWorld().getHex();
            int x = temp.getX();
            int y = temp.getY();
            ArrayList<Hex> sixNeighborHexs = new ArrayList<Hex>();

            if (y % 2 != 0) {
                if (x + 1 < 10 && hex[x + 1][y].getOwner() == null&& (!hex[x+1][y].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x+1][y].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x + 1][y]);
                }

                if (x - 1 >= 0 && hex[x - 1][y].getOwner() == null&& (!hex[x-1][y].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x-1][y].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x - 1][y]);
                }
                if (y - 1 >= 0 && hex[x][y - 1].getOwner() == null&& (!hex[x][y-1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x][y-1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x][y - 1]);
                    if (x + 1 < 10 && hex[x + 1][y - 1].getOwner() == null&& (!hex[x+1][y-1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x+1][y-1].getTerrain().getName().matches("Mountain||Ocean"))) {
                        sixNeighborHexs.add(hex[x + 1][y - 1]);
                    }
                }
                if (y + 1 < 10 && hex[x][y + 1].getOwner() == null && (!hex[x][y+1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x][y+1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x][y + 1]);
                    if (x + 1 < 10 && hex[x][y + 1].getOwner() == null&& (!hex[x+1][y+1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x+1][y+1].getTerrain().getName().matches("Mountain||Ocean"))) {
                        sixNeighborHexs.add(hex[x + 1][y + 1]);
                    }
                }
            }
            if (y % 2 == 0) {
                if (x + 1 < 10 && hex[x + 1][y].getOwner() == null&&(!hex[x+1][y].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x+1][y].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x + 1][y]);
                }

                if (x - 1 >= 0 && hex[x - 1][y].getOwner() == null&&(!hex[x-1][y].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x-1][y].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x - 1][y]);
                }
                if (y - 1 >= 0 && hex[x][y - 1].getOwner() == null&&(!hex[x][y-1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x][y-1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x][y - 1]);
                    if (x - 1 >= 0 && hex[x - 1][y - 1].getOwner() == null&& (!hex[x-1][y-1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x-1][y-1].getTerrain().getName().matches("Mountain||Ocean"))) {
                        sixNeighborHexs.add(hex[x - 1][y - 1]);
                    }
                }
                if (y + 1 < 10 && hex[x][y + 1].getOwner() == null&&(!hex[x][y+1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x][y+1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x][y + 1]);
                    if (x - 1 >= 0 && hex[x - 1][y + 1].getOwner() == null&&(!hex[x-1][y+1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar))&&(!hex[x-1][y+1].getTerrain().getName().matches("Mountain||Ocean"))) {
                        sixNeighborHexs.add(hex[x - 1][y + 1]);
                    }
                }
            }

            for (Hex tempHex : sixNeighborHexs) {
                counter++;
                toBuyTiles.add(tempHex);
                availableHexs.append(counter + ")" + " x: " + tempHex.getX() + " y: " + tempHex.getY() + "\n");
            }


        }

        if (availableHexs.length() == 0) {
            return "there are no tiles around your city";
        }


        return availableHexs.toString();


    }

    public static String buyHex(int count) {
        Player buyer = GameController.getCurrentPlayer();

        int price = GameController.getSelectedCity().getHexs().size() * 5;
        if (buyer.getGold() < price) {
            return "you don't have enough money";
        }
        if (toBuyTiles.get(count - 1).getOwner() != null) {

            return "this hex has an owner";
        }

        buyer.decreaseGold(price);
        GameController.getSelectedCity().addHex(toBuyTiles.get(count - 1));
//        if (toBuyTiles.get(count - 1).getResource().getType().equals("Luxury"))
//            GameController.happinessDueToLuxuries(toBuyTiles.get(count - 1).getResource().getName());
        toBuyTiles.get(count - 1).setOwner(buyer);

        return "new hex added to your city successfully";

    }


    public static String removeCitizenFromWork(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "out of bounds";
        }
        if (hex[x][y].getOwner() != GameController.getCurrentPlayer()) {
            return "this tile is not yours";
        }
        if (!hex[x][y].isHasCitizen()) return "there is no citizen";
        //todo: kam kardan ghodrat tolid
        hex[x][y].setHasCitizen(false);
        GameController.getSelectedCity().decreaseNumberOfUnemployedCitizen(1);
        return "citizen removed successfully";
    }

    public static String lockCitizenTo(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "out of bounds";
        }
        if (hex[x][y].getOwner() != GameController.getCurrentPlayer()) {
            return "this tile is not yours";
        }
        if (!hex[x][y].isHasCitizen()) return "there is already a citizen";
        if (GameController.getSelectedCity().getNumberOfUnemployedCitizen() > 0) {
            GameController.getSelectedCity().increaseNumberOfUnemployedCitizen(1);
            //todo: afzayesh
            hex[x][y].setHasCitizen(true);
            return "citizen locked";
        } else return "unemployed a citizen firt";

    }

    public static int showUnEmployedCitizen() {
        return GameController.getSelectedCity().getPopulation();
    }

    public static String cityBanner() {
        if (GameController.getSelectedCity() == null) {
            return "please select a city first";
        }

        StringBuilder cityBanner = new StringBuilder();
        cityBanner.append(GameController.getSelectedCity().getName() + " hitpoint: " + GameController.getSelectedCity().getHitPoint());
        GameController.setSelectedCity(null);
        return cityBanner.toString();
    }
}
