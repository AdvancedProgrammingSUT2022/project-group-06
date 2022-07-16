package serverapp.models.gainable;

import serverapp.models.maprelated.Hex;
import serverapp.models.units.Unit;
import serverapp.models.units.Worker;

public interface Construction {

    void setLeftTurns(int turns);

    void decreaseLeftTurns();

    int getLeftTurns();

    void build(String type); //new the object and remove it from list

    String getName();

    Hex getHex();

    void zeroMpWorker();

    Unit getWorker();


}
