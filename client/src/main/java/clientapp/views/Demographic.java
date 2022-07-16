package clientapp.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import clientapp.controllers.GameController;

public class Demographic {
    
    @FXML
    private AnchorPane anchorPane;

    public void initialize() {
      
        writeInfo();

    }

    private void writeInfo()
    {
        Label info=new Label();
        info.setText(GameController.demographicScreen());
        info.setLayoutX(20);
        info.setLayoutY(100);
        anchorPane.getChildren().add(info);
        
    }
}
