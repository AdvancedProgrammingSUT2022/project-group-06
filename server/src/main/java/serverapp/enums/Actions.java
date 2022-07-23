package serverapp.enums;

public enum Actions {
    LOGIN("login"),
    SAVEUSERS("saveUsers"),
    REGISTER("register"),
    LOGOUT("logout"),
    SCOREBOARD("scoreBoard"),
    DELETEACOUNT("deleteAcount"),
    CHANGENICKNAME("changeNickName"),
    CHAGEPASSWORD("changePassWord"),
    INVITETOGAME("inviteToGame"),
    STARTGAME("startGame"),
    SENDINITATION("sendInvitation"),
    BROADCASTINVITATION("broadCastInvitation"),
    INITIALIZEHEARINGSERVERSOCKET("initializeHearingServerSocket"),
    UNIQUEUSERNAME("uniqueUsername"),
    ACCEPTINVITATION("acceptInvitation"),
    REJECTINVITATION("REJECTINVITAION"),
    SENDREJECTION("sendRejection"),
    GETPANEDETAILS("getPaneDetails"),
    SELECTILE("selectTile"),
    GETUNITINFORMATION("getUnitInformation"),
    GetHexDetails("getHexDetails"),
    GetTerrainNames("getTerrainNames"),
    GETHexInWidth("getHexInWidth"),
    GETHexInHeight("getHexInHeight"),
    SELECTUNIT("selectUnit"),
    ISMYUNIT("isMyUnit"),
    GETAVAILABLEWORKS("getAvailableWorks");
    private final String character;

    Actions(String color) {
        this.character = color;
    }

    public String getCharacter() {
        return character;
    }
}

