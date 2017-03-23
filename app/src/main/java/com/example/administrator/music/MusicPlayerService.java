package com.example.administrator.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/23.
 */
public class MusicPlayerService extends Service {
    private MediaPlayer mPlayer;
    private  MusicBinder mBinder;
    @Override
    public IBinder onBind(Intent intent) {
     if (mBinder==null){
         mBinder=new MusicBinder();
     }
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
      mPlayer=new MediaPlayer();
    }

    /**
     * 播放
     */
    public void playMusic() {
        // 没有播放，才开始
        if (!mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }
    //

     public boolean isPlaying()
     {
         return mPlayer.isPlaying();
     }
    public void pauseMusic(){
        if (mPlayer.isPlaying()){
            mPlayer.pause();
        }
        else {
            mPlayer.start();
        }

    }
      public void setMusic(Song song){

          try {
              mPlayer.reset();
              mPlayer.setDataSource(this,song.getSong_location());
          mPlayer.prepare();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
    class MusicBinder extends Binder{

        public MusicPlayerService getService(){

            return MusicPlayerService.this;
        }


    }
}
