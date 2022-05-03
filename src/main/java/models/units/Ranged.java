package models.units;

import controllers.GameController;
import enums.HexState;
import models.maprelated.City;
import models.maprelated.Hex;

public class Ranged extends Military implements Combatable{
    public Ranged(String name, Hex hex) {
        super(name, hex);
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
