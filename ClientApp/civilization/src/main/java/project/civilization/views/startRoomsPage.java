package project.civilization.views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.ChatController;
import project.civilization.enums.Menus;

import java.util.ArrayList;

public class startRoomsPage {
    @FXML
    private AnchorPane pane;
    @FXML
    private Text text;
    private static ArrayList<String> usernames = new ArrayList<>();

    public void initialize() {
        text = new Text("Online Users:");
        text.setX(500);
        text.setY(130);
        pane.getChildren().add(text);
        showUsers();
    }

    public void showUsers() {
        for (int i = 0; i < ChatMenuNavigation.getOnlineUsers().size(); i++) {
            String name = ChatController.getOnlineUsers().get(i);
            Button button = new Button();
            button.setText(name);
            button.setPrefWidth(600);
            button.setPrefHeight(70);
            button.setLayoutX(500);
            button.setLayoutY(200 + (i * 100));
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    usernames.add(name);
                }
            });
            pane.getChildren().add(button);
        }
        Button confirmButton = new Button("Create Room");
        confirmButton.setPrefWidth(400);
        confirmButton.setPrefHeight(70);
        confirmButton.setLayoutX(500);
        confirmButton.setLayoutY(500);
        confirmButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CivilizationApplication.changeMenu(Menus.PUBLICCHAT);
                PublicChatMenu.setChat(ChatController.startRoom(usernames));
            }
        });
        pane.getChildren().add(confirmButton);
    }

}
