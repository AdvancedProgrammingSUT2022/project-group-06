package project.civilization.models;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.annotations.Expose;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.enums.HexState;
import project.civilization.models.gainable.Construction;
import project.civilization.models.gainable.Technology;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.twopartyactions.Combat;
import project.civilization.models.twopartyactions.Trade;
import project.civilization.models.units.Civilian;
import project.civilization.models.units.Military;
import project.civilization.models.units.Unit;

public class Player {
    @Expose
    private int gold = 129;
    @Expose
    private int happiness;
    @Expose
    private int production;
    @Expose
    private int food;
    @Expose
    private int trophies;
    @Expose
    private int population;
    @Expose
    private int foodForNewCitizen;
    @Expose
    private String name;
    @Expose
    private int score;
    @Expose
    private ArrayList<String> notifications = new ArrayList<String>();
    @Expose
    private static ArrayList<String> technologies = new ArrayList<String>();
    @Expose
    private HashMap<String, Boolean> achievedTechnologies = new HashMap<String, Boolean>();
    @Expose
    private ArrayList<Integer> notificationsTurns = new ArrayList<Integer>();

    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<City> cities = new ArrayList<City>();
    private ArrayList<Trade> trades = new ArrayList<Trade>();
    private ArrayList<Combat> combats = new ArrayList<Combat>();
    private ArrayList<Military> militaries = new ArrayList<Military>();
    private ArrayList<Civilian> civilians = new ArrayList<Civilian>();
    private ArrayList<Construction> unfinishedProjects = new ArrayList<Construction>();
    private ArrayList<Technology> archivedTechnologies = new ArrayList<>();
    private City mainCity;
    //private ArrayList<TimeVariantProcess> timeVariantProcesses = new ArrayList<TimeVariantProcess>();
    //todo: handel city tile in reveled
    private static HashMap<Hex, Hex> reveledHexes = new HashMap<>();
    private Technology currentResearch;

    public ArrayList<String> getTechnologies() {
        return technologies;
    }

    public void increaseTrophies(int amount) {
        trophies += amount;
    }

    public ArrayList<Construction> getUnfinishedProjects() {
        return unfinishedProjects;
    }

    public void addUnfinishedProject(Construction construction) {
        unfinishedProjects.add(construction);
    }

    public ArrayList<Technology> getArchivedTechnologies() {
        return this.archivedTechnologies;
    }

    public void addArchivedTechnology(Technology technology) {
        this.archivedTechnologies.add(technology);
    }

    public HashMap<String, Boolean> getAchievedTechnologies() {
        return achievedTechnologies;
    }


    public static void addTechnology(String technology) {
        technologies.add(technology);
    }

    public City getMainCity() {
        return mainCity;
    }

    public void setMainCity(City mainCity) {
        this.mainCity = mainCity;
    }

    public void setTechnologyForPlayers() {
        for (String temp : technologies) {
            achievedTechnologies.put(temp, false);
        }
    }

    public static void setTechnologies(ArrayList<String> set) {

        technologies = new ArrayList<>(set);
    }

    public void addToRevealedHexes(Hex hex) {
        hex.setState(HexState.Revealed, this);
        //todo: check of hex fields
        Hex hexCopy = new Hex(hex.getX(), hex.getY(), hex.getTerrain(), hex.getFeature());
        hexCopy.setOwner(hex.getOwner());
        hexCopy.setResource(hex.getResource());
        //hexCopy.setMilitaryUnit(hex.getMilitaryUnit());
        //hexCopy.setCivilianUnit(hex.getCivilianUnit());
        hexCopy.setHasRiver(hex.getHasRiver());
        hexCopy.setHasCitizen(hex.getHasCitizen());
        hexCopy.setCapital(hex.getCapital());
        hexCopy.setCity(hex.getCity());
        hexCopy.setHasRoad(hex.hasRoad());
        hexCopy.setHasRailRoad(hex.hasRailRoad());
        hexCopy.setPillaged(hex.isPillaged());
        hexCopy.setStateOfHexForEachPlayer(hex.getStateOfHexForEachPlayer());
        //hexCopy.setImprovements(hex.getImprovement());
        reveledHexes.put(hex, hexCopy);
    }

    public void removeFromReveledTiles(Hex hex) {
        reveledHexes.remove(hex);
    }

    public HashMap<Hex, Hex> getReveledHexes() {
        return reveledHexes;
    }

    public Player(String name) {
        this.name = name;
        civilians = new ArrayList<Civilian>();
        militaries = new ArrayList<Military>();
        //initial happiness
        //hardcode
        for(int i=0;i<50;i++)
        {
            this.notifications.add("a");
            this.notificationsTurns.add(i);
        }
        this.happiness = 10;
        InitializeGameInfo.getPlayers().add(this);
        this.foodForNewCitizen = 2;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public int getTrophies() {
        return this.trophies;
    }

    public void increasePopulation(int amount) {
        this.population += amount;
    }

    public void decreasePopulation(int amount) {
        this.population -= amount;
    }

    public int getPopulation() {
        return this.population;
    }

    public int getGold() {
        return this.gold;
    }

    public void increaseGold(int amount) {
        gold += amount;
    }

    public void decreaseGold(int amount) {
        int i = (gold >= amount) ? (gold -= amount) : (trophies -= amount);
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
        if (unit instanceof Military) militaries.remove(unit);
        if (unit instanceof Civilian) civilians.remove(unit);
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

    public ArrayList<Integer> getNotificationsTurns() {
        return this.notificationsTurns;
    }

    public void setNotificationsTurns(int turn) {
        notificationsTurns.add(turn);
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

    public void addToMilitaries(Military military) {
        this.militaries.add(military);
    }

    public void setMilitaries(ArrayList<Military> militaries) {
        this.militaries = militaries;
    }

    public ArrayList<Civilian> getCivilians() {
        return civilians;
    }

    public void addToCivilians(Civilian civilian) {
        this.civilians.add(civilian);
    }

    public void addCivilians(Civilian civilians) {
        this.civilians.add(civilians);
    }

    public Technology getCurrentResearch() {
        return currentResearch;
    }

    public void setCurrentResearch(Technology currentResearch) {
        this.currentResearch = currentResearch;
    }

    public int getFoodForNewCitizen() {
        return foodForNewCitizen;
    }

    public void setFoodForNewCitizen(int foodForNewCitizen) {
        this.foodForNewCitizen = foodForNewCitizen;
    }
}
