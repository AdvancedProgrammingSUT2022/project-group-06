package project.civilization.views;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;

import java.io.IOException;
import java.util.ArrayList;

public class TechnologyMenu {

    @FXML
    private AnchorPane pane;
    @FXML
    private Label lastTechnologyLabel;//showing the last studied technology
    @FXML
    private Button openTreeButton; //open technology tree
    //name of technologies you can start researching//TODO

    private ArrayList<HBox> techBoxes;
    int screenWidth=600;
    int screenHeight=400;


    public void setLastTechnologyLabel(String name) { //should be called as this page initializes
        lastTechnologyLabel.setText(name);
    }

    public void initialize() {
        addBackground();
        lastTechnologyLabel = new Label(GameController.getLastTechnology());
        lastTechnologyLabel.setLayoutX(400);
        lastTechnologyLabel.setLayoutY(100);
        pane.getChildren().add(lastTechnologyLabel);
        openTreeButton = new Button();
        openTreeButton.setLayoutY(300);
        openTreeButton.setLayoutX(370);
        openTreeButton.setPrefWidth(150);
        openTreeButton.setPrefHeight(10);
        openTreeButton.setText("open technology tree");
        openTreeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadPanel("technology-tree-page");
            }
        });
        pane.getChildren().add(openTreeButton);
        showAvailableTechnologies();
    }

    private void addBackground() {
        String address = "pictures/menu_background/sfd.jpg";
        Image image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitWidth(screenWidth);
        imageView.setFitHeight(screenHeight);
        pane.getChildren().add(imageView);
    }

    private void showAvailableTechnologies() {
        ArrayList<String> labels = GameController.getAvailableTechs();
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

        for (String name : labels) {
            initializeLabel(name, vBox);
            String address = "pictures/technology/" + name.toLowerCase() + ".png";
            image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageBox.getChildren().add(imageView);
        }
        pane.getChildren().add(vBox);
        pane.getChildren().add(imageBox);
    }

    private void initializeLabel(String name, VBox vBox) {
        Button button = new Button(name);
        button.setPrefWidth(100);
        button.setPrefHeight(30);
        String tooltipString = GameController.getTechInfo(name);
        button.setTooltip(new Tooltip(tooltipString));
        vBox.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        if(MapPage.technologyPass)
                        {
                            GameController.unlockTechnology(button.getText());
                            Alert alert=new Alert(Alert.AlertType.INFORMATION,"Technology achieved");
                            alert.showAndWait();
                        }else
                        {
                            GameController.changeResearch(button.getText());
                        }

                    }
                }
            }
        });
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
