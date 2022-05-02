package models.maprelated;


import java.util.ArrayList;

import controllers.CityController;
import controllers.CombatController;
import controllers.GameController;
import models.Player;
import models.gainable.Building;
import models.units.Civilian;
import models.units.Combatable;
import models.units.Military;
import models.units.Unit;

public class City implements Combatable{
    private static ArrayList<City> cities = new ArrayList<City>();

    private String name;
    private int population;
    private int rangedDefensivePower = 8;
    private int meleeDefensivePower = 8;
    private int food;
    private int since;
    private int gold;
    private int production;
    private Military militaryUnit = null;
    private Civilian civilianUnit = null;
    private ArrayList<Building> constructingBuldings = new ArrayList<Building>();
    private ArrayList<Hex> hexs = new ArrayList<Hex>();
    private Player owner = null;
    private int hitPoint = 20;
    private  Hex capital;

    public City(Player owner, String name, Hex beginingHex) {
        this.owner = owner;
        this.name = name;
        population = 1;
        food = 0;
        since = 0;
        gold = 0;
        production = 0;
        hexs.add(beginingHex);
        this.capital = beginingHex;
        CombatController.getHex()[beginingHex.getX()][beginingHex.getY()].setCapital(this);
        health = 20;
        this.numberOfUnemployedCitizen = 0;
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
    private int numberOfUnemployedCitizen;
    private int health;

    public int getMeleeDefensivePower() {
        return this.meleeDefensivePower;
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

    public Military getMilitaryUnit() {
        return militaryUnit;
    }

    public Civilian getCivilianUnit() {
        return civilianUnit;
    }

    public static City getCityByName(String name) {
        for (City temp : cities) {
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
        hexs.add(hex);
    }

    public int getPopulation() {
        return this.population;
    }

    public void increasePopulation(int amount) {
        for (int i = 0; i < amount; i++) {
            for (Hex hex : hexs) {
                if (!hex.isHasCitizen()) {
                    CityController.lockCitizenTo(hex.getX(), hex.getY());
                    break;
                }
            }
        }
    }

    public void decreasePopulation(int amount) {
        population -= amount;
    }

    public void increaseMeleeDefensivePower(int amount) {
        meleeDefensivePower += amount;
    }

    public void decreaseMeleeDefensivePower(int amount) {
        meleeDefensivePower -= amount;
    }

    public int getRangedDefencePower() {
        return this.rangedDefensivePower;
    }

    public void increaseRangedDefencePower(int amount) {
        rangedDefensivePower += amount;
    }

    public void decreaseRangedDefencePower(int amount) {
        rangedDefensivePower -= amount;
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
        return this.since;
    }

    public void increaseSince(int amount) {
        since += amount;
    }

    public void decreaseSince(int amount) {
        since -= amount;
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


}
