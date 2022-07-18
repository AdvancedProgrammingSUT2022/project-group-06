package project.civilization.views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import project.civilization.controllers.ChatController;
import project.civilization.models.Message;

public class ChatMenu {

    private static TextField previewMessageTextField;
    private Button sendButton;
    @FXML
    private VBox vBox;
    private static VBox optionsVBox;
    private static BorderPane pane  = new BorderPane();
    private static Button editButton;
    private static Button deleteButton;//for urself
    private static Button deleteForAllButton;//for all
    private static Message editingMessage;

    public static BorderPane getPane() {
        return pane;
    }

    public static void setPane(BorderPane pane1) {
        ChatMenu.pane = pane1;
    }

    public static Message getEditingMessage() {
        return editingMessage;
    }

    public static void setEditingMessage(Message editingMessage) {
        ChatMenu.editingMessage = editingMessage;
    }

    public void initialize() {
        previewMessageTextField = new TextField();
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        addPreviewTextFieldToScreen(previewMessageTextField, vBox);
        initializeSendButton();
    }

    private void addPreviewTextFieldToScreen(TextField previewMessage, VBox vBox) {
        previewMessage.setText("Type here...");
        previewMessage.setPrefHeight(20);
        previewMessage.setPrefWidth(180);
        vBox.getChildren().add(previewMessage);
    }

    private void initializeSendButton() {
        sendButton = new Button();
        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(editingMessage == null) {
                    ChatController.sendMessage(previewMessageTextField.getText(), pane);
                }
                else {
                    ChatController.editMessage(editingMessage, previewMessageTextField.getText());
                }
            }
        });
        vBox.getChildren().add(sendButton);
    }

    public static void showOptionsBox(Message message1) {
        double x = message1.getHBox().getLayoutX();
        double y = message1.getHBox().getLayoutY();
        optionsVBox = new VBox();
        editButton = new Button("edit");
        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editMessageView(message1);
                pane.getChildren().remove(optionsVBox);
            }
        });
        deleteButton = new Button("delete for yourself");
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChatController.deleteMessage(message1, pane);
                pane.getChildren().remove(optionsVBox);
            }
        });
        deleteForAllButton = new Button("delete for all");
        deleteForAllButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChatController.deleteMessage(message1, pane);
                pane.getChildren().remove(optionsVBox);
            }
        });
        optionsVBox.setLayoutX(x - 30);
        optionsVBox.setLayoutY(y - 20);
        optionsVBox.getChildren().add(editButton);
        optionsVBox.getChildren().add(deleteButton);
        optionsVBox.getChildren().add(deleteForAllButton);
        pane.getChildren().add(optionsVBox);
    }

    public static void editMessageView(Message message) {
        previewMessageTextField.setText(message.getText());
        editingMessage = message;
    }

}