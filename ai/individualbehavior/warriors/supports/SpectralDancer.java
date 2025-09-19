package ru.catssoftware.fakes.ai.individualbehavior.warriors.supports;

import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.battle.SupportTask;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class SpectralDancer extends PhantomDefaultAI {
   public void doCast() {
      L2PcInstance phantom = this.getActor();
      int skill_chance = 60;
      L2Character target = phantom.phantom_params.getLockedTarget();
      if (!this.castSituationSkill(target)) {
         if (!Rnd.chance(skill_chance) || !this.castRareDebuffSkill(target)) {
            int var4 = skill_chance + 30;
            if (!this.castDebuffSkill(target)) {
               ;
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
}
