package models.units;

import java.util.ArrayList;

import controllers.GameController;
import controllers.InitializeGameInfo;
import enums.UnitState;
import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Unit implements Combatable
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

    public Unit(String name,Hex hex, Player owner)
    {
        this.owner = owner;
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

    public int getCombatStrength() {
        return combatStrength;
    }

    public void setCombatStrength(int combatStrength) {
        this.combatStrength = combatStrength;
    }

    public int getRangedStrength() {
        return rangedStrength;
    }

    public void setRangedStrength(int rangedStrength) {
        this.rangedStrength = rangedStrength;
    }

    public int getCost()
    {
        return cost;
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

    @Override
    public String attack(Combatable defender) {
        return null;
    }

    @Override
    public String defend(Combatable attacker) {
        return null;
    }

    @Override
    public void healPerTurn() {

    }

    @Override
    public  boolean isInPossibleCombatRange(int x, int y, int seenRange ,int unitX ,int unitY ) {
        if (seenRange == (this.getRange()==0 ? 1 : this.getRange())) return false;
        boolean res = false;
        int[][] direction = GameController.getDirection(unitY);
        for (int[] ints : direction) {
            if (unitX + ints[0] == x && unitY + ints[1] == y) {
                return true;
            }
            res = isInPossibleCombatRange(x, y, seenRange + 1, unitX + ints[0], unitY + ints[1]);
            if(res) break;
        }
        return res;
    }

    @Override
    public int getX() {
        return this.getCurrentHex().getX();
    }

    @Override
    public int getY() {
        return this.getCurrentHex().getY();
    }
}
