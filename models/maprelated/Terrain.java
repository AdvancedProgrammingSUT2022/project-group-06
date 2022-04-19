package models.maprelated;

import enums.TerrainNames;

public class Terrain 
{
    private TerrainNames name;
    private int food;
    private int production;
    private int gold;
    private int movePoint;
    private int combatModifiersPercentage;


    public TerrainNames getName() 
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
