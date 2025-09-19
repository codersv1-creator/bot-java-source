package ru.catssoftware.fakes.objects.equip;

public class AccessoryObject {
   private int _id;
   private int _chance;

   public AccessoryObject(int id, int chance) {
      this._id = id;
      this._chance = chance;
   }

   public int getId() {
      return this._id;
   }

   public int getChance() {
      return this._chance;
   }
}
