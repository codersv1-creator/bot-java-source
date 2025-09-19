package ru.catssoftware.fakes.objects.sets;

import ru.catssoftware.fakes.objects.equip.ArmorObject;

public class JewelSet {
   private ArmorObject earring_L;
   private ArmorObject earring_R;
   private ArmorObject ring_L;
   private ArmorObject ring_R;
   private ArmorObject necklace;
   private String name;

   public JewelSet(String name, ArmorObject[] jewels) {
      this.earring_L = jewels[0];
      this.earring_R = jewels[1];
      this.ring_L = jewels[2];
      this.ring_R = jewels[3];
      this.necklace = jewels[4];
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public ArmorObject getEarringL() {
      return this.earring_L;
   }

   public ArmorObject getEarringR() {
      return this.earring_R;
   }

   public ArmorObject getRingL() {
      return this.ring_L;
   }

   public ArmorObject getRingR() {
      return this.ring_R;
   }

   public ArmorObject getNecklace() {
      return this.necklace;
   }
}
