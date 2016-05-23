package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.SaSGame;
import com.mygdx.game.audio.AudioHelper;

/**
 * Represents the player's game preferences as decided by the options screen. This is a singleton
 * class.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public class GamePreferences {

  /**
   * The name this object will be registered as inside the logger.
   */
  private static final String LOG_NAME = GamePreferences.class.getSimpleName();

  /**
   * The name of the Preferences file, which (on Windows) may be found in "C:/Users/<USER>/.prefs".
   */
  private static final String PREFERENCES_NAME = SaSGame.NAME + ".Preferences";

  /**
   * The volume of the game's music.
   */
  private float musicVolume;

  /**
   * The name of the music volume attribute within the preferences file.
   */
  private static final String MUSIC_VOLUME_PREFERENCES_NAME = "MUSICVOLUME";

  /**
   * The volume of the game's sound effects.
   */
  private float soundVolume;

  /**
   * The name of the sound volume attribute within the preferences file.
   */
  private static final String SOUND_VOLUME_PREFERENCES_NAME = "SOUNDVOLUME";

  /**
   * The overall volume of the game, which will be multiplied by any sub-volumes to determine their
   * final volume.
   */
  private float masterVolume;

  /**
   * The name of the master volume attribute within the preferences file.
   */
  private static final String MASTER_VOLUME_PREFERENCES_NAME = "MASTERVOLUME";

  /**
   * Whether or not VSync will be used by the application.
   */
  private boolean vsync;

  /**
   * The name of the VSync attribute within the preferences file.
   */
  private static final String USE_VSYNC_PREFERENCES_NAME = "VSYNC";

  /**
   * Whether or not the application will be fullscreen.
   */
  private boolean fullscreen;

  /**
   * The name of the fullscreen attribute within the preferences file.
   */
  private static final String USE_FULLSCREEN_PREFERENCES_NAME = "FULLSCREEN";

  /**
   * Whether or not the intro will be skipped.
   */
  private boolean skipIntro;

  /**
   * The name of the skip intro attribute within the preferences file.
   */
  private static final String SKIP_INTRO_PREFERENCES_NAME = "SKIPINTRO";

  /**
   * Whether or not the intro will be skipped.
   */
  private int screenWidth;

  /**
   * The name of the skip intro attribute within the preferences file.
   */
  private static final String SCREEN_WIDTH_PREFERENCES_NAME = "SCREENWIDTH";
  
  /**
   * Whether or not the intro will be skipped.
   */
  private int screenHeight;

  /**
   * The name of the skip intro attribute within the preferences file.
   */
  private static final String SCREEN_HEIGHT_PREFERENCES_NAME = "SCREENHEIGHT";
  
  /**
   * The singleton instance of the game preferences class.
   */
  private static GamePreferences instance;

  /**
   * The constructor of a singleton class should always be private.
   */
  private GamePreferences() {
    loadData();
  }
  
  public static GamePreferences getInstance() {
    // Ensures there is only one instance of the game preferences class.
    if (instance == null) {
      instance = new GamePreferences();
    }
    return instance;
  }

  /**
   * Applies the effects of all game preferences.
   */
  private void applyEffects() {
    Gdx.app.log(LOG_NAME, "Applying preferences");
    // Apply volume effects
    updateMusic();
    updateSound();
    // Apply vsync effect
    updateVsync();
    // Apply full screen/windowed effect
    updateFullscreen();
  }

  public void updateMusic() {
    AudioHelper.getInstance().setVolume(getMusicVolume() * getMasterVolume());
  }

  public void updateSound() {
  }

  public void updateVsync() {
    Gdx.graphics.setVSync(vsync);
  }

  public void updateFullscreen() {
    if (!Gdx.graphics.isFullscreen() || Gdx.graphics.isFullscreen() && !fullscreen) {
      Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
    } else if (!Gdx.graphics.isFullscreen() && fullscreen) {
      Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }
  }

  /**
   * Loads the game preferences data from the preferences file and applies their effects.
   */
  public void loadData() {
    Gdx.app.log(LOG_NAME, "Loading preferences");
    Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);

    masterVolume = preferences.getFloat(MASTER_VOLUME_PREFERENCES_NAME, 1);
    soundVolume = preferences.getFloat(SOUND_VOLUME_PREFERENCES_NAME, 1);
    musicVolume = preferences.getFloat(MUSIC_VOLUME_PREFERENCES_NAME, 1);
    vsync = preferences.getBoolean(USE_VSYNC_PREFERENCES_NAME, false);
    fullscreen = preferences.getBoolean(USE_FULLSCREEN_PREFERENCES_NAME, true);
    skipIntro = preferences.getBoolean(SKIP_INTRO_PREFERENCES_NAME, true);
    screenHeight = preferences.getInteger(SCREEN_HEIGHT_PREFERENCES_NAME, 
        Gdx.graphics.getDisplayMode().height);
    screenWidth = preferences.getInteger(SCREEN_WIDTH_PREFERENCES_NAME, 
        Gdx.graphics.getDisplayMode().width); 

    applyEffects();
  }

  /**
   * Saves the game preferences data from the preferences file and applies their effects.
   */
  public void saveData() {
    Gdx.app.log(LOG_NAME, "Saving preferences");
    Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);

    prefs.putFloat(MASTER_VOLUME_PREFERENCES_NAME, masterVolume);
    prefs.putFloat(MUSIC_VOLUME_PREFERENCES_NAME, musicVolume);
    prefs.putFloat(SOUND_VOLUME_PREFERENCES_NAME, soundVolume);
    prefs.putBoolean(USE_VSYNC_PREFERENCES_NAME, vsync);
    prefs.putBoolean(USE_FULLSCREEN_PREFERENCES_NAME, fullscreen);
    prefs.putBoolean(SKIP_INTRO_PREFERENCES_NAME, skipIntro);

    prefs.flush();

    applyEffects();
  }
  
  /**
   * Saves the current height and width of the game to the preferences file.
   */
  public void saveScreenSizeData(int screenWidth, int screenHeight) {
    Gdx.app.log(LOG_NAME, "Saving screen size");
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    
    Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
    
    prefs.putInteger(SCREEN_WIDTH_PREFERENCES_NAME, screenWidth);
    prefs.putInteger(SCREEN_HEIGHT_PREFERENCES_NAME, screenHeight);
    
    prefs.flush();
  }

  public float getMasterVolume() {
    return masterVolume;
  }

  public float getMusicVolume() {
    return musicVolume;
  }

  public float getSoundVolume() {
    return soundVolume;
  }

  public boolean isFullscreen() {
    return fullscreen;
  }

  public boolean isSkipIntro() {
    return skipIntro;
  }

  public boolean isVSync() {
    return vsync;
  }
  
  public int getScreenWidth() {
    return screenWidth;
  }
  
  public int getScreenHeight() {
    return screenHeight;
  }

  public void setFullscreen(boolean fullscreen) {
    this.fullscreen = fullscreen;
  }

  public void setMasterVolume(float masterVolume) {
    this.masterVolume = masterVolume;
  }

  public void setMusicVolume(float musicVolume) {
    this.musicVolume = musicVolume;
  }

  public void setSkipIntro(boolean skipIntro) {
    this.skipIntro = skipIntro;
  }

  public void setSoundVolume(float soundVolume) {
    this.soundVolume = soundVolume;
  }

  public void setVSync(boolean vsync) {
    this.vsync = vsync;
  }

}
