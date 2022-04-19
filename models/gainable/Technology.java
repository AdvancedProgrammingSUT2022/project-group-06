package models.gainable;

import java.util.ArrayList;

import controllers.Game;

public class Technology 
{
    private String name;
    private int cost;
    private ArrayList<String> neededPreviousTechnologies=new ArrayList<>();

    public Technology(String name) 
    {
        this.name = name;
        String[] info=Game.technologyInfo.get(name).split(" ");
        this.cost=Integer.parseInt(info[0]);

        String[] neededTechnologies=info[1].split(",");
        for(String temp: neededTechnologies)
        {
            neededPreviousTechnologies.add(temp);
        }

    }
    
    public ArrayList<String> GetneededPreviousTechnologies()
    {
        return neededPreviousTechnologies;
    }
    public String getName() {
        return this.name;
    }

    
    public int getCost() {
        return this.cost;
    }

 
    
}
