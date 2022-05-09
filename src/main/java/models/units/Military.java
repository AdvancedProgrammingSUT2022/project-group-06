package models.units;

import controllers.GameController;
import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Military extends Unit
{


    @Override
    public void build()
    {
      GameController.getCurrentPlayer().decreaseGold(cost);
      currentHex.setMilitaryUnit(this);
      GameController.getCurrentPlayer().addToMilitaries(this);
      GameController.getCurrentPlayer().addUnit(this);
      GameController.addAllMilitary(this);
    }
    
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
