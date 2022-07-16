package clientapp.enums;

import clientapp.views.ChatMenu;

public enum Menus {
    SCORE("score-page"),
    LOGIN("login-page"),
    PROFILE("profile-page"),
    MAIN("main-page"),
    GAME(""),
    GAMEMenu("game-page"),
    SETTING(""),
    GAMEOVER(""),
    REGISTER("register-page"),
    LOADGAME("load-game-page"),
    CHAT("chat-page"),
    CHATNAVIGATION("chat-navigation-page"),
    STARTPRIVATECHAT("start-private-chat-page"),
    CHALLENGEPAGE("challenge-page"),
    MAPPAGE("map-page");

    private final String character;

    Menus(String name) {
        this.character = name;
    }

    public String getCharacter() {
        return character;
    }


}
