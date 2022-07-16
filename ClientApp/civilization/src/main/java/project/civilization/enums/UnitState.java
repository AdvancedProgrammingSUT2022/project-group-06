package project.civilization.enums;

public enum UnitState {
    Active("active"),
    Sleep("sleep"),
    Alert("alert"),
    Fortified("fortified"),
    FortifiedUntilHeal("fortifiedUntilHeal"),
    Garrisoned("garrisoned"),
    setUpForRangeAttack("setUpForRangeAttack");
    private final String character;

    UnitState(String color) {
        this.character = color;
    }

    public String getCharacter() {
        return character;
    }
}
