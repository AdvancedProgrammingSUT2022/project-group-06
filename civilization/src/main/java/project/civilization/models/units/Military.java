package project.civilization.models.units;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.annotations.SerializedName;
import project.civilization.controllers.GameController;
import project.civilization.enums.UnitType;
import project.civilization.models.Player;
import project.civilization.models.maprelated.Hex;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Melee.class, name = "Melee"),
        @JsonSubTypes.Type(value = Ranged.class, name = "Ranged")
})
public class Military extends Unit {
    public Military(){

    }
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
