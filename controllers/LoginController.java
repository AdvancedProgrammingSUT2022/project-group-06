package controllers;


import java.util.regex.Matcher;



public class LoginController
{
    UserController userController=new UserController();

    public String login(String input)
    {
        int passwordIndex=0,usernameIndex=0;
        boolean checked=false;
        if(((passwordIndex=input.indexOf("--password"))!=-1)&&((usernameIndex=input.indexOf("--username"))!=-1))
        {
            passwordIndex+=10;
            usernameIndex+=10;
            checked=true;
        }

        if(((passwordIndex=input.indexOf("-p"))!=-1)&&((usernameIndex=input.indexOf("-u"))!=-1))
        {
            passwordIndex+=2;
            usernameIndex+=2;
            checked=true;
        }
        if(!checked)
        {
            return "invalid command!";
        }
        String getPassword=input.substring(passwordIndex+1);
        String password=getPassword.split("[ \\t]+")[0];

        String getUsername=input.substring(usernameIndex+1);
        String username=getUsername.split("[ \\t]+")[0];


        if(!UserController.users.containsKey(username) || !UserController.users.get(username).getPassword().equals(password))
        {

            return "Username and password didn't match!";
        }

        userController.Login(username, password);
        return "user logged in successfully!";
    }

    public String createUser(String input)
    {
        int passwordIndex=0,usernameIndex=0,nicknameIndex=0;

        boolean checked2=false;
        boolean checked=false;
        if(((passwordIndex=input.indexOf("--password"))!=-1)&&((usernameIndex=input.indexOf("--username"))!=-1)&&((nicknameIndex=input.indexOf("--nickname"))!=-1))
        {
            passwordIndex+=11;
            usernameIndex+=11;
            nicknameIndex+=11;
            checked=true;
            checked2=true;
        }

        if(!checked2&&((passwordIndex=input.indexOf("-p"))!=-1)&&((usernameIndex=input.indexOf("-u"))!=-1)&&((nicknameIndex=input.indexOf("-n"))!=-1))
        {
            passwordIndex+=3;
            usernameIndex+=3;
            nicknameIndex+=3;
            checked=true;
        }
        if(!checked)
        {
            return "invalid command!";
        }
        String getPassword=input.substring(passwordIndex);
        String password=getPassword.split("[ \\t]+")[0];

        String getUsername=input.substring(usernameIndex);
        String username=getUsername.split("[ \\t]+")[0];

        String getNickname=input.substring(nicknameIndex);
        String nickname=getNickname.split("[ \\t]+")[0];

        if(UserController.users.containsKey(username))
        {
            return "user with username "+username+" already exists";
        }
        if(userController.getNicknames().contains(nickname))
        {
            return "user with nickname "+nickname+" already exists";
        }

        userController.createUser(username, password, nickname);
        return "user created successfuly";
    }

    public boolean changeMenu(Matcher matcher)
    {
        if(matcher.group("menuname").equals("Main Menu"))
        {
            return true;
        }
        return false;
    }
}
