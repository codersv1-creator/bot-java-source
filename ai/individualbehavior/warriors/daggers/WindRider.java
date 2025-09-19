package ru.catssoftware.fakes.ai.individualbehavior.warriors.daggers;

import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.battle.BattleTask;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class WindRider extends PhantomDefaultAI {
   public void doCast() {
      L2PcInstance phantom = this.getActor();
      if (!this.castUltimateBuffSkill(phantom, 50)) {
         L2Character target = phantom.phantom_params.getLockedTarget();
         if (!this.castSituationSkill(target)) {
            int skill_chance = 10;
            if (!Rnd.chance(skill_chance) || !this.castRareDebuffSkill(target)) {
               int skill_chance = skill_chance + 10;
               if (!Rnd.chance(skill_chance) || !this.castDebuffSkill(target)) {
                  skill_chance += 5;
                  if (!Rnd.chance(skill_chance) || !this.castRareNukeSkill(target)) {
                     this.castNukeSkill(target);
                  }
               }
            }
         }
      }
   }

   public void startAITask(long delay) {
      this.startAITask(new BattleTask(this.getActor()), delay);
   }

   public boolean isNuker() {
      return true;
   }
}
