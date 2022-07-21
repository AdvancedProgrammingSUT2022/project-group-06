package project.civilization.models.units;

import project.civilization.controllers.GameController;
import project.civilization.enums.HexState;
import project.civilization.enums.UnitState;
import project.civilization.models.Player;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;

import java.util.Objects;

public class Ranged extends Military implements Combatable {
    public Ranged(String name, Hex hex, Player owner) {
        super(name, hex, owner);
    }

    public Ranged() {
        super();
    }

    @Override
    public String attack(Combatable defender) {
        return null;
    }

    @Override
    public String defend(Combatable attacker) {
        return null;
    }

    public int calculateCombatModifier(Combatable defender) {
        if (Objects.equals(this.getName(), "Chariot Archer") &&
                this.getCurrentHex().getFeature().getName().matches("Jungle|Forest") ||
                this.getCurrentHex().getTerrain().getName().matches("Hills")) {
            //todo: check count of rough terrain
            combatStrength *= 80.0 / 100;
        }
        combatStrength = combatStrength * (100 + this.currentHex.getTerrain().getCombatModifiersPercentage()) / 100;
        combatStrength = combatStrength * (100 + (1 - (this.health / this.maxHealth)) * 100) / 100;
        return this.getCombatStrength();
    }

    public int calculateRangedAttackStrength() {
        if (Objects.equals(this.getName(), "Chariot Archer") &&
                this.getCurrentHex().getFeature().getName().matches("Jungle|Forest") ||
                this.getCurrentHex().getTerrain().getName().matches("Hills")) {
            //todo: check count of rough terrain
            rangedStrength *= 80.0 / 100;
        }
        rangedStrength = rangedStrength * (100 + this.currentHex.getTerrain().getCombatModifiersPercentage()) / 100;
        rangedStrength = rangedStrength * (100 + (1 - (this.health / this.maxHealth)) * 100) / 100;
        return this.getCombatStrength();
    }

    public Ranged CloneARanged(Unit unit){
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
