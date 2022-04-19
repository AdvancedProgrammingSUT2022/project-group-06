package models.maprelated;

import enums.HexState;
import models.Player;

public class Hex
{
    private HexState state;
    private int x;
    private int y;
    private Player owner;
    private Terrain terrain;
    private Feature feature;

    Hex(int x, int y, Terrain terrain, Feature feature)
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

}