package controllers;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.ArrayList;


import enums.HexState;
import models.Player;
import models.maprelated.City;
import models.maprelated.Hex;
import models.units.Ranged;
import models.units.Settler;
import models.units.Unit;
import models.units.Worker;


public class CityController {
    private static ArrayList<Hex> toBuyTiles = new ArrayList<Hex>();
    private static final Hex[][] hex = GameController.getWorld().getHex();
    private static final Player currentPlayer = GameController.getCurrentPlayer();

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
        if (GameController.getWorld().getHex()[x][y].getState(currentPlayer)== HexState.FogOfWar) {
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
        if(GameController.getSelectedHex().getOwner() != currentPlayer) {
             return "this tile does not belong to you";
         }
         if(GameController.getSelectedHex().getCapital() == null){
             return "this is not capital";
         }
         String type=InitializeGameInfo.unitInfo.get(name).split(" ")[7];
         GameController.setSelectedCity(GameController.getSelectedHex().getCity());

        if(type.matches("Settler||Worker")&&GameController.getSelectedCity().getCivilianUnit()!=null)
        {
            return "you can't have two Civilian units in a city";
        }else if((!type.matches("Settler||Worker"))&&(GameController.getSelectedCity().getMilitaryUnit()!=null)) {
            return "you can't have two Military units in a city";
        }
            if (name.equals("Settler") && currentPlayer.getHappiness() < 0)
            return "your civilization is unhappy";

        if (!InitializeGameInfo.unitInfo.containsKey(name)) {
            return "invalid unit name";
        }

        
        Unit newUnit = new Unit(name, GameController.getSelectedHex(), currentPlayer);


        if(newUnit.getNeededTech()!=null&&currentPlayer.getAchievedTechnologies().get(newUnit.getNeededTech()))
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

        if (currentPlayer.getGold() >= newUnit.getCost()) {
            UnitController.makeUnit(name, GameController.getSelectedHex());
            return "Unit created successfully";
        }

        int goldPerTurn = 0;
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            goldPerTurn += temp.getGold();
        }

        Unit unit = new Unit(name, GameController.getSelectedHex(), currentPlayer);


        if (newUnit.getCost() < goldPerTurn) {
            unit.setLeftTurns(1);
            ;
        } else if (goldPerTurn == 0) {
            return "you have no income so it's impossible to start making a unit right now";
        } else {
            unit.setLeftTurns((newUnit.getCost() / goldPerTurn) + 1);
        }


        currentPlayer.addUnfinishedProject(unit);

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


        City newCity = new City(currentPlayer, name, UnitController.getSelectedUnit().getCurrentHex());
        currentPlayer.decreaseHappiness(1); //happiness decrease as num of cities increase
        if (currentPlayer.getHappiness() < 0) GameController.unhappinessEffects();
        City.addCities(newCity);
        UnitController.getSelectedUnit().getCurrentHex().setCity(newCity);
        currentPlayer.addCity(newCity);
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
                if (x + 1 < 10 && hex[x + 1][y].getOwner() == null&& (!hex[x+1][y].getState(currentPlayer).equals(HexState.FogOfWar))) {
                    sixNeighborHexs.add(hex[x + 1][y]);
                }

                if (x - 1 >= 0 && hex[x - 1][y].getOwner() == null&& (!hex[x-1][y].getState(currentPlayer).equals(HexState.FogOfWar))) {
                    sixNeighborHexs.add(hex[x - 1][y]);
                }
                if (y - 1 >= 0 && hex[x][y - 1].getOwner() == null&& (!hex[x][y-1].getState(currentPlayer).equals(HexState.FogOfWar))) {
                    sixNeighborHexs.add(hex[x][y - 1]);
                    if (x + 1 < 10 && hex[x + 1][y - 1].getOwner() == null&& (!hex[x+1][y-1].getState(currentPlayer).equals(HexState.FogOfWar))) {
                        sixNeighborHexs.add(hex[x + 1][y - 1]);
                    }
                }
                if (y + 1 < 10 && hex[x][y + 1].getOwner() == null && (!hex[x][y+1].getState(currentPlayer).equals(HexState.FogOfWar))) {
                    sixNeighborHexs.add(hex[x][y + 1]);
                    if (x + 1 < 10 && hex[x][y + 1].getOwner() == null) {
                        sixNeighborHexs.add(hex[x + 1][y + 1]);
                    }
                }
            }
            if (y % 2 == 0) {
                if (x + 1 < 10 && hex[x + 1][y].getOwner() == null&&(!hex[x+1][y].getState(currentPlayer).equals(HexState.FogOfWar))) {
                    sixNeighborHexs.add(hex[x + 1][y]);
                }

                if (x - 1 >= 0 && hex[x - 1][y].getOwner() == null&&(!hex[x-1][y].getState(currentPlayer).equals(HexState.FogOfWar))) {
                    sixNeighborHexs.add(hex[x - 1][y]);
                }
                if (y - 1 >= 0 && hex[x][y - 1].getOwner() == null&&(!hex[x][y-1].getState(currentPlayer).equals(HexState.FogOfWar))) {
                    sixNeighborHexs.add(hex[x][y - 1]);
                    if (x - 1 >= 0 && hex[x - 1][y - 1].getOwner() == null&& (!hex[x-1][y-1].getState(currentPlayer).equals(HexState.FogOfWar))) {
                        sixNeighborHexs.add(hex[x - 1][y - 1]);
                    }
                }
                if (y + 1 < 10 && hex[x][y + 1].getOwner() == null&&(!hex[x][y+1].getState(currentPlayer).equals(HexState.FogOfWar))) {
                    sixNeighborHexs.add(hex[x][y + 1]);
                    if (x - 1 >= 0 && hex[x - 1][y + 1].getOwner() == null) {
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
        Player buyer = currentPlayer;
        int price = GameController.getSelectedCity().getHexs().size() * 5;
        if (buyer.getGold() < price) {
            return "you don't have enough money";
        }
        if (GameController.getSelectedHex().getOwner() != null) {
            return "this hex has an owner";
        }

        buyer.decreaseGold(price);
        GameController.getSelectedCity().addHex(toBuyTiles.get(count - 1));
        if (toBuyTiles.get(count - 1).getResource().getType().equals("Luxury"))
            GameController.happinessDueToLuxuries(toBuyTiles.get(count - 1).getResource().getName());
        toBuyTiles.get(count - 1).setOwner(buyer);

        return "new hex added to your city successfully";

    }


    public static String removeCitizenFromWork(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "out of bounds";
        }
        if (hex[x][y].getOwner() != currentPlayer) {
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
        if (hex[x][y].getOwner() != currentPlayer) {
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
