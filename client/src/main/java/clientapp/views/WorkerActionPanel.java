package clientapp.views;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import clientapp.controllers.GameController;
import clientapp.controllers.UnitController;

import java.util.ArrayList;

public class WorkerActionPanel {

    public AnchorPane anchorPane;
    public void initialize(){
        extractUnits();
    }
    private void extractUnits() {
        ScrollPane scrollPane = new ScrollPane();
        Pane demoPane = new Pane();

        int startSize = 50;
        int height = 50;
        int buttonHeight = 30;
        int screenWidth = 600;
        int screenHeight = 400;

        ArrayList<String> availableWorks = GameController.getAvailableWorks(UnitController.getSelectedUnit());
        for (String temp : availableWorks) {
            Button newUnit = new Button();
            newUnit.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
            newUnit.setText(temp);
            newUnit.setPrefWidth(screenWidth);
            newUnit.setPrefHeight(buttonHeight);
            newUnit.setLayoutY(startSize);

            newUnit.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    newUnit.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#000000");
                }
            });
            newUnit.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    newUnit.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
                }
            });
            newUnit.setOnMouseClicked(event -> {
                GameController.orderToWorker(temp);

            });
            demoPane.getChildren().add(newUnit);
            demoPane.setStyle("-fx-background-color: #1a1a1a");
            startSize += height;
        }
        scrollPane.setMaxHeight(screenHeight);
        scrollPane.setPrefWidth(screenWidth);
        scrollPane.setLayoutY(27);
        scrollPane.setContent(demoPane);
        scrollPane.setPannable(true);
        anchorPane.getChildren().add(scrollPane);
    }
}
