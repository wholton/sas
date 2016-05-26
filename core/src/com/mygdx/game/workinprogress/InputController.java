package com.mygdx.game.workinprogress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.SaSGame;
import com.mygdx.game.screen.MainMenuScreen;

public class InputController implements InputProcessor {

  @Override
  public boolean keyDown(int keycode) {
    switch(keycode) {
      case Keys.ESCAPE:
        ((SaSGame) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        break;
      case Keys.W:
        
        break;
      case Keys.A:
        break;
      case Keys.S:
        break;
      case Keys.D:
        break;
      default:
        break;
    }

    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    // TODO Auto-generated method stub
    return false;
  }

}
