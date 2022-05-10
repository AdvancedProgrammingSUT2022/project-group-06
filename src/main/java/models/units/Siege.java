package models.units;

import models.Player;
import models.maprelated.City;
import models.maprelated.Hex;

public class Siege extends Ranged implements Combatable{
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
        int changes = 0;
        if(defender instanceof City){
            changes += 10;
        }
        changes += this.getCurrentHex().getTerrain().getCombatModifiersPercentage();
        this.isReadyToAttack = false;
        changes += (this.isFirstFortify()) ? 25  : 50;
        changes += (1 - (this.health/this.maxHealth))*100;
        return this.getCombatStrength() * (100 + changes)/100;
    }

}
