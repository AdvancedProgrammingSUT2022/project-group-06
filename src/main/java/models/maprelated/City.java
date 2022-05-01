package models.maprelated;


import java.util.ArrayList;

import models.Player;
import models.gainable.Building;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;

public class City
{
    private static ArrayList<City> cities=new ArrayList<City>();

    private String name;
    private int population;
    private int defencePower;
    private int food;
    private int since;
    private int gold;
    private int production;
    private Military militaryUnit=null;
    private Civilian civilianUnit=null;
    private ArrayList<Building> constructingBuldings=new ArrayList<Building>();
    private ArrayList<Hex> hexs=new ArrayList<Hex>();
    private int health;
    private Player owner=null;
    public City(Player owner,String name,Hex beginingHex)
    {
        this.owner=owner;
        this.name=name;
        population=0;
        defencePower=0;
        food=0;
        since=0;
        gold=0;
        production=0;
        hexs.add(beginingHex);
        health=20;
    }

    public Player getOwner()
    {
        return owner;
    }
    public Military getMilitaryUnit()
    {
        return militaryUnit;
    }
    public Civilian getCivilianUnit()
    {
        return civilianUnit;
    }
    public static City getCityByName(String name)
    {
        for(City temp: cities)
        {
            if(temp.name.equals(name))
            {
                return temp;
            }
        }
        return null;
    }
    public static ArrayList<City> getCities()
    {
        return cities;
    }
    public static void addCities(City newCity)
    {
        cities.add(newCity);
    }

    public String getName()
    {
        return this.name;
    }
    public ArrayList<Hex> getHexs()
    {
        return hexs;
    }
    public void addHex(Hex hex)
    {
        hexs.add(hex);
    }

    public int getPopulation() {
        return this.population;
    }

    public void increasePopulation(int amount) {
        population+=amount;
    }

    public void decreasePopulation(int amount) {
        population-=amount;
    }

    public int getDefencePower() {
        return this.defencePower;
    }

    public void increaseDefencePower(int amount) {
        defencePower+=amount;
    }
    public void decreaseDefencePower(int amount) {
        defencePower-=amount;
    }



    public int getFood() {
        return this.food;
    }

    public void increaseFood(int amount) {
        food+=amount;
    }

    public void decreaseFood(int amount) {
        food-=amount;
    }


    public int getSince() {
        return this.since;
    }

    public void increaseSince(int amount) {
        since+=amount;
    }

    public void decreaseSince(int amount) {
        since-=amount;
    }


    public int getGold() {
        return this.gold;
    }

    public void increaseGold(int amount) {
        gold+= amount;

    }

    public void decreaseGold(int amount) {
        gold-=amount;

    }

    public int getProduction() {
        return this.production;
    }

    public void increaseProduction(int amount) {
        production+=amount;
    }

    public void decreaseProduction(int amount) {
        production-=amount;
    }

    public ArrayList<Building> getConstructingBuldings() {
        return this.constructingBuldings;
    }
    public void addBuilding(Building building)
    {
        constructingBuldings.add(building);
    }
    public void removeConstructingBuilding(Building building)
    {
        constructingBuldings.remove(building);

    }





}
