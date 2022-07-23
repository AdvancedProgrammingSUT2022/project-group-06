package project.civilization.views;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

        List<String> info= Arrays.asList(GameController.cityScreen(cityName).split("\n"));

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

        Button buyHex=new Button();
        buyHex.setText("Buy Hex");
        buyHex.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");


        handleButtonUsage(buyHex);

        demoPane.getChildren().add(buyHex);

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

    private void handleButtonUsage(Button button)
    {
        button.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                loadPanel(cityName);

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
    }

    private Node openPanel;
    public void loadPanel(String cityName)
    {
        FXMLLoader loader = new FXMLLoader(CivilizationApplication.class.getResource("fxml/panels/buy-hex.fxml"));
        try {
            // notificationHistory=;
            if(openPanel!=null)
            {
                return;
            }
            BuyHex cityScreenController=new BuyHex(cityName);
            loader.setController(cityScreenController);
            openPanel= (Node)(loader.load());
            openPanel.setLayoutY(80);
            openPanel.setLayoutX(80);
            anchorPane.getChildren().add(openPanel);


            Button closeButton=new Button();
            closeButton.setLayoutX(80);
            closeButton.setLayoutY(80);
            closeButton.setPrefSize(60, 15);
            closeButton.setText("Close");
            closeButton.setStyle("-fx-background-color:black; -fx-text-fill: goldenrod");

            anchorPane.getChildren().add(closeButton);
            closeButton.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    closeButton.setStyle("-fx-background-color:#3a3a3a; -fx-text-fill: goldenrod");

                }

            });
            closeButton.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    closeButton.setStyle("-fx-background-color:black; -fx-text-fill: goldenrod");

                }

            });
            closeButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent arg0) {
                    anchorPane.getChildren().remove(openPanel);
                    anchorPane.getChildren().remove(closeButton);
                    openPanel=null;
                }

            });
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


}

