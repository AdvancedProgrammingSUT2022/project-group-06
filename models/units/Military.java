package models.units;

import enums.UnitType;
import models.maprelated.Hex;

public class Military extends Unit 
{


    public Boolean isMovementPossible(Hex destination, Unit source)
    {
        return false;
    }
}
