package controllers;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import models.User;

public class UserController 
{
    public static User loggedInUser=null;
    public static HashMap<String,User> users;
    public static ArrayList<String> nicknames;
    

   

    public ArrayList<String> getNicknames()
    {
        return nicknames;
    }
  
    public void addUser(User user) 
    {
        users.put(user.getUsername(),user);
        nicknames.add(user.getNickName());
    }
    
    public void removeUsers(User user) 
    {
        users.remove(user.getUsername());
        nicknames.remove(user.getNickName());
    }

    public void createUser(String username,String password,String nickname)
    {
        User user=new User(username, password, nickname);
        users.put(username,user);
        nicknames.add(nickname);
    }
    public void Login(String username,String password)
    {
        loggedInUser=users.get(username);
    }
    public void logout()
    {
        loggedInUser=null;
    }
    public static void saveUsers()
    {
        try{    
            FileWriter user=new FileWriter("files/UserInfo.txt");

            //fw.write("Welcome to javaTpoint.");    
           
            users.forEach((key, value) -> {

                try {
                    user.write(key+" "+value.getPassword()+" "+value.getNickName()+"\n");

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
              
              });
            
            user.close();    
           }catch(Exception e){System.out.println(e);}    
           
    }
    public static void importSavedUsers()
    {
        try 
         {
            String user=new String(Files.readAllBytes(Paths.get("files/UserInfo.txt")));
            String[] readUser=user.split("\n");
            for(String temp:readUser)
            {
                String[] read=temp.split(" ");
                String Username=read[0];
                String Password=read[1];
                String Nickname=read[2];

                User addUser= new User(Username, Password, Nickname);
                users.put(Username,addUser);
                nicknames.add(Nickname);
            }
            
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
}

