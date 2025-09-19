package ru.catssoftware.fakes.ai.party;

import java.util.List;
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

public class PhantomDaggerPartyAI extends PhantomPartyObject {
   public PhantomDaggerPartyAI(StatsSet set) {
      super(set);
   }

   public void doPeaceAction() {
   }

   public void doBattleAction() {
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
         _log.error(PhantomMagePartyAI$PhantomStopPartyAI.um("窓臟墯ᨥㆦ㫗裧ᶎᤀ弰咔혋ꐣ꣺貋料\uf3fe䏑釭率\uedabΜเ⽫\ude90ʝ옪豭\uea99耣\u0d84䢞詹贐\udd0e"));
      }

   }

   public void onPartyMemberDebuffed(L2PcInstance member) {
      L2PcInstance healer = this.getAnyHealer();
      if (healer != null) {
         L2Skill sk = SkillTable.getInstance().getInfo(1409, 1);
         healer.phantom_params.getPhantomAI().abortAITask();
         healer.abortCast();
         healer.setTarget(member);
         healer.getCharacter().doCast(sk);
         healer.phantom_params.getPhantomAI().startAITask(500L);
      }
   }

   public void onPartyMemberAttacked(L2PcInstance member, L2Character attacker) {
      PhantomDefaultAI ai = member.phantom_params.getPhantomAI();
      L2PcInstance healer;
      if (ai.isHealer()) {
         healer = member;
      } else {
         healer = this.getAnyHealer();
      }

      if (!this.isMainAssistTaken()) {
         this.takeMainAssist(attacker);
      }

      this.setSubAssist(attacker);
      if (healer != null) {
         healer.phantom_params.setLockedTarget(member);
         healer.phantom_params.getPhantomAI().doCast();
         if (member.getCurrentHp() < 50.0D) {
            member.moveToLocation(healer.getLoc().getX(), healer.getLoc().getY(), healer.getLoc().getZ(), 150, false);
         }

      }
   }

   public void getAndSetTarget(L2PcInstance phantom) {
      while(phantom.phantom_params.getLockedTarget() == null) {
         List<L2PcInstance> targets = phantom.getAroundPlayers(1200, 600);
         if (targets == null || targets.size() == 0) {
            phantom.phantom_params.getPhantomAI().startAITask(2500L);
            return;
         }

         L2Character target = (L2Character)targets.get(Rnd.get(targets.size()));
         if (target != null && (phantom.phantom_params.getPhantomAI().isHealer() || phantom.phantom_params.getPhantomAI().isSupport() && Rnd.chance(25))) {
            phantom.phantom_params.setLockedTarget(target);
         }
      }

   }
}
