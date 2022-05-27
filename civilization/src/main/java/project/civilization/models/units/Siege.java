package project.civilization.models.units;
import project.civilization.models.Player;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;

public class Siege extends Ranged implements Combatable {
    private boolean isReadyToAttack = false;

    public Siege(String name, Hex hex, Player owner) {
        super(name, hex, owner);
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

}
