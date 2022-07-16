package project.civilization.enums;
public enum Actions {
    LOGIN("login"),
    SAVEUSERS("saveUsers"),
    REGISTER("register"),
    LOGOUT("logout"),
    SCOREBOARD("scoreBoard"), DELETEACOUNT("deleteAcount"),
    CHAGENICKNAME("changeNickName"), CHAGEPASSWORD("changePassWord");
    private final String character;

    Actions(String color) {
        this.character = color;
    }

    public String getCharacter() {
        return character;
    }
}

