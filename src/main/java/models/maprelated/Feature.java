package models.maprelated;

import controllers.InitializeGameInfo;

public class Feature
{

    private String name;
    private int food;
    private int production;
    private int gold;
    private int movePoint;
    private int combatModifiersPercentage;

    public Feature(String name)
    {
        String info= InitializeGameInfo.getFeatureInfo().get(name);
        String[] splitInfo=info.split(" ");
        this.name = name;
        this.food=Integer.parseInt(splitInfo[0]);
        this.production=Integer.parseInt(splitInfo[1]);
        this.gold=Integer.parseInt(splitInfo[2]);
        this.combatModifiersPercentage=Integer.parseInt(splitInfo[3]);
        this.movePoint=Integer.parseInt(splitInfo[4]);

    }


    public String getName()
    {
        return this.name;
    }

    public int getFood()
    {
        return this.food;
    }

    public int getProduction()
    {
        return this.production;
    }

    public int getGold()
    {
        return this.gold;
    }

    public int getMovePoint()
    {
        return this.movePoint;
    }

    public int getCombatModifiersPercentage()
    {
        return this.combatModifiersPercentage;
    }

}
