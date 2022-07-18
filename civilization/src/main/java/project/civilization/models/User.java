package project.civilization.models;

import javafx.scene.image.Image;

public class User implements Comparable{
    private String username;
    private String password;
    private String nickName;
    private int score;
    private Image avatarPic;
    private int picNum;

    // @Override
    // public String toString()
    // {
    //     return this.name;
    // }
    @Override
    public int compareTo(Object o) {
        return Integer.compare(((User)o).getScore(),this.score );
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
