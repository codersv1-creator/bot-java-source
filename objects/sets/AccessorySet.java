package ru.catssoftware.fakes.objects.sets;

import ru.catssoftware.fakes.objects.equip.AccessoryObject;
import ru.catssoftware.tools.random.Rnd;

public class AccessorySet {
   private int id;
   private String set_assign;
   private AccessoryObject male_hat;
   private AccessoryObject female_hat;
   private AccessoryObject cloak;

   public AccessorySet(int id, String set_assign, AccessoryObject[] accs) {
      this.id = id;
      this.set_assign = set_assign;
      this.male_hat = accs[0];
      this.female_hat = accs[1];
      this.cloak = accs[2];
   }

   public int getId() {
      return this.id;
   }

   public String getSetAssign() {
      return this.set_assign;
   }

   public AccessoryObject getAccessory(boolean isMale) {
      AccessoryObject acc = isMale ? this.male_hat : this.female_hat;
      if (acc == null) {
         return null;
      } else {
         return Rnd.chance(acc.getChance()) ? acc : null;
      }
   }

   public AccessoryObject getCloak() {
      return this.cloak == null ? null : (Rnd.chance(this.cloak.getChance()) ? this.cloak : null);
   }
}
