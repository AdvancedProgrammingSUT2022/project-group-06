package project.civilization.views;


import java.net.MalformedURLException;
import java.util.Random;



import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;


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
        //"pictures/avatar/" + avatarPicCount + ".png"
        String res = UserController.register(username.getText(), password.getText(),nickname.getText(),avatarPic.getImage().getUrl());
        if(res.equals("This username is taken")){
            error.setText("This username is taken");
            error.setStyle("-fx-text-fill: #ff0066;");
            return;
        }
        if(res.equals("This nickname is taken")){
            error.setText(res);
            error.setStyle("-fx-text-fill: #ff0066;");
            return;
        }
        Alert alert = new Alert(AlertType.INFORMATION, "New Account created successfully :)\nMoving to Login Menu");
        alert.showAndWait();
        CivilizationApplication.changeMenu(Menus.LOGIN);
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
