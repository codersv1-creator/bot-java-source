package ru.catssoftware.fakes.ai.tasks.battle;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomAITask;
import ru.catssoftware.fakes.abstracts.PhantomDefaultPartyAI;
import ru.catssoftware.fakes.ai.tasks.other.ChatTask;
import ru.catssoftware.fakes.ai.tasks.other.ResurrectTask;
import ru.catssoftware.fakes.utils.PhantomUtil;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2NpcInstance;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class BattleTask extends PhantomAITask {
   public BattleTask(L2PcInstance ph) {
      super(ph);
   }

   public void runImpl() {
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

         boolean isPlayer = true;
         if ((this.phantom.phantom_params.getAllBattleCoordinates() == null || this.phantom.phantom_params.getAllBattleCoordinates().isEmpty()) && this.phantom.phantom_params.getAllFarmCoordinates() != null) {
            isPlayer = false;
         } else if (this.phantom.phantom_params.getAllBattleCoordinates() == null || this.phantom.phantom_params.getAllFarmCoordinates() != null && !this.phantom.phantom_params.getAllFarmCoordinates().isEmpty()) {
            if (this.phantom.phantom_params.getAllBattleCoordinates() != null && this.phantom.phantom_params.getAllFarmCoordinates() != null) {
               isPlayer = Rnd.get(1, 2) == 1;
            }
         } else {
            isPlayer = true;
         }

         this.phantom.phantom_params.getPhantomAI().doBuffCast();
         PhantomDefaultPartyAI party_ai = this.phantom.phantom_params.getPhantomPartyAI();
         if (party_ai != null && party_ai.getMoving() == 1) {
            return;
         }

         boolean is_need_to_get_new_target = this.phantom.phantom_params.getPhantomAI().isNeedToGetNewTarget();
         if ((is_need_to_get_new_target || this.returnToAllowedLocation(this.getComebackDistanceL() + 800)) && !this.phantom.isInsideZone((byte)1)) {
            if (this.returnToAllowedLocation(this.getComebackDistanceL())) {
               this.phantom.phantom_params.getPhantomAI().removeTarg();
               return;
            }

            int min_distance;
            for(min_distance = 300; min_distance < this.getComebackDistanceL() && !PhantomUtil.getAndSetLockedTarget(this.phantom, min_distance, isPlayer); min_distance += 300) {
            }

            min_distance = this.phantom.phantom_params.getPhantomPartyAI() == null ? 300 : 150;
            int max_distance = this.phantom.phantom_params.getPhantomPartyAI() == null ? 600 : 300;
            L2Character target = this.phantom.phantom_params.getLockedTarget();
            if ((target != null && Rnd.chance(10) && target.isInRange((L2PcInstance)this.phantom, 600) || target == null) && !this.phantom.isCastingNow() && !this.phantom.isMoving()) {
               this.phantom.setRunning();
               this.randomMove(min_distance, max_distance);
            }
         } else {
            this.doAction();
            this.fallBack(400);
         }
      } catch (Exception var7) {
         if (Config.DEBAG_LVL > 0) {
            var7.printStackTrace();
         }
      }

   }

   public boolean doAction() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(SupportBardTask$BattleMageTask.y("\uf760ᇤ싈揪\uea42"));
      }

      if (super.doAction()) {
         return true;
      } else if (!this.phantom.phantom_params.isLockedTargetHere()) {
         return false;
      } else {
         L2Character target = this.phantom.phantom_params.getLockedTarget();
         if (target == null) {
            return false;
         } else {
            if (Config.DEBAG_LVL > 0) {
               System.out.println(SupportBardTask$BattleMageTask.y("\uf763ᇮ식揊\uea59礗䎟䦎돝ꆍᓄ蒉開趙\ue6d5턀ᜡ䄶虹䉮뾰籗뵫鏲ᅑ"));
            }

            if (!this.phantom.isInRange(target, 900) || !(target instanceof L2NpcInstance) && (!target.isPlayer() || target.getActingPlayer().getAppearance().isInvisible())) {
               if (Config.DEBAG_LVL > 0) {
                  System.out.println(SupportBardTask$BattleMageTask.y("\uf763ᇮ식揊\uea59礗䎟䦎돝ꆍᓄ蒉開趙\ue6d5턀ᜡ䄶虹䉮뾰籗뵩鏲ᅑꋊ툋ꖈ\ue441쉇\ue377Ⳅ࠭ﷆꥄË尽䠭") + target.getName());
               }

               this.phantom.getAI().startFollow(target, Rnd.get(200, 400));
            } else if (!this.phantom.isCastingNow() && Rnd.chance(80) && !this.phantom.isMoving()) {
               if (Config.DEBAG_LVL > 0) {
                  System.out.println(SupportBardTask$BattleMageTask.y("\uf763ᇮ식揊\uea59礗䎟䦎돝ꆍᓄ蒉開趙\ue6d5턀ᜡ䄶虹䉮뾰籗뵪鏲ᅑꋝ툐ꖪ\ue452쉀\ue345ⲃ\u0868ﶊ") + target.getName());
               }

               this.phantom.stopMove();
               this.phantom.phantom_params.getPhantomAI().doCast();
            } else if (!this.phantom.isCastingNow() && !this.phantom.isMoving()) {
               this.phantom.setRunning();
               this.randomMove(10, 150);
            }

            if (Config.DEBAG_LVL > 0) {
               System.out.println(SupportBardTask$BattleMageTask.y("\uf763ᇮ식揊\uea59礗䎟䦎돝ꆍᓄ蒉開趙\ue6d5턀ᜡ䄶虹䉮뾰籗뵨鏲ᅑ"));
            }

            return true;
         }
      }
   }
}
