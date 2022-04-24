package models.maprelated;

import enums.Color;
import enums.HexState;
import models.Player;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;

public class Hex {
    private HexState state;
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

    public Hex(int x, int y, Terrain terrain, Feature feature, HexState state) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
        this.feature = feature;
        this.state = state;
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
        if (this.owner == null) {
            return null;
        }
        return owner;
    }

    public HexState getState() {
        return state;
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
    public int riverDir(){
        for (int i = 0; i < 4; i++) {
            if(hasRiver[i]) return i;
        }
        return 7;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
    }

    public String getImprovement() {
        return improvement;
    }
}