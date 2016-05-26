package com.mygdx.game.screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Assets;

public class AssetLoadingScreen extends AbstractScreen {

  protected Stage stage;

  @Override
  public void render(float delta) {
    game.getAssetManager().finishLoading();
    transitionScreen();
    // if asset loading is done, transition
    // if (game.getAssetManager().update()) {
    // transitionScreen();
    // }
  }

  @Override
  public void show() {
    super.show();

    // Load all assets.
    game.getAssetManager().load(Assets.class);

    // TODO: Progress Bar
  }

  public void transitionScreen() {
    if (game.getGamePreferences().isSkipIntro()) {
      game.setScreen(new MainMenuScreen());
    } else {
      final SplashScreen trailer = new SplashScreen(2, 2, new MainMenuScreen(),
          Assets.TRAILER_SPLASH1);
      final SplashScreen company = new SplashScreen(2, 2, trailer, Assets.COMPANY_SPLASH1);
      game.setScreen(company);
    }
  }

}
