package gabriel.medpel.superheroescardbattlegame;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

public class Song {
    //Singleton
    private static MediaPlayer currentlyPlayingSong, currentlyPlayingFX;
    private static int currentSongId;
    private static boolean isMuted;

    public Song() {

    }

    public void PlayFX(int fxId, Context context, boolean shouldLoop) {
        if (!isMuted) {
            MediaPlayer fx = MediaPlayer.create(context, fxId);
            currentlyPlayingSong.pause();


            if (currentlyPlayingFX != fx) {
                StopFX();

                currentlyPlayingFX = fx;
                currentlyPlayingFX.start();



                currentlyPlayingFX.setLooping(shouldLoop);
            }

            new Handler().postDelayed(() -> currentlyPlayingSong.start(), currentlyPlayingFX.getDuration());
        }


    }

    public void PlaySong(int songId, boolean shouldLoop, Context context) {
        if (!isMuted) {
            MediaPlayer song = MediaPlayer.create(context, songId);
            currentSongId = songId;


            if (currentlyPlayingSong != song) {
                StopSong();

                currentlyPlayingSong = song;

                currentlyPlayingSong.setVolume(20, 20);
                currentlyPlayingSong.start();


                currentlyPlayingSong.setLooping(shouldLoop);
            }
        }

    }

    public void StopFX() {
        if (currentlyPlayingFX != null) {
            currentlyPlayingFX.stop();

            currentlyPlayingFX.release();

            currentlyPlayingFX = null;
        }
    }

    public void StopSong() {
        if (currentlyPlayingSong != null) {
            currentlyPlayingSong.stop();

            currentlyPlayingSong.release();

            currentlyPlayingSong = null;
        }
    }

    public void PauseSong() {
        if (currentlyPlayingSong != null) {
            currentlyPlayingSong.pause();

        }
    }

    public void ResumeSong() {
        if (!isMuted) {
            if (currentlyPlayingSong != null) {
                currentlyPlayingSong.start();
            }
        }

    }

    public int getCurrentSongId() {
        return currentSongId;
    }

    public void mute() {
        currentlyPlayingSong.pause();
        if (currentlyPlayingFX != null){
            currentlyPlayingFX.pause();
        }

        isMuted = true;

    }

    public void unMute() {
        currentlyPlayingSong.start();
        isMuted = false;

    }

    public boolean isMuted() {
        return isMuted;
    }
}
