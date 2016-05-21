package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The abstract form of a screen.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public abstract class AbstractScreen implements Screen {

  /**
   * The name this object will be registered as inside the logger.
   */
  protected final String logName;

  /**
   * The width of the screen (from Gdx.graphics) at the time this screen was created.
   */
  protected static final int SCREEN_WIDTH = Gdx.graphics.getWidth();

  /**
   * The height of the screen (from Gdx.graphics) at the time this screen was created.
   */
  protected static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

  /**
   * The alpha value corresponding to opaqueness.
   */
  public static final float ALPHA_OPAQUE = 1;

  /**
   * The alpha value corresponding to transparentness.
   */
  public static final float ALPHA_TRANSPARENT = 0;

  /**
   * The sprite batch that every screen uses to draw textures to the screen without needing a stage.
   * This must be disposed of.
   */
  protected SpriteBatch batch;

  public AbstractScreen() {
    logName = this.getClass().getSimpleName();
    Gdx.app.log(logName, "Constructor called");
  }

  /**
   * Disposes of the screen's sprite batch, as it will no longer be used.
   */
  @Override
  public void dispose() {
    Gdx.app.log(logName, "Dispose called");
    batch.dispose();
  }

  /**
   * Hide is called as a screen gets replaced by another, thus it is used to call the dispose
   * method.
   */
  @Override
  public void hide() {
    Gdx.app.log(logName, "Hide called");
    dispose();
  }

  /**
   * Called when the game loses focus.
   */
  @Override
  public void pause() {
    Gdx.app.log(logName, "Pause called");
  }

  /**
   * Called every time the screen is to be rendered to the screen and acts as the "game loop". Sets
   * up the screen to be drawn to by clearing it to black and opaque (0, 0, 0, 1) and clearing the
   * buffer.
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
  }

  /**
   * Called directly after show() and at any point the game is resized.
   */
  @Override
  public void resize(int width, int height) {
    Gdx.app.log(logName, "Resize called");
  }

  /**
   * Called when the game regains focus.
   */
  @Override
  public void resume() {
    Gdx.app.log(logName, "Resume called");
  }

  /**
   * Called directly before the screen is to be rendered. Sets up the sprite batch to be used during
   * the rendering phase.
   */
  @Override
  public void show() {
    Gdx.app.log(logName, "Show called");
    batch = new SpriteBatch();
  }
}
