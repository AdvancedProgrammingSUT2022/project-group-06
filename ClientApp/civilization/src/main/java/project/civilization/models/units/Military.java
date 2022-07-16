package project.civilization.models.units;

import com.google.gson.annotations.SerializedName;
import project.civilization.controllers.GameController;
import project.civilization.enums.UnitType;
import project.civilization.models.Player;
import project.civilization.models.maprelated.Hex;

public class Military extends Unit {
    @SerializedName("type")
    private String typeName;

    @Override
    public void build(String type) {
        if (type.equals("production")) {
            GameController.getCurrentPlayer().decreaseProduction(neededProduction);
        } else {
            GameController.getCurrentPlayer().decreaseGold(cost);
        }

        currentHex.setMilitaryUnit(this);
        GameController.getCurrentPlayer().addToMilitaries(this);
        GameController.getCurrentPlayer().addUnit(this);

    }

    public Military(String name, Hex hex, Player owner) {
        super(name, hex, owner);
        typeName = getClass().getName();
        hex.setMilitaryUnit(this);

    }

    @Override
    public String attack(Combatable defender) {
        return null;
    }

    @Override
    public String defend(Combatable attacker) {
        return null;
    }

    public Boolean isMovementPossible(Hex destination, Unit source) {
        return false;
    }
}
