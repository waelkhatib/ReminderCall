package com.waelalk.remindercall.External;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.waelalk.remindercall.View.ConfigurationActivity;

import androidx.annotation.NonNull;

import java.io.IOException;

public class RingTonePlayer {
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private boolean stopped=false;

  public   RingTonePlayer(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();

    }

    /**
     * Play the ringtone for the given uri.
     *
     * @param uri uri of the ringtone to play.
     * @throws IOException if it cannot play the ringtone.
     */
   public void playRingtone(@NonNull Uri uri) throws IOException,
            IllegalArgumentException,
            SecurityException,
            IllegalStateException {

        if (!stopped && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
       mMediaPlayer=new MediaPlayer();

        mMediaPlayer.setDataSource(mContext, uri);
       mMediaPlayer.prepareAsync();
       mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
               Log.d("%%%1",""+mMediaPlayer.getDuration());
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           if ((double) mp.getCurrentPosition() / (double) mp.getDuration() < 0.0004) {
                               Log.d("%%%2", "-p" + mp.getCurrentPosition());
                               mp.stop();
                               mp.release();
                               stopped = true;
                           }
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                   }
               },mp.getDuration());
               mp.start();
               stopped=false;
           }
       });

    }
    public void play(@NonNull Uri uri) throws IOException,
            IllegalArgumentException,
            SecurityException,
            IllegalStateException {

        if (!stopped && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            stopped=true;
            ((ConfigurationActivity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((ConfigurationActivity)mContext).changeImageBtn();
                }
            });
        }else {
            mMediaPlayer=new MediaPlayer();

            mMediaPlayer.setDataSource(mContext, uri);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("%%%1",""+mMediaPlayer.getDuration());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if ((double) mp.getCurrentPosition() / (double) mp.getDuration() < 0.0004) {
                                    Log.d("%%%2", "-p" + mp.getCurrentPosition());
                                    mp.stop();
                                    mp.release();
                                    stopped = true;
                                    ((ConfigurationActivity)mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((ConfigurationActivity)mContext).changeImageBtn();
                                        }
                                    });
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },mp.getDuration());
                    mp.start();
                    stopped=false;
                    ((ConfigurationActivity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ConfigurationActivity)mContext).changeImageBtn();
                        }
                    });
                }
            });

                    }
    }
    public boolean isPlayed(){
       return !stopped && mMediaPlayer.isPlaying();
    }
    /**
     * Release the {@link MediaPlayer} instance. Remember to call this method in on destroy.
     */
   public void release() {
        if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}
