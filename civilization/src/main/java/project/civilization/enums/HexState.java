package project.civilization.enums;

import java.io.Serializable;

public enum HexState implements Serializable {
    Visible(),
    Revealed(),
    FogOfWar();

    public String getState(){
        return this.name();
    }
}
