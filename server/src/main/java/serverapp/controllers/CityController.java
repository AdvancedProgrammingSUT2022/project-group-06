package serverapp.controllers;

import java.util.ArrayList;
import java.util.jar.JarEntry;

import com.google.gson.Gson;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import org.json.JSONObject;
import serverapp.enums.HexState;
import serverapp.enums.UnitState;
import serverapp.models.Player;
import serverapp.models.gainable.Building;
import serverapp.models.gainable.Construction;
import serverapp.models.gainable.Improvement;
import serverapp.models.gainable.Technology;
import serverapp.models.maprelated.City;
import serverapp.models.maprelated.Hex;
import serverapp.models.units.Settler;
import serverapp.models.units.Unit;


public class CityController {
    private static final ArrayList<Hex> toBuyTiles = new ArrayList<Hex>();
    private static final Hex[][] hex = GameController.getWorld().getHex();


    public static ArrayList<Hex> getToBuyTiles() {
        return toBuyTiles;
    }

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
        if (GameController.getWorld().getHex()[x][y].getState(GameController.getCurrentPlayer()) == HexState.FogOfWar) {
            return "he zerangi inja fog of ware";
        }
        GameController.setSelectedHex(GameController.getWorld().getHex()[x][y]);
        return "tile selected";
    }


    public static String selectCity(String name) {
        for (City temp : City.getCities()) {
            if (temp.getName().equals(name)) {
                GameController.setSelectedCity(temp);
                return "city selected successfully";
            }
        }

        return "no city with this name exists";
    }


    public static String startMakingUnit(String name) {
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


        if (type.matches("Settler||Worker") && GameController.getSelectedHex().getCivilianUnit() != null) {
            return "you can't have two Civilian units in a city";
        } else if ((!type.matches("Settler||Worker")) && (GameController.getSelectedHex().getMilitaryUnit() != null)) {
            return "you can't have two Military units in a city";
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
            if (newUnit.getNeededResource() != null && hex.getResource() != null && hex.getResource().getName().equals(newUnit.getNeededResource())) {
                check = true;
            }
        }

        if (!check && newUnit.getNeededResource() != null) {
            return "you don't have the needed resource to make this unit";
        }

        if (GameController.getCurrentPlayer().getProduction() >= newUnit.getNeededProduction()) {
            UnitController.makeUnit(name, GameController.getSelectedHex(), "production");
            return "Unit created successfully";
        }

        int productionPerTurn = 0;
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            productionPerTurn += temp.getProduction();

        }

        Unit unit = new Unit(name, GameController.getSelectedHex(), GameController.getCurrentPlayer());

        if (newUnit.getNeededProduction() < productionPerTurn) {
            unit.setLeftTurns(1);

        } else if (productionPerTurn <= 0) {
            return "you have no production per turn so it's impossible to start making a unit right now";
        } else {
            unit.setLeftTurns((newUnit.getNeededProduction() / productionPerTurn) + 1);
        }


        GameController.getCurrentPlayer().addUnfinishedProject(unit);

        String temp = "the process of " + "making a " + name + " unit" + " on the hex: x=" + GameController.getSelectedHex().getX() + " y=" + GameController.getSelectedHex().getY() + " started successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        return "process for builing an unit started successfully";
    }


    public static String buildCity() {
        String name = UnitController.getSelectedUnit().getOwner().getName()+UnitController.getSelectedUnit().getOwner().getCities().size();

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
        UnitController.getSelectedUnit().setState(UnitState.Active);
        UnitController.getSelectedUnit().setOrdered(true);
        City newCity = new City(GameController.getCurrentPlayer(), name, UnitController.getSelectedUnit().getCurrentHex());
        if (GameController.getCurrentPlayer().getCities().size() == 0)
            buildPalace(newCity);
        GameController.getCurrentPlayer().decreaseHappiness(1); //happiness decrease as num of cities increase
        if (GameController.getCurrentPlayer().getHappiness() < 0) GameController.unhappinessEffects();
        City.addCities(newCity);
        GameController.getCurrentPlayer().addCity(newCity);
        getCurrentPlayer().decreaseHappiness(2);//happiness decrease as the number of cities grow
        return "new city created successfully";
    }


    public static String presaleTiles() {
        toBuyTiles.clear();
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

                if (x + 1 < 10 && hex[x + 1][y].getOwner() == null && !toBuyTiles.contains(hex[x + 1][y]) && (!hex[x + 1][y].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x + 1][y].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x + 1][y]);
                }

                if (x - 1 >= 0 && !toBuyTiles.contains(hex[x - 1][y]) && hex[x - 1][y].getOwner() == null && (!hex[x - 1][y].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x - 1][y].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x - 1][y]);
                }
                if (y - 1 >= 0 && !toBuyTiles.contains(hex[x][y - 1]) && hex[x][y - 1].getOwner() == null && (!hex[x][y - 1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x][y - 1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x][y - 1]);
                }
                if (x + 1 < 10 && y - 1 >= 0 && !toBuyTiles.contains(hex[x + 1][y - 1]) && hex[x + 1][y - 1].getOwner() == null && (!hex[x + 1][y - 1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x + 1][y - 1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x + 1][y - 1]);
                }
                if (y + 1 < 10 && !toBuyTiles.contains(hex[x][y + 1]) && hex[x][y + 1].getOwner() == null && (!hex[x][y + 1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x][y + 1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x][y + 1]);
                }
                if (x + 1 < 10 && y + 1 < 10 && !toBuyTiles.contains(hex[x + 1][y + 1]) && hex[x + 1][y + 1].getOwner() == null && (!hex[x + 1][y + 1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x + 1][y + 1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x + 1][y + 1]);
                }
            }
            if (y % 2 == 0) {
                if (x + 1 < 10 && !toBuyTiles.contains(hex[x + 1][y]) && hex[x + 1][y].getOwner() == null && (!hex[x + 1][y].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x + 1][y].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x + 1][y]);
                }

                if (x - 1 >= 0 && !toBuyTiles.contains(hex[x - 1][y]) && hex[x - 1][y].getOwner() == null && (!hex[x - 1][y].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x - 1][y].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x - 1][y]);
                }
                if (y - 1 >= 0 && !toBuyTiles.contains(hex[x][y - 1]) && hex[x][y - 1].getOwner() == null && (!hex[x][y - 1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x][y - 1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x][y - 1]);
                }
                if (x - 1 >= 0 && y - 1 >= 0 && !toBuyTiles.contains(hex[x - 1][y - 1]) && hex[x - 1][y - 1].getOwner() == null && (!hex[x - 1][y - 1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x - 1][y - 1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x - 1][y - 1]);
                }
                if (y + 1 < 10 && !toBuyTiles.contains(hex[x][y + 1]) && hex[x][y + 1].getOwner() == null && (!hex[x][y + 1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x][y + 1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x][y + 1]);
                }
                if (x - 1 >= 0 && y + 1 < 10 && !toBuyTiles.contains(hex[x - 1][y + 1]) && hex[x - 1][y + 1].getOwner() == null && (!hex[x - 1][y + 1].getState(GameController.getCurrentPlayer()).equals(HexState.FogOfWar)) && (!hex[x - 1][y + 1].getTerrain().getName().matches("Mountain||Ocean"))) {
                    sixNeighborHexs.add(hex[x - 1][y + 1]);
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


        int price = GameController.getSelectedCity().getHexs().size() * 5;
        if (GameController.getCurrentPlayer().getGold() < price) {
            return "you don't have enough money";
        }
        if (toBuyTiles.get(count - 1).getOwner() != null) {

            return "this hex has an owner";
        }

        GameController.getCurrentPlayer().decreaseGold(price);
        GameController.getSelectedCity().addHex(toBuyTiles.get(count - 1));

        if (toBuyTiles.get(count - 1).getResource() != null &&
                toBuyTiles.get(count - 1).getResource().getType().equals("Luxury")) {
            GameController.happinessDueToLuxuries(toBuyTiles.get(count - 1).getResource().getName());
        }


        return "new hex added to your city successfully";

    }


    public static String removeCitizenFromWork(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "out of bounds";
        }
        if (hex[x][y].getOwner() != GameController.getCurrentPlayer()) {
            return "this tile is not yours";
        }
        if (!hex[x][y].getHasCitizen()) return "there is no citizen";
        City city = hex[x][y].getCity();
        city.decreaseFood(hex[x][y].getTerrain().getFood());
        city.decreaseGold(hex[x][y].getTerrain().getGold());
        city.decreaseProduction(hex[x][y].getTerrain().getProduction());
        hex[x][y].setHasCitizen(false);
        GameController.getSelectedCity().increaseNumberOfUnemployedCitizen(1);
        return "citizen removed successfully";
    }

    public static String lockCitizenTo(int x, int y) {
        if (GameController.isOutOfBounds(x, y)) {
            return "out of bounds";
        }
        if (hex[x][y].getOwner() != GameController.getCurrentPlayer()) {
            return "this tile is not yours";
        }
        if (hex[x][y].getHasCitizen()) return "there is already a citizen";
        if (GameController.getSelectedCity().getNumberOfUnemployedCitizen() > 0) {
            GameController.getSelectedCity().increaseNumberOfUnemployedCitizen(1);
            City city = hex[x][y].getCity();
            city.increaseFood(hex[x][y].getTerrain().getFood());
            city.increaseGold(hex[x][y].getTerrain().getGold());
            city.increaseProduction(hex[x][y].getTerrain().getProduction());
            hex[x][y].setHasCitizen(true);
            return "citizen locked";
        } else return "unemployed a citizen first";
    }

    public static String showUnEmployedCitizen() {
        if (GameController.getSelectedCity() == null) return "please select a city first";
        return String.valueOf(GameController.getSelectedCity().getNumberOfUnemployedCitizen());
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
    public static City getCityWithName(String name) {
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            if (temp.getName().equals(name)) {
                return temp;
            }
        }
        return null;
    }



    public static boolean hasBuilding(City city, String buildingName) {
        for (Building building : city.getBuiltBuildings()) {
            if (building.getName().toLowerCase().equals(buildingName.toLowerCase()))
                return true;
        }
        return false;
    }

    public static String getAvailableBuildings(JSONObject object) {
        String cityName = (String) object.get("cityName");
        City city = null;
        for (City city1 : GameController.getCurrentPlayer().getCities()) {
            if (city1.getName().equals(cityName))
                city = city1;
        }
        ArrayList<Building> availableBuildings = new ArrayList<>();
        for (Building building : InitializeGameInfo.getAllBuildings()) {
            if (building.getTechnology() != null
                    && city.getOwner().getAchievedTechnologies().get(building.getTechnology()) != null
                    && city.getOwner().getAchievedTechnologies().get(building.getTechnology())) {
                if (building.getPrerequisite() == null || hasBuilding(city, building.getName()))
                    availableBuildings.add(building);
            }
            if (building.getTechnology() == null)
                availableBuildings.add(building);
        }
        ArrayList<String> buildingNames = new ArrayList<>();
        for (Building building : availableBuildings)
            buildingNames.add(building.getName());
        return new Gson().toJson(buildingNames);
    }

    public static String buildABuilding(JSONObject object) {
        String cityName = (String) object.get("cityName");
        String buildingName = (String) object.get("buildingName");
        City city = null;
        for (City city1 : GameController.getCurrentPlayer().getCities()) {
            if (city1.getName().equals(cityName))
                city = city1;
        }
        Building building = null;
        for (Building building1 : InitializeGameInfo.getAllBuildings()) {
            if (building1.getName().equals(buildingName))
                building = Building.clone(building1, city.getHexs().get(city.getHexs().size() - 1));
        }
        city.getOwner().addUnfinishedProject(building);
        return "started to build";
    }

    public static void buildPalace(City city) {
        Building palace = Building.clone(InitializeGameInfo.getBuildingsInfo().get("Palace"), city.getHexs().get(0));
        city.getBuiltBuildings().add(palace);
    }

    public static void effectOfDifferentBuildingsEachTurn(City city) {
        for (Building building : city.getBuiltBuildings()) {
            switch (building.getName()) {
                case "Barracks":
                case "Armory":
                    barracksEffect(city);
                    break;
                case "Granary":
                case "Watermill":
                    city.increaseFood(2);
                    break;
                case "Burial Tomb":
                    city.getOwner().increaseHappiness(2);
                    break;
                case "Circus":
                    city.getOwner().increaseHappiness(3);
                    break;
                case "Colosseum":
                case "Theater":
                    city.getOwner().increaseHappiness(4);
                    break;
                case "Courthouse":
                    city.getOwner().decreaseHappiness(city.getOwner().getHappiness());
                    break;
                case "Stable":
                    stableEffect(city);
                    break;
                //TODO: forge, garden
                case "Market":
                case "Bank":
                    city.increaseGold((int) (city.getGold() * 0.25));
                    break;
                case "Mint":
                    mintEffect(city);
                    break;
                case "University":
                    universityEffect(city);
                    break;
                case "Public School":
                    city.increaseScience((int) (city.getSince() * 0.5));
                    break;
                case "Satrap's Court":
                    city.increaseGold((int) (city.getGold() * 0.25));
                    city.getOwner().increaseHappiness(2);
                    break;
                case "Stock Exchange":
                    city.increaseGold((int) (city.getGold() * 0.33));
                    break;
                case "Library":
                    libraryEffect(city);
                    break;
            }
        }
    }

    private static void libraryEffect(City city) {
        city.getOwner().increaseTrophies(city.getPopulation() / 2);
        city.setTrophy(city.getTrophy() + city.getPopulation() / 2);
    }

    private static void barracksEffect(City city) {
        for (Unit unit : city.getOwner().getUnits()) {
            unit.increaseMP(15);
        }
    }

    private static void stableEffect(City city) {
        //todo
    }

    private static void mintEffect(City city) {
        city.increaseGold(3);
    }

    private static void universityEffect(City city) {
        int amount = city.getTrophy() / 2;
        city.setTrophy(city.getTrophy() + amount);
        city.getOwner().increaseTrophies(amount);
    }

    public static String getImprovementNameOfWoorker(int i, int j) {
        JSONObject jsonObject = new JSONObject();
        Unit unit = hex[i][j].getCivilianUnit();
        for (Construction imp : GameController.getCurrentPlayer().getUnfinishedProjects()) {
            if (imp instanceof Improvement && imp.getWorker().equals(unit)) {
                jsonObject.put("progress",imp.getLeftTurns() * 1.0 / ((Improvement) imp).getMaxTurn());
                jsonObject.put("impName",imp.getName());
            }
        }

        return jsonObject.toString();
    }
}
