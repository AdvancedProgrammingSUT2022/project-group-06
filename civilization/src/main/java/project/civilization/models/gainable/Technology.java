package project.civilization.models.gainable;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.models.Player;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.maprelated.Terrain;
import project.civilization.models.units.Unit;
import project.civilization.models.units.Worker;

public class Technology implements Construction {
    public Technology(){}
    private String name;
    private int cost;
    private ArrayList<String> neededPreviousTechnologies = new ArrayList<>();
    private int leftTurns;

    @JsonIgnore
    private transient Player owner;

    public Technology(String name, Player owner) {
        this.name = name;
        this.owner = owner;
        String[] info = InitializeGameInfo.getTechnologyInfo().get(name).split(" ");
        this.cost = Integer.parseInt(info[0]);

        String[] neededTechnologies = info[1].split(",");
        for (String temp : neededTechnologies) {
            neededPreviousTechnologies.add(temp);
        }

    }

    @Override
    public int getLeftTurns() {
        return this.leftTurns;
    }

    @Override
    public void decreaseLeftTurns() {
        this.leftTurns -= 1;
    }

    @Override
    public void setLeftTurns(int leftTurns) {
        this.leftTurns = leftTurns;
    }

    public ArrayList<String> getNeededPreviousTechnologies() {
        return neededPreviousTechnologies;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    @Override
    public void build(String type) {
        owner.unlockTechnology(this.name);
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
        // TODO Auto-generated method stub
        return null;
    }


}
