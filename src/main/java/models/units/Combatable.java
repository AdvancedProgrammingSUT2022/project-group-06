package models.units;

import models.Player;
import models.maprelated.Hex;

public interface Combatable {
    Player getOwner();

    String attack(Combatable defender);

    String defend(Combatable attacker);

    void healPerTurn();

    boolean isInPossibleCombatRange(int x, int y, int seenRange ,int unitX ,int unitY );

    int getX();

    int getY();
}
