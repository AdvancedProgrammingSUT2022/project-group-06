package models.maprelated;

import java.util.HashMap;
import java.util.ArrayList;

public class Resource 
{
    private int food;
    private int production;
    private int gold;
    private String name;
    private HashMap<String,String> requiredTechnologies;
    private ArrayList<String> appropriateTerrain;


    public Resource(String name) 
    {
        
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

    public HashMap<String,String> getRequiredTechnologies() 
    {
        return this.requiredTechnologies;
    }

    public ArrayList<String> getAppropriateTerrain() 
    {
        return this.appropriateTerrain;
    }

   

}
