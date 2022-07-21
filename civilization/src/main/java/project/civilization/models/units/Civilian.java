package project.civilization.models.units;

import java.util.ArrayList;

import project.civilization.controllers.GameController;
import project.civilization.models.Player;
import project.civilization.models.maprelated.Hex;

public class Civilian extends Unit {
    public Civilian() {
        super();
    }

    @Override
    public void build(String type) {
        if (type.equals("production")) {
            GameController.getCurrentPlayer().decreaseProduction(neededProduction);
        } else {
            GameController.getCurrentPlayer().decreaseGold(cost);
        }


        currentHex.setCivilianUnit(this);
        GameController.getCurrentPlayer().addToCivilians(this);
        GameController.getCurrentPlayer().addUnit(this);

    }

    public Civilian(String name, Hex hex, Player owner) {
        super(name, hex, owner);
        hex.setCivilianUnit(this);
    }


    public Boolean isMovementPossible(Hex destination, Unit source) {
        return false;
    }

}
