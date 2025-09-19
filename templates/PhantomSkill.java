package ru.catssoftware.fakes.templates;

import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.gameserver.datatables.SkillTable;
import ru.catssoftware.gameserver.model.L2Skill;
import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.StatsSet;

public class PhantomSkill {
   private static final Logger _log = Logger.getLogger(PhantomSkill.class);
   private int id;
   private int level;
   private boolean is_enchantable;
   private boolean is_random;
   private int ench_max_value;
   private int ench_route;
   private String cond_type;
   private String target;
   private int first_cond;
   private int second_cond;

   public PhantomSkill(StatsSet set) {
      this.id = set.getInteger(ob.Q("\ue8c3耓"));
      this.level = set.getInteger(ob.Q("\ue8c6耒砊\udba0ᜆ"));
      this.is_enchantable = set.getBool(ob.Q("\ue8c3耄砣\udba0ᜄ馚줎哺쵧씃\ufdd8啝뭩\ue0ed"), false);
      this.is_random = set.getBool(ob.Q("\ue8c3耄砣\udbb7ᜋ馗줂哴쵤"), false);
      this.ench_max_value = set.getInteger(ob.Q("\ue8cf耙砟\udbad᜵馔줇哣쵖씁\ufdd8啓뭰\ue0ed"));
      this.ench_route = set.getInteger(ob.Q("\ue8cf耙砟\udbad᜵馋줉哮쵽씒"));
      if (set.getString(ob.Q("\ue8c9耘砒\udba1᜵馍줟哫쵬")) != null) {
         this.cond_type = set.getString(ob.Q("\ue8c9耘砒\udba1᜵馍줟哫쵬"));
         this.target = set.getString(ob.Q("\ue8de耖砎\udba2ᜏ馍"));
         this.first_cond = set.getInteger(ob.Q("\ue8c9耘砒\udba1᜵馟줏哩쵺씃"));
         this.second_cond = set.getInteger(ob.Q("\ue8c9耘砒\udba1᜵馊줃哸쵦씙\ufddd"));
      }

   }

   public String getCondType() {
      return this.cond_type;
   }

   public String getTargetType() {
      return this.target;
   }

   public int getFirstCond() {
      return this.first_cond;
   }

   public int getSecondCond() {
      return this.second_cond;
   }

   public L2Skill getSkill() {
      if (this.id == -1) {
         return null;
      } else {
         int skill_lv = this.level;
         L2Skill skill = SkillTable.getInstance().getInfo(this.id, 1);

         try {
            int ench_count = skill.getLevel();
            if (this.is_enchantable && ench_count == 30) {
               if (this.is_random) {
                  skill_lv = skill_lv + (this.ench_route - 1) * 30 + Rnd.get(this.ench_max_value);
               } else {
                  skill_lv = skill_lv + (this.ench_route - 1) * 30 + this.ench_max_value;
               }
            } else if (this.is_enchantable && ench_count == 15) {
               if (this.is_random) {
                  skill_lv = skill_lv + (this.ench_route - 1) * 15 + Rnd.get(this.ench_max_value);
               } else {
                  skill_lv = skill_lv + (this.ench_route - 1) * 15 + this.ench_max_value;
               }
            }

            skill = SkillTable.getInstance().getInfo(this.id, skill_lv);
            skill.getName();
         } catch (NullPointerException var4) {
            if (Config.DEBAG_LVL == -1) {
               _log.error(ob.Q("톪～䙟㓦즘\ued0a鿎䏡᮱嵌\ue27d盁䊎丑禀끗\ue48e鉶") + SkillTable.getInstance().getInfo(this.id, 1) + ob.Q("톆ｈ䘗㒩") + this.id + ob.Q("퇃，䙁㓿즵\ued1b龇䎪") + skill_lv + ob.Q("퇃，䙁㓿즵\ued18龇䎪") + this.level);
            }

            if (Config.DEBAG_LVL > 0) {
               _log.error(ob.Q("톪～䙟㓦즘\ued0a鿎䏡᮱嵌\ue27d盁䊉七禲끗\ue4da惘\ueeb5▼徖喠\ue7b3咼Ꭱ疁많멸") + this.is_enchantable + ob.Q("퇃，䙄㓺즵\ued58鿜䏤ᮼ嵏\ue27c盛䋀") + this.is_random);
            }
         }

         return skill;
      }
   }

   public int getId() {
      return this.id;
   }
}
