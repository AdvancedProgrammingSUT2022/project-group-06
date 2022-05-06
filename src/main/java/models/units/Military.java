package models.units;

import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Military extends Unit
{


    public Military(String name, Hex hex, Player owner) {
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
    public Boolean isMovementPossible(Hex destination, Unit source)
    {
        return false;
    }
}
