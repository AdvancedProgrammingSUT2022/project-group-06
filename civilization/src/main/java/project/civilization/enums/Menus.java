package project.civilization.enums;

public enum Menus {
    SCORE(""),
    LOGIN("login-page"),
    PROFILE(""),
    MAIN(""),
    GAME(""),
    GAMEMenu("game-page"),
    SETTING(""),
    GAMEOVER(""), LOADGAME("load-game-page");

    private final String character;

    Menus(String name) {
        this.character = name;
    }

    public String getCharacter() {
        return character;
    }


}
