package models.maprelated;

import enums.Color;
import enums.HexState;
import models.Player;
import models.units.Civilian;
import models.units.Military;
import models.units.Unit;

public class Hex
{
    private HexState state;
    private int x;
    private int y;
    private Player owner;
    private Terrain terrain;
    private Feature feature;
    private  Resource resource;
    private Military military;
    private Civilian civilian;

    public Hex(int x, int y, Terrain terrain, Feature feature)
    {
        this.x=x;
        this.y=y;
        this.terrain=terrain;
        this.feature=feature;
    }

    public void setOwner(Player owner)
    {
        this.owner=owner;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Player getOwner()
    {
        return owner;
    }

    public HexState getState()
    {
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
}