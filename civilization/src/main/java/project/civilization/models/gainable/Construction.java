package project.civilization.models.gainable;

import project.civilization.models.maprelated.Hex;
import project.civilization.models.units.Unit;
import project.civilization.models.units.Worker;

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
