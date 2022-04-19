package models.units;

import enums.UnitType;
import models.maprelated.Hex;

public class Military extends Unit 
{
    private UnitType type;

    public Boolean isMovementPossible(Hex destination, Unit source)
    {
        return false;
    }
}
