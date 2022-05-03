package models.maprelated;

import controllers.GameController;
import controllers.InitializeGameInfo;
import enums.Color;
import enums.HexState;
import models.Player;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;

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
    private String improvement;
    private boolean[] hasRiver = new boolean[]{false, false, false, false};
    private boolean hasCitizen = false;
    private City capital = null;
    private City city = null;
    private HashMap<Player, HexState> StateOfHexForEachPlayer = new HashMap<>();

    public boolean isHasCitizen() {
        return hasCitizen;
    }

    public void setHasCitizen(boolean hasCitizen) {
        this.hasCitizen = hasCitizen;
    }

    public Hex(int x, int y, Terrain terrain, Feature feature) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
        this.feature = feature;
        for (int i = 0; i < InitializeGameInfo.getNumberOFPlayers(); i++) {
            this.StateOfHexForEachPlayer.put(InitializeGameInfo.getPlayers().get(i), HexState.FogOfWar);
        }
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
        this.civilianUnit = civilianUnit;
    }


    public void setRiver(int i) {
        hasRiver[i] = true;
    }

    public boolean isRiver(int dir) {
        return hasRiver[dir];
    }

    public int riverDir() {
        for (int i = 0; i < 4; i++) {
            if (hasRiver[i]) return i;
        }
        return 7;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
    }

    public String getImprovement() {
        return improvement;
    }

    public void setCapital(City city) {
        this.capital = city;
    }

    public City getCapital() {
        return this.capital;
    }
}