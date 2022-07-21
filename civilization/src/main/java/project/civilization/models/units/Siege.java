package project.civilization.models.units;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import project.civilization.models.Player;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;

public class Siege extends Ranged implements Combatable {

    public Siege(String name, Hex hex, Player owner) {
        super(name, hex, owner);
        this.isReadyToAttack = false;
    }

    public Siege() {

    }

    public boolean isReadyToAttack() {
        return isReadyToAttack;
    }

    public void setReadyToAttack(boolean readyToAttack) {
        isReadyToAttack = readyToAttack;
    }

    public int calculateCombatModifier(Combatable defender) {
        if (defender instanceof City) {
            combatStrength *= 110.0 / 100;
        }
        this.isReadyToAttack = false;
        combatStrength = combatStrength * (100 + this.currentHex.getTerrain().getCombatModifiersPercentage()) / 100;
        combatStrength = combatStrength * (100 + (1 - (this.health / this.maxHealth)) * 100) / 100;
        return this.combatStrength;
    }

    public Siege CloneASiege(Unit unit){
        this.Yhex = unit.Yhex;
        this.Xhex = unit.Xhex;
        this.state = unit.state;
        this.leftTurns = unit.leftTurns;
        this.isFirstFortify = unit.isFirstFortify;
        this.combatType = unit.combatType;
        this.ordered = unit.ordered;
        this.maxHealth = unit.maxHealth;
        this.health = unit.health;
        this.combatStrength = unit.combatStrength;
        this.rangedStrength = unit.rangedStrength;
        this.range = unit.range;
        this.MP = unit.MP;
        this.backUpMP = unit.backUpMP;
        this.name = unit.name;
        this.cost = unit.cost;
        this.neededTech = unit.neededTech;
        this.neededResource = unit.neededResource;
        this.neededProduction = unit.neededProduction;
        this.defenciveBounes = unit.defenciveBounes;
        this.isReadyToAttack = false;
        this.turn = unit.turn;
        this.building = unit.building;
        return this;
    }
}
