package project.civilization.enums;

public enum MenuCategory {
    LOGIN("login"),
    PROFILE("profile"),
    MAIN("main"),
    GAMEMenu("game"),
    NETWORK("network");

    private final String character;

    MenuCategory(String name) {
        this.character = name;
    }

    public String getCharacter() {
        return character;
    }

}
