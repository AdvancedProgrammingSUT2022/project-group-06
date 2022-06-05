package project.civilization.controllers;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javafx.scene.image.Image;
import project.civilization.CivilizationApplication;
import project.civilization.models.User;
import project.civilization.views.LoginMenu;

public class UserController {
    public static User loggedInUser = null;
    private static HashMap<String, User> users = new HashMap<String, User>();
    private static ArrayList<String> nicknames = new ArrayList<String>();
    private static ArrayList<User> usersArray=new ArrayList<User>();

    public static void setLoggedInUser(User user)
    {
        loggedInUser=user;
    }
    public static ArrayList<User> getUsersArray()
    {
        return usersArray;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        UserController.users = users;
    }

    public static void setNicknames(ArrayList<String> nicknames) {
        UserController.nicknames = nicknames;
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public void createUser(String username, String password, String nickname) {
        User user = new User(username, password, nickname);
        users.put(username, user);
        nicknames.add(nickname);
    }

    public static boolean isNicknameUnique(String nickname)
    {
        for (User user : usersArray) {
            if (user.getNickName().equals(nickname)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUsernameUnique(String username) {
        for (User user : usersArray) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public static String login(String username, String password) {

      
        for (User user : usersArray) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    loggedInUser = user;
                
                    return null;
                }

                return "Incorrect password";

            }
        }
        return "No user with this username exists";
    }

    public void Login(String username, String password) {
        loggedInUser = users.get(username);
    }

    public static String logout() {
        if (loggedInUser == null) {
            return "you have not logged in yet";
        }
        loggedInUser = null;
        return "logout successfully";
    }

    public static void saveUsers() {
        try {
            // URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/UserInfo.txt")).toExternalForm());
            // // File file = new File(CivilizationApplication.class.getResource("files/UserInfo.txt").toURI());
            // File file=new File(address.toURI());
            /*String user = new String(Files.readAllBytes(Paths.get(address.toURI())));
            String resourceName = "files/UserInfo.txt";
            ClassLoader classLoader = LoginMenu.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());*/

            URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/UserInfo.txt")).toExternalForm());
            File file = new File(Paths.get(address.toURI()).toString());
           
            
            PrintWriter user = new PrintWriter(file);

                       
            users.forEach((key, value) -> {
            
                user.write(key + " " + value.getPassword() + " " + value.getNickName() +" "+value.getScore()+" "+value.getPicNum()+ "\n");

            });
            

            user.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void importSavedUsers() {
        try {
            URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/UserInfo.txt")).toExternalForm());
            String user = new String(Files.readAllBytes(Paths.get(address.toURI())));
            if (user.equals("")) {
                return;
            }
            String[] readUser = user.split("\n");
            for (String temp : readUser) {
                String[] read = temp.split(" ");
                String Username = read[0];
                String Password = read[1];
                String Nickname = read[2];
                int score=Integer.parseInt(read[3]);
                int picNum=Integer.parseInt(read[4]);

       
                User addUser = new User(Username, Password, Nickname);
                
                addUser.setAvatarPic(new Image(CivilizationApplication.class.getResource("pictures/avatar/" + picNum + ".jpg").toExternalForm()), picNum);
                addUser.increaseScore(score);
                users.put(Username, addUser);
                usersArray.add(addUser);
                nicknames.add(Nickname);
            }

        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isNicknameUsed(String newNickname) {
        for (String nickname : nicknames) {
            if (nickname.equals(newNickname))
                return true;
        }
        return false;
    }

    public static boolean isPasswordValid(String password) {
        return loggedInUser.getPassword().equals(password);
    }

    public static String changeNickname(String nickname) {
        if (isNicknameUsed(nickname))
            return ("user with nickname" + nickname + "already exists");
        else {
            UserController.getLoggedInUser().setNickName(nickname);
            return ("nickname changed successfully!");
        }
    }

    public static String changePassword(String currentPass, String newPass) {
        if (!UserController.isPasswordValid(currentPass))
            return ("current password is invalid");
        else if (currentPass.equals(newPass))
            return ("current pass is equal to new pass");
        else {
            UserController.getLoggedInUser().setPassword(newPass);
            return ("password changed successfully!");
        }
    }
}

