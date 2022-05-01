package models.units;

import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Military extends Unit 
{

    public Military(String name,Hex currentHex)
    {
      super(name, currentHex);
      
    }
    public Boolean isMovementPossible(Hex destination, Unit source)
    {
        return false;
    }
}
