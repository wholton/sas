package com.mygdx.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;

public class XBox360ControllerCode {
  public static final int X_BUTTON_CODE = 2; 
  public static final int Y_BUTTON_CODE = 3; 
  public static final int A_BUTTON_CODE = 0; 
  public static final int B_BUTTON_CODE = 1; 
  public static final int BACK_BUTTON_CODE = 6;
  public static final int START_BUTTON_CODE = 7; 
  public static final int LB_BUTTON_CODE = 4;
  public static final int L3_BUTTON_CODE = 8;
  public static final int RB_BUTTON_CODE = 5;
  public static final int R3_BUTTON_CODE = 9;
  
  /** Value will be from -1 to 1 depending how far left/right. */
  public static final int LEFT_X_AXIS_CODE = 1;
  /** Value will be from -1 to 1 depending how far up/down. */
  public static final int LEFT_Y_AXIS_CODE = 0; 
  /** Value will be from 0 to 1 based on how hard the trigger is pressed. */
  public static final int LEFT_TRIGGER_AXIS_CODE = 4; 
  /** Value will be from -1 to 1 depending how far left/right. */
  public static final int RIGHT_X_AXIS_CODE = 3; 
  /** Value will be from -1 to 1 depending how far up/down. */
  public static final int RIGHT_Y_AXIS_CODE = 2;
  /** Value will be from 0 to 1 based on how hard the trigger is pressed. */
  public static final int RIGHT_TRIGGER_AXIS_CODE = 5; //TODO: ??
  
  /**
   * Checks whether the given controller is an xbox 360 controller.
   * @param controller  the controller we are checking
   * @return  whether the controller is an xbox 360 controller
   */
  public static boolean isXBox360Controller(Controller controller) {
    if (controller.getName().toLowerCase().contains("xbox") 
        && controller.getName().contains("360")) {
      return true;
    }
    return false;
  }
  
  /**
   * Returns the human recognizable string value for the given button code.
   * @param buttonCode  the button code to interpret.
   * @param axis  whether the button code is for the left or right axis.
   * @return  the human recognizable string value of the button code.
   */
  public static String toString(int buttonCode, boolean axis) {
    if (axis) {
      switch (buttonCode) {
        case XBox360ControllerCode.LEFT_X_AXIS_CODE: 
          return "LEFT X";
        case XBox360ControllerCode.LEFT_Y_AXIS_CODE: 
          return "LEFT Y";
        case XBox360ControllerCode.LEFT_TRIGGER_AXIS_CODE:
          return "LEFT TRIGGER";
        case XBox360ControllerCode.RIGHT_X_AXIS_CODE: 
          return "RIGHT X";
        case XBox360ControllerCode.RIGHT_Y_AXIS_CODE: 
          return "RIGHT Y";
        case XBox360ControllerCode.RIGHT_TRIGGER_AXIS_CODE:
          return "RIGHT TRIGGER";
        default:
      }
    } else {
      switch (buttonCode) {
        case XBox360ControllerCode.X_BUTTON_CODE:
          return "X";
        case XBox360ControllerCode.Y_BUTTON_CODE:
          return "Y";
        case XBox360ControllerCode.A_BUTTON_CODE:
          return "A";
        case XBox360ControllerCode.B_BUTTON_CODE:
          return "B";
        case XBox360ControllerCode.BACK_BUTTON_CODE:
          return "BACK";
        case XBox360ControllerCode.START_BUTTON_CODE:
          return "START";
        case XBox360ControllerCode.LB_BUTTON_CODE:
          return "LB";
        case XBox360ControllerCode.L3_BUTTON_CODE:
          return "L3";
        case XBox360ControllerCode.RB_BUTTON_CODE:
          return "RB";
        case XBox360ControllerCode.R3_BUTTON_CODE: 
          return "R3";
        default:
      }
    }
    return null;
  }
}
