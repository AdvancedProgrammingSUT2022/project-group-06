package serverapp.models.units;
import java.util.ArrayList;
import serverapp.controllers.GameController;
import serverapp.controllers.InitializeGameInfo;
import serverapp.enums.UnitState;
import serverapp.models.Player;
import serverapp.models.gainable.Construction;
import serverapp.models.maprelated.Hex;

public class Unit implements Combatable, Construction {
    final protected int maxHealth = 10;
    protected int health;
    protected int combatStrength;
    protected int rangedStrength;
    protected int range;
    protected Hex currentHex;
    protected UnitState state;
    protected int MP;
    protected int backUpMP;
    protected String name;
    protected int cost;
    protected String neededTech;
    protected String neededResource;
    protected Player owner;
    protected int neededProduction = 10;
    protected int defenciveBounes = 0;
    protected boolean ordered = false;

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

    protected String combatType;

    protected boolean isFirstFortify = true;
    int leftTurns;


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
    public void build(String type) {


    }

    @Override
    public Hex getHex() {
        return currentHex;
    }

    public Unit(String name, Hex hex, Player owner) {
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
        health = 5;


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
    public void setOwner(Player owner) {
        this.owner = owner;
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
        this.health += 1;
    }

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

    @Override
    public int getX() {
        return this.getCurrentHex().getX();
    }

    @Override
    public int getY() {
        return this.getCurrentHex().getY();
    }

    @Override
    public void zeroMpWorker() {
        // TODO Auto-generated method stub

    }

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
