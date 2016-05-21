package com.mygdx.game.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Roller {

  private static Random random;
  private static Roller instance;
  public static final int d12Sides = 12;
  public static final int d6Sides = 6;

  private Roller() {
    random = new Random(System.currentTimeMillis());
  }

  public static Roller getInstance() {
    if (instance == null) {
      instance = new Roller();
    }
    return instance;
  }

  public int rollD12() {
    return random.nextInt(d12Sides) + 1;
  }

  public List<Integer> rollD12(final int numberOfRolls) {
    final List<Integer> results = new ArrayList<Integer>(numberOfRolls);
    for (int i = 0; i < numberOfRolls; i++) {
      results.add(rollD12());
    }
    return results;
  }

}
