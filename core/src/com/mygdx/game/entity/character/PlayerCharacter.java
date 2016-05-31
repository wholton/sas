package com.mygdx.game.entity.character;

import com.mygdx.game.entity.armor.LeatherHelmet;
import com.mygdx.game.entity.character.attribute.BrawnAttribute;
import com.mygdx.game.entity.character.attribute.CunningAttribute;
import com.mygdx.game.entity.character.attribute.DaringAttribute;
import com.mygdx.game.entity.character.attribute.HeartAttribute;
import com.mygdx.game.entity.character.attribute.SagacityAttribute;
import com.mygdx.game.entity.character.attribute.TenacityAttribute;
import com.mygdx.game.entity.character.culture.CivilizedCulture;
import com.mygdx.game.entity.character.proficiencies.SwordAndShieldProficiency;

public class PlayerCharacter extends AbstractCharacter {

  // make builder when ready
  public PlayerCharacter() {
    super("Delvin", "Male", new CivilizedCulture());
    brawnAttribute = new BrawnAttribute(8);
    heartAttribute = new HeartAttribute(4);
    sagacityAttribute = new SagacityAttribute(4);
    tenacityAttribute = new TenacityAttribute(8);
    cunningAttribute = new CunningAttribute(4);
    daringAttribute = new DaringAttribute(6);
    swordAndShieldProficiency = new SwordAndShieldProficiency(6);
    armors.put("Head", new LeatherHelmet());
  }

}
