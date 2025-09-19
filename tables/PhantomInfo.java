package ru.catssoftware.fakes.tables;

public class PhantomInfo {
   public String name;
   public String title;
   public int x;
   public int y;
   public int z;
   public int clanId;
   public int charId;
   public int sex;
   public int race;

   public PhantomInfo(String name, String title, int x, int y, int z, int clanId, int charId, int sex, int race) {
      this.name = name;
      this.title = title;
      this.x = x;
      this.y = y;
      this.z = z;
      this.clanId = clanId;
      this.charId = charId;
      this.sex = sex;
      this.race = race;
   }
}
