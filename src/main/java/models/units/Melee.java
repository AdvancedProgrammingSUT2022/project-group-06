package models.units;

import models.Player;
import models.maprelated.Hex;

public class Melee extends Military implements Combatable{

    public Melee(String name, Hex hex, Player owner) {
        super(name, hex, owner);
    }
    public String attack(Combatable defender) {
        return null;
    }
    public String defend(Combatable attacker) {
        return null;
    }
}
