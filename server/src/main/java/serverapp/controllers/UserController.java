package serverapp.controllers;


import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;
import org.json.JSONException;
import org.json.JSONObject;
import serverapp.CivilizationApplication;
import serverapp.enums.Actions;
import serverapp.models.Request;
import serverapp.models.User;

public class UserController {
    public static User loggedInUser = null;
    private static HashMap<String, User> users = new HashMap<String, User>();
    private static ArrayList<String> nicknames = new ArrayList<String>();
    private static ArrayList<User> usersArray=new ArrayList<User>();
    private static ArrayList<String> uuids = new ArrayList<>();

    public static HashMap<String, User> getUserHashMap() {
        return userHashMap;
    }

    private static HashMap<String,User> userHashMap = new HashMap<>();

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

    public static String deleteAccount(String uuid) {
        users.remove(userHashMap.get(uuid).getUsername());
        usersArray.remove(userHashMap.remove(uuid));
        userHashMap.remove(uuid);
        return "deleted successfully";
    }

    public static User getUserByUserName(String username) {
        return users.get(username);
    }

    public static ArrayList<String> getUuids() {
        return uuids;
    }

    public static String getPicUrl(String uuid) {
        return userHashMap.get(uuid).getUrl();
    }
    public static void setPicUrl(String uuid,String url)
    {
        userHashMap.get(uuid).setUrl(url);
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

    public static String isUsernameUnique(String username) {
        for (User user : usersArray) {
            if (user.getUsername().equals(username)) {
                return "no";
            }
        }
        return "yes";
    }
    public static String register(String username, String password, String nickname,String url) {
        if(isUsernameUnique(username).equals("no")) return "This username is taken";
        if (!isNicknameUnique(nickname)) return "This nickname is taken";
        User newUser = new User(username,  password, nickname);
        newUser.setUrl(url);
        users.put(username, newUser);
        usersArray.add(newUser);
        return "registered";
    }

    public static String login(String username, String password) {
        JSONObject json = new JSONObject();
        for (User user : usersArray) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    String uuid = UUID.randomUUID().toString();
                    userHashMap.put(uuid,user);
                    uuids.add(uuid);
                    try {
                        json.put("message", "logged in");
                        json.put("UUID", uuid);
                        return json.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                json.put("message", "Incorrect password");

                return json.toString();
            }
        }
        json.put("message", "No user with this username exists");
        return json.toString();
    }

    public void Login(String username, String password) {
        loggedInUser = users.get(username);
    }

    public static String logout(String uuid) {
        userHashMap.remove(uuid);
        NetWorkController.getLoggedInClientsSocket().remove(uuid);
        return "logged out";
    }
    public static String getTopUsers(){
        Collections.sort(UserController.getUsersArray());
        ArrayList<String> topUsers = new ArrayList<>();
        for (int i = 0; i< UserController.getUsersArray().size()&& i < 8; i++) {
            topUsers.add(UserController.getUsersArray().get(i).getUsername() +
                    " "+ UserController.getUsersArray().get(i).getScore()+
                    " "+UserController.getUsersArray().get(i).getUrl());
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(topUsers);
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
            
                user.write(key + " " + value.getPassword() + " " + value.getNickName() +" "+value.getScore()+" "+value.getUrl()+ "\n");

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
                String url=read[4];
                User addUser = new User(Username, Password, Nickname);
               //addUser.setAvatarPic(image, picNum);
                addUser.increaseScore(score);
                addUser.setUrl(url);
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

    public static String changeNickname(String uuid,String nickname) {
        if(isNicknameUnique(nickname)){
            userHashMap.get(uuid).setNickName(nickname);
            return "nickname changed successfully";
            //UserController.getLoggedInUser().setPassword(password.getText());
        }
        return "This nickname is taken";
    }

    public static String changePassword(String uuid, String newPass) {
        userHashMap.get(uuid).setPassword(newPass);
        return "password changed successfully";
    }


    public static String getAllFriendsNames(String uuid) {
        Gson gson = new GsonBuilder().create();
        return (gson.toJson(userHashMap.get(uuid).getFriendsUsernames()));
    }

    public static String getAllFreinShipRequests(String uuid) {
        ArrayList<String> allRequestNames = new ArrayList<>();
        for (Request r: userHashMap.get(uuid).getAllFriendShipRequests()) {
            allRequestNames.add(r.getSenderName());
        }
        Gson gson = new GsonBuilder().create();
        return (gson.toJson(allRequestNames));
    }

    public static String sendFriendShipRequest(String uuid, String anotherUsername) {
        User friend = UserController.getUserByUserName(anotherUsername);
        Request request = new Request(userHashMap.get(uuid).getUsername(),anotherUsername);
        friend.getAllFriendShipRequests().add(request);
        userHashMap.get(uuid).getAllSentRequests().add(request);
        return "sent successfully";
    }

    public static String acceptFriendShipRequest(String uuid, String senderName) {
/*        ArrayList<Request> t= userHashMap.get(uuid).getAllFriendShipRequests();
        for (int i = 0; i < t.size(); i++) {
            if(t.get(i).getSenderName().equals(senderName)){

            }
        }
        User friend = UserController.getUserByUserName(anotherUsername);
        Request request = new Request(,anotherUsername);
        friend.getAllFriendShipRequests().add(request);*/
        return "accepted";
    }

    public static String rejectFriendship(String uuid, String anotherUsername) {

        return "rejected successfully";
    }
}

