package project.civilization.models.maprelated;
import com.google.gson.annotations.Expose;
import project.civilization.controllers.InitializeGameInfo;

public class Feature {
    @Expose
    private String name;
    @Expose
    private int food;
    @Expose
    private int production;
    @Expose
    private int gold;
    @Expose
    private int movePoint;
    @Expose
    private int combatModifiersPercentage;

    public Feature(String name) {
        String info = InitializeGameInfo.getFeatureInfo().get(name);
        String[] splitInfo = info.split(" ");
        this.name = name;
        this.food = Integer.parseInt(splitInfo[0]);
        this.production = Integer.parseInt(splitInfo[1]);
        this.gold = Integer.parseInt(splitInfo[2]);
        this.combatModifiersPercentage = Integer.parseInt(splitInfo[3]);
        this.movePoint = Integer.parseInt(splitInfo[4]);

    }


    public String getName() {
        return this.name;
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

    public int getMovePoint() {
        return this.movePoint;
    }

    public int getCombatModifiersPercentage() {
        return this.combatModifiersPercentage;
    }

}
