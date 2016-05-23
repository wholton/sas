package com.mygdx.game.WIP;

import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.SaSGame;
import com.mygdx.game.asset.AssetHelper;
import com.mygdx.game.data.GamePreferences;
import com.mygdx.game.screen.AbstractScreen;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.screen.SplashScreen;

public class LoadingScreen extends AbstractScreen {
  // TODO: This currently does nothing but passes things on, so it's just a
  // demo of how a loading screen might be implemeneted (by adding to the
  // render method
  // and checking MANAGER.getProgress() for the loading bar or whatever

  public static final AssetManager MANAGER = AssetHelper.MANAGER;

  @Override
  public void render(float delta) {
    if (MANAGER.update()) {
      transitionScreen();
    }
  }

  @Override
  public void show() {
    super.show();
    AssetHelper.loadAll();
  }

  public void transitionScreen() {
    if (GamePreferences.getInstance().isSkipIntro()) {
      SaSGame.getInstance().setScreen(new MainMenuScreen());
    } else {
      final SplashScreen trailer = new SplashScreen(2, 2, new MainMenuScreen(),
          AssetHelper.TRAILER_SPLASH1);
      final SplashScreen company = new SplashScreen(2, 2, trailer, AssetHelper.COMPANY_SPLASH1);
      SaSGame.getInstance().setScreen(company);
    }
  }

}
