package project.civilization.views;

import java.util.ArrayList;

import javax.swing.BorderFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import project.civilization.controllers.GameController;
import project.civilization.models.Player;

public class NotificationHistory {
 
    @FXML
    private AnchorPane anchorPane;
   

    
    int lowerBound=0;
    int upperBound=400;

    public void initialize() {
      
        writeNotifications();

    }

    private void writeNotifications()
    {
        //hardcode tor test
        //Player newPlayer=new Player("niki");
        //GameController.setCurrentPlayer(newPlayer);
        ArrayList<String> notifications =GameController.getCurrentPlayer().getNotifications();
        ArrayList<Integer> turns= GameController.getCurrentPlayer().getNotificationsTurns();

        ScrollPane scrollPane=new ScrollPane();
        Pane demoPane=new Pane();
        
        int startSize=50;
        int height=50;
        int labelHeight=30;
        int screenWidth=600;
        int screenHeight=400;

     
        

        for(int i=notifications.size()-1;i>=0;i--)
        {
          
            Label notification=new Label();
            notification.setText(turns.get(i)+") "+notifications.get(i));
            notification.setLayoutY(startSize);
            notification.setPrefWidth(screenWidth);
            notification.setPrefHeight(labelHeight);
            notification.setStyle("-fx-background-color: #2f2f2f");

            // Line newLine=new Line();
            // newLine.setStartX(0);
            // newLine.setEndX(screenWidth);
            // newLine.setFill(Color.GOLDENROD);
            // newLine.setStartY(startSize+labelHeight);
            // newLine.setEndY(startSize+labelHeight);
            // newLine.prefWidth(10);
            

            demoPane.getChildren().add(notification);
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
    
    
    public void close(MouseEvent mouseEvent)
    {
      // MapPage.closePanel("notification-history");
    }

}
