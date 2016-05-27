package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.AssetLoadingScreen;

import net.dermetfan.gdx.assets.AnnotationAssetManager;

public class SaSGame extends Game {

  /**
   * The string to be displayed at the top of the application.
   */
  public static final String NAME = "S&S";

  /**
   * The string representing the current version number of the game, which is displayed with the
   * game's name at the top of the application. First digit is the overall version which changes
   * based the level of revision (major update or expansion). Second digit represents incremental
   * changes to a particular version (medium updates). Third digit represents the development stage
   * of an update (0 is alpha, 1 is beta, 2 is release candidate, 3 is release). Fourth digit
   * represents the level of completion of the development stage.
   * 
   * <p>EXAMPLES: 1.2.0.1 instead of 1.2-a1 1.2.1.2, instead of 1.2-b2 (beta with some bug fixes),
   * 1.2.2.3 instead of 1.2-rc3 (release candidate), 1.2.3.0 instead of 1.2-r (commercial
   * distribution), 1.2.3.5 instead of 1.2-r5 (commercial distribution with many bug fixes)
   */
  public static final String VERSION = "0.0.3.0";

  /**
   * The game's asset helper class, which includes functions for asset management.
   */
  private AnnotationAssetManager assetManager;

  private AudioHelper audioHelper;
  
  /**
   * The sprite batch that every screen uses to draw textures to the screen without needing a stage.
   * This must be disposed of.
   */
  private SpriteBatch spriteBatch;

  /**
   * The game's preferences.
   */
  private GamePreferences gamePreferences;

  /**
   * The name this object will be registered as inside the logger.
   */
  private final String logName;

  /**
   * A logger for FPS.
   */
  private FPSLogger fpsLogger;

  /**
   * Whether FPS should be logged.
   */
  private boolean logFps;

  /**
   * Default constructor for the SaSGame.
   */
  public SaSGame() {
    logName = getClass().getSimpleName();
  }

  /**
   * Called directly after the constructor. Used to load prior game data and transition to the first
   * screen.
   */
  @Override
  public void create() {
    Gdx.app.log(logName, "Creating");

    spriteBatch = new SpriteBatch();
    assetManager = new AnnotationAssetManager();
    audioHelper = new AudioHelper();
    gamePreferences = new GamePreferences();
    fpsLogger = new FPSLogger();
    logFps = false;
    
    
    // Load the game preferences
    gamePreferences.loadData();

    // Load the assets then transition to the first screen
    setScreen(new AssetLoadingScreen());
  }

  @Override
  public void dispose() {
    Gdx.app.log(logName, "Disposing");
    super.dispose();

    spriteBatch.dispose();
    
    // Dispose of all of the game assets
    assetManager.dispose();

    // TODO: Save game data.

  }

  @Override
  public void render() {
    super.render();

    // log current FPS
    if (logFps) {
      fpsLogger.log();
    }
    
    // Probably get a debug boolean and wrap this, as well as the logging
    if (Gdx.input.isKeyPressed(Keys.R)) {
      try {
        setScreen(getScreen().getClass().newInstance());
      } catch (InstantiationException | IllegalAccessException exception) {
        exception.printStackTrace();
      }
    }
  }

  @Override
  public void resize(int width, int height) {
    Gdx.app.log(logName, "Resizing game to: " + width + " x " + height);
    super.resize(width, height);

    // save the screen size data
    gamePreferences.saveScreenSizeData(width, height);
  }

  @Override
  public void resume() {
    Gdx.app.log(logName, "Resuming");
    super.resume();
  }

  @Override
  public void setScreen(Screen screen) {
    Gdx.app.log(logName, "Setting screen: " + screen.getClass().getSimpleName());
    super.setScreen(screen);
  }

  public AudioHelper getAudioHelper() {
    return audioHelper;
  }

  public AnnotationAssetManager getAssetManager() {
    return assetManager;
  }

  public GamePreferences getGamePreferences() {
    return gamePreferences;
  }
  
  public SpriteBatch getSpriteBatch() {
    return spriteBatch;
  }

}
