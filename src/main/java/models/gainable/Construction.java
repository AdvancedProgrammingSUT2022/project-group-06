package models.gainable;

import models.maprelated.Hex;

public interface Construction {

    void setLeftTurns(int turns);

    void decreaseLeftTurns();

    int getLeftTurns();

    void build(); //new the object and remove it from list

    String getName();

    Hex getHex();
}
