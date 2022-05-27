package project.civilization.models.units;

import project.civilization.models.Player;
import project.civilization.models.maprelated.Hex;

public class Settler extends Civilian {


    public Settler(String name, Hex currentHex, Player owner) {
        super(name, currentHex, owner);
    }

    public void explore() {

    }
}
