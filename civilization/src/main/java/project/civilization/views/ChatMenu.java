package project.civilization.views;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import project.civilization.controllers.ChatController;

public class ChatMenu {

    @FXML
    private TextField previewMessageTextField;
    private Button sendButton;


    @FXML
    private VBox vBox;

    public void initialize() {
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        addPreviewTextFieldToScreen(previewMessageTextField, vBox);
    }

    private void addPreviewTextFieldToScreen(TextField previewMessage, VBox vBox) {
        previewMessage.setText("Enter your message here...");
        previewMessage.setPrefHeight(20);
        previewMessage.setPrefWidth(200);
        vBox.getChildren().add(previewMessage);
    }

    private void initializeSendButton() {

    }

    public void sendMessage() {
        //add a rectangle and a text attached to it
    }


}
