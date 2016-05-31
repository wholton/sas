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
  
  private Random random;
  
  public DiceRoller() {
    random = new Random(System.nanoTime());
  }

  public int roll(DieSize dieSize) {
    return random.nextInt(dieSize.getValue()) + 1;
  }

  public List<Integer> roll(DieSize dieSize, int numberOfRolls) {
    final List<Integer> results = new ArrayList<Integer>(numberOfRolls);
    for (int i = 0; i < numberOfRolls; i++) {
      results.add(roll(dieSize));
    }
    return results;
  }

}
