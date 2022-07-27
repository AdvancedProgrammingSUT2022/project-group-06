package project.civilization.views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.CityController;
import project.civilization.controllers.GameController;

import java.util.ArrayList;

public class BuildingMenu {

    @FXML
    private AnchorPane pane;

    private String cityName;

    public BuildingMenu(String cityName) {
        this.cityName = cityName;
    }

    public void initialize() {
        pane.setStyle("-fx-background-color:#000000");
        showAvailableBuildings();
    }

    public void showAvailableBuildings() {
        ArrayList<String> names = CityController.getAvailableBuildings(cityName);
        VBox vBox = new VBox();
        VBox imageBox = new VBox();
        imageBox.setSpacing(10);
        imageBox.setLayoutX(200);
        imageBox.setLayoutY(100);
        imageBox.setPrefWidth(200);
        imageBox.setPrefHeight(300);
        vBox.setLayoutX(100);
        vBox.setLayoutY(100);
        vBox.setPrefWidth(100);
        vBox.setPrefHeight(300);
        vBox.setSpacing(25);
        Image image;

        names.remove("Palace");
        for (String name : names) {
            initializeLabel(name, vBox);
            String address = "pictures/building/" + name.toLowerCase() + ".png";
            image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageBox.getChildren().add(imageView);
        }
        pane.getChildren().add(vBox);
        pane.getChildren().add(imageBox);
    }

    public void initializeLabel(String name, VBox vBox) {
        Button button = new Button(name);
        button.setPrefWidth(100);
        button.setPrefHeight(30);
        vBox.getChildren().add(button);
        String tooltipString = GameController.getBuildingInfo(name);
        button.setTooltip(new Tooltip(tooltipString));
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CityController.buildABuilding(name, cityName);
            }
        });
    }
}
