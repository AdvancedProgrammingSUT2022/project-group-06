package project.civilization.views;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.SaveAndLoadController;
import project.civilization.enums.Menus;
import project.civilization.models.maprelated.World;

import javax.swing.*;

public class AuotSaveSetting {
    public CheckBox everyTurnCheckBox;
    public CheckBox twoFile;
    public CheckBox threeFile;
    public CheckBox fourFile;
    JPanel panel;

    public void back(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.GAMEMenu);
    }

    public void handelEveryTurnCheckBox(ActionEvent actionEvent) {
    }

    public void ApplyAutoSaveAndPeriods(MouseEvent mouseEvent) {
        if (everyTurnCheckBox.isSelected()) {
            World.autoSave.put("turn", true);
        }


        if(twoFile.isSelected()){
            SaveAndLoadController.autoSaveTurns = 2;
        }else if(threeFile.isSelected()){
            SaveAndLoadController.autoSaveTurns = 3;
        }else if(fourFile.isSelected()){
            SaveAndLoadController.autoSaveTurns = 4;
        }
    }
}
