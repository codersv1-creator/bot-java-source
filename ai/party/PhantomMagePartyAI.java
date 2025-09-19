package ru.catssoftware.fakes.ai.party;

import java.util.List;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.party.PartyPeaceTask;
import ru.catssoftware.fakes.objects.PhantomPartyObject;
import ru.catssoftware.gameserver.datatables.SkillTable;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.L2Skill;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.StatsSet;

public class PhantomMagePartyAI extends PhantomPartyObject {
   public PhantomMagePartyAI(StatsSet set) {
      super(set);
   }

   public void doPeaceAction() {
   }

   public void doBattleAction() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(PhantomMagePartyAI$PhantomStopPartyAI.um("紺䛀\ue5f7ꐺ츣柒班\uf336ﺣ볖鏙⚶۶\uf398䂠숾溱ʮ\uea3dヤᚁ⠁긛蕜\ue7f6䅳擜⇆첡涒\ua9ec釖ದ沽瑠\u2bf5눈") + this._partyLeader.getName());
      }

      try {
         this._is_moving = 0;
         if (this.getPartyPercentDead() == 1.0D) {
            this.changePartyState(0);
            this.spawnPartyPeace();
            this.initSubTask(new PartyPeaceTask(this.getPartyId()), (long)this.getPartyCooldown());
            return;
         }

         L2PcInstance deadman = this.getDeadPartyMember();
         L2PcInstance resurrecter;
         if (deadman != null) {
            resurrecter = this.getAnyResurrectMan();
            if (resurrecter != null) {
               resurrecter.phantom_params.getPhantomAI().abortAITask();
               resurrecter.abortCast();
               resurrecter.phantom_params.getPhantomAI().castResurrectSkill(deadman);
               resurrecter.phantom_params.getPhantomAI().startAITask(500L);
            }
         }

         if (this.getDeadPartyMembersCount() >= this._all_members.size() / 2) {
            resurrecter = this.getAnyResurrectMan();
            if (resurrecter != null && resurrecter.getSkillById(1254) != null) {
               resurrecter.phantom_params.getPhantomAI().abortAITask();
               resurrecter.abortCast();
               L2Skill skill = resurrecter.getSkillById(1254);
               resurrecter.setTarget(resurrecter);
               resurrecter.getCharacter().doCast(skill);
               resurrecter.phantom_params.getPhantomAI().startAITask(500L);
            }
         }

         if (this._is_moving != 1 && Rnd.chance(this.getRegroupToLeaderChance())) {
            this._is_moving = 1;
            this.regroup();
         }

         if (this._is_moving != 1 && Rnd.chance(this.getRegroupToPlaceChance())) {
            this._is_moving = 1;
            this.moveToLovelyPlace(this._partyLeader);
         }

         if (this._is_moving != 1 && Rnd.chance(this.getRandomMoveChance())) {
            this._is_moving = 1;
            Location loc = this.getRandomMove(this._partyLeader, 200, 400);
            this.moveToLocation(loc);
         }
      } catch (Exception var4) {
         _log.error(PhantomMagePartyAI$PhantomStopPartyAI.um("紺䛀\ue5f7ꐺ츣柒班\uf336ﺣ볖鏙⚶۶\uf398䂠숾溱ʮ\uea33ヤᚁ⠁긛蕜\ue7f6䅳擜⇆첡涒\ua9ec釖ದ"));
      }

   }

   public void onPartyMemberDebuffed(L2PcInstance member) {
      try {
         L2PcInstance healer = this.getAnyHealer();
         if (healer == null) {
            return;
         }

         L2Skill sk = SkillTable.getInstance().getInfo(1409, 1);
         healer.phantom_params.getPhantomAI().abortAITask();
         healer.abortCast();
         healer.setTarget(member);
         healer.getCharacter().doCast(sk);
         healer.phantom_params.getPhantomAI().startAITask(500L);
      } catch (Exception var4) {
         if (Config.DEBAG_LVL > 0) {
            var4.printStackTrace();
         }
      }

   }

   public void onPartyMemberAttacked(L2PcInstance member, L2Character attacker) {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(PhantomMagePartyAI$PhantomStopPartyAI.um("\ue6fd唲囇❕븶좛\uf834㺹㼉깢ӏ뚾㣞튴ꎂ踦굩擴便䃅㣅뿊\uf696ݫ\uf663앜濪▌\uec94훌䔧䑵\ue8cf與\uf779⒈㓩壀簙暩昝\uf1a4") + member.getName());
      }

      try {
         PhantomDefaultAI ai = member.phantom_params.getPhantomAI();
         L2PcInstance healer;
         if (ai.isHealer()) {
            healer = member;
         } else {
            healer = this.getAnyHealer();
         }

         if (!this.isMainAssistTaken() && (!(member.getTarget() instanceof L2PcInstance) || (L2PcInstance)member.getTarget() != attacker)) {
            this.takeMainAssist(attacker);
         }

         this.setSubAssist(attacker);
         if (healer == null) {
            return;
         }

         healer.phantom_params.setLockedTarget(member);
         healer.phantom_params.getPhantomAI().doCast();
         if (member.getCurrentHp() < 50.0D) {
            member.moveToLocation(healer.getLoc().getX(), healer.getLoc().getY(), healer.getLoc().getZ(), 150, false);
         }
      } catch (Exception var5) {
         if (Config.DEBAG_LVL > 0) {
            var5.printStackTrace();
         }
      }

   }

   public void getAndSetTarget(L2PcInstance phantom) {
      while(true) {
         try {
            if (phantom.phantom_params.getLockedTarget() == null) {
               List<L2PcInstance> targets = phantom.getAroundPlayers(1200, 600);
               if (targets != null && targets.size() != 0) {
                  L2Character target = (L2Character)targets.get(Rnd.get(targets.size()));
                  if (target != null && (phantom.phantom_params.getPhantomAI().isHealer() || phantom.phantom_params.getPhantomAI().isSupport()) && Rnd.chance(25)) {
                     phantom.phantom_params.setLockedTarget(target);
                  }
                  continue;
               }

               phantom.phantom_params.getPhantomAI().startAITask(2500L);
               return;
            }
         } catch (Exception var4) {
            if (Config.DEBAG_LVL > 0) {
               var4.printStackTrace();
            }
         }

         return;
      }
   }
}
