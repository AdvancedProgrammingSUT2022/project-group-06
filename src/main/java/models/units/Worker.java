package models.units;

import enums.UnitType;
import models.Player;
import models.gainable.Building;
import models.maprelated.Hex;

public class Worker extends Civilian 
{
    public Worker(String name,Hex currentHex)
    {
      super(name, currentHex);
    
    } 
    private int turn;
    private Building building;
    
    public void build()
    {
        
    }
}
