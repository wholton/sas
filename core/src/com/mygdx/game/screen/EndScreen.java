package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.accessor.AbstractAccessor;
import com.mygdx.game.accessor.SpriteAccessor;
import com.mygdx.game.asset.AssetHelper;

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

    batch.begin();
    splash.draw(batch);
    batch.end();

    tweenManager.update(delta);
  }

  @Override
  public void show() {
    super.show();

    // TODO: Render credits.
    tweenManager = new TweenManager();
    Tween.registerAccessor(Sprite.class, new SpriteAccessor());

    splash = new Sprite(AssetHelper.MANAGER.get(AssetHelper.END_SPLASH1));
    splash.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

    // Sets the initial alpha value of the sprite such that it is
    // transparent.
    Tween.set(splash, AbstractAccessor.ALPHA).target(ALPHA_TRANSPARENT).start(tweenManager);
    // Fades the alpha of the sprite such that it is opaque. The duration
    // must be in seconds.
    // Also fades the alpha of the sprite such that it is transparent after
    // a delay ("yoyos"). Duration and delay must be in seconds.
    // This also sets a call back which will transition the screen
    final float FADE_DURATION_SECONDS = 1;
    final float DISPLAY_DURATION_SECONDS = 1;
    TweenCallback tweenCallback = new TweenCallback() {
      @Override
      public void onEvent(int type, BaseTween<?> source) {
        Gdx.app.exit();
      }
    };
    Tween.to(splash, AbstractAccessor.ALPHA, FADE_DURATION_SECONDS).target(ALPHA_OPAQUE)
        .repeatYoyo(1, DISPLAY_DURATION_SECONDS).setCallback(tweenCallback).start(tweenManager);

    tweenManager.update(Float.MIN_VALUE);
  }
}
