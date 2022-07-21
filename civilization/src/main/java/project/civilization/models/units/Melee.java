package project.civilization.models.units;

import project.civilization.models.Player;
import project.civilization.models.maprelated.Hex;

public class Melee extends Military implements Combatable {
    public Melee(String name, Hex hex, Player owner) {
        super(name, hex, owner);
    }

    public Melee() {
    }

    public String attack(Combatable defender) {
        return null;
    }

    public String defend(Combatable attacker) {
        return null;
    }

    public Melee CloneAMelee(Unit unit){
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
