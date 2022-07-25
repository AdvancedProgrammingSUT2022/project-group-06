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
    GETAVAILABLEWORKS("getAvailableWorks"),
    ATTACKUNIT("attackUnit"),
    STARTMOVEMENT("startMovement"),
    DELETEUNIT("deletUnit"),
    PILLAGE("pillage"),
    ALERT("alert"),
    SLEEPUNIT("SLEEPUNIT"),
    FORTIFY("frtify"),
    WAKEDUP("wakeUp"),
    GARRISON("garrison"),
    SETUPFORRANGEATTACK("setUpSiegeForRangeAttack"),
    FORTIFYUNTILLHEAL("fortifyUntilHeal"),
    BUILDCITY("buildCity"),
    UNITLISTPANEL("unitListPanel"),
    getLastTechnology("getLastTechnology"),
    getAvailableTechsArray("getAvailableTechsArray"),
    changeResearch("changeResearch"),
    ISANACHIVEDTECK("ISANACHIVEDTECK"),
    getNotificationsTurns("getNotificationsTurns"),
    getNotifications("getNotifications"),
    militaryPanel("militaryPanel"),
    demographicScreen("demographicScreen"),
    cityScreen("cityScreen"),
    getPlayerCitiesNames("getPlayerCitiesNames"),

    cheatCityProduction("cheatCityProduction"),
    cheatGold("cheatGold"),
    cheatHappiness("cheatHappiness"),
    cheatPopulation("cheatPopulation"),
    cheatProduction("cheatProduction"),
    cheatScore("cheatScore"),
    cheatMP("cheatMP"),
    cheatRangedCombatStrength("cheatRangedCombatStrength"),
    cheatMeleeCombatStrength("cheatMeleeCombatStrength"),
    cheatCityFood("cheatCityFood"),
    cheatCityHitPoint("cheatCityHitPoint"),
    cheatTrophy("cheatTrophy"),
    initializePublicChat("initializePublicChat"),
    sendMessage("sendMessage"),
    updateMessages("updateMessages"),
    getOnlineUsers("getOnlineUsers");

    cheatTrophy("cheatTrophy"),
    setSelectedCity("setSelectedCity"),
    presaleTiles("presaleTiles"),
    buyHex("buyHex"),
    citiesPanel("citiesPanel");

    private final String character;

    Actions(String color) {
        this.character = color;
    }

    public String getCharacter() {
        return character;
    }
}
