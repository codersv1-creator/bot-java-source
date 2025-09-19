package ru.catssoftware.fakes.objects.equip;

import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.StatsSet;

public class ShieldObject {
   private int _id;
   private int _item_id;
   private int _enchant;
   private boolean _is_random;

   public ShieldObject(StatsSet set) {
      this._id = set.getInteger(WeaponObject$ShieldObject.p("ឹ\ue1e0"), 0);
      this._item_id = set.getInteger(WeaponObject$ShieldObject.p("ឹ\ue1f0詗\ue376跽䇓퇆"), 0);
      this._enchant = set.getInteger(WeaponObject$ShieldObject.p("឵\ue1ea詑\ue373跃䇔퇖"), 0);
      this._is_random = set.getBool(WeaponObject$ShieldObject.p("ឹ\ue1f7詭\ue369跃䇔퇆兂벐"), false);
   }

   public int getId() {
      return this._id;
   }

   public int getItemId() {
      return this._item_id;
   }

   public int getEnchant() {
      return this._is_random ? Rnd.get(this._enchant + 1) : this._enchant;
   }
}
