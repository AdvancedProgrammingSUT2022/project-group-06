package models.units;

import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Civilian extends Unit
{
    Civilian(String name, int speed, int militaryPower, UnitType type, int maxDistance, Player owner) {
        super(name, speed, militaryPower, type, maxDistance, owner);
    }

    public Boolean isMovementPossible(Hex destination, Unit source)
    {
        return false;
    }    
}
