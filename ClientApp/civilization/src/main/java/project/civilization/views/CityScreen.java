package project.civilization.views;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.CityController;
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

        int startSize=150;
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

        Button buyBuilding = new Button();
        buyBuilding.setText("Buildings");
        handleBuyBuildingButton(buyBuilding);
        buyBuilding.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");


        HBox fields=new HBox();
        TextField x=new TextField();
        TextField y=new TextField();
        x.setPromptText("enter x");
        y.setPromptText("enter y");

        fields.getChildren().add(x);
        fields.getChildren().add(y);

        HBox buttons=new HBox();
        Button lock=new Button();
        Button release=new Button();
        lock.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
        release.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");
        lock.setText("lock citizen");
        release.setText("release citizen");

        buttons.getChildren().add(lock);
        buttons.getChildren().add(release);

        handleCitizenButton(lock,release,x,y);
        handleButtonUsage(buyHex);

        Label UnEmployedCitizen = new Label("UnEmployedCitizens: "+CityController.showUnEmployedCitizen());
        VBox buyBox = new VBox(buyHex, buyBuilding,fields,buttons,UnEmployedCitizen);
        demoPane.getChildren().add(buyBox);

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

   
    private void handleCitizenButton(Button lock,Button release,TextField x_field,TextField y_Field)
    {   

        lock.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(x_field.getText().equals("")||y_Field.getText().equals(""))
                {
                    Alert alert=new Alert(AlertType.INFORMATION,"fill all fields");
                    alert.showAndWait();
                    return;
                }
                int x=Integer.parseInt(x_field.getText());
                int y=Integer.parseInt(y_Field.getText());
                Alert alert=new Alert(AlertType.INFORMATION,CityController.lockCitizenTo(x, y));
                alert.showAndWait();
                
            }
            
        });


        release.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(x_field.getText().equals("")||y_Field.getText().equals(""))
                {
                    Alert alert=new Alert(AlertType.INFORMATION,"fill all fields");
                    alert.showAndWait();
                    return;
                }
                int x=Integer.parseInt(x_field.getText());
                int y=Integer.parseInt(y_Field.getText());
                Alert alert=new Alert(AlertType.INFORMATION,CityController.removeCitizenFromWork(x, y));
                alert.showAndWait();
            }
            
        });
        









        lock.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                lock.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#000000");

            }

        });
        lock.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                lock.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");

            }

        });
        release.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                release.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#000000");

            }

        });
        release.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                release.setStyle("-fx-text-fill: goldenrod; -fx-background-color:#2f2f2f");

            }

        });
    }
    private void handleBuyBuildingButton(Button button) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadPanelForBuildings();
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

    public void loadPanelForBuildings() {
        FXMLLoader loader = new FXMLLoader(CivilizationApplication.class.getResource("fxml/panels/building-page.fxml"));
        try {
            // notificationHistory=;
            if(openPanel!=null)
            {
                return;
            }
            BuildingMenu buildingMenu = new BuildingMenu(cityName);
            loader.setController(buildingMenu);
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

