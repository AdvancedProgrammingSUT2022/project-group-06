package controllers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import enums.Color;
import models.Player;
import models.maprelated.*;

public class InitializeGameInfo {
    public static HashMap<String, String> terrainInfo = new HashMap<>();
    public static HashMap<String, String> featureInfo = new HashMap<>();
    public static HashMap<String, String> resourceInfo = new HashMap<>();
    public static HashMap<String, String> technologyInfo = new HashMap<>();
    public static HashMap<String, String> unitInfo=new HashMap<>();

    public static void initializeTerrainInfo() {
        try {
            String readTerrainInfo = new String(Files.readAllBytes(Paths.get("files/TerrainInfo.txt")));
            String[] readInfo = readTerrainInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];

                World.terrainNames.add(name);

                terrainInfo.put(name, info);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeFeatureInfo() {
        try {
            String readFeatureInfo = new String(Files.readAllBytes(Paths.get("files/FeatureInfo.txt")));
            String[] readInfo = readFeatureInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];


                featureInfo.put(name, info);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeTechnologyInfo() {
        try {
            String readTechnologyInfo = new String(Files.readAllBytes(Paths.get("files/TechnologyInfo.txt")));
            String[] readInfo = readTechnologyInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];

                technologyInfo.put(name, info);
                Player.achievedTechnologies.put(name, false);

            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeResourceInfo() {
        try {
            String readResourceInfo = new String(Files.readAllBytes(Paths.get("files/ResourceInfo.txt")));
            String[] readInfo = readResourceInfo.split("\n");
            for (String temp : readInfo) {

                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];


                ArrayList<String> terrains = new ArrayList<String>();
                ArrayList<String> features = new ArrayList<String>();

                String[] terrainOrFeature = read[1].split(" ")[3].split(",");
                for (String temp1 : terrainOrFeature) {
                    if (InitializeGameInfo.featureInfo.containsKey(temp1)) {
                        features.add(temp1);
                    } else {
                        terrains.add(temp1);
                    }

                }
                World.appropriateFeature.put(name, features);
                World.appropriateTerrain.put(name, terrains);

                World.resourceNames.add(name);

                resourceInfo.put(name, info);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static void initializeUnitInfo(){
            
        try {
            String readUnitInfo = new String(Files.readAllBytes(Paths.get("files/UnitInfo.txt")));
            String[] readInfo = readUnitInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];

                String tech=info.split(" ")[6];
                String resource=info.split(" ")[5];

                if(tech.equals("NA"))
                {
                    unitInfo.put(name, null);
                }
                else
                {
                    unitInfo.put(name, tech);
                }
                if(resource.equals("NA"))
                {
                    World.unitNeededResource.put(name,null);
                }
                else
                {
                    World.unitNeededResource.put(name,resource);
                }
                
              
                
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    private static void initializeHashMap() {
        World.terrainPossibleFeature.put("Desert", new String[]{"Oasis", "FoodPlains"});
        World.terrainPossibleFeature.put("Grassland", new String[]{"Forest", "Marsh"});
        World.terrainPossibleFeature.put("Hills", new String[]{"Forest", "Jungle"});
        World.terrainPossibleFeature.put("Plain", new String[]{"Forest", "Jungle"});
        World.terrainPossibleFeature.put("Tundra", new String[]{"Forest"});

        World.terrainColors.put("Desert", Color.ANSI_YELLOW_BACKGROUND);
        World.terrainColors.put("Grassland", Color.ANSI_GREEN_BACKGROUND);
        World.terrainColors.put("Hills", Color.ANSI_RED_BACKGROUND);
        World.terrainColors.put("Mountain", Color.ANSI_BRAWN_BACKGROUND);
        World.terrainColors.put("Ocean", Color.ANSI_BULE_BACKGROUND);
        World.terrainColors.put("Plain", Color.ANSI_Bright_Green_BACKGROUND);
        World.terrainColors.put("Snow", Color.ANSI_WHITE_BACKGROUND);
        World.terrainColors.put("Tundra", Color.ANSI_BLACK_BACKGROUND);
        World.terrainColors.put("Tundra", Color.ANSI_BLACK_BACKGROUND);
    }

    public static void run() {
        initializeTerrainInfo();
        initializeFeatureInfo();
        initializeResourceInfo();
        initializeTechnologyInfo();

        initializeHashMap();
        Terrain terrain = new Terrain("Snow");
        Resource resource = new Resource("Iron");
        Feature feature = new Feature("Marsh");
        Hex hex = new Hex(0, 0, terrain, feature);
    }
}
/*        System.out.println(World.terrainNames);
        System.out.println(World.resourceNames);*/
/*        Technology technology = new Technology("Agriculture");
        System.out.println( technology.GetneededPreviousTechnologies().get(0));*/
/*        System.out.println(World.appropriateTerrain.get("Deer"));
        System.out.println(World.appropriateFeature.get("Deer"));*/
/*        System.out.println(featureInfo.get("FoodPlains"));
        System.out.println(technologyInfo.get("BronzeWorking"));
        System.out.println(resourceInfo.get("Furs"));*/