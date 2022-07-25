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
import project.civilization.CivilizationApplication;
import project.civilization.controllers.ChatController;
import project.civilization.enums.Menus;
import project.civilization.models.Chat;
import project.civilization.models.Message;
import project.civilization.models.User;

import java.awt.desktop.AppReopenedEvent;
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
    @FXML
    private Button backButton;

    private static Button editButton;
    private static Button deleteButton;
    public static boolean isEditing;

    private static int chatID;

    public static int getChat() {
        return chatID;
    }

    public static void setChat(int chatId) {
        PublicChatMenu.chatID = chatId;
    }

    public AnchorPane getPane() {
        return pane;
    }

//    public static void setPane(BorderPane pane1) {
//        PublicChatMenu.pane = pane1;
//    }

    public void initialize() {
        isEditing = false;
        initializeSendButton();
    }

    private static void initializeOptionsVbox(int messageIndex, HBox hBox) {
        optionsVBox = new VBox();
        optionsVBox.setPrefWidth(50);
        optionsVBox.setPrefHeight(60);
        optionsVBox.setLayoutX(hBox.getLayoutX() - 40);
        optionsVBox.setLayoutY(hBox.getLayoutY()- 50);

        initializeDeleteButton(messageIndex);
        initializeEditButton(messageIndex);
        CivilizationApplication.chatPane.getChildren().add(optionsVBox);
    }

    private static void initializeEditButton(int messageIndex) {
        editButton = new Button("edit");
        editButton.setPrefWidth(50);
        editButton.setPrefHeight(30);
        optionsVBox.getChildren().add(editButton);

        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isEditing = true;
                ChatController.editMessage(messageIndex, chatID);
                CivilizationApplication.chatPane.getChildren().remove(optionsVBox);
            }
        });
    }

    private static void initializeDeleteButton(int messageIndex) {
        deleteButton = new Button("delete");
        deleteButton.setPrefWidth(50);
        deleteButton.setPrefHeight(30);
        optionsVBox.getChildren().add(deleteButton);

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChatController.deleteMessage(messageIndex, chatID);
                CivilizationApplication.chatPane.getChildren().remove(optionsVBox);
            }
        });
    }

    private void initializeSendButton() {
        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isEditing) {
                    ArrayList<Message> messages = ChatController.sendMessage(previewMessageTextField.getText(), chatID);
                    previewMessageTextField.setText("");
                } else {
                    ChatController.editMessageFinal(previewMessageTextField.getText());
                    isEditing = false;
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
            text.setX(10);
            Text username = new Text(messages.get(i).getSenderUsername() + ":");
            VBox vBox = new VBox(username,text, new Text(messages.get(i).getDateString()));
            hBox.getChildren().add(vBox);

            hBox.setLayoutY(540 - (45 * i));
            hBox.setPrefHeight(35);
            hBox.setPrefWidth((8 * messages.get(i).getText().length()) + 30);
            hBox.setMinWidth((8 * messages.get(i).getText().length()) + 30);
            if (shouldBeRight(messages.get(i)))
                hBox.setLayoutX(1100 - hBox.getPrefWidth());
            else
                hBox.setLayoutX(80);
            int index = messages.size() - 1 - i;
            hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    initializeOptionsVbox(index, hBox);
                }
            });
            pane.getChildren().add(hBox);
        }
    }

    public static boolean shouldBeRight(Message message) {
        return message.getSenderUUID().equals(User.getUuid());
    }

    public void back() {
        CivilizationApplication.changeMenu(Menus.CHATNAVIGATION);
    }
}