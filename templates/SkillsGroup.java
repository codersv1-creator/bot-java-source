package ru.catssoftware.fakes.templates;

import org.apache.log4j.Logger;
import ru.catssoftware.gameserver.model.L2Skill;
import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.StatsSet;

public class SkillsGroup {
   private static final Logger _log = Logger.getLogger(SkillsGroup.class);
   private PhantomSkill[] skills = new PhantomSkill[0];
   private int type = 0;

   public void setType(int t) {
      this.type = t;
   }

   public int getType() {
      return this.type;
   }

   public void addSkill(PhantomSkill skill) {
      PhantomSkill[] temp = new PhantomSkill[this.skills.length + 1];

      for(int i = 0; i < this.skills.length; ++i) {
         temp[i] = this.skills[i];
      }

      temp[temp.length - 1] = skill;
      this.skills = temp;
   }

   public boolean isContains(L2Skill skill) {
      if (this.skills.length == 0) {
         return false;
      } else {
         PhantomSkill[] var5;
         int var4 = (var5 = this.skills).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            PhantomSkill sk = var5[var3];
            if (sk.getId() == skill.getId()) {
               return true;
            }
         }

         return false;
      }
   }

   public L2Skill getSkillByIndex(int index) {
      if (this.skills.length == 0) {
         return null;
      } else {
         return index < this.skills.length && index >= 0 ? this.skills[index].getSkill() : null;
      }
   }

   public L2Skill getSkillById(int id) {
      if (this.skills.length == 0) {
         _log.warn(ob.Q("ꁛ\ue74a츭⭙撫捾\ud82a\uf170埶郋燅\uf8e2儈埅嘍羰앰"));
         return null;
      } else {
         PhantomSkill[] var5;
         int var4 = (var5 = this.skills).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            PhantomSkill sk = var5[var3];
            if (sk.getId() == id) {
               return sk.getSkill();
            }
         }

         return null;
      }
   }

   public PhantomSkill getRandomSkill() {
      if (this.skills.length == 0) {
         StatsSet set = new StatsSet();
         set.set(ob.Q("靹㿵"), -1);
         set.set(ob.Q("靼㿴턲䶞ꙭ"), -1);
         set.set(ob.Q("靹㿢턛䶞꙯擨駏┳\ue459Ꭶ芺ⶼ侉俧"), false);
         set.set(ob.Q("靹㿢턛䶉Ꙡ擥駃┽\ue45a"), false);
         set.set(ob.Q("靵㿿턧䶓Ꙟ擦駆┪\ue468Ꭴ芺ⶲ侐俧"), 0);
         set.set(ob.Q("靵㿿턧䶓Ꙟ擹駈┧\ue443Ꮇ"), -1);
         set.set(ob.Q("靳㿾턪䶟Ꙟ擿駞┢\ue452"), ob.Q("靾㿾턪䶞"));
         set.set(ob.Q("靤㿰턶䶜Ꙥ擿"), ob.Q("靾㿾턪䶞"));
         set.set(ob.Q("靳㿾턪䶟Ꙟ擭駎┠\ue444Ꭶ"), -1);
         set.set(ob.Q("靳㿾턪䶟Ꙟ擸駂┱\ue458Ꮌ芿"), -1);
         return new PhantomSkill(set);
      } else {
         return this.skills[Rnd.get(this.skills.length)];
      }
   }

   public PhantomSkill[] getAllSkills() {
      return this.skills;
   }
}
