package serverapp.models.maprelated;


import java.util.ArrayList;
import java.util.Objects;

import serverapp.controllers.CityController;
import serverapp.controllers.CombatController;
import serverapp.controllers.GameController;
import serverapp.controllers.UnitController;
import serverapp.enums.HexState;
import serverapp.models.Player;
import serverapp.models.gainable.Building;
import serverapp.models.units.Civilian;
import serverapp.models.units.Combatable;
import serverapp.models.units.Military;


public class City implements Combatable {
    private String name;
    private int population;
    private int rangedCombatStrength = 8;
    private int meleeCombatStrength = 8;
    private int food;
    private int changingFood;
    private int science;
    private int gold;
    private int production;
    private int numberOfUnemployedCitizen;
    private int health;
    private int trophy = 0;
    final private int maxHitPoint = 20;
    private int hitPoint = 20;
    private int beginningX;
    private  int beginningY;
    private final ArrayList<Building> builtBuildings;
    private ArrayList<int[]> otherTilesCoordinates = new ArrayList<int[]>();
    private ArrayList<Building> constructingBuldings = new ArrayList<Building>();


    private transient static ArrayList<City> cities = new ArrayList<City>();

    private transient ArrayList<Hex> hexs = new ArrayList<Hex>();

    private transient Player owner = null;


    private transient Hex capital;


    public City(Player owner, String name, Hex beginingHex) {
        this.beginningX = beginingHex.getX();
        this.beginningY = beginingHex.getY();

        this.owner = owner;
        this.name = name;
        increasePopulation(1);
        this.increaseFood(beginingHex.getTerrain().getFood());
        this.increaseGold(beginingHex.getTerrain().getGold());
        this.increaseProduction(beginingHex.getTerrain().getProduction());
        food = 0;
        changingFood = 0;
        science = 0;
        gold = 40;
        production = 0;
        hexs.add(beginingHex);
        this.food += beginingHex.getTerrain().getFood();
        beginingHex.setOwner(owner);
        beginingHex.setCity(this);
        beginingHex.setHasCitizen(true);
        this.capital = beginingHex;
        beginingHex.setCapital(this);
        health = 20;
        this.numberOfUnemployedCitizen = 0;
        if (Objects.equals(this.capital.getTerrain().getName(), "Hills")) {
            meleeCombatStrength += 3;
            rangedCombatStrength += 3;
        }
        builtBuildings = new ArrayList<>();
    }

    public ArrayList<int[]> getOtherTilesCoordinates() {
        return otherTilesCoordinates;
    }

    public int getBeginningX() {
        return beginningX;
    }

    public int getBeginningY() {
        return beginningY;
    }

    public void setHexs(ArrayList<Hex> hexs) {
        this.hexs = hexs;
    }

    public void setCapital(Hex capital) {
        this.capital = capital;
    }

    public int getTrophy() {
        return trophy;
    }

    public void setTrophy(int amount) {
        trophy = amount;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Hex getCapital() {
        return capital;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void decreaseHitPoint(int amount) {
        hitPoint -= amount;
    }

    public int getHitPoint() {
        return hitPoint;
    }


    public int getNumberOfUnemployedCitizen() {
        return numberOfUnemployedCitizen;
    }

    public void decreaseNumberOfUnemployedCitizen(int amount) {
        this.numberOfUnemployedCitizen -= amount;
    }

    public void increaseNumberOfUnemployedCitizen(int amount) {
        this.numberOfUnemployedCitizen += amount;
    }


    public int getMeleeCombatStrength() {
        return this.meleeCombatStrength;
    }


    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String attack(Combatable defender) {
        return null;
    }

    @Override
    public String defend(Combatable attacker) {
        return null;
    }

    @Override
    public void healPerTurn() {
        this.hitPoint++;
    }

    @Override
    public boolean isInPossibleCombatRange(int x, int y, int seenRange, int attackerX, int attackerY) {
        int range = 2;
        if (seenRange == range) return false;
        boolean res = false;
        int[][] direction = GameController.getDirection(attackerY);
        for (int[] ints : direction) {
            if (attackerX + ints[0] == x && attackerY + ints[1] == y) {
                return true;
            }
            res = isInPossibleCombatRange(x, y, seenRange + 1, attackerX + ints[0], attackerY + ints[1]);
            if (res) break;
        }
        return res;
    }

    @Override
    public int getX() {
        return capital.getX();
    }

    @Override
    public int getY() {
        return capital.getY();
    }


    public static City getCityByName(String name) {
        for (City temp : GameController.getCurrentPlayer().getCities()) {
            if (temp.name.equals(name)) {
                return temp;
            }
        }
        return null;
    }

    public static ArrayList<City> getCities() {
        return cities;
    }

    public static void addCities(City newCity) {
        cities.add(newCity);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Hex> getHexs() {
        return hexs;
    }

    public void addHex(Hex hex) {
        otherTilesCoordinates.add(new int[]{hex.getX(), hex.getY()});
        hex.setOwner(this.owner);
        hex.setCity(this);
        hexs.add(hex);
    }

    public static String deleteCity(City city) {
        if (city.getCapital().getMilitaryUnit() != null) {
            UnitController.deleteMilitaryUnit(city.getCapital().getMilitaryUnit());
        }
        if (city.getOwner().getCities().get(0) == city) {
            CombatController.addCityToTerritory(city, GameController.getCurrentPlayer());
            return "this city is the main city of the opp civilization you can not delete this we will add this to your cities";
        }
        city.getCapital().setCapital(null);
        for (Hex hex : city.hexs) {
            hex.setOwner(null);
            hex.setCity(null);
            cities.remove(city);
        }
        return "deleted";
    }

    public int getPopulation() {
        return this.population;
    }

    public void increasePopulation(int amount) {
        this.population += amount;
        owner.increasePopulation(amount);
    }

    public void decreasePopulation(int amount) {
        population -= amount;
        owner.decreasePopulation(amount);
    }

    public void increaseMeleeDefensivePower(int amount) {
        meleeCombatStrength += amount;
    }

    public void decreaseMeleeDefensivePower(int amount) {
        meleeCombatStrength -= amount;
    }

    public int getRangedCombatStrength() {
        return this.rangedCombatStrength;
    }

    public void increaseRangedDefencePower(int amount) {
        rangedCombatStrength += amount;
    }

    public void decreaseRangedDefencePower(int amount) {
        rangedCombatStrength -= amount;
    }

    public int getFood() {
        return this.food;
    }

    public void increaseFood(int amount) {
        food += amount;
    }

    public void decreaseFood(int amount) {
        food -= amount;
    }


    public int getSince() {
        return this.science;
    }

    public void increaseScience(int amount) {
        science += amount;
    }

    public void decreaseScience(int amount) {
        science -= amount;
    }


    public int getGold() {
        return this.gold;
    }

    public void increaseGold(int amount) {
        gold += amount;

    }

    public void decreaseGold(int amount) {
        gold -= amount;

    }

    public int getProduction() {
        return this.production;
    }

    public void increaseProduction(int amount) {
        production += amount;
    }

    public void decreaseProduction(int amount) {
        production -= amount;
    }

    public ArrayList<Building> getConstructingBuldings() {
        return this.constructingBuldings;
    }

    public void addBuilding(Building building) {
        constructingBuldings.add(building);
    }

    public void removeConstructingBuilding(Building building) {
        constructingBuldings.remove(building);
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public void increaseHitPoint(int amount) {
        this.hitPoint += amount;
    }

    public int getChangingFood() {
        return this.changingFood;
    }

    public void increaseChangingFood(int amount) {
        this.changingFood += amount;
    }

    public void decreaseChangingFood(int amount) {
        this.changingFood -= amount;
    }

    public ArrayList<Building> getBuiltBuildings() {
        return builtBuildings;
    }

}
