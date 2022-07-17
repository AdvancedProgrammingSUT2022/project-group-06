package project.civilization.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;

public class CityScreen {

    private String cityName;
    @FXML
    private Label title;
    @FXML
    private AnchorPane anchorPane;

    CityScreen(String cityName)
    {
        this.cityName=cityName;
    }
    public void initialize()
    {
        
        title.setText(cityName);
        ScrollPane scrollPane=new ScrollPane();
        Pane demoPane=new Pane();
        
        int startSize=50;
        int height=50;
        int vBoxHeight=30;
        int screenWidth=600;
        int screenHeight=400;
        
        List<String> info=Arrays.asList(GameController.cityScreen(cityName).split("\n"));

        for(int i=0;i<info.size();i++)
        {
            VBox vBox=new VBox();
            if(i<8&&i!=0)
            {
                ImageView logo=new ImageView();
                logo.setFitWidth(20);
                logo.setFitHeight(20);
                logo.setImage(new Image(CivilizationApplication.class.getResource("pictures/cityscreenlogos/" + i + ".png").toExternalForm()));
                vBox.getChildren().add(logo);
            }
            if(i==8)
            {
                ImageView logo=new ImageView();
                logo.setFitWidth(40);
                logo.setFitHeight(40);
                logo.setImage(new Image(CivilizationApplication.class.getResource("pictures/cityscreenlogos/" + i + ".png").toExternalForm()));
                demoPane.getChildren().add(logo);
            }
            vBox.getChildren().add(new Label(info.get(i)));
            vBox.setLayoutY(startSize);
            vBox.setPrefHeight(vBoxHeight);
            vBox.setPrefWidth(screenWidth);
            vBox.setLayoutX(100);

            demoPane.getChildren().add(vBox);
        
            startSize+=height;
        }

        demoPane.setStyle("-fx-background-color:black");
        scrollPane.setMaxHeight(screenHeight);
        scrollPane.setPrefWidth(screenWidth);
        scrollPane.setLayoutY(27);
        scrollPane.setContent(demoPane);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color:black");

        anchorPane.setStyle("-fx-background-color:black");
        anchorPane.getChildren().add(scrollPane);

    }

}
