package com.mygdx.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.asset.AssetHelper;
import com.mygdx.game.data.GamePreferences;

/**
 * A Singleton that allows for music to be displayed despite screens switching.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public class AudioHelper {

  /**
   * The name this object will be registered as inside the logger.
   */
  private static final String LOG_NAME = AudioHelper.class.getName();

  /**
   * Represents music that will play despite screens switching.
   */
  private static Music music;

  /**
   * Represents the descriptor of the particular asset to be played which will be used to ask the
   * asset manager for the concrete asset.
   */
  private static AssetDescriptor<Music> musicDescriptor;

  /**
   * The single instance of this class.
   */
  private static AudioHelper instance;

  /**
   * This class is singleton, and thus its constructor is private.
   */
  private AudioHelper() {
  }

  /**
   * 
   * @return instance Returns the only instance of this class.
   */
  public static synchronized AudioHelper getInstance() {
    if (instance == null) {
      instance = new AudioHelper();
    }
    return instance;
  }

  /**
   * Plays music that will continue between screens.
   * 
   * @param musicDescriptor
   *          Information about the track being played.
   * @param looping
   *          Whether the music should loop.
   * @param restart
   *          Whether the music should restart if it was already playing the given track.
   */
  public void playMusic(final AssetDescriptor<Music> musicDescriptor, boolean looping,
      boolean restart) {
    Gdx.app.log(LOG_NAME, "playMusic(final AssetDescriptor<Music> musicDescriptor, boolean looping,"
        + "boolean restart)");

    if (musicDescriptor == null) {
      Gdx.app.log(LOG_NAME, "ERROR: musicDescriptor parameter is null.");
      return;
    }

    final boolean sameTrack = AudioHelper.musicDescriptor != null
        && musicDescriptor.equals(AudioHelper.musicDescriptor);
    if (sameTrack && restart) {
      music.stop();
    } else if (!sameTrack) {
      setMusic(musicDescriptor);
    }

    music.play();
    music.setLooping(looping);
  }

  /**
   * Sets a new track to be played.
   * 
   * @param musicDescriptor
   *          The description of the track to be played
   */
  private void setMusic(AssetDescriptor<Music> musicDescriptor) {
    dispose();
    AudioHelper.musicDescriptor = musicDescriptor;
    music = AssetHelper.MANAGER.get(AssetHelper.TRACK1);
    music.setVolume(GamePreferences.getInstance().getMasterVolume()
        * GamePreferences.getInstance().getMusicVolume());
  }

  /**
   * Sets the volume of the currently playing song to the given value.
   * 
   * @param volume
   *          the volume the music will be set to
   */
  public void setVolume(final float volume) {
    Gdx.app.log(LOG_NAME, "setVolume(final float volume)");
    if (music != null) {
      music.setVolume(volume);
    }
  }

  /**
   * If music is being played through the game instance, this method will stop it.
   */
  public void dispose() {
    Gdx.app.log(LOG_NAME, "disposeMusic()");
    if (music != null) {
      // music.stop();
      music.dispose();
      music = null;
    }
    musicDescriptor = null;
  }

}
