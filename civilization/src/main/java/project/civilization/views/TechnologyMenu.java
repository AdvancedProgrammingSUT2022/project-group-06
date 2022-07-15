package project.civilization.views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.civilization.CivilizationApplication;
import project.civilization.models.gainable.Technology;

import java.io.IOException;
import java.util.ArrayList;

public class TechnologyMenu {

    @FXML
    private AnchorPane pane;
    @FXML
    private Label lastTechnologyLabel;//showing the last studied technology
    @FXML
    private Button openTreeButton; //open technology tree
    //name of technologies you can start researching

    private ArrayList<HBox> techBoxes;
    int screenWidth=600;
    int screenHeight=400;


    public void setLastTechnologyLabel(String name) { //should be called as this page initializes
        lastTechnologyLabel.setText(name);
    }

    public void initialize() {
        lastTechnologyLabel = new Label();//TODO: set label
        openTreeButton = new Button();
        openTreeButton.setLayoutY(300);
        openTreeButton.setLayoutX(150);
        openTreeButton.setPrefWidth(100);
        openTreeButton.setPrefHeight(70);
        openTreeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadPanel("technology-tree-page");
            }
        });
        pane.getChildren().add(openTreeButton);
    }

    public void openTree(MouseEvent mouseEvent) {
        loadPanel("technology-tree-page");
    }

    private Node openPanel;

    public void loadPanel(String name)
    {
        FXMLLoader loader = new FXMLLoader(CivilizationApplication.class.getResource("fxml/panels/"+name+".fxml"));
        try {
            // notificationHistory=;
            openPanel= (Node)(loader.load());
            openPanel.setLayoutY(80);
            openPanel.setLayoutX(80);
            pane.getChildren().add(openPanel);


            Button closeButton=new Button();
            closeButton.setLayoutX(80);
            closeButton.setLayoutY(80);
            closeButton.setPrefSize(60, 15);
            closeButton.setText("Close");
            closeButton.setStyle("-fx-background-color:black; -fx-text-fill: goldenrod");

            pane.getChildren().add(closeButton);
            closeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    closeButton.setStyle("-fx-background-color:#3a3a3a; -fx-text-fill: goldenrod");

                }

            });
            closeButton.setOnMouseExited(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    closeButton.setStyle("-fx-background-color:black; -fx-text-fill: goldenrod");

                }

            });
            closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent arg0) {
                    pane.getChildren().remove(openPanel);
                    pane.getChildren().remove(closeButton);
                }

            });
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


}
