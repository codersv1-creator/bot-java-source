package ru.catssoftware.fakes.model;

public class RacesDictionary {
   private static final String[] allRaces = new String[]{RacesDictionary$PhantomLevelFarmParams.f("츐弴䂻꿭\ue1c9"), RacesDictionary$PhantomLevelFarmParams.f("츝弭䂰"), RacesDictionary$PhantomLevelFarmParams.f("츜张䂤꿧\ue187\ue14e\uec93⬺"), RacesDictionary$PhantomLevelFarmParams.f("츗弳䂵"), RacesDictionary$PhantomLevelFarmParams.f("츜弶䂷꿾\ue1c1")};

   public static String getNameById(int id) {
      return allRaces[id];
   }

   public static int getRaceByClassId(int class_id) {
      int race = 0;
      switch(class_id) {
      case 88:
      case 89:
      case 90:
      case 91:
      case 92:
      case 93:
      case 94:
      case 95:
      case 96:
      case 97:
      case 98:
         race = 0;
         break;
      case 99:
      case 100:
      case 101:
      case 102:
      case 103:
      case 104:
      case 105:
         race = 1;
         break;
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
         race = 2;
         break;
      case 113:
      case 114:
      case 115:
      case 116:
         race = 3;
         break;
      case 117:
      case 118:
         race = 4;
      }

      return race;
   }
}
