package models.gainable;

import models.maprelated.Hex;
import models.units.Unit;
import models.units.Worker;

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
