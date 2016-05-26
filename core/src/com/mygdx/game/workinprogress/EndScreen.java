package com.mygdx.game.workinprogress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Assets;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.tween.AbstractAccessor;
import com.mygdx.game.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class EndScreen extends AbstractScreen {
  // TODO: a very rough class, more of an example
  /**
   * The image to be splashed at the end of the game.
   */
  private Sprite splash;

  /**
   * Handles the splash fading effect.
   */
  private TweenManager tweenManager;

  @Override
  public void dispose() {
    super.dispose();
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    game.getSpriteBatch().begin();
    splash.draw(game.getSpriteBatch());
    game.getSpriteBatch().end();

    tweenManager.update(delta);
  }

  @Override
  public void show() {
    super.show();

    // TODO: Render credits.
    tweenManager = new TweenManager();
    Tween.registerAccessor(Sprite.class, new SpriteAccessor());

    splash = new Sprite(game.getAssetManager().get(Assets.END_SPLASH1, Texture.class));
    splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    // Sets the initial alpha value of the sprite such that it is
    // transparent.
    Tween.set(splash, AbstractAccessor.ALPHA).target(alphaTransparent).start(tweenManager);
    // Fades the alpha of the sprite such that it is opaque. The duration
    // must be in seconds.
    // Also fades the alpha of the sprite such that it is transparent after
    // a delay ("yoyos"). Duration and delay must be in seconds.
    // This also sets a call back which will transition the screen
    float fadeDurationSeconds = 1;
    float displayDurationSeconds = 1;
    TweenCallback tweenCallback = new TweenCallback() {
      @Override
      public void onEvent(int type, BaseTween<?> source) {
        Gdx.app.exit();
      }
    };
    Tween.to(splash, AbstractAccessor.ALPHA, fadeDurationSeconds).target(alphaOpaque)
        .repeatYoyo(1, displayDurationSeconds).setCallback(tweenCallback).start(tweenManager);

    tweenManager.update(Float.MIN_VALUE);
  }
}
