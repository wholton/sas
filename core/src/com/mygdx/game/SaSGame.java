package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.mygdx.game.asset.AssetHelper;
import com.mygdx.game.data.GamePreferences;
import com.mygdx.game.screen.LoadingScreen;

public class SaSGame extends Game {

  private static final FPSLogger FPS_LOGGER = new FPSLogger();

  /**
   * The name this object will be registered as inside the logger.
   */
  private static final String LOG_NAME = SaSGame.class.getName();

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
   * <p>
   * EXAMPLES: 1.2.0.1 instead of 1.2-a1 1.2.1.2, instead of 1.2-b2 (beta with some bug fixes),
   * 1.2.2.3 instead of 1.2-rc3 (release candidate), 1.2.3.0 instead of 1.2-r (commercial
   * distribution), 1.2.3.5 instead of 1.2-r5 (commercial distribution with many bug fixes)
   */
  public static final String VERSION = "0.0.3.0";

  /**
   * The singleton instance of the class. Lazy initialization.
   */
  private static SaSGame instance;

  /**
   * This class is singleton, and thus its constructor is private.
   */
  private SaSGame() {
  }

  /**
   * 
   * @return instance Returns the only instance of this class.
   */
  public static synchronized SaSGame getInstance() {
    if (instance == null) {
      instance = new SaSGame();
    }
    return instance;
  }

  /**
   * Called directly after the constructor. Used to load prior game data and transition to the first
   * screen.
   */
  @Override
  public void create() {
    Gdx.app.log(LOG_NAME, "Creating");

    // Load the game preferences
    GamePreferences.getInstance();

    // Load the assets then transition to the first screen
    setScreen(new LoadingScreen());

    // TODO: Load game data.
  }

  @Override
  public void dispose() {
    Gdx.app.log(LOG_NAME, "Disposing");

    // Dispose of all of the game assets
    AssetHelper.MANAGER.dispose();

    super.dispose();
    // TODO: Save game data.
  }

  @Override
  public void render() {
    super.render();
    FPS_LOGGER.log();
  }

  @Override
  public void resize(int width, int height) {
    Gdx.app.log(LOG_NAME, "Resizing game to: " + width + " x " + height);
    super.resize(width, height);
  }

  @Override
  public void resume() {
    Gdx.app.log(LOG_NAME, "Resuming");
    super.resume();
  }

  @Override
  public void setScreen(Screen screen) {
    Gdx.app.log(LOG_NAME, "Setting screen: " + screen.getClass().getSimpleName());
    super.setScreen(screen);
  }

}
