package ru.catssoftware.fakes.ai.individualbehavior.mages.healers;

import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.battle.HealerMageTask;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.L2Skill;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.skills.Stats;
import ru.catssoftware.tools.random.Rnd;

public class Cardinal extends PhantomDefaultAI {
   public void doCast() {
      L2PcInstance phantom = this.getActor();
      if (!this.castUltimateBuffSkill(phantom, 50)) {
         int skill_chance = 80;
         L2Character target = phantom.phantom_params.getLockedTarget();
         if (target != null) {
            if (phantom.getCurrentHp() < 80.0D) {
               target = phantom;
               skill_chance += 20;
            }

            boolean isHealReduced;
            if (((L2Character)target).calcStat(Stats.HEAL_EFFECTIVNESS, 100.0D, (L2Character)target, (L2Skill)null) / 100.0D >= 1.0D) {
               isHealReduced = false;
            } else {
               isHealReduced = true;
            }

            if (Rnd.chance(skill_chance) && this.castHealSkill((L2Character)target, 80, isHealReduced)) {
               return;
            }
         }

         L2Character target = phantom.phantom_params.getSubTarget();
         if (target != null) {
            if (Rnd.chance(skill_chance) && this.castDebuffSkill(target)) {
               return;
            }

            if (this.castRareDebuffSkill(target)) {
               return;
            }
         }

      }
   }

   public void startAITask(long delay) {
      this.startAITask(new HealerMageTask(this.getActor()), delay);
   }

   public boolean isHealer() {
      return true;
   }
}
