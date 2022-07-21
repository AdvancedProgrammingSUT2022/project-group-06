package project.civilization.models.maprelated;


import com.google.gson.annotations.Expose;
import project.civilization.controllers.GameController;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.enums.Color;
import project.civilization.enums.HexState;
import project.civilization.models.Player;
import project.civilization.models.gainable.Improvement;
import project.civilization.models.units.Civilian;
import project.civilization.models.units.Military;
import project.civilization.models.units.Unit;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.HashMap;

public class Hex {
    private int x;
    private int y;
    private boolean[] hasRiver = new boolean[]{false, false, false, false};
    private boolean hasCitizen = false;
    private boolean hasRoad;
    private boolean hasRailRoad;
    private boolean isPillaged = false;
    private Terrain terrain;
    private Feature feature;
    private Resource resource;
    private String  ownerUserName;
    private HashMap<String , HexState> StateOfHexForEachPlayer = new HashMap<>();
    private int hasRuin=0;

    private transient Military militaryUnit;

    private transient Civilian civilianUnit;

    private transient City capital = null;

    private transient City city = null;

    private transient Player owner = null;



    private transient ArrayList<Improvement> improvements = new ArrayList<Improvement>();

    public void setFeature(Feature newFeature) {
        feature = newFeature;
    }
    public void setHasCitizen(boolean hasCitizen) {
        this.hasCitizen = hasCitizen;
    }

    public Hex(int x, int y, Terrain terrain, Feature feature) {
        this.x = x;
        this.y = y;
        hasRoad = false;
        hasRailRoad = false;
        this.terrain = terrain;
        this.feature = feature;
        for (int i = 0; i < InitializeGameInfo.getNumberOFPlayers(); i++) {
            this.StateOfHexForEachPlayer.put(InitializeGameInfo.getPlayers().get(i).getName(), HexState.FogOfWar);
        }
    }


    public int getHasRuins()
    {
        return hasRuin;
    }
    public void setRuinsValue(int value)
    {
        hasRuin=value;
    }
    
    public void setImprovements(ArrayList<Improvement> improvements) {
        this.improvements = improvements;
    }

    public void setHasRiver(boolean[] hasRiver) {
        this.hasRiver = hasRiver;
    }

    public HashMap<String, HexState> getStateOfHexForEachPlayer() {
        return StateOfHexForEachPlayer;
    }

    public void setStateOfHexForEachPlayer(HashMap<String, HexState> stateOfHexForEachPlayer) {
        StateOfHexForEachPlayer = stateOfHexForEachPlayer;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public boolean getHasCitizen() {
        return hasCitizen;
    }

    public void setState(HexState hexState, Player player) {
        this.StateOfHexForEachPlayer.put(player.getName(), hexState);
    }

    public void setOwner(Player owner) {
        if(owner != null){
            ownerUserName = owner.getName();
        }else ownerUserName = null;
        this.owner = owner;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getOwner() {
        return owner;
    }

    public HexState getState(Player player) {
        return StateOfHexForEachPlayer.get(player.getName());
    }

    public Feature getFeature() {
        return feature;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Military getMilitaryUnit() {
        return militaryUnit;
    }

    public void setMilitaryUnit(Military military) {
        this.militaryUnit = military;
    }

    public Civilian getCivilianUnit() {
        return civilianUnit;
    }

    public void setCivilianUnit(Civilian civilianUnit) {
        this.civilianUnit = civilianUnit;
    }


    public void setRiver(int i) {
        hasRiver[i] = true;
    }

    public boolean isRiver(int dir) {
        return hasRiver[dir];
    }

    public boolean[] getHasRiver() {
        return hasRiver;
    }

    public boolean isNextToAnyRiver() {
        for (boolean river : hasRiver) {
            if (river)
                return true;
        }
        return false;
    }

    public int riverDir() {
        for (int i = 0; i < 4; i++) {
            if (hasRiver[i]) return i;
        }
        return 7;
    }

    public void addImprovement(Improvement improvement) {
        improvements.add(improvement);

    }

    public boolean isPillaged() {
        return isPillaged;
    }

    public void setPillaged(boolean pillaged) {
        isPillaged = pillaged;
    }

    public ArrayList<Improvement> getImprovement() {
        return improvements;
    }

    public void setCapital(City city) {
        this.capital = city;
    }

    public City getCapital() {
        return this.capital;
    }

    public boolean hasRoad() {
        return this.hasRoad;
    }

    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public boolean hasRailRoad() {
        return this.hasRailRoad;
    }

    public void setHasRailRoad(boolean hasRailRoad) {
        this.hasRailRoad = hasRailRoad;
    }
}