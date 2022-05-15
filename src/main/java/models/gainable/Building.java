package models.gainable;

import models.maprelated.Hex;
import models.units.Unit;
import models.units.Worker;

public class Building implements Construction
{
    private int cost;
    private int maintenance;
    private int leftTurns;
    
    public Building(int cost, int maintenance, int leftTurns) {
        this.cost = cost;
        this.maintenance = maintenance;
        this.leftTurns = leftTurns;
    }
  
    public int getCost() {
        return this.cost;
    }


    public int getMaintenance() {
        return this.maintenance;
    }

    @Override
    public int getLeftTurns() {
        return this.leftTurns;
    }

    @Override
    public void setLeftTurns(int turns) {
        this.leftTurns = turns;
    }

    @Override
    public void decreaseLeftTurns() {
        this.leftTurns -= 1;
    }

    @Override
    public void build(String type) {

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Hex getHex() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void zeroMpWorker() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Unit getWorker()
    {
        return null;
    }


}
