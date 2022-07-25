package project.civilization.views;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class startRoomsPage {
    @FXML
    AnchorPane pane;

    public void initialize() {
        Text text = new Text("Rooms: ");
        text.setX(500);
        text.setY(80);
        pane.getChildren().add(text);
        showAvailableRooms();
        Text text1 = new Text("Create A New Room: ");

    }

    public void showAvailableRooms() {

    }
}
