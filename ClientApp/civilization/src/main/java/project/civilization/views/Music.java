package project.civilization.views;

import javafx.animation.Animation;
import javafx.scene.media.AudioClip;
import project.civilization.CivilizationApplication;

import java.net.URL;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

public class Music {
    private static AudioClip music;
    private static boolean isMute = false;

    public static void addMusic(String address){
        if(!isMute){
            if(music != null)music.stop();
            URL url = CivilizationApplication.class.getResource(address);
            assert url != null;
            music = new AudioClip(url.toExternalForm());
            music.setCycleCount(INDEFINITE);
            music.play();
            //music.setVolume(20);
        }
    }

    public static AudioClip getMusic() {
        return music;
    }

    public static void mute(){
        if(music != null) music.stop();
        isMute = true;
    }

    public static void effect(String address) {
        address = "songs/"+ address;
        if(!isMute){
            URL url = CivilizationApplication.class.getResource(address);
            assert url != null;
            AudioClip effect = new AudioClip(url.toExternalForm());
            effect.play();
            //music.setVolume(20);
        }
    }
}

