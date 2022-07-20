package project.civilization.models.units;
import project.civilization.models.Player;
import project.civilization.models.gainable.Building;
import project.civilization.models.maprelated.Hex;

public class Worker extends Civilian {
    public Worker(){

    }
    public Worker(String name, Hex currentHex, Player owner) {
        super(name, currentHex, owner);
    }

    private int turn;
    private Building building;


}
