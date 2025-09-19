package ru.catssoftware.fakes.ai.tasks.battle;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomAITask;
import ru.catssoftware.fakes.abstracts.PhantomDefaultPartyAI;
import ru.catssoftware.fakes.ai.tasks.other.ChatTask;
import ru.catssoftware.fakes.ai.tasks.other.ResurrectTask;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class SupportBardTask extends PhantomAITask {
   public SupportBardTask(L2PcInstance ph) {
      super(ph);
   }

   public void runImpl() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(SupportBardTask$BattleMageTask.y("䯃\ue71d⫸ᮅ叝饚\ueacd䤎ੜ\u0a54\ueea0\uec38썿瘚爧똤俎戸쁌糣ڻ腫䲥뿃탋"));
      }

      try {
         if (this.phantom.isOutOfControl() || this.phantom.isMovementDisabled()) {
            return;
         }

         if (this.phantom.isDead()) {
            if (!this.phantom.phantom_params.isResurrecting()) {
               this.phantom.phantom_params.setIsResurrecting(true);
               int delay = Rnd.get(500, 3500);
               if (delay > 1000 && Rnd.get(100) < 25) {
                  ThreadPoolManager.getInstance().scheduleAi(new ChatTask(this.phantom, Rnd.chance(80) ? 1 : 0, 2), (long)delay, true);
               }

               ThreadPoolManager.getInstance().scheduleAi(new ResurrectTask(this.phantom), (long)delay, true);
            }

            return;
         }

         this.phantom.phantom_params.getPhantomAI().doBuffCast();
         PhantomDefaultPartyAI party_ai = this.phantom.phantom_params.getPhantomPartyAI();
         if (party_ai != null && party_ai.getMoving() == 1) {
            return;
         }

         this.doAction();
         if (this.returnToAllowedLocation(this.getComebackDistanceL())) {
            this.phantom.phantom_params.getPhantomAI().removeTarg();
            return;
         }

         int min_distance = this.phantom.phantom_params.getPhantomPartyAI() == null ? 200 : 100;
         int max_distance = this.phantom.phantom_params.getPhantomPartyAI() == null ? 400 : 250;
         if (!this.phantom.isCastingNow() && !this.phantom.isMoving()) {
            this.phantom.setRunning();
            this.randomMove(min_distance, max_distance);
         }
      } catch (Exception var4) {
         if (Config.DEBAG_LVL > 0) {
            var4.printStackTrace();
         }
      }

   }

   public boolean doAction() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(SupportBardTask$BattleMageTask.y("봻䤨⸶\uf7a8\ue794Ꚃ蝪묠\ue195\ue5b4ꄅ嵐獶ᨋᙕ飕▴虾\uf4d5矢⋫봒佌흔\uf39eﰗ"));
      }

      if (super.doAction()) {
         return true;
      } else if (!this.phantom.phantom_params.isLockedTargetHere()) {
         return false;
      } else {
         this.phantom.phantom_params.getPhantomAI().doBuffCast();
         L2Character target = this.phantom.phantom_params.getLockedTarget();
         if (target == null) {
            return false;
         } else {
            this.phantom.phantom_params.getPhantomAI().doCast();
            if (!this.phantom.isCastingNow()) {
               this.phantom.getAIAccessor().doAttack(target);
            }

            return true;
         }
      }
   }
}
