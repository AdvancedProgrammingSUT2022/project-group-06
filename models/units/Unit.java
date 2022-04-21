package models.units;
 
import enums.UnitState;
import enums.UnitType;
import models.maprelated.Hex;

public class Unit 
{
    private int health;
    private int speed;
    private int militaryPower;
    private Hex currentHex;
    private UnitState state;
    private int MP;
    private UnitType type;
    private int maxDistance;


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


    public int getMaxDistance() {
        return maxDistance;
    }
}
