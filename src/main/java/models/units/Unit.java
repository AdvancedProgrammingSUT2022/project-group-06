package models.units;

import java.util.ArrayList;

import controllers.InitializeGameInfo;
import enums.UnitState;
import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Unit
{
    private static ArrayList<Unit> units=new ArrayList<Unit>();
    private int health;
    private int combatStrength;
    private int rangedStrength;
    private int range;
    private Hex currentHex;
    private UnitState state;
    private int MP;
    private String name;
    private int cost;
    private String neededTech;
    private String neededResource;
    private Player owner;

    public Unit(String name,Hex hex)
    {
        
        this.name=name;
        this.currentHex=hex;
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


        units.add(this);

    }



    public Unit(String name, int speed, int militaryPower, UnitType type2, int maxDistance, Player owner) {
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


    public int getRange() {
        return range;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName(){
        return name;
    }
}
