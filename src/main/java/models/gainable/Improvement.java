package models.gainable;

import models.maprelated.Hex;
import models.units.Unit;
import models.units.Worker;

public class Improvement implements Construction {
    private int leftTurns;
    private String name;
    private Hex hex;
    private Unit worker;
    public Improvement(String name,Unit unit,Hex hex)
    {
        this.name=name;
        this.hex=hex;
        this.worker=unit;
    }
    @Override
    public void setLeftTurns(int leftTurns) {
        this.leftTurns = leftTurns;
    }

    @Override
    public int getLeftTurns() {
        return this.leftTurns;
    }

    @Override
    public void decreaseLeftTurns() {
        this.leftTurns -= 1;
    }

    @Override
    public void build() {
        switch(name)
        {
            case "Farm":
                makeFarm();
                break;
            case "Mine":
                makeMine();
                break;
            case "Road":
                makeRoad();
                break;
        }
    }
    private void makeFarm()
    {
        hex.getCity().increaseFood(1);
        hex.addImprovement(this);
        if(hex.getFeature().getName().equals("Marsh||Jungle||Forest"))
        {
            hex.setFeature(null);
        }

    }

    private void makeMine()
    {
        hex.getCity().increaseProduction(1);
        hex.addImprovement(this);
        if(hex.getFeature().getName().equals("Marsh||Jungle||Forest"))
        {
            hex.setFeature(null);
        }

    }

    private void makeRoad() {
        hex.setHasRoad(true);
    }
    @Override
    public Hex getHex()
    {
        return hex;
    }
    @Override
    public String getName()
    {
        return name;
    }
    public void setHex(Hex newHex)
    {
        this.hex=newHex;
    }

}
