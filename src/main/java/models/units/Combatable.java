package models.units;

import models.Player;

public interface Combatable {
    public Player getOwner();

    String attack(Combatable defender);

    String defend(Combatable attacker);
}
