package controllers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import models.Player;

public class Game 
{
    public static HashMap<String,String> terrainInfo=new HashMap<>();
    public static HashMap<String,String> featureInfo=new HashMap<>();
    public static HashMap<String,String> resourceInfo=new HashMap<>();
    public static HashMap<String,String> technologyInfo=new HashMap<>();
    
    public void initializeTerrainInfo()
    { 
         try 
         {
            String readTerrainInfo=new String(Files.readAllBytes(Paths.get("files/TerrainInfo.txt")));
            String[] readInfo=readTerrainInfo.split("\n");
            for(String temp:readInfo)
            {
                String[] read=temp.split("#");
                String name=read[0];
                String info=read[1];

                terrainInfo.put(name,info);
            }
            
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
    }
    public void initializeFeatureInfo()
    {
        try 
         {
            String readFeatureInfo=new String(Files.readAllBytes(Paths.get("files/FeatureInfo.txt")));
            String[] readInfo=readFeatureInfo.split("\n");
            for(String temp:readInfo)
            {
                String[] read=temp.split("#");
                String name=read[0];
                String info=read[1];

                featureInfo.put(name,info);
            }
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
     
    public void initializeTechnologyInfo()
    {
        try 
         {
            String readTechnologyInfo=new String(Files.readAllBytes(Paths.get("files/TechnologyInfo.txt")));
            String[] readInfo=readTechnologyInfo.split("\n");
            for(String temp:readInfo)
            {
                String[] read=temp.split("#");
                String name=read[0];
                String info=read[1];

                technologyInfo.put(name,info);
                
            }



        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
    public void initializeResourceInfo()
    {
        try 
         {
            String readResourceInfo=new String(Files.readAllBytes(Paths.get("files/ResourceInfo.txt")));
            String[] readInfo=readResourceInfo.split("\n");
            for(String temp:readInfo)
            {
                String[] read=temp.split("#");
                String name=read[0];
                String info=read[1];

                resourceInfo.put(name,info);
                Player.achievedTechnologies.put(name, false);
            }
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  

    }
    public void run()
    {
        initializeTerrainInfo();
        initializeFeatureInfo();
        initializeResourceInfo();
        initializeTechnologyInfo();
    }    
}
