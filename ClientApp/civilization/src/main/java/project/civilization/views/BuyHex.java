package project.civilization.views;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import project.civilization.controllers.CityController;
import project.civilization.controllers.GameController;


public class BuyHex {
    private String cityName;
    @FXML
    private Label title;
    @FXML
    private AnchorPane anchorPane;

    BuyHex(String cityName)
    {
        this.cityName=cityName;
    }
    public void initialize()
    {
        ScrollPane scrollPane=new ScrollPane();
        Pane demoPane=new Pane();

        int startSize=50;
        int height=50;
        int buttonHeight=30;
        int screenWidth=600;
        int screenHeight=400;


        GameController.setSelectedCityByName(cityName);
        String[] tiles = CityController.presaleTiles().split("\n");
        for(String temp:tiles)
        {
            Button button=new Button();
            button.setLayoutY(startSize);
            button.setPrefHeight(buttonHeight);
            button.setText(temp);
            button.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
            button.setPrefWidth(screenWidth);

            button.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, CityController.buyHex(Integer.parseInt(button.getText().substring(0,button.getText().indexOf(")")))));
                    alert.showAndWait();

                }

            });
            button.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    button.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#000000");

                }

            });
            button.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    button.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");

                }

            });



            demoPane.getChildren().add(button);
            demoPane.setPrefWidth(screenWidth);
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
