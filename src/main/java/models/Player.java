package models;

import java.util.ArrayList;
import java.util.HashMap;

import controllers.GameController;
import models.maprelated.City;
import models.twopartyactions.Combat;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;
import models.twopartyactions.Trade;

public class Player {//-currentProject
    public static HashMap<String, Boolean> achievedTechnologies = new HashMap<String, Boolean>();

    private int gold;
    private int happiness;
    private int production;
    private int food;
    private ArrayList<Unit> units;
    private ArrayList<City> cities;
    private int score;
    private ArrayList<Trade> trades;
    private ArrayList<String> notifications;
    private ArrayList<Combat> combats;
    private ArrayList<Military> militaries;
    private ArrayList<Civilian> civilians;
    private String name;

    public Player(String name) {
        this.name = name;
        GameController.addPlayer(this);
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


    public int getHappiness() {
        return this.happiness;
    }

    public void increaseHappiness(int amount) {
        happiness += amount;
    }

    public void decreaseHappiness(int amount) {
        happiness -= amount;
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

    public int getFood() {
        return this.food;
    }

    public void increaseFood(int amount) {
        food += amount;
    }

    public void decreaseFood(int amount) {
        food -= amount;
    }


    public void unlockTechnology(String technology) {
        achievedTechnologies.put(technology, true);
    }

    public ArrayList<Unit> getUnits() {
        return this.units;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public void removeCity(City city) {
        cities.remove(city);
    }


    public int getScore() {
        return this.score;
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    public void decreaseScore(int amount) {
        score -= amount;
    }


    public ArrayList<Trade> getTrades() {
        return this.trades;
    }

    public void addTrades(Trade trade) {
        trades.add(trade);
    }

    public void removeTrades(Trade trade) {
        trades.remove(trade);
    }

    public ArrayList<String> getNotifications() {
        return this.notifications;
    }

    public void addNotifications(String notification) {
        notifications.add(notification);
    }

    public ArrayList<Combat> getCombats() {
        return this.combats;
    }

    public void addCombats(Combat combat) {
        combats.add(combat);
    }

    public void removeCombats(Combat combat) {
        combats.remove(combat);
    }


    public String getName() {
        return name;
    }

    public ArrayList<Military> getMilitaries() {
        return militaries;
    }

    public void setMilitaries(ArrayList<Military> militaries) {
        this.militaries = militaries;
    }

    public ArrayList<Civilian> getCivilians() {
        return civilians;
    }

    public void setCivilians(ArrayList<Civilian> civilians) {
        this.civilians = civilians;
    }
}
