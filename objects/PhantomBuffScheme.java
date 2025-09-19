package ru.catssoftware.fakes.objects;

import java.util.ArrayList;

public class PhantomBuffScheme {
   private ArrayList<PhantomBuff> buff_list = new ArrayList();
   private String name;

   public PhantomBuffScheme(String name) {
      this.name = name;
      this.buff_list = new ArrayList();
   }

   public void addBuff(PhantomBuff buff) {
      this.buff_list.add(buff);
   }

   public ArrayList<PhantomBuff> getBuffList() {
      return this.buff_list;
   }

   public String getName() {
      return this.name;
   }
}
