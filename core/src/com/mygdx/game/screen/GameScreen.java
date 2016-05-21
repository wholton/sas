package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.asset.AssetHelper;

public class GameScreen extends AbstractScreen {
  // TODO: a very rough class, just an example
  private TextureAtlas textureAtlas;
  private Animation animation;
  private float elapsedTime = 0;

  public GameScreen() {
    textureAtlas = AssetHelper.MANAGER.get(AssetHelper.ATLAS);
    animation = new Animation(1 / 10f, textureAtlas.getRegions());
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    batch.begin();
    elapsedTime += Gdx.graphics.getDeltaTime();
    batch.draw(animation.getKeyFrame(elapsedTime, true), 0, 0);
    batch.end();
  }

}
