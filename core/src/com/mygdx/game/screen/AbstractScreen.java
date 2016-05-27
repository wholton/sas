package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.SaSGame;

/**
 * The abstract form of a screen.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public abstract class AbstractScreen implements Screen, InputProcessor, ControllerListener {

  /**
   * The name this object will be registered as inside the logger.
   */
  protected final String logName;

  /**
   * The alpha value corresponding to opaqueness. Default 1.
   */
  protected float alphaOpaque;

  /**
   * The alpha value corresponding to transparentness. Default 0.
   */
  protected float alphaTransparent;

  protected final SaSGame game;

  public AbstractScreen() {
    logName = this.getClass().getSimpleName();
    alphaOpaque = 1;
    alphaTransparent = 0;
    game = ((SaSGame) Gdx.app.getApplicationListener());
  }

  /**
   * Disposes of the screen's sprite batch, as it will no longer be used.
   */
  @Override
  public void dispose() {
    Gdx.app.log(logName, "Dispose called");
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
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
   * Called directly before the screen is to be rendered.
   */
  @Override
  public void show() {
    Gdx.app.log(logName, "Show called");
    Gdx.input.setInputProcessor(this);
    Controllers.addListener(this);
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }
  
  @Override
  public void connected(Controller controller) {}

  @Override
  public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
    return false;
  }

  @Override
  public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
    return false;
  }

  @Override
  public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
    return false;
  }
  
  @Override
  public boolean povMoved(Controller controller, int povCode, PovDirection value) {
    return false;
  }
}
