package ru.catssoftware.fakes.objects.sets;

import ru.catssoftware.fakes.objects.equip.ArmorObject;

public class UnderwearSet {
   private String name;
   private ArmorObject shirt;
   private ArmorObject belt;

   public UnderwearSet(String name, ArmorObject[] arms) {
      this.name = name;
      this.shirt = arms[0];
      this.belt = arms[1];
   }

   public String getName() {
      return this.name;
   }

   public ArmorObject getShirt() {
      return this.shirt;
   }

   public ArmorObject getBelt() {
      return this.belt;
   }
}
