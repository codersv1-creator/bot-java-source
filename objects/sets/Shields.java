package ru.catssoftware.fakes.objects.sets;

import java.util.Iterator;
import java.util.LinkedList;
import ru.catssoftware.fakes.objects.equip.ShieldObject;

public class Shields {
   private LinkedList<ShieldObject> shields;

   public Shields(LinkedList<ShieldObject> arms) {
      this.shields = arms;
   }

   public ShieldObject getShield(int id) {
      Iterator var3 = this.shields.iterator();

      while(var3.hasNext()) {
         ShieldObject shield = (ShieldObject)var3.next();
         if (shield.getId() == id) {
            return shield;
         }
      }

      return null;
   }
}
