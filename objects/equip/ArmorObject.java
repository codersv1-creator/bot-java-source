package ru.catssoftware.fakes.objects.equip;

import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.StatsSet;

public class ArmorObject extends ShieldObject {
   private int _attribute_min;
   private int _attribute_max;
   private boolean _is_att_random;

   public ArmorObject(StatsSet set) {
      super(set);
      this._attribute_min = set.getInteger(WeaponObject$ShieldObject.p("苫猻䪨싛銨⡍뜗鞰\uf8e5⋫\uf14aꢠ单"), 0);
      this._attribute_max = set.getInteger(WeaponObject$ShieldObject.p("苫猻䪨싛銨⡍뜗鞰\uf8e5⋫\uf14aꢨ千"), 0);
      this._is_att_random = set.getBool(WeaponObject$ShieldObject.p("苣猼䪃싈銵⡛뜽鞶\uf8e1⋚\uf143ꢦ卖"), false);
   }

   public int getAttributeEnchant() {
      return this._is_att_random ? Rnd.get(this._attribute_min, this._attribute_max) : this._attribute_max;
   }
}
