package project.civilization.views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import project.civilization.models.gainable.Technology;

public class TechnologyMenu {

    @FXML
    private BorderPane pane;
    @FXML
    private Label lastTechnologyLabel;//showing the last studied technology
    @FXML
    private Button openTreeButton; //open technology tree
    //name of technologies you can start researching

    public void setLastTechnologyLabel(String name) { //should be called as this page initializes
        lastTechnologyLabel.setText(name);
    }

    public void initialize() {
        lastTechnologyLabel = new Label();//TODO: set label
        openTreeButton = new Button();
        openTreeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO: open graph
            }
        });
    }
}
