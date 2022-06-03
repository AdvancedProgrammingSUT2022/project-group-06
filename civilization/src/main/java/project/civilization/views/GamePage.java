package project.civilization.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Menus;

public class GamePage extends GameMenus{

    public Button backToMAinButton;
    public BorderPane borderPane;
    public ChoiceBox mapSize;
    public Text error;
    public VBox invitationBox;
    private TextField textField;
    private TextField invitationText;
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
    public void changeUsername(MouseEvent mouseEvent) {
        if(invitationBox.getChildren().size() == 1){
            textField = new TextField();
            textField.setPromptText("enter enemy username");
            invitationText = new TextField();
            invitationText.setPromptText("enter enemy username");
            Button apply = new Button();
            apply.setText("send");
            invitationBox.getChildren().add(textField);
            invitationBox.getChildren().add(invitationText);
            invitationBox.getChildren().add(apply);
            apply.setOnMouseClicked(this::applySendIvitation);
        }
    }

    private void applySendIvitation(MouseEvent mouseEvent) {
        invitationBox.getChildren().remove(textField);
        invitationBox.getChildren().remove(invitationText);
        invitationBox.getChildren().remove(1);
    }

    public void loadGame(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.LOADGAME);

    }
}
