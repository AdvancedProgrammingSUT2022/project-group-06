package models.gainable;

import models.maprelated.Hex;

public class TimeVariantProcess 
{
    int duration;
    int beginningTurn;
    Hex hex;
    String name;
    String type;


    


    public TimeVariantProcess(int duration, int beginningTurn, Hex hex, String name,String type) {
        this.duration = duration;
        this.beginningTurn = beginningTurn;
        this.hex = hex;
        this.name = name;
        this.type=type;
    }
    

    public String getType()
    {
        return type;
    }
    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBeginningTurn() {
        return this.beginningTurn;
    }

    public void setBeginningTurn(int beginningTurn) {
        this.beginningTurn = beginningTurn;
    }

    public Hex getHex() {
        return this.hex;
    }

    public void setHex(Hex hex) {
        this.hex = hex;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
