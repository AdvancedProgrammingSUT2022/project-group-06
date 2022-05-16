package models.twopartyactions;

import models.Player;

public class Trade {
    private Player firstParty;
    private Player secondParty;
    private String good;


    public Trade(Player firstParty, Player secondParty, String good) {
        this.firstParty = firstParty;
        this.secondParty = secondParty;
        this.good = good;
    }

    public void doTrade() {

    }

    public Player getFirstParty() {
        return this.firstParty;
    }


    public Player getSecondParty() {
        return this.secondParty;
    }

    public String getGood() {
        return this.good;
    }


}
