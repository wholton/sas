package com.mygdx.game.entity.characters;

import com.mygdx.game.entity.armor.LeatherHelmet;
import com.mygdx.game.entity.characters.attributes.BrawnAttribute;
import com.mygdx.game.entity.characters.attributes.CunningAttribute;
import com.mygdx.game.entity.characters.attributes.DaringAttribute;
import com.mygdx.game.entity.characters.attributes.HeartAttribute;
import com.mygdx.game.entity.characters.attributes.SagacityAttribute;
import com.mygdx.game.entity.characters.attributes.TenacityAttribute;
import com.mygdx.game.entity.characters.cultures.CivilizedCulture;
import com.mygdx.game.entity.characters.proficiencies.SwordAndShieldProficiency;

public class Hero extends AbstractCharacter {

  // make builder when ready
  public Hero() {
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
