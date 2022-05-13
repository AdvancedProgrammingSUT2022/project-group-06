package models.units;

import controllers.GameController;
import enums.HexState;
import enums.UnitState;
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
        if(Objects.equals(this.getName(), "Chariot Archer") &&
                this.getCurrentHex().getFeature().getName().matches("Jungle|Forest")||
                this.getCurrentHex().getTerrain().getName().matches("Hills")){
            //todo: check count of rough terrain
            combatStrength *= 80.0 /100;
        }
        combatStrength = combatStrength* (100 + this.currentHex.getTerrain().getCombatModifiersPercentage()) /100;
        if(this.state == UnitState.Fortified) combatStrength *= (double) (100 + ((this.isFirstFortify()) ? 25 : 50)) / 100;
        combatStrength =combatStrength * (100 + (1 - (this.health / this.maxHealth)) * 100) /100;
        return this.getCombatStrength() ;
    }
}
