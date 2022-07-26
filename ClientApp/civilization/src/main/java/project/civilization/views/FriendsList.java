package project.civilization.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;
import project.civilization.models.User;

import java.util.ArrayList;

public class FriendsList {
    public VBox vbox;
    public TextField username;
    public VBox friendsVbox;
    public VBox search;
    public Label error;

    public void initialize(){
        ArrayList<String> friendsList = UserController.getAllFriendsNames();
        for (String r : friendsList) {
            Text text = new Text(r);
            friendsVbox.getChildren().add(text);
        }
    }

    public void sendFriendshipRequest(MouseEvent mouseEvent) {
        UserController.sendFriendShipRequest(username.getText());
    }

    public void searchUser(MouseEvent mouseEvent) {
        if(!UserController.isUsernameUnique(username.getText())){
            HBox hBox = new HBox();
            String address = "pictures/avatar/1.png";
            Image image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
            ImageView view = new ImageView(image);
            view.setFitWidth(30);
            view.setFitHeight(30);
            hBox.getChildren().add(view);
            search.getChildren().add(hBox);
            Button button = new Button("send");
            button.setOnMouseClicked(this::sendFriendshipRequest);
            hBox.getChildren().add(button);
        }else{
            error.setText("username does not exist");
        }
    }

    public void back(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.PROFILE);
    }
}
