package project.civilization.models.twopartyactions;

import project.civilization.models.units.Unit;

public class Combat {
    private Unit firstUnit;
    private Unit secondUnit;


    public Combat(Unit firstUnit, Unit secondUnit) {
        this.firstUnit = firstUnit;
        this.secondUnit = secondUnit;
    }

    public Unit getFirstUnit() {
        return this.firstUnit;
    }


    public Unit getSecondUnit() {
        return this.secondUnit;
    }


}
