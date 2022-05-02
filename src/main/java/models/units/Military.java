package models.units;

import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Military extends Unit
{


    public Military(String name, Hex hex) {
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
    public Boolean isMovementPossible(Hex destination, Unit source)
    {
        return false;
    }
}
