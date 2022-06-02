package project.civilization.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GamePage extends GameMenus{

    public Button backToMAinButton;
    public BorderPane borderPane;
    public ChoiceBox mapSize;
    private Text hint = new Text();

    public void initialize(){
        // string array
        mapSize.getItems().addAll("item1", "item2", "item3");

        // add a listener
        mapSize.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {   //if(new_value.equals())
                // set the text for the label to the selected item
                System.out.println(ov);
                System.out.println(value);
                System.out.println(new_value);
                System.out.println(mapSize.getValue() + " selected");
            }
        });
        backToMAinButton.setTooltip(new Tooltip("stupid it is clear that you can back to main by this button"));
    }
}
