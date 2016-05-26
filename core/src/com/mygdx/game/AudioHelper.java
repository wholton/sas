package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Allows for music to be displayed despite screens switching.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public class AudioHelper {

  /**
   * The name this object will be registered as inside the logger.
   */
  private final String logName;

  /**
   * Represents music that will play despite screens switching.
   */
  private Music music;

  public AudioHelper() {
    logName = getClass().getSimpleName();
  }

  /**
   * Plays music that will continue between screens.
   * 
   * @param music
   *          The track to be played.
   * @param volume
   *          The volume the music should be at.
   * @param looping
   *          Whether the music should loop.
   * @param restart
   *          Whether the music should restart if it was already playing the given track.
   */
  public void playMusic(Music music, float volume, boolean looping, boolean restart) {
    Gdx.app.log(logName, "playMusic(String music, float volume, boolean looping, boolean restart)");

    if (music == null) {
      Gdx.app.log(logName, "ERROR: music parameter is null.");
      return;
    }

    if (!music.equals(this.music) || restart) {
      stopMusic();
    }

    this.music = music;
    this.music.setVolume(volume);
    this.music.setLooping(looping);
    this.music.play();
  }

  /**
   * Sets the volume of the currently playing song to the given value.
   * 
   * @param volume
   *          the volume the music will be set to
   */
  public void setVolume(float volume) {
    Gdx.app.log(logName, "setVolume(float volume)");
    if (music != null) {
      music.setVolume(volume);
    }
  }

  /**
   * If music is being played through the game instance, this method will stop it.
   */
  public void stopMusic() {
    Gdx.app.log(logName, "stopMusic()");
    if (music != null) {
      music.stop();
    }
  }

}
