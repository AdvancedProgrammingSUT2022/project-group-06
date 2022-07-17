package project.civilization.views;

import java.beans.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import project.civilization.controllers.GameController;
import project.civilization.models.Player;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;


public class CityPanel {
   
    @FXML
    private AnchorPane anchorPane;

    public void initialize() {
      
        // //hardcode
        // GameController.setCurrentPlayer(new Player("niki"));
        // for(int i=0;i<50;i++)
        // {
        //     GameController.getCurrentPlayer().addCity(new City(GameController.getCurrentPlayer(), "hello", new Hex(0, i, null, null)));
        // }
    
        getCities();

    }

    private void getCities()
    {
        ScrollPane scrollPane=new ScrollPane();
        Pane demoPane=new Pane();
        
        int startSize=50;
        int height=50;
        int buttonHeight=30;
        int screenWidth=600;
        int screenHeight=400;

        
        String[] cities=GameController.citiesPanel().split("\n");
        for(String temp:cities)
        {
            Button city=new Button();
            city.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
            city.setText(temp);
            city.setPrefWidth(screenWidth);
            city.setPrefHeight(buttonHeight);
            city.setLayoutY(startSize);
            
         
            city.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    city.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#000000");
                    
                }
                
            });
            city.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    city.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
                    
                }
                
            });

            demoPane.getChildren().add(city);
            demoPane.setPrefHeight(screenHeight);
            demoPane.setStyle("-fx-background-color: #1a1a1a" );
            startSize+=height;
        }

        scrollPane.setMaxHeight(screenHeight);
        scrollPane.setPrefWidth(screenWidth);
        scrollPane.setLayoutY(27);
        scrollPane.setContent(demoPane);
        scrollPane.setPannable(true);

        anchorPane.getChildren().add(scrollPane);
        
    }
}
