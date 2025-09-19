package ru.catssoftware.fakes.ai.individualbehavior.mages.supports;

import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.battle.SupportTask;
import ru.catssoftware.fakes.objects.PhantomPartyObject;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;

public class Doomcryer extends PhantomDefaultAI {
   public void doCast() {
      L2PcInstance phantom = this.getActor();
      L2Character target = phantom.phantom_params.getLockedTarget();
      if (!this.castSituationSkill(target)) {
         if (!this.castBuffSkillOnPartyMember()) {
            PhantomPartyObject party = phantom.phantom_params.getPhantomPartyAI();
            if (party != null) {
               L2PcInstance burning_chop_target = party.getAnyTank() != null ? party.getAnyTank() : (party.getAnySupport() != null ? party.getAnySupport() : (party.getAnyHealer() != null ? party.getAnyHealer() : party.getAnyNuker()));
               if (this.castRareNukeSkill(burning_chop_target)) {
                  return;
               }
            }

            if (!this.castUltimateBuffSkill(phantom, 50)) {
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
