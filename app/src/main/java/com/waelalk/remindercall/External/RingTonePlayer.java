package com.waelalk.remindercall.External;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import androidx.annotation.NonNull;

import java.io.IOException;

public class RingTonePlayer {
    private Context mContext;
    private MediaPlayer mMediaPlayer;

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

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();

        mMediaPlayer.setDataSource(mContext, uri);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
    }
    public void play(@NonNull Uri uri) throws IOException,
            IllegalArgumentException,
            SecurityException,
            IllegalStateException {

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }else {
            mMediaPlayer.reset();

            mMediaPlayer.setDataSource(mContext, uri);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }
    }
    /**
     * Release the {@link MediaPlayer} instance. Remember to call this method in on destroy.
     */
   public void release() {
        if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}
