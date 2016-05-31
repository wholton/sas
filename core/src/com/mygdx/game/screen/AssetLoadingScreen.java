package com.mygdx.game.screen;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.util.Assets;

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
          Assets.TRAILER_SPLASH);
      final SplashScreen company = new SplashScreen(2, 2, trailer, Assets.COMPANY_SPLASH);
      game.setScreen(company);
    }
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
