package models.units;

import models.maprelated.Hex;

public class Civilian extends Unit
{
    public Boolean isMovementPossible(Hex destination, Unit source)
    {
        return false;
    }    
}
