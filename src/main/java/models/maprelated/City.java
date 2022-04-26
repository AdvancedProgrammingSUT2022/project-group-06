package models.maprelated;


import java.util.ArrayList;
import models.gainable.Building;

public class City
{
    private int population;
    private int defencePower;
    private int food;
    private int since;
    private int gold;
    private int production;
    private ArrayList<Building> constructingBuldings;



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
