package models.maprelated;


import java.util.ArrayList;
import java.util.Objects;

import controllers.CityController;
import controllers.CombatController;
import controllers.GameController;
import controllers.InitializeGameInfo;
import models.Player;
import models.gainable.Building;
import models.gainable.Construction;
import models.units.Civilian;
import models.units.Combatable;
import models.units.Military;
import models.units.Unit;

public class City implements Combatable {
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
    private int trophy=0;


    public int getTrophy()
    {
        return trophy;
    }
    public void setTrophy(int amount)
    {
        trophy=amount;
    }
    public City(Player owner, String name, Hex beginingHex) {
        this.owner = owner;
        this.name = name;
        population = 1;
        food = 0;
        since = 0;
        gold = 40;
        production = 0;
        hexs.add(beginingHex);
        beginingHex.setOwner(owner);
        beginingHex.setCity(this);
        this.capital = beginingHex;
        beginingHex.setCapital(this);
        health = 20;
        this.numberOfUnemployedCitizen = 0;
        if(Objects.equals(this.capital.getTerrain().getName(), "Hills")){
            hitPoint += 3;
        }
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

    @Override
    public void healPerTurn() {
        this.hitPoint++;
    }

    @Override
    public boolean isInPossibleCombatRange(int x, int y, int seenRange ,int unitX ,int unitY ){
        return false;
    }

    @Override
    public int getX() {
        return capital.getX();
    }

    @Override
    public int getY() {
        return capital.getY();
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
        hex.setOwner(this.owner);
        hex.setCity(this);
        hexs.add(hex);
    }

    public static void deleteCity(City city){
        //todo : if garrison delete the unit from all unit list
        city.getCapital().setCapital(null);
        for (Hex hex: city.hexs){
            hex.setOwner(null);
            hex.setCity(null);
            cities.remove(city);
        }
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
        owner.increasePopulation(amount);
    }

    public void decreasePopulation(int amount) {
        population -= amount;
        owner.decreasePopulation(amount);
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
