package models.units;

import controllers.GameController;
import enums.HexState;
import models.Player;
import models.maprelated.City;
import models.maprelated.Hex;

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

}
