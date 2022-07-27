package project.civilization.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import org.json.JSONException;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Actions;
import project.civilization.enums.MenuCategory;
import project.civilization.enums.Menus;
import project.civilization.models.User;
import project.civilization.views.ScorePage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientNetworkController{
    private static DataInputStream invitationDataInputStream;
    private static DataOutputStream invitationDataOutputStream;

    public static void initializeNetworkForInvitationProcess() {
        Thread receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = invitationDataInputStream.readUTF();
                        process(message);
                    } catch (SocketException socketException) {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        receiver.start();
    }

    public static void process(String message) {
        try {
            JSONObject obj = new JSONObject(message);
            String action = obj.getString("action");
            if (action.equals(Actions.INVITETOGAME.getCharacter())) {
                alertInvitation(obj.getString("username"), obj.getString("gameUuid"));
            } else if (action.equals(Actions.STARTGAME.getCharacter())) {
                Platform.runLater(() -> CivilizationApplication.changeMenu(Menus.MAPPAGE));
            } else if (action.equals(Actions.updateMessages.getCharacter())) {
                Platform.runLater(() -> ChatController.updateMessages(obj.toString()));
            }else if(action.equals(Actions.gameOver.getCharacter())){
                Platform.runLater(() -> {
                    if(obj.getString("show").equals("yes"))
                    {
                        Alert alert=new Alert(AlertType.INFORMATION,obj.getString("winners"));
                        alert.showAndWait();
                    }
                    CivilizationApplication.changeMenu(Menus.MAIN);
                });
            }else if(action.equals(Actions.CHANGETURNOFOTHEROLAYERS.getCharacter())){
                Platform.runLater(() -> CivilizationApplication.mapPageController.changeTurnForOthers());
            }else if(action.equals(Actions.updateScoreBoard.getCharacter())){
                if(CivilizationApplication.currentMenu.equals(Menus.SCORE))
                {   
                    Platform.runLater(() -> CivilizationApplication.scorePageController.update());
                    
                }
                
            }else System.out.println(message + "a fucking thing is wrong");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void alertInvitation(String inviter, String gameUUid) {
        Platform.runLater(() -> alert(inviter, gameUUid));
    }

    private static void alert(String inviter, String gameUUid) {
        String invitationText = inviter + "wants to play with you!"
                + "\nwould you accept their request?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, invitationText, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            UserController.alertSystem(gameUUid, Actions.ACCEPTINVITATION);
            Alert alertFinish = new Alert(Alert.AlertType.INFORMATION, "please wait to other enemies accept their requests");
            alertFinish.showAndWait();
        } else {
            UserController.alertSystem(gameUUid, Actions.REJECTINVITATION);
        }
    }


    public static void initializeNetwork() {
        try {
            Socket socket = new Socket("localhost", 772);
            invitationDataInputStream = new DataInputStream(socket.getInputStream());
            invitationDataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static void initializeSocketToServer() {
        initializeNetwork();
        JSONObject json = new JSONObject();
        try {
            json.put("menu", MenuCategory.NETWORK.getCharacter());
            json.put("action", Actions.INITIALIZEHEARINGSERVERSOCKET.getCharacter());
            json.put("UUID", User.getUuid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            invitationDataOutputStream.writeUTF(json.toString());
            invitationDataOutputStream.flush();
            invitationDataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
