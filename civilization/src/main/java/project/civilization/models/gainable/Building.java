package project.civilization.models.gainable;

import com.google.gson.Gson;
import javafx.scene.image.ImageView;
import project.civilization.controllers.CityController;
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
    private int cost;
    private int maintenance;
    private int leftTurns;
    private String technology;
    private Hex hex;
    private Building prerequisite;

    private ImageView buildingView;


    public Building() {}

    public Building(String name, int cost, int maintenance, int leftTurns, String technology) {
        this.name = name;
        this.cost = cost;
        this.maintenance = maintenance;
        this.leftTurns = leftTurns;
        this.technology = technology;
    }

    public static Building clone(Building building, Hex hex) {
        Building newBuilding = new Building();
        newBuilding.cost = building.cost;
        newBuilding.maintenance = building.maintenance;
        newBuilding.leftTurns = building.leftTurns;
        newBuilding.name = building.name;
        newBuilding.technology = building.technology;
        newBuilding.buildingView = building.buildingView;

        newBuilding.hex = hex;
        return newBuilding;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Building fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Building.class);
    }

    public int getCost() {
        return this.cost;
    }


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
        this.hex.getCity().getBuiltBuildings().add(this);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Hex getHex() {
        return this.hex;
    }

    @Override//ignore
    public void zeroMpWorker() {

    }

    @Override
    public Unit getWorker() {
        return null;
    }

    public void setHex(Hex hex) {
        this.hex = hex;
    }

    public String getTechnology() {
        return technology;
    }

    public Building getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(Building prerequisite) {
        this.prerequisite = prerequisite;
    }

    public ImageView getBuildingView() {
        return buildingView;
    }

    public void setBuildingView(ImageView buildingView) {
        this.buildingView = buildingView;
    }
}
