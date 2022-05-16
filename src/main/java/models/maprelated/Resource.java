package models.maprelated;

import controllers.InitializeGameInfo;

import java.util.ArrayList;

public class Resource {
    private static ArrayList<Resource> resources = new ArrayList<Resource>();
    private int food;
    private int production;
    private int gold;
    private String name;
    private String requiredImprovement;
    private ArrayList<String> appropriateTerrain = new ArrayList<>();
    private ArrayList<String> appropriateFeature = new ArrayList<>();
    private String requiredTechnology;
    private String type;


    public Resource(String name) {
        String info = InitializeGameInfo.getResourceInfo().get(name);
        String[] splitInfo = info.split(" ");
        this.name = name;
        this.food = Integer.parseInt(splitInfo[0]);
        this.production = Integer.parseInt(splitInfo[1]);
        this.gold = Integer.parseInt(splitInfo[2]);
        this.requiredImprovement = splitInfo[4];
        this.requiredTechnology = splitInfo[5];
        this.type = splitInfo[6];

        String[] terrainOrFeature = splitInfo[3].split(",");
        for (String temp : terrainOrFeature) {
            if (InitializeGameInfo.getFeatureInfo().containsKey(temp)) {
                appropriateFeature.add(temp);
            } else {
                appropriateTerrain.add(temp);
            }

        }


        resources.add(this);
    }

    public String getType() {
        return type;
    }

    public int getFood() {
        return this.food;
    }

    public int getProduction() {
        return this.production;
    }

    public int getGold() {
        return this.gold;
    }


    public String getRequiredImprovement() {
        return this.requiredImprovement;
    }

    public ArrayList<String> getAppropriateTerrain() {
        return this.appropriateTerrain;
    }

    public String getName() {
        return name;
    }
}
