package project.civilization.models.maprelated;

import project.civilization.models.units.Unit;

public class Movement {
    private Unit unit;
    private Hex currentHex;
    private Hex destination;

    public Movement(Unit unit, Hex currentHex, Hex destination) {
        this.unit = unit;
        this.currentHex = currentHex;
        this.destination = destination;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Hex getCurrentHex() {
        return currentHex;
    }

    public void setCurrentHex(Hex currentHex) {
        this.currentHex = currentHex;
    }

    public Hex getDestination() {
        return destination;
    }

    public void setDestination(Hex destination) {
        this.destination = destination;
    }
}
