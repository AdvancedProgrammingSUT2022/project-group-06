package models.units;

import controllers.GameController;
import enums.HexState;
import models.Player;
import models.maprelated.City;
import models.maprelated.Hex;

import java.util.Objects;

public class Ranged extends Military implements Combatable{
    public Ranged(String name, Hex hex, Player owner) {
        super(name, hex, owner);
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
        int changes = 0;
        if(Objects.equals(this.getName(), "Chariot Archer") &&
                this.getCurrentHex().getFeature().getName().matches("Jungle|Forest")||
                this.getCurrentHex().getTerrain().getName().matches("Hills")){
            //todo: check count of rough terrain
            changes += -20;
        }
        changes += 100 + this.getCurrentHex().getTerrain().getCombatModifiersPercentage();
        return this.getCombatStrength() * changes/100;
    }
}
