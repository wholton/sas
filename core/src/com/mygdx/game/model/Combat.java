package com.mygdx.game.model;

import java.util.List;

import com.mygdx.game.model.characters.AbstractCharacter;
import com.mygdx.game.model.characters.AttackType;
import com.mygdx.game.model.characters.Hero;
import com.mygdx.game.model.characters.HeroTargetZone;
import com.mygdx.game.model.weapons.ArmingSword;
import com.mygdx.game.model.weapons.ShortSword;

public class Combat {

  private final List<AbstractCharacter> playerCharacters;
  private final List<AbstractCharacter> allyCharacters;
  private final List<AbstractCharacter> enemyCharacters;

  public Combat(List<AbstractCharacter> playerCharacters, List<AbstractCharacter> allyCharacters,
      List<AbstractCharacter> enemyCharacters) {
    super();
    this.playerCharacters = playerCharacters;
    this.allyCharacters = allyCharacters;
    this.enemyCharacters = enemyCharacters;
  }

  public static void main(String[] args) {
    Hero hero = new Hero();
    hero.setCurrentWeapon(new ArmingSword());
    Hero hero2 = new Hero();
    hero2.setCurrentWeapon(new ShortSword());
    hero.readyForCombat();
    hero2.readyForCombat();
    hero.attack(hero2, HeroTargetZone.HEAD, AttackType.SWING, 8);
    hero2.attack(hero, HeroTargetZone.CHEST, AttackType.THRUST, 8);
    System.out.println("Hero pain: " + hero.getPain() + "   ;    Hero2 pain: " + hero2.getPain());
  }
}
