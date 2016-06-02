package com.mygdx.game.workinprogress;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRoller {

  public enum DieSize {
    D6(6), D12(12);
    
    private int size;
    
    private DieSize(int size) { 
      this.size = size; 
    }
    
    public int getValue() { 
      return size; 
    }
  }
  
  private static final Random RANDOM = new Random(System.currentTimeMillis());
  
  public DiceRoller() {}

  public int roll(DieSize dieSize) {
    return RANDOM.nextInt(dieSize.getValue()) + 1;
  }

  public List<Integer> roll(DieSize dieSize, int numberOfRolls) {
    final List<Integer> results = new ArrayList<Integer>(numberOfRolls);
    for (int i = 0; i < numberOfRolls; i++) {
      results.add(roll(dieSize));
    }
    return results;
  }

}
