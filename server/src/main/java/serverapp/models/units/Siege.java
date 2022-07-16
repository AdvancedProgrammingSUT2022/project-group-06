package serverapp.models.units;

import serverapp.models.Player;
import serverapp.models.maprelated.City;
import serverapp.models.maprelated.Hex;

public class Siege extends Ranged implements Combatable {
    private boolean isReadyToAttack = false;
    private String typeName;
    public Siege(String name, Hex hex, Player owner) {
        super(name, hex, owner);
        typeName = getClass().getName();

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
