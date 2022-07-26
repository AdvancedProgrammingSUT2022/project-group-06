package project.civilization.views;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.civilization.controllers.UserController;
import project.civilization.models.User;

public class ScorePage extends GameMenus {
    @FXML
    private VBox list;
    private int defaultUserX=600;
    private int defaultAvatarX=400;
    private int defaultPointX=1000;
    private int boxWidth=600;
    private int boxHeight=400;

    ScrollPane scrollPane=new ScrollPane();

    public void update()
    {
        list.getChildren().remove(scrollPane);
        
        ArrayList<String> usernamesAndScores = UserController.getTopUsers();

        assert usernamesAndScores != null;
        
        VBox vBox=new VBox();

        for(String user: usernamesAndScores)
        {
            HBox hBox=new HBox();
            
            String[] temp = user.split(" ");


            ImageView avatarPic=new ImageView(new Image(temp[2]));
            avatarPic.setFitWidth(40);
            avatarPic.setFitHeight(40);
            avatarPic.setLayoutX(defaultAvatarX);
            hBox.getChildren().add(avatarPic);

            Label userLabel=new Label();
            userLabel.setStyle("-fx-font-size: 20;-fx-border-radius: 100;-fx-font-weight: bold;-fx-text-fill: ffff00;");
            userLabel.setText(temp[0]);
            userLabel.setLayoutX(defaultUserX);
            hBox.getChildren().add(userLabel);
            //list.getChildren().add(userLabel);

            if(temp[5].equals("true"))
            {
                Label online=new Label("  online");
                online.setStyle("-fx-font-size: 20;-fx-border-radius: 100;-fx-font-weight: bold; -fx-text-fill: #f99c02 ;");
                hBox.getChildren().add(online);
            }

            if(!temp[3].equals("A"))
            {
                Label time=new Label("  "+temp[3]);
                time.setStyle("-fx-font-size: 20;-fx-border-radius: 100;-fx-font-weight: bold; -fx-text-fill: #f99c02 ;");
                hBox.getChildren().add(time);
            }

            
            
            vBox.getChildren().add(hBox);

            Label pointLabel=new Label();
            pointLabel.setStyle("-fx-font-size: 20;-fx-border-radius: 100;-fx-font-weight: bold;-fx-text-fill: ffff00;");
            pointLabel.setText(temp[1]);
            pointLabel.setLayoutX(defaultPointX);
            vBox.getChildren().add(pointLabel);

            vBox.setStyle("-fx-background-color:black");
            vBox.setPrefWidth(boxWidth);

        }
        scrollPane.setContent(vBox);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color:black");
        scrollPane.setPrefWidth(boxWidth);
        scrollPane.setMaxHeight(boxHeight);

        list.getChildren().add(scrollPane);
    }
    public void initialize() {

        ArrayList<String> usernamesAndScores = UserController.getTopUsers();

        assert usernamesAndScores != null;
        
        VBox vBox=new VBox();

        for(String user: usernamesAndScores)
        {
            HBox hBox=new HBox();
            
            String[] temp = user.split(" ");


            ImageView avatarPic=new ImageView(new Image(temp[2]));
            avatarPic.setFitWidth(40);
            avatarPic.setFitHeight(40);
            avatarPic.setLayoutX(defaultAvatarX);
            hBox.getChildren().add(avatarPic);

            Label userLabel=new Label();
            userLabel.setStyle("-fx-font-size: 20;-fx-border-radius: 100;-fx-font-weight: bold;-fx-text-fill: ffff00;");
            userLabel.setText(temp[0]);
            userLabel.setLayoutX(defaultUserX);
            hBox.getChildren().add(userLabel);
            //list.getChildren().add(userLabel);

            if(temp[5].equals("true"))
            {
                Label online=new Label("  online");
                online.setStyle("-fx-font-size: 20;-fx-border-radius: 100;-fx-font-weight: bold; -fx-text-fill: #f99c02 ;");
                hBox.getChildren().add(online);
            }

            if(!temp[3].equals("A"))
            {
                Label time=new Label("  "+temp[3]);
                time.setStyle("-fx-font-size: 20;-fx-border-radius: 100;-fx-font-weight: bold; -fx-text-fill: #f99c02 ;");
                hBox.getChildren().add(time);
            }

            
            
            vBox.getChildren().add(hBox);

            Label pointLabel=new Label();
            pointLabel.setStyle("-fx-font-size: 20;-fx-border-radius: 100;-fx-font-weight: bold;-fx-text-fill: ffff00;");
            pointLabel.setText(temp[1]);
            pointLabel.setLayoutX(defaultPointX);
            vBox.getChildren().add(pointLabel);

            vBox.setStyle("-fx-background-color:black");
            vBox.setPrefWidth(boxWidth);

        }
        scrollPane.setContent(vBox);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color:black");
        scrollPane.setPrefWidth(boxWidth);
        scrollPane.setMaxHeight(boxHeight);

        list.getChildren().add(scrollPane);
    }

}
