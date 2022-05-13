package controllers;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import models.User;

public class UserController {
    public static User loggedInUser = null;
    public static HashMap<String, User> users = new HashMap<String, User>();
    public static ArrayList<String> nicknames = new ArrayList<String>();


    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        nicknames.add(user.getNickName());
    }

    public void removeUsers(User user) {
        users.remove(user.getUsername());
        nicknames.remove(user.getNickName());
    }

    public void createUser(String username, String password, String nickname) {
        User user = new User(username, password, nickname);
        users.put(username, user);
        nicknames.add(nickname);
    }

    public void Login(String username, String password) {
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
            PrintWriter user = new PrintWriter("files/UserInfo.txt");

            //fw.write("Welcome to javaTpoint.");    

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

}

