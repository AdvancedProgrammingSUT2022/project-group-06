package models.units;

import java.util.ArrayList;

import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Civilian extends Unit
{
    private static ArrayList<Civilian> civilians=new ArrayList<Civilian>();
    private boolean isWorking;

    public Civilian(String name,Hex currentHex)
    {
      super(name, currentHex);
      this.isWorking=false;
      
      civilians.add(this);
    } 

    public static ArrayList<Civilian> geiCivilians()
    {
        return civilians;
    }
    public boolean getIsWorking()
    {
        return isWorking;
    }
    public Boolean isMovementPossible(Hex destination, Unit source)
    {
        return false;
    }
}
