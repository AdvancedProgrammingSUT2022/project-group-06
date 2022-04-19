package models.maprelated;

import controllers.Game;
import java.util.ArrayList;

public class Resource 
{
    private int food;
    private int production;
    private int gold;
    private String name;
    private String requiredImprovement;
    private ArrayList<String> appropriateTerrain;
    private ArrayList<String> appropriateFeature;


    public Resource(String name) 
    {
        String info=Game.terrainInfo.get(name);
        String[] splitInfo=info.split(" ");
        this.food=Integer.parseInt(splitInfo[0]);
        this.production=Integer.parseInt(splitInfo[1]);
        this.gold=Integer.parseInt(splitInfo[2]);
        this.requiredImprovement=splitInfo[4];

        String[] terrainOrFeature=splitInfo[3].split(",");
        for(String temp:terrainOrFeature)
        {
            if(Game.featureInfo.containsKey(temp))
            {
                appropriateFeature.add(temp);
            }
            else
            {
                appropriateTerrain.add(temp);
            }

        }
    
        
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

    public String getName()
    {
        return this.name;
    }

    public String getRequiredImprovement() 
    {
        return this.requiredImprovement;
    }

    public ArrayList<String> getAppropriateTerrain() 
    {
        return this.appropriateTerrain;
    }

   

}
