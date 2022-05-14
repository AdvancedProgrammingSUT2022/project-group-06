package models.maprelated;

import controllers.GameController;
import controllers.InitializeGameInfo;
import enums.Color;
import enums.HexState;
import models.Player;
import models.gainable.Improvement;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;
import net.bytebuddy.implementation.bytecode.collection.ArrayLength;

import java.util.ArrayList;
import java.util.HashMap;

public class Hex {
    private int x;
    private int y;
    private Player owner = null;
    private Terrain terrain;
    private Feature feature;
    private Resource resource;
    private Military militaryUnit;
    private Civilian civilianUnit;
    private boolean[] hasRiver = new boolean[]{false, false, false, false};
    private boolean hasCitizen = false;
    private City capital = null;
    private City city = null;
    private boolean hasRoad;
    private boolean hasRailRoad;
    private boolean isPillaged = false;
    private HashMap<Player, HexState> StateOfHexForEachPlayer = new HashMap<>();
    private ArrayList<Improvement> improvements=new ArrayList<Improvement>();
    public void setFeature(Terrain newFeature)
    {
        terrain=newFeature;
    }
    public boolean isHasCitizen() {
        return hasCitizen;
    }

    public void setHasCitizen(boolean hasCitizen) {
        this.hasCitizen = hasCitizen;
    }

    public Hex(int x, int y, Terrain terrain, Feature feature) {
        this.x = x;
        this.y = y;
        hasRoad = false;
        hasRailRoad = false;
        this.terrain = terrain;
        this.feature = feature;
        for (int i = 0; i < InitializeGameInfo.getNumberOFPlayers(); i++) {
            this.StateOfHexForEachPlayer.put(InitializeGameInfo.getPlayers().get(i), HexState.FogOfWar);
        }
    }


    public void setImprovements(ArrayList<Improvement> improvements) {
        this.improvements = improvements;
    }

    public void setHasRiver(boolean[] hasRiver) {
        this.hasRiver = hasRiver;
    }

    public HashMap<Player, HexState> getStateOfHexForEachPlayer() {
        return StateOfHexForEachPlayer;
    }

    public void setStateOfHexForEachPlayer(HashMap<Player, HexState> stateOfHexForEachPlayer) {
        StateOfHexForEachPlayer = stateOfHexForEachPlayer;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public boolean getHasCitizen() {
        return hasCitizen;
    }

    public void setState(HexState hexState, Player player) {
        this.StateOfHexForEachPlayer.put(player,hexState);
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getOwner() {
        return owner;
    }

    public HexState getState(Player player) {
        return StateOfHexForEachPlayer.get(player);
    }

    public Feature getFeature() {
        return feature;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Military getMilitaryUnit() {
        return militaryUnit;
    }

    public void setMilitaryUnit(Military military) {
        this.militaryUnit = military;
    }

    public Civilian getCivilianUnit() {
        return civilianUnit;
    }

    public void setCivilianUnit(Civilian civilianUnit) {
       // System.out.println(civilianUnit.getX()+" "+civilianUnit.getY());
        this.civilianUnit = civilianUnit;
    }


    public void setRiver(int i) {
        hasRiver[i] = true;
    }

    public boolean isRiver(int dir) {
        return hasRiver[dir];
    }

    public boolean[] getHasRiver() {
        return hasRiver;
    }

    public int riverDir() {
        for (int i = 0; i < 4; i++) {
            if (hasRiver[i]) return i;
        }
        return 7;
    }

    public void addImprovement(Improvement improvement) {
        improvements.add(improvement);
        
    }

    public boolean isPillaged() {
        return isPillaged;
    }

    public void setPillaged(boolean pillaged) {
        isPillaged = pillaged;
    }

    public ArrayList<Improvement> getImprovement() {
        return improvements;
    }

    public void setCapital(City city) {
        this.capital = city;
    }

    public City getCapital() {
        return this.capital;
    }

    public boolean hasRoad() {
        return this.hasRoad;
    }

    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public boolean hasRailRoad() {
        return this.hasRailRoad;
    }

    public void setHasRailRoad(boolean hasRailRoad) {
        this.hasRailRoad = hasRailRoad;
    }
}