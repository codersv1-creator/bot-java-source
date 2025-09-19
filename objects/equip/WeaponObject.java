package ru.catssoftware.fakes.objects.equip;

import ru.catssoftware.gameserver.skills.Stats;
import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.StatsSet;

public class WeaponObject {
   private int _id;
   private int _enchant;
   private boolean _is_random;
   private int _augment_chance;
   private int _attribute_min;
   private int _attribute_max;
   private boolean _is_att_random;
   private String _attribute;
   private int class_id;

   public WeaponObject(StatsSet set, int classId) {
      this._id = set.getInteger(WeaponObject$ShieldObject.p("ጒẩ"), 0);
      this._enchant = set.getInteger(WeaponObject$ShieldObject.p("ጞảը\ue0e4뗿堼퍥"), 0);
      this._is_random = set.getBool(WeaponObject$ShieldObject.p("ጒẾՔ\ue0fe뗿堼퍵吩鹢"), false);
      this._augment_chance = set.getInteger(WeaponObject$ShieldObject.p("ጚẸլ\ue0e1뗻堼퍥吙鹬靌谣咦ଦ턺"), 0);
      this._attribute_min = set.getInteger(WeaponObject$ShieldObject.p("ጚẹտ\ue0fe뗷堰퍤吲鹪靻谯咡ଫ"), 0);
      this._attribute_max = set.getInteger(WeaponObject$ShieldObject.p("ጚẹտ\ue0fe뗷堰퍤吲鹪靻谯咩ଽ"), 0);
      this._is_att_random = set.getBool(WeaponObject$ShieldObject.p("ጒẾՔ\ue0ed뗪堦퍎吴鹮靊谦咧ନ"), false);
      this._attribute = set.getString(WeaponObject$ShieldObject.p("ጚẹտ\ue0fe뗷堰퍤吲鹪"), WeaponObject$ShieldObject.p("ጕẢե\ue0e9"));
      this.class_id = classId;
   }

   public int getClassId() {
      return this.class_id;
   }

   public int getId() {
      return this._id;
   }

   public Stats getElement() {
      if (this._attribute.equalsIgnoreCase(WeaponObject$ShieldObject.p("◺꽍ᬲ륿"))) {
         return Stats.HOLY;
      } else if (this._attribute.equalsIgnoreCase(WeaponObject$ShieldObject.p("◧꽌ᬶ륩\u0edb疲"))) {
         return Stats.UNHOLY;
      } else if (this._attribute.equalsIgnoreCase(WeaponObject$ShieldObject.p("◥꽋ᬰ륢"))) {
         return Stats.WIND;
      } else if (this._attribute.equalsIgnoreCase(WeaponObject$ShieldObject.p("◷꽃ᬬ륲ໟ"))) {
         return Stats.EARTH;
      } else if (this._attribute.equalsIgnoreCase(WeaponObject$ShieldObject.p("◥꽃ᬪ륣\u0ec5"))) {
         return Stats.WATER;
      } else {
         return this._attribute.equalsIgnoreCase(WeaponObject$ShieldObject.p("◴꽋ᬬ륣")) ? Stats.FIRE : Stats.NONE_WPN_VULN;
      }
   }

   public int getEnchant() {
      return this._is_random ? Rnd.get(0, this._enchant) : this._enchant;
   }

   public boolean getAugmentSuccess() {
      return Rnd.chance(this._augment_chance);
   }

   public int getAttributeEnchant() {
      return this._is_att_random ? Rnd.get(this._attribute_min, this._attribute_max) : this._attribute_max;
   }
}
