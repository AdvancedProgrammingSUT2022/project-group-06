package project.civilization.models;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import project.civilization.views.PublicChatMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Message extends Node {

    // private Rectangle rectangle;
    private HBox hBox;
    private Text text;
    private int hour;
    private int minute;
    private String state;
    private static SimpleDateFormat formatter = new SimpleDateFormat("H:mm");
    private Date date;
    private Text dateText;

    private static final ArrayList<Message> messages = new ArrayList<>();

    public Message(AnchorPane pane, String message) {
        text = new Text();
        hBox = new HBox();
        text.setText(message);
        //initializeHBox();//todo
        date = new Date();
        dateText = new Text(formatter.format(date));
        hBox.getChildren().add(dateText);
        pane.getChildren().add(hBox);
        messages.add(this);
    }

    private void initializeHBox() {
        hBox.setLayoutX(500);
        hBox.setLayoutY(300);
        hBox.getChildren().add(text);
//        hBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" +
//                "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
//        BorderStroke borderStroke = new BorderStroke(Color.BLUE, BorderStrokeStyle.DASHED, null,
//                new BorderWidths(2));
//        Border border = new Border(borderStroke);
//        hBox.setBorder(border);

        hBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.requestFocus();
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
             //   PublicChatMenu.showOptionsBox(Message.this);
            }
        });

    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }

    public static void addMessage(Message message) {
        messages.add(message);
    }

    public static void deleteMessage(Message message) {
        messages.remove(message);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public String getText() {
        return text.getText();
    }

    public HBox getHBox() {
        return hBox;
    }
}