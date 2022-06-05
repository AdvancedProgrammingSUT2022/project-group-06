package project.civilization.models;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Message extends Node {

   // private Rectangle rectangle;
    private Text text;

    private ArrayList<Message> messages;

    public Message(BorderPane pane, String message) {
        text.setText(message);
        //TODO:set x y width ... for message
        pane.getChildren().add(text);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void deleteMessage(Message message) {
        messages.remove(message);
    }
}
