package project.civilization.views;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import project.civilization.controllers.UserController;
import project.civilization.models.User;

public class ScorePage extends GameMenus {
    @FXML
    private VBox list;
    private int defaultUserX=400;
    private int defaultPointX=800;
    public void initialize() {

        ArrayList<String> usernamesAndScores = UserController.getTopUsers();

        assert usernamesAndScores != null;
        for(String user: usernamesAndScores)
        {
            String[] temp = user.split(" ");
            Label userLabel=new Label();
            userLabel.setText(temp[0]);
            userLabel.setLayoutX(defaultUserX);
            list.getChildren().add(userLabel);

            Label pointLabel=new Label();
            pointLabel.setText(temp[1]);
            pointLabel.setLayoutX(defaultPointX);
            list.getChildren().add(pointLabel);


        }
    }

}
