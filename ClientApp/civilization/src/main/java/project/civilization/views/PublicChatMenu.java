package project.civilization.views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.xml.sax.HandlerBase;
import project.civilization.controllers.ChatController;
import project.civilization.models.Chat;
import project.civilization.models.Message;
import project.civilization.models.User;

import java.util.ArrayList;
import java.util.Collections;

public class PublicChatMenu {

    @FXML
    private TextField previewMessageTextField;
    @FXML
    private Button sendButton;
    @FXML
    private VBox vBox;
    private static VBox optionsVBox;
    @FXML
    private AnchorPane pane;
    private static Button editButton;
    private static Button deleteButton;//for urself
    private static Button deleteForAllButton;//for all
    private static Message editingMessage;

    public AnchorPane getPane() {
        return pane;
    }

//    public static void setPane(BorderPane pane1) {
//        PublicChatMenu.pane = pane1;
//    }

    public static Message getEditingMessage() {
        return editingMessage;
    }

    public static void setEditingMessage(Message editingMessage) {
        PublicChatMenu.editingMessage = editingMessage;
    }

    public void initialize() {
//        vBox.setAlignment(Pos.BOTTOM_CENTER);
        initializeSendButton();
    }

    private void addPreviewTextFieldToScreen(TextField previewMessage, VBox vBox) {
        previewMessage.setText("Type here...");
        previewMessage.setPrefHeight(20);
        previewMessage.setPrefWidth(180);
        vBox.getChildren().add(previewMessage);
    }

    private void initializeSendButton() {
        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editingMessage == null) {
                    ArrayList<Message> messages = ChatController.sendMessage(previewMessageTextField.getText(), Chat.getPublicChat());
//                    showMessages(messages);
                } else {
//                   ChatController.editMessage(editingMessage, previewMessageTextField.getText());
                }
            }
        });
    }

    public static void clearMessages(AnchorPane pane) {
        pane.getChildren().removeIf(node -> node instanceof HBox);
    }

    public static void showMessages(ArrayList<Message> messages, AnchorPane pane) {
        clearMessages(pane);
        Collections.reverse(messages);

        for (int i = 0; i < messages.size(); i++) {
            HBox hBox = new HBox();
            Text text = new Text(messages.get(i).getText());

            hBox.getChildren().add(text);
            text.setStyle("-fx-background-color: #effdde; -fx-padding: 12; -fx-background-radius: 12");
            hBox.setLayoutY(540 - (30 * i));
            hBox.setPrefHeight(40);
            hBox.setPrefWidth((3 * messages.get(i).getText().length()) + 20);
            if (shouldBeRight(messages.get(i)))
                hBox.setLayoutX(1100 - hBox.getPrefWidth());
            else
                hBox.setLayoutX(80);
            //todo: time, seen or not, style, if you send it or others
            pane.getChildren().add(hBox);
        }
    }

    public static boolean shouldBeRight(Message message) {
        return message.getSenderUUID().equals(User.getUuid());
    }

    private static void setStyleForMessage(HBox hBox, Text text) {
//        text.setStyle("-fx-font-size: 15");
//        text.setStyle("-fx-font-weight: bold");
//        text.setStyle("-fx-background-color: white");
//        text.setStyle("-fx-border-radius: 25px");
//        text.setStyle("-fx-border: 2px solid #FFFFFF");
        text.setStyle("-fx-background-color: #effdde; -fx-padding: 12; -fx-background-radius: 12");

    }

//    public static void showOptionsBox(Message message1) {
//        double x = message1.getHBox().getLayoutX();
//        double y = message1.getHBox().getLayoutY();
//        optionsVBox = new VBox();
//        editButton = new Button("edit");
//        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                editMessageView(message1);
//                pane.getChildren().remove(optionsVBox);
//            }
//        });
//        deleteButton = new Button("delete for yourself");
//        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                ChatController.deleteMessage(message1, pane);
//                pane.getChildren().remove(optionsVBox);
//            }
//        });
//        deleteForAllButton = new Button("delete for all");
//        deleteForAllButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                ChatController.deleteMessage(message1, pane);
//                pane.getChildren().remove(optionsVBox);
//            }
//        });
//        optionsVBox.setLayoutX(x - 30);
//        optionsVBox.setLayoutY(y - 20);
//        optionsVBox.getChildren().add(editButton);
//        optionsVBox.getChildren().add(deleteButton);
//        optionsVBox.getChildren().add(deleteForAllButton);
//        pane.getChildren().add(optionsVBox);
//    }
//
//    public static void editMessageView(Message message) {
//        previewMessageTextField.setText(message.getText());
//        editingMessage = message;
//    }

}