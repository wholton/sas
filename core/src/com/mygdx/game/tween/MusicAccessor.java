package com.mygdx.game.tween;

import com.badlogic.gdx.audio.Music;

import aurelienribon.tweenengine.TweenAccessor;

public class MusicAccessor extends AbstractAccessor implements TweenAccessor<Music> {

  /**
   * Represents an enumeration of the tween type corresponding to the volume value.
   */
  public static final int VOLUME = 0;
  
  /**
   * Gets the values of the target corresponding to the given tween type and places them within the
   * return values array then returns the number of values retrieved.
   * 
   * @param target
   *          the target of the tween which will supply the return values
   * @param tweenType
   *          the type of tween to be performed which will decide which values are retrieved
   * @param returnValues
   *          the set of values retrieved from the target decided by the tween type
   * 
   * @return the number of values placed into the return value array
   */
  @Override
  public int getValues(Music target, int tweenType, float[] returnValues) {
    switch (tweenType) {
      case VOLUME:
        returnValues[0] = target.getVolume();
        return 1;
      default:
        throw new RuntimeException(ERROR_MESSAGE_TWEEN_TYPE);
    }
  }

  /**
   * Sets the values of the target corresponding to the given tween type to the values within the
   * new values array.
   * 
   * @param target
   *          the target of the tween which will have its variables updated
   * @param tweenType
   *          the type of tween to be performed which will decide which values are updated
   * @param newValues
   *          the set of values to be updated decided by the tween type
   */
  @Override
  public void setValues(Music target, int tweenType, float[] newValues) {
    switch (tweenType) {
      case VOLUME:
        target.setVolume(newValues[0]);
        break;
      default:
        throw new RuntimeException(ERROR_MESSAGE_TWEEN_TYPE);
    }
  }
}
