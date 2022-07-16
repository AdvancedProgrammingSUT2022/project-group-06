package serverapp.models.units;

import serverapp.models.Player;
import serverapp.models.maprelated.Hex;

public class Settler extends Civilian {


    public Settler(String name, Hex currentHex, Player owner) {
        super(name, currentHex, owner);
    }

    public void explore() {

    }
}
