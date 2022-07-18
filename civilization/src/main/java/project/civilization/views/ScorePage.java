package project.civilization.views;

import java.awt.event.ActionListener;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import project.civilization.controllers.UserController;
import project.civilization.models.User;

public class ScorePage extends GameMenus {
    @FXML
    private VBox list;
    private int maxShow=8;
    private int defaultUserX=400;
    private int defaultPointX=800;
    public void initialize() {


        int temp=1;

        Collections.sort(UserController.getUsersArray());

        for(User user:UserController.getUsersArray())
        {
            if(temp>maxShow)
            {
                break;
            }
            Label userLabel=new Label();
            userLabel.setText(user.getUsername());
            userLabel.setLayoutX(defaultUserX);
            list.getChildren().add(userLabel);

            Label pointLabel=new Label();
            pointLabel.setText(Integer.toString(user.getScore()));
            pointLabel.setLayoutX(defaultPointX);
            list.getChildren().add(pointLabel);

            temp++;

        }
    }

}
