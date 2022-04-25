package models.units;
 
import java.util.ArrayList;

import controllers.InitializeGameInfo;
import enums.UnitState;
import enums.UnitType;
import models.maprelated.Hex;

public class Unit 
{
    private int health;
    private int combatStrength;
    private int rangedStrength;
    private int range;
    private Hex currentHex;
    private UnitState state;
    private int MP;
    private String type;
    private int cost;
    private String neededTech;
    private String neededResource;

    public Unit(String name,Hex hex )
    {
        this.currentHex=hex;
        this.type=name;

        String[] info=InitializeGameInfo.unitInfo.get(name).split(" ");
        this.cost=Integer.parseInt(info[0]);
        combatStrength=Integer.parseInt(info[1]);
        rangedStrength=Integer.parseInt(info[2]);
        range=Integer.parseInt(info[3]);
        MP=Integer.parseInt(info[4]);
        health=10;


        String tech=info[6];
        String resource=info[5];

        if(tech.equals("NA"))
        {
            neededTech=null;
        }
        else
        {
            neededTech=tech;
        }
        if(resource.equals("NA"))
        {
            neededResource=null;
        }
        else
        {
            neededResource=resource;
        }

    }



    public int getHealth() {
        return this.health;
    }

    public void increaseHealth(int amount) {
        health+=amount;
    }

    public void decreaseHealth(int amount) {
        health-=amount;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Hex getCurrentHex() {
        return this.currentHex;
    }

    public void changeCurrentHex(Hex currentHex) {
        this.currentHex = currentHex;
    }

    public UnitState getState() {
        return this.state;
    }

    public void changeUnitState(UnitState state) {
        this.state = state;
    }

    public int getMP() {
        return this.MP;
    }

    public void increaseMP(int amount) {
        MP+=amount;
    }
    public void decreaseMP(int amount) {
        MP-=amount;
    }


 
}
