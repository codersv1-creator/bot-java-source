package ru.catssoftware.fakes.objects.sets;

import ru.catssoftware.fakes.objects.equip.ArmorObject;

public class ArmorSet {
   private ArmorObject helm;
   private ArmorObject chest;
   private ArmorObject gaiter;
   private ArmorObject gloves;
   private ArmorObject boots;
   private String name;

   public ArmorSet(String name, ArmorObject[] arms) {
      this.helm = arms[0];
      this.chest = arms[1];
      this.gaiter = arms[2];
      this.gloves = arms[3];
      this.boots = arms[4];
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public ArmorObject getHelm() {
      return this.helm;
   }

   public ArmorObject getChest() {
      return this.chest;
   }

   public ArmorObject getGaiter() {
      return this.gaiter;
   }

   public ArmorObject getGloves() {
      return this.gloves;
   }

   public ArmorObject getBoots() {
      return this.boots;
   }
}
