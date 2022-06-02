package project.civilization.enums;

public enum Menus {
    SCORE(""),
    LOGIN("login-page"),
    PROFILE(""),
    MAIN(""),
    GAME(""),
    GAMEMenu("game-page"),
    SETTING(""),
    GAMEOVER("");

    private final String character;

    Menus(String name) {
        this.character = name;
    }

    public String getCharacter() {
        return character;
    }


}
