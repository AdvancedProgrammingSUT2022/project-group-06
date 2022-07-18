package project.civilization.views;


import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;



import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;
import project.civilization.models.User;
import project.civilization.models.units.Civilian;



public class RegisterPage extends GameMenus{


    private int avatarPicCount = 4;
    private int currentPic = 1;

    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private Label error;
    @FXML
    private ImageView avatarPic;
    @FXML
    private TextField nickname;
    // public void initialize() throws MalformedURLException { 

    //     URL address = new URL(Main.class.getResource("avatarpics/1.jpg").toExternalForm());
    //     avatarPic.setImage(new Image(address.toExternalForm()));

    // }


    public void initialize() {
        Random rand = new Random();
        int picNum = rand.nextInt(4) + 1;
        currentPic = picNum;
        avatarPic.setImage(new Image(CivilizationApplication.class.getResource("pictures/avatar/" + picNum + ".png").toExternalForm()));
    }

    public void register(MouseEvent mouseEvent) {
        if (username.getText().equals("") || password.getText().equals("")||nickname.getText().equals("")) {
            error.setText("Fill all fields");
            error.setStyle("-fx-text-fill: #ff0066;");
            return;
        }
        if (UserController.isUsernameUnique(username.getText())) {


            if(UserController.isNicknameUnique(nickname.getText()))
            {
                // User newUser = new User(username.getText(), password.getText(),nickname.getText());
                // newUser.setAvatarPic(avatarPic.getImage(), currentPic);

                // UserController.getUsers().put(username.getText(), newUser);
                // UserController.getUsersArray().add(newUser);
                UserController.createUser(username.getText(), password.getText(),nickname.getText(),avatarPic.getImage(),currentPic);
                Alert alert = new Alert(AlertType.INFORMATION, "New Account created successfully :)\nMoving to Login Menu");
                alert.showAndWait();

                CivilizationApplication.changeMenu(Menus.LOGIN);
                return;
            }

            error.setText("This nickname is taken");
            error.setStyle("-fx-text-fill: #ff0066;");
            return;
        }

        error.setText("This username is taken");
        error.setStyle("-fx-text-fill: #ff0066;");


    }

    public void back(MouseEvent mouseeEvent) {
        CivilizationApplication.changeMenu(Menus.LOGIN);
    }

    public void changeAvatarPic(MouseEvent mouseEvent) throws MalformedURLException {

        if (currentPic == avatarPicCount) {
            currentPic = 1;

        } else {
            currentPic++;
        }
        Image nextImage = new Image(CivilizationApplication.class.getResource("pictures/avatar/" + currentPic + ".png").toExternalForm());
        avatarPic.setImage(nextImage);
    }
}
