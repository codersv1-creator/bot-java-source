package ru.catssoftware.fakes.ai.individualbehavior.mages.summoners;

import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.battle.BattleMageTask;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class ElementalMaster extends PhantomDefaultAI {
   public void doCast() {
      L2PcInstance phantom = this.getActor();
      if (!this.castSummonSkill()) {
         if (Rnd.chance(60)) {
            phantom.setTarget(phantom.phantom_params.getLockedTarget());
            phantom.getCharacter().doCast(phantom.getSkillById(1175));
         } else if (Rnd.chance(40)) {
            phantom.setTarget(phantom.phantom_params.getLockedTarget());
            phantom.getCharacter().doCast(phantom.getSkillById(1206));
         } else if (Rnd.chance(20)) {
            phantom.setTarget(phantom.phantom_params.getLockedTarget());
            phantom.getCharacter().doCast(phantom.getSkillById(1558));
         }

         if (phantom.getAllEffectsList() == null || phantom.getAllEffectsList().get(1547) == null) {
            phantom.setTarget(phantom);
            phantom.getCharacter().doCast(phantom.getSkillById(1547));
         }

      }
   }

   public void startAITask(long delay) {
      this.startAITask(new BattleMageTask(this.getActor()), delay);
   }

   public boolean isNuker() {
      return true;
   }
}
