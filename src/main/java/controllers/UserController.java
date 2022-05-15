package controllers;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

import models.User;
import views.LoginMenu;

public class UserController {
    public static User loggedInUser = null;
    private static HashMap<String, User> users = new HashMap<String, User>();
    private static ArrayList<String> nicknames = new ArrayList<String>();

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public  void setUsers(HashMap<String, User> users) {
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

    public void Login   (String username, String password) {
        loggedInUser = users.get(username);
    }

    public static String logout() {
        if(loggedInUser == null){
            return "you have not logged in yet";
        }
        loggedInUser = null;
        return "logout successfully";
    }

    public static void saveUsers() {
        try {

            String resourceName = "files/UserInfo.txt";
            ClassLoader classLoader = LoginMenu.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
            

            
            PrintWriter user = new PrintWriter(file);

    
            users.forEach((key, value) -> {

                user.write(key + " " + value.getPassword() + " " + value.getNickName() + "\n");

            });

    
            user.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void importSavedUsers() {
        try {
            String resourceName = "files/UserInfo.txt";
            ClassLoader classLoader = InitializeGameInfo.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
            String user = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            if (user.equals("")) {
                return;
            }
            String[] readUser = user.split("\n");
            for (String temp : readUser) {
                String[] read = temp.split(" ");
                String Username = read[0];
                String Password = read[1];
                String Nickname = read[2];

                User addUser = new User(Username, Password, Nickname);
                users.put(Username, addUser);
                nicknames.add(Nickname);
            }

        } catch (IOException e) {
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

