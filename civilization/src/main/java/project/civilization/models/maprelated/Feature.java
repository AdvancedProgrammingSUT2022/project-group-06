package project.civilization.models.maprelated;

import com.google.gson.annotations.Expose;
import project.civilization.controllers.InitializeGameInfo;

public class Feature {
    public Feature(){}
    private String name;
    private int food;
    private int production;
    private int gold;
    private int movePoint;
    private int combatModifiersPercentage;


    private transient javafx.scene.image.ImageView featureView;

    public javafx.scene.image.ImageView getFeatureView() {
        return featureView;
    }

    public void setFeatureView(javafx.scene.image.ImageView featureView) {
        this.featureView = featureView;
    }
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
