package project.civilization.models.units;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.annotations.Expose;
import project.civilization.controllers.GameController;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.enums.UnitState;
import project.civilization.models.Player;
import project.civilization.models.gainable.Construction;
import project.civilization.models.maprelated.Hex;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Military.class, name = "Military"),
        @JsonSubTypes.Type(value = Civilian.class, name = "Civilian")
})
public class Unit implements Combatable, Construction {
    final protected int maxHealth = 10;
    protected int health;
    protected int combatStrength;
    protected int rangedStrength;
    protected int range;
    protected int MP;
    protected int backUpMP;
    protected String name;
    protected int cost;
    protected String neededTech;
    protected String neededResource;
    protected int neededProduction = 10;
    protected int defenciveBounes = 0;
    protected boolean ordered = false;
    protected String combatType;
    protected boolean isFirstFortify = true;
    int leftTurns;
    protected UnitState state;
    private int Xhex;
    private int Yhex;

    @JsonIgnore
    protected transient Player owner;
    @JsonIgnore
    protected transient Hex currentHex;
    public Unit(){

    }
    public Unit(String name, Hex hex, Player owner) {
        this.Xhex = hex.getX();
        this.Yhex = hex.getY();

        this.owner = owner;
        this.name = name;
        this.currentHex = hex;
        this.state = UnitState.Active;
        String[] info = InitializeGameInfo.unitInfo.get(name).split(" ");
        this.cost = Integer.parseInt(info[0]);
        combatStrength = Integer.parseInt(info[1]);
        rangedStrength = Integer.parseInt(info[2]);
        range = Integer.parseInt(info[3]);
        MP = Integer.parseInt(info[4]);
        backUpMP = MP;
        combatType = info[7];
        health = maxHealth;


        String tech = info[6];
        String resource = info[5];

        if (tech.equals("NA")) {
            neededTech = null;
        } else {
            neededTech = tech;
        }
        if (resource.equals("NA")) {
            neededResource = null;
        } else {
            neededResource = resource;
        }
    }

    //todo: set combat type
    public int getNeededProduction() {
        return neededProduction;
    }

    public String getNeededResource() {
        return neededResource;
    }

    public String getNeededTech() {
        return neededTech;
    }

    public void setMP(int amount) {
        MP = amount;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public int getBackUpMp() {
        return backUpMP;
    }
    @JsonIgnore
    @Override
    public void setLeftTurns(int leftTurns) {
        this.leftTurns = leftTurns;
    }
    @JsonIgnore
    @Override
    public int getLeftTurns() {
        return this.leftTurns;
    }
    @JsonIgnore
    @Override
    public void decreaseLeftTurns() {
        this.leftTurns -= 1;
    }
    @JsonIgnore
    @Override
    public void build(String type) {
    }

    @JsonIgnore
    @Override
    public Hex getHex() {
        return currentHex;
    }

    public boolean isFirstFortify() {
        return isFirstFortify;
    }

    public void unFortify() {

    }

    public void setFirstFortify(boolean firstFortify) {
        isFirstFortify = firstFortify;
    }

    public String getCombatType() {
        return combatType;
    }

    public int calculateCombatModifier(Combatable defender) {
        combatStrength = combatStrength * (100 + this.currentHex.getTerrain().getCombatModifiersPercentage()) / 100;
        combatStrength = combatStrength * (100 + (1 - (this.health / this.maxHealth)) * 100) / 100;
        return this.combatStrength;
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

    public int getCost() {
        return cost;
    }

    public int getHealth() {
        return this.health;
    }

    public void increaseHealth(int amount) {
        health += amount;
    }

    public void decreaseHealth(int amount) {
        health -= amount;
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

    public void setState(UnitState state) {
        this.state = state;
    }

    public void changeUnitState(UnitState state) {
        this.state = state;
    }

    public int getMP() {
        return this.MP;
    }

    public void increaseMP(int amount) {
        MP += amount;
    }

    public void decreaseMP(int amount) {
        MP -= amount;
    }

    public int getRange() {
        return range;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }
    @JsonIgnore
    @Override
    public String attack(Combatable defender) {
        return null;
    }
    @JsonIgnore
    @Override
    public String defend(Combatable attacker) {
        return null;
    }
    @JsonIgnore
    @Override
    public void healPerTurn() {
        this.health += 1;
    }
    @JsonIgnore
    @Override
    public boolean isInPossibleCombatRange(int x, int y, int seenRange, int attackerX, int attackerY) {
        if (seenRange == (this.getRange() == 0 ? 1 : this.getRange())) return false;
        boolean res = false;
        int[][] direction = GameController.getDirection(attackerY);
        for (int[] ints : direction) {
            if (attackerX + ints[0] == x && attackerY + ints[1] == y) {
                return true;
            }
            res = isInPossibleCombatRange(x, y, seenRange + 1, attackerX + ints[0], attackerY + ints[1]);
            if (res) break;
        }
        return res;
    }

    @JsonIgnore
    @Override
    public int getX() {
        return this.getCurrentHex().getX();
    }

    @JsonIgnore
    @Override
    public int getY() {
        return this.getCurrentHex().getY();
    }

    @JsonIgnore
    @Override
    public void zeroMpWorker() {
        // TODO Auto-generated method stub

    }

    @JsonIgnore
    @Override
    public Unit getWorker() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void increaseBounes(int amount) {
        this.defenciveBounes += amount;
    }

    public int getDefenciveBounes() {
        return this.defenciveBounes;
    }
}
