package models.units;

import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Settler extends Civilian
{
    

    public Settler(String name,Hex currentHex)
    {
      super(name, currentHex);
    } 

    public void explore()
    {
        
    }    
}
