package project.civilization.models.gainable;

import com.google.gson.Gson;
import javafx.scene.image.ImageView;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.units.Unit;
import project.civilization.models.units.Worker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Building implements Construction {
    private String name;
    private int production;
    private int maintenance;
    private int leftTurns;
    private ArrayList<String> requiredTechnologies;

//    private ImageView buildingView;

    public Building(String name, int production, int maintenance, int leftTurns, ArrayList<String> requiredTechnologies) {
        this.name = name;
        this.production = production;
        this.maintenance = maintenance;
        this.leftTurns = leftTurns;
        this.requiredTechnologies = new ArrayList<>(requiredTechnologies);
    }

    public Building(String name) {
        for (Building building : InitializeGameInfo.getAllBuildings()) {
            if(building.getName().equals(name)) {
                this.name = building.getName();
                this.production = building.production;
                this.maintenance = building.getMaintenance();
                this.leftTurns = building.getLeftTurns();
                this.requiredTechnologies = building.requiredTechnologies;
            }
        }
//        Building.fromJson(Files.readString(Path.of(fileName)));//TODO???
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Building fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Building.class);
    }

//    public int getCost() {
//        return this.cost;
//    }


    public int getMaintenance() {
        return this.maintenance;
    }

    @Override
    public int getLeftTurns() {
        return this.leftTurns;
    }

    @Override
    public void setLeftTurns(int turns) {
        this.leftTurns = turns;
    }

    @Override
    public void decreaseLeftTurns() {
        this.leftTurns -= 1;
    }

    @Override
    public void build(String type) {

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Hex getHex() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void zeroMpWorker() {
        // TODO Auto-generated method stub

    }

    @Override
    public Unit getWorker() {
        return null;
    }


//    public ImageView getBuildingView() {
//        return buildingView;
//    }
//
//    public void setBuildingView(ImageView buildingView) {
//        this.buildingView = buildingView;
//    }

    public int getProduction() {
        return production;
    }

    public ArrayList<String> getRequiredTechnologies() {
        return requiredTechnologies;
    }
}
