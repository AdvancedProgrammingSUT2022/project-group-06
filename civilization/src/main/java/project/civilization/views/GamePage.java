package project.civilization.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.enums.Menus;
import project.civilization.models.maprelated.World;

public class GamePage extends GameMenus{

    public Button backToMAinButton;
    public BorderPane borderPane;
    public ChoiceBox<String> mapSize;
    public Text error;
    public VBox invitationBox;
    public ChoiceBox<String> numberOfPlayer;
    public Rectangle fadeRectangle;
    public Button invitationButton;
    public Button loadGameButton;
    private TextField textField;
    private TextField invitationText;
    private Text hint = new Text();
    private String[] mapSizeValues;
    public void initialize(){
        createMapSizeChoiceBoxes();
        createNumberOfPlayerChoiceBoxes();
        setToolTips();

    }

    private void setToolTips() {
        Tooltip tooltip1 =new Tooltip("you can invite a friend to play online game with you");
        invitationButton.setTooltip(tooltip1);
        Image image = new Image(CivilizationApplication.class.getResource("pictures/menu_background/sendInvitation.png").toExternalForm());
        tooltip1.setGraphic(new ImageView(image));
        backToMAinButton.setTooltip(new Tooltip("stupid it is clear that you can back to main by this button"));
        loadGameButton.setTooltip(new Tooltip("you can load your saved games and continue them"));
    }

    private void createNumberOfPlayerChoiceBoxes() {
        String[] numberOfPlayersValues = {"2","3","4","5","6","7"};
        numberOfPlayer.getItems().addAll(numberOfPlayersValues);
        numberOfPlayer.setValue(numberOfPlayersValues[0]);
        InitializeGameInfo.setNumberOFPlayers(2);
        numberOfPlayer.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                InitializeGameInfo.setNumberOFPlayers(Integer.parseInt(numberOfPlayersValues[new_value.intValue()]));
                switch (numberOfPlayersValues[new_value.intValue()]){
                    case "2":
                    case "3":
                        setValues(new String[]{"9*9", "10*10", "11*11"});
                        break;
                    case "4":
                    case "5":
                        setValues(new String[]{"10*10", "11*11", "12*12"});
                        break;
                    case "6":
                    case "7":
                        setValues(new String[]{"11*11", "12*12", "13*13"});
                        break;
                }
            }
        });
    }

    private void createMapSizeChoiceBoxes() {
        setValues(new String[]{"9*9", "10*10", "11*11"});
        mapSize.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                if(new_value.intValue()>=0){
                    InitializeGameInfo.setMapSize(mapSizeValues[new_value.intValue()]);
                }
            }
        });
    }

    private void setValues(String[] values) {
        mapSizeValues = values;
        mapSize.getItems().setAll(values);
        mapSize.setValue(mapSizeValues[0]);
        InitializeGameInfo.setMapSize(mapSizeValues[0]);
    }

    public void changeUsername(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.CHALLENGEPAGE);
    }

    public void loadGame(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.LOADGAME);

    }

}
