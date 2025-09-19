package ru.catssoftware.fakes.tables;

public class PhantomHenna {
   public int henna_one;
   public int henna_two;
   public int henna_three;
   public int[] all_hennas;
   public int class_id;

   public PhantomHenna(int cl, int id1, int id2, int id3) {
      this.class_id = cl;
      this.henna_one = id1;
      this.henna_two = id2;
      this.henna_three = id3;
      this.all_hennas = new int[]{id1, id2, id3};
   }
}
