package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Displays a full-screen texture for a particular duration of time before fading to a follow-up
 * screen.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public class SplashScreen extends AbstractScreen {

  /**
   * The amount of time the splash texture will take to fade in to opaqueness and out to
   * transparentness.
   */
  private final float fadeTime;

  /**
   * The amount of time the splash texture will remain opaque before fading back out.
   */
  private final float displayTime;

  /**
   * The next screen to be displayed.
   */
  private final Screen transition;

  /**
   * The sprite representing the splash image which will be drawn to the screen. Must dispose of the
   * sprite's texture.
   */
  private Sprite splash;

  /**
   * The description of the particular asset to be displayed as the splash texture. Will be used to
   * query the asset manager for the concrete texture.
   */
  private final String texture;

  /**
   * Handles the splash fading effect.
   */
  private TweenManager tweenManager;

  public SplashScreen(final float fadeTime, final float displayTime, final Screen transition,
      final String trailerSplash1) {
    this.fadeTime = fadeTime;
    this.displayTime = displayTime;
    this.transition = transition;
    this.texture = trailerSplash1;
  }

  @Override
  public void render(final float delta) {
    super.render(delta);

    // Draw any sprites using the sprite batch
    game.getSpriteBatch().begin();
    splash.draw(game.getSpriteBatch());
    game.getSpriteBatch().end();

    // Update the tween manager
    tweenManager.update(delta);
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    splash.setSize(width, height);
  }

  @Override
  public void show() {
    super.show();

    // 2 is the number of times the effect will happen.
    Gdx.app.log(logName, "Transition in " + (displayTime + 2 * fadeTime) + " seconds");

    splash = new Sprite(game.getAssetManager().get(texture, Texture.class));

    // Registers the game's SpriteAccessor class to handle the Sprite
    // tweening effects.
    tweenManager = new TweenManager();
    Tween.registerAccessor(Sprite.class, new SpriteAccessor());

    // Sets the initial alpha value of the sprite such that it is
    // transparent.
    Tween.set(splash, SpriteAccessor.ALPHA).target(alphaTransparent).start(tweenManager);

    // Fades the alpha of the sprite such that it is opaque. The duration
    // must be in seconds.
    // Also fades the alpha of the sprite such that it is transparent after
    // a delay ("yoyos"). Duration and delay must be in seconds.
    // This also sets a call back which will transition the screen
    TweenCallback tweenCallback = new TweenCallback() {
      @Override
      public void onEvent(int type, BaseTween<?> source) {
        game.setScreen(transition);
      }
    };

    Tween.to(splash, SpriteAccessor.ALPHA, fadeTime).target(alphaOpaque)
        .repeatYoyo(1, displayTime).setCallback(tweenCallback).start(tweenManager);

    // This is to get rid of the flicker caused by drawing with the batch
    // then updating the tween in render.
    tweenManager.update(Float.MIN_VALUE);
  }

  @Override
  public boolean keyDown(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void disconnected(Controller controller) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean buttonDown(Controller controller, int buttonCode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean buttonUp(Controller controller, int buttonCode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean axisMoved(Controller controller, int axisCode, float value) {
    // TODO Auto-generated method stub
    return false;
  }
}
