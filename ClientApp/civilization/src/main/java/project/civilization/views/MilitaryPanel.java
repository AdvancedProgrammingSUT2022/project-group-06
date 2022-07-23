package project.civilization.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import project.civilization.controllers.GameController;

public class MilitaryPanel {
    @FXML
    private AnchorPane anchorPane;

    public void initialize() {

        //hardcode for test
        // Player newPlayer=new Player("Niki");
        // for(int i=0;i<50;i++)
        // {
        //     newPlayer.addToMilitaries(new Military("Warrior", new Hex(0,0, null, null), newPlayer));
        // }
        // GameController.setCurrentPlayer(newPlayer);

        writeMilitaryInfo();

    }
    private void writeMilitaryInfo()
    {
        int screenWidth=600;
        int screenHeight=400;

        ScrollPane scrollPane=new ScrollPane();
        Pane demoPane=new Pane();

        Label militaryInfo=new Label();
        militaryInfo.setText(GameController.militaryPanel());
        militaryInfo.setLayoutX(50);
        militaryInfo.setLayoutY(50);
        militaryInfo.setPrefWidth(screenWidth);
        militaryInfo.setStyle("-fx-text-fill: goldenrod; -fx-background-color: black");

        demoPane.setStyle("-fx-background-color: black");
        demoPane.getChildren().add(militaryInfo);
        demoPane.setPrefHeight(screenHeight);
        // demoPane.setLayoutX(50);
        // demoPane.setLayoutY(50);


        scrollPane.setPrefHeight(screenHeight);
        scrollPane.setMaxHeight(screenHeight);
        scrollPane.setPrefWidth(screenWidth);
        scrollPane.setContent(demoPane);
        scrollPane.setLayoutY(27);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: black");
        anchorPane.setStyle("-fx-background-color:black");


        anchorPane.getChildren().add(scrollPane);

    }
}
