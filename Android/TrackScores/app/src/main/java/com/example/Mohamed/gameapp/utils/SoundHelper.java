package com.example.Mohamed.gameapp.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.Mohamed.gameapp.R;

/**
 * Created by chaba on 10-12-2017.
 */

public class SoundHelper {

    private MediaPlayer mMusicPlayer;
    public SoundHelper(){
    }
    public void prepareMusicPlayer(Context context){ // instatiere og fylder varibler
       mMusicPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.pleasant_music); // laver en mediaplayer med udvalgt musik
       mMusicPlayer.setVolume(.5f, .5f); // lydstyrke
       mMusicPlayer.setLooping(true); //
    }
    public void playMusic(){ // start music
        if (mMusicPlayer != null){
            mMusicPlayer.start();
        }
    }

    public void pauseMusic(){
        if (mMusicPlayer != null && mMusicPlayer.isPlaying()){
            mMusicPlayer.pause();
        }
    }
}
