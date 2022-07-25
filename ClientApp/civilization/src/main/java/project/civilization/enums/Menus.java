package project.civilization.enums;

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
    PUBLICCHAT("public-chat-page"),
    CHATNAVIGATION("chat-navigation-page"),
    STARTPRIVATECHAT("start-private-chat-page"),
    CHALLENGEPAGE("challenge-page"),
    MAPPAGE("map-page"),
    INVIATIONBOX("invitation-box");

    private final String character;

    Menus(String name) {
        this.character = name;
    }

    public String getCharacter() {
        return character;
    }


}
