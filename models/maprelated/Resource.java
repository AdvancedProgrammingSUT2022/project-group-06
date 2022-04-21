package models.maprelated;

import controllers.InitializeGameInfo;
import java.util.ArrayList;

public class Resource 
{
    private int food;
    private int production;
    private int gold;
    private String name;
    private String requiredImprovement;
    private ArrayList<String> appropriateTerrain = new ArrayList<>();
    private ArrayList<String> appropriateFeature = new ArrayList<>();
    private String requiredTechnology;


    public Resource(String name) 
    {
        String info= InitializeGameInfo.resourceInfo.get(name);
        String[] splitInfo=info.split(" ");
        this.name = name;
        this.food=Integer.parseInt(splitInfo[0]);
        this.production=Integer.parseInt(splitInfo[1]);
        this.gold=Integer.parseInt(splitInfo[2]);
        this.requiredImprovement=splitInfo[4];
        this.requiredTechnology=splitInfo[5];

        String[] terrainOrFeature=splitInfo[3].split(",");
        for(String temp:terrainOrFeature)
        {
            if(InitializeGameInfo.featureInfo.containsKey(temp))
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


    public String getRequiredImprovement() 
    {
        return this.requiredImprovement;
    }

    public ArrayList<String> getAppropriateTerrain() 
    {
        return this.appropriateTerrain;
    }

    public String getName() {
        return name;
    }
}