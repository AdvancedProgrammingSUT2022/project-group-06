package project.civilization.models.units;
import project.civilization.models.Player;
import project.civilization.models.gainable.Building;
import project.civilization.models.maprelated.Hex;

public class Worker extends Civilian {
    public Worker(String name, Hex currentHex, Player owner) {
        super(name, currentHex, owner);
    }

    public Worker() {
    }

    public Worker CloneAWorker(Unit unit){
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
