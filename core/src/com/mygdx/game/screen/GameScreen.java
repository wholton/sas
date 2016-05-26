package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Assets;

public class GameScreen extends AbstractScreen {
  // TODO: a very rough class, just an example
  private TextureAtlas textureAtlas;
  private Animation animation;
  private float elapsedTime = 0;

  public GameScreen() {
    textureAtlas = game.getAssetManager().get(Assets.ATLAS, TextureAtlas.class);
    animation = new Animation(1 / 3f, textureAtlas.findRegions("stand right"));
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    game.getSpriteBatch().begin();
    elapsedTime += Gdx.graphics.getDeltaTime();
    game.getSpriteBatch().draw(animation.getKeyFrame(elapsedTime, true), 0, 0);
    game.getSpriteBatch().end();
  }

}
