package com.mygdx.game.workinprogress;

import java.util.List;

import com.mygdx.game.entity.character.AbstractCharacter;
import com.mygdx.game.entity.character.AttackType;
import com.mygdx.game.entity.character.HeroTargetZone;
import com.mygdx.game.entity.character.PlayerCharacter;
import com.mygdx.game.entity.weapon.ArmingSword;
import com.mygdx.game.entity.weapon.ShortSword;

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
    PlayerCharacter hero = new PlayerCharacter();
    hero.setCurrentWeapon(new ArmingSword());
    PlayerCharacter hero2 = new PlayerCharacter();
    hero2.setCurrentWeapon(new ShortSword());
    hero.readyForCombat();
    hero2.readyForCombat();
    hero.attack(hero2, HeroTargetZone.HEAD, AttackType.SWING, 8);
    hero2.attack(hero, HeroTargetZone.CHEST, AttackType.THRUST, 8);
    System.out.println("Hero pain: " + hero.getPain() + "   ;    Hero2 pain: " + hero2.getPain());
  }
}
