package serverapp.models;

import javafx.scene.image.Image;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User implements Comparable{
    private String username;
    private String password;
    private String nickName;
    private int score;
    private Image avatarPic;
    private int picNum;
    private String url;
    private Date winTime=null;
    private Date loginTime=null;

    public void setScore(int score) {
        this.score = score;
    }
    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }
    public static String getDateString(Date date)
    {
        if(date==null)
        {
            return "A";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");  
        return formatter.format(date);
    }
    public Date getWinTime() {
        return this.winTime;
    }

    public void setWinTime(Date winTime) {
        this.winTime = winTime;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
    public void setAllFriendShipRequests(ArrayList<Request> allFriendShipRequests) {
        this.allFriendShipRequests = allFriendShipRequests;
    }
    public void setAllSentRequests(ArrayList<Request> allSentRequests) {
        this.allSentRequests = allSentRequests;
    }
    public void setFriendsUsernames(ArrayList<String> friendsUsernames) {
        this.friendsUsernames = friendsUsernames;
    }


    

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Request> getAllFriendShipRequests() {
        return allFriendShipRequests;
    }

    private ArrayList<Request> allFriendShipRequests = new ArrayList<>();

    public ArrayList<Request> getAllSentRequests() {
        return allSentRequests;
    }

    private ArrayList<Request> allSentRequests = new ArrayList<>();

    public ArrayList<String> getFriendsUsernames() {
        return friendsUsernames;
    }

    private ArrayList<String> friendsUsernames = new ArrayList<>();
    // @Override
    // public String toString()
    // {
    //     return this.name;
    // }
    @Override
    public int compareTo(Object o) {

        int score=Integer.compare(((User)o).getScore(),this.score );
        int name=this.username.compareTo(((User)o).getUsername());
        int time;

        if(winTime==null||((User ) o).getWinTime()==null)
        {
            time=0;
        }else
        {
            time=this.winTime.compareTo(((User) o).getWinTime());
            // if(this.winTime.after(((User) o).getWinTime()))
            // {
            //     return 1;
            // }
            // if(this.winTime.)
        }
        
        

        if(score==0)
        {
            if(name==0)
            {
                return time;
            }
            return name;
        }
        return score;
    }


    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickName = nickname;
        this.score=0;
        
    }

    public Image getAvatarPic()
    {
        return avatarPic;
    }
    public int getPicNum()
    {
        return picNum;
    }
    
    public void setAvatarPic(Image image, int num) {
        this.avatarPic = image;
        this.picNum = num;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getScore() {
        return this.score;
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    public void decreaseScore(int amount) {
        score -= amount;
    }


}
