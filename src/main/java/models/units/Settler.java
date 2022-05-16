package models.units;

import controllers.GameController;
import enums.UnitType;
import models.Player;
import models.maprelated.Hex;

public class Settler extends Civilian {


    public Settler(String name, Hex currentHex, Player owner) {
        super(name, currentHex, owner);
    }

    public void explore() {

    }
}
