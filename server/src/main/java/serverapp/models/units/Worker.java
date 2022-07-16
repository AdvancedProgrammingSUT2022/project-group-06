package serverapp.models.units;
import serverapp.models.Player;
import serverapp.models.gainable.Building;
import serverapp.models.maprelated.Hex;

public class Worker extends Civilian {

    public Worker(String name, Hex currentHex, Player owner) {
        super(name, currentHex, owner);
    }

    private int turn;
    private Building building;


}
