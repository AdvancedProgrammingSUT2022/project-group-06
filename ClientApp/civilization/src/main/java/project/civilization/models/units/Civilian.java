package project.civilization.models.units;

import java.util.ArrayList;

import project.civilization.controllers.GameController;
import project.civilization.models.Player;
import project.civilization.models.maprelated.Hex;

public class Civilian extends Unit {
    private static ArrayList<Civilian> civilians = new ArrayList<Civilian>();
    private boolean isWorking;

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
        this.isWorking = false;
        hex.setCivilianUnit(this);
        civilians.add(this);

    }

    public static ArrayList<Civilian> geiCivilians() {
        return civilians;
    }

    public boolean getIsWorking() {
        return isWorking;
    }

    public Boolean isMovementPossible(Hex destination, Unit source) {
        return false;
    }
}
