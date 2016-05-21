package com.mygdx.game.accessor;

/**
 * Represents an abstract form of an accessor, which is used by the tween manager to access and
 * manipulate its concrete forms.
 * 
 * @author Bebop
 * @version 0.0.3.0
 */
public abstract class AbstractAccessor {

  /**
   * The error message to be displayed when a particular tween type does not exist.
   */
  public static final String ERROR_MESSAGE_TWEEN_TYPE = "Unknown tweenType.";

  /**
   * Represents an enumeration of the tween type corresponding to the alpha value.
   */
  public static final int ALPHA = 0;

  /**
   * Represents an enumeration of the tween type corresponding to the red, green, and blue values.
   */
  public static final int RGB = 1;

  /**
   * Represents an enumeration of the tween type corresponding to the Y value.
   */
  public static final int Y = 2;

}
