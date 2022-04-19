package models.maprelated;

import enums.FeatureNames;

public class Feature 
{
    private FeatureNames name;
    private int food;
    private int production;
    private int gold;
    private int movePoint;
    private int combatModifiersPercentage;


    public FeatureNames getName() 
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
