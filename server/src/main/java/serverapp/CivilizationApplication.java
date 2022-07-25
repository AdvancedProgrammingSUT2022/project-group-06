package serverapp;

import javafx.scene.Parent;
import serverapp.controllers.UserController;
import serverapp.enums.MenuCategory;
import serverapp.views.Process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class CivilizationApplication {
    public static void main(String[] args) {
        UserController.importSavedUsers();
        //make a thread to handle in time requests
        try {
            ServerSocket serverSocket = new ServerSocket(772);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while (true) {
                            String input = dataInputStream.readUTF();
                            String result = Process.run(input, socket);
                            if (result.equals("oooooooooooooooooo")) break;
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                        }
                        dataInputStream.close();
                        socket.close();
                        serverSocket.close();
                    } catch (SocketException socketException){

                    }  catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        Scanner scanner = new Scanner(System.in);
        LoginMenu loginMenu = new LoginMenu();
        UserController.importSavedUsers();
        loginMenu.run(scanner);*/
    }
}