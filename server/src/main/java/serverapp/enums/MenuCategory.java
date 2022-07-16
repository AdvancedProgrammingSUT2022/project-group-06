package serverapp.enums;

public enum MenuCategory {
    LOGIN("login"),
    PROFILE("profile"),
    MAIN("main"),
    GAMEMenu("game");

    private final String character;

    MenuCategory(String name) {
        this.character = name;
    }

    public String getCharacter() {
        return character;
    }

}
