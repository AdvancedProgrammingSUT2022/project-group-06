package project.civilization.views;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.UserController;
import project.civilization.enums.Menus;

public class ProfilePage extends GameMenus{

    private int avatarPicCount = 5;
    private int currentPic = 1;

    @FXML
    private TextField nickname;
    @FXML
    private TextField password;
    @FXML
    private Label nicknameError;
    @FXML
    private Label passwordError;
    @FXML
    private ImageView avatarPic;

    

    public void initialize()
    {   
        avatarPic.setImage(UserController.getLoggedInUser().getAvatarPic());
        currentPic=UserController.getLoggedInUser().getPicNum();
    }
    public void selectImage(MouseEvent mouseEvent)
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        JFileChooser imageChoooser=new JFileChooser();
        imageChoooser.setFileFilter(filter);
        int response=imageChoooser.showOpenDialog(null);
        if(response==JFileChooser.APPROVE_OPTION)
        {
            String imageName=imageChoooser.getSelectedFile().getAbsolutePath();
            avatarPic.setImage(new Image(imageName));
        }
    }
    public void changeAvatarPic(MouseEvent mouseEvent) throws MalformedURLException {

        if (currentPic == avatarPicCount) {
            currentPic = 1;

        } else {
            currentPic++;
        }
        Image nextImage = new Image(CivilizationApplication.class.getResource("pictures/avatar/" + currentPic + ".jpg").toExternalForm());
        avatarPic.setImage(nextImage);
        UserController.getLoggedInUser().setAvatarPic(nextImage, currentPic);
    }
    public void changePassword(MouseEvent mouseEvent)
    {
        
        if(password.getText().equals(""))
        {
            passwordError.setText("Fill all fields");
            passwordError.setStyle("-fx-text-fill: #ff0066;");
            return;
        }
        UserController.getLoggedInUser().setPassword(password.getText());
        Alert alert =new Alert(AlertType.INFORMATION,"Password changed successfully");
        alert.showAndWait();

    }
    public void changeNickname(MouseEvent mouseEvent)
    {
        if(nickname.getText().equals(""))
        {
            nicknameError.setText("Fill all fields");
            nicknameError.setStyle("-fx-text-fill: #ff0066;");
            return;
        }
        if(UserController.isNicknameUnique(nickname.getText()))
        {
            UserController.getLoggedInUser().setUsername(nickname.getText());
            UserController.getLoggedInUser().setPassword(password.getText());
            Alert alert =new Alert(AlertType.INFORMATION,"nickname changed successfully");
            alert.showAndWait();
            return;
        }

        nicknameError.setText("This nickname is taken");
        nicknameError.setStyle("-fx-text-fill: #ff0066;");

    }
    
    public void deleteAccount(MouseEvent mouseEvent)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete your account?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            
            UserController.getUsers().remove(UserController.getLoggedInUser().getUsername());
            UserController.getUsersArray().remove(UserController.getLoggedInUser());

            UserController.setLoggedInUser(null);
            
            Alert alertFinish=new Alert(AlertType.INFORMATION, "Account deleted successfull\nProceeding to login menu");
            alertFinish.showAndWait();
            CivilizationApplication.changeMenu(Menus.LOGIN);
        }
    }

    
}
