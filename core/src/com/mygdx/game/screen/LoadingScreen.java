package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.SaSGame;
import com.mygdx.game.asset.AssetHelper;
import com.mygdx.game.data.GamePreferences;

public class LoadingScreen extends AbstractScreen {

  protected Stage stage;
  protected ProgressBar progressBar;
  protected Skin skin;
  protected static final String SKIN_PATH = "skin/uiskin.json";
  
  @Override
  public void render(float delta) {
    // if asset loading is done, transition
    if (AssetHelper.MANAGER.update()) {
      transitionScreen();
    }
    // else, progress
    //progressBar.setValue(AssetHelper.MANAGER.getProgress());
  }

  @Override
  public void show() {
    super.show();
    // Load all assets.
    AssetHelper.loadAll();
    // Setup skin
    skin = new Skin(Gdx.files.internal(SKIN_PATH));
    // Setup progress bar
    //progressBar = new ProgressBar(...);
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
