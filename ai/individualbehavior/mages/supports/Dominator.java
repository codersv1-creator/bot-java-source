package ru.catssoftware.fakes.ai.individualbehavior.mages.supports;

import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.battle.SupportTask;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class Dominator extends PhantomDefaultAI {
   public void doCast() {
      L2PcInstance phantom = this.getActor();
      L2Character target = phantom.phantom_params.getLockedTarget();
      if (!this.castSituationSkill(target)) {
         if (!this.castBuffSkillOnPartyMember()) {
            if (!this.castUltimateBuffSkill(phantom, 50)) {
               int skill_chance = 30;
               if (!(phantom.getCurrentHp() < 70.0D) || !this.castHealSkill(phantom, 70, true)) {
                  if (!Rnd.chance(skill_chance) || !this.castHealSkill(target, 70, true)) {
                     target = phantom.phantom_params.getSubTarget();
                     int skill_chance = skill_chance + 20;
                     if (!Rnd.chance(skill_chance) || !this.castRareDebuffSkill(target)) {
                        skill_chance += 20;
                        if (!this.castDebuffSkill(target)) {
                           this.castRareNukeSkill(target);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void startAITask(long delay) {
      this.startAITask(new SupportTask(this.getActor()), delay);
   }

   public boolean isSupport() {
      return true;
   }

   public boolean isDisabler() {
      return true;
   }
}
