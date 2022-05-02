package models.units;

import models.maprelated.Hex;

public class Melee extends Military implements Combatable{

    public Melee(String name, Hex hex) {
        super(name, hex);
    }
    public String attack(Combatable defender) {
        return null;
    }
    public String defend(Combatable attacker) {
        return null;
    }
}
