package serverapp.models.units;

import serverapp.models.Player;
import serverapp.models.maprelated.Hex;

public class Melee extends Military implements Combatable {

    public Melee(String name, Hex hex, Player owner) {
        super(name, hex, owner);
    }

    public String attack(Combatable defender) {
        return null;
    }

    public String defend(Combatable attacker) {
        return null;
    }
}
