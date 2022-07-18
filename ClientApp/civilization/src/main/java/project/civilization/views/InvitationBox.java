package project.civilization.views;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvitationBox {
    public Text error;
    public BorderPane borderPane;
    public VBox vBox;

    public void initialize(){
        ArrayList<String> Units = UserController.getAllInvitationLetters();
        ScrollPane scrollPane=new ScrollPane();
        Pane demoPane=new Pane();

        int startSize=50;
        int height=50;
        int buttonHeight=30;
        int screenWidth=600;
        int screenHeight=400;

        for(String temp:Units) {
            Label newUnit=new Label();
            newUnit.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
            newUnit.setText(temp);
            newUnit.setPrefWidth(screenWidth);
            newUnit.setPrefHeight(buttonHeight);
            newUnit.setLayoutY(startSize);
/*            TilePane newUnit=new TilePane();
            newUnit.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
            newUnit.getChildren().add(new Text(temp));
            newUnit.setPrefWidth(screenWidth);
            newUnit.setPrefHeight(buttonHeight);*/
            newUnit.setLayoutY(startSize);
            demoPane.getChildren().add(newUnit);
            demoPane.setStyle("-fx-background-color: #1a1a1a" );
            startSize+=height;
        }


        scrollPane.setMaxHeight(screenHeight);
        scrollPane.setPrefWidth(screenWidth);
        scrollPane.setLayoutY(27);
        scrollPane.setContent(demoPane);
        scrollPane.setPannable(true);

        vBox.getChildren().add(scrollPane);
    }

    public void reload(MouseEvent mouseEvent) {
        initialize();
    }

    public void back(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.GAMEMenu);
    }
}
