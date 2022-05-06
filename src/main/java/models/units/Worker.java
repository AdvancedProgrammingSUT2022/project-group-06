package models.units;

import enums.UnitType;
import models.Player;
import models.gainable.Building;
import models.maprelated.Hex;

public class Worker extends Civilian 
{
    public Worker(String name,Hex currentHex, Player owner)
    {
      super(name, currentHex, owner);
    
    } 
    private int turn;
    private Building building;
    
    public void build()
    {
        
    }
}
