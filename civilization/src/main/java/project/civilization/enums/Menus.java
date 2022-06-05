package project.civilization.enums;

import project.civilization.views.ChatMenu;

public enum Menus {
    SCORE(""),
    LOGIN("login-page"),
    PROFILE(""),
    MAIN(""),
    GAME(""),
    GAMEMenu("game-page"),
    SETTING(""),
    GAMEOVER(""), LOADGAME("load-game-page"),
    CHAT("chat-page"),
    CHATNAVIGATION("chat-navigation-page"),
    STARTPRIVATECHAT("start-private-chat-page");

    private final String character;

    Menus(String name) {
        this.character = name;
    }

    public String getCharacter() {
        return character;
    }


}
