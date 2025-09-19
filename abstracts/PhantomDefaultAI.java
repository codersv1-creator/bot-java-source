package ru.catssoftware.fakes.abstracts;

import java.util.Iterator;
import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.ai.tasks.other.ChatTask;
import ru.catssoftware.fakes.model.ClassesDictionary;
import ru.catssoftware.fakes.templates.PhantomSkill;
import ru.catssoftware.fakes.templates.SkillsGroup;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.geodata.GeoEngine;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.L2Effect;
import ru.catssoftware.gameserver.model.L2Skill;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public abstract class PhantomDefaultAI extends PhantomAbstractAI {
   protected static final Logger _log = Logger.getLogger(PhantomDefaultAI.class);

   public abstract void doCast();

   public void doBuffCast() {
      L2PcInstance phantom = this.getActor();

      try {
         SkillsGroup item_skills = phantom.phantom_params.getClassAI().getItemUseSkills();
         this.castItemSkill(item_skills);
         this.castBuffSkill(phantom);
      } catch (Exception var3) {
         _log.error("PhantomDefaultAI.doBuffCast() E" + var3);
         var3.printStackTrace();
      }

   }

   public boolean isNeedToGetNewTarget() {
      if (Config.DEBAG_LVL > 1) {
         System.out.println("PhantomDefaultAI.isNeedToGetNewTarget()1 :" + this.getActor().getName());
      }

      L2PcInstance phantom = this.getActor();
      L2Character target = phantom.phantom_params.getLockedTarget();
      if (Config.DEBAG_LVL > 1) {
         System.out.println("PhantomDefaultAI.isNeedToGetNewTarget()2 :" + this.getActor().getName());
      }

      if (target == null) {
         return true;
      } else {
         if (Config.DEBAG_LVL > 1) {
            System.out.println("PhantomDefaultAI.isNeedToGetNewTarget()3 :" + this.getActor().getName());
         }

         if (target.isPlayer() && target.getActingPlayer().getAppearance().isInvisible()) {
            if (Config.DEBAG_LVL > 1) {
               System.out.println("PhantomDefaultAI.isNeedToGetNewTarget()4 :" + this.getActor().getName());
            }

            this.removeTargets();
            return true;
         } else {
            if (Config.DEBAG_LVL > 1) {
               System.out.println("PhantomDefaultAI.isNeedToGetNewTarget()5 :" + this.getActor().getName());
            }

            if (target.isDead()) {
               int delay = Rnd.get(500, 3500);
               if (delay > 1000 && Rnd.get(100) < 25) {
                  ThreadPoolManager.getInstance().scheduleAi(new ChatTask(phantom, Rnd.chance(80) ? 1 : 0, 3), (long)delay, true);
               }

               this.removeTargets();
               return true;
            } else {
               if (Config.DEBAG_LVL > 1) {
                  System.out.println("PhantomDefaultAI.isNeedToGetNewTarget()6 :" + this.getActor().getName());
               }

               if (phantom.phantom_params.getLockedTargetFirstLocation() != null && phantom.phantom_params.getLockedTargetFirstLocation().distance(target.getLoc()) > 1000.0D) {
                  this.removeTargets();
                  return true;
               } else {
                  if (Config.DEBAG_LVL > 1) {
                     System.out.println("PhantomDefaultAI.isNeedToGetNewTarget()7 :" + this.getActor().getName());
                  }

                  if (target.isInsideZone((byte)1)) {
                     this.removeTargets();
                     return true;
                  } else {
                     try {
                        if (!GeoEngine.canSeeTarget(phantom, target, false)) {
                           Location loc = target.getLoc();
                           if (GeoEngine.canSeeCoord(phantom.getX(), phantom.getY(), phantom.getZ(), loc.getX(), loc.getY(), loc.getZ(), false, phantom.getInstanceId())) {
                              phantom.moveToLocationF(loc.getX(), loc.getY(), loc.getZ(), 0, true);
                              return false;
                           } else {
                              this.removeTargets();
                              return true;
                           }
                        } else {
                           return false;
                        }
                     } catch (NullPointerException var4) {
                        this.removeTargets();
                        _log.error("Phantom Default AI error. X: " + phantom.getX() + ", Y: " + phantom.getY() + ", Z: " + phantom.getZ() + ", tX: " + target.getX() + ", tY: " + target.getY() + ", tZ: " + target.getZ());
                        return true;
                     }
                  }
               }
            }
         }
      }
   }

   public void removeTarg() {
      this.getActor().phantom_params.setLockedTarget((L2Character)null);
      this.clearPartyAssist();
   }

   protected void removeTargets() {
      this.getActor().phantom_params.setLockedTarget((L2Character)null);
      this.clearPartyAssist();
   }

   public void clearPartyAssist() {
      if (this.getActor().phantom_params.getPhantomPartyAI() != null) {
         this.getActor().phantom_params.getPhantomPartyAI().clearAssists();
      }
   }

   public void castItemSkill(SkillsGroup item_skills) {
      if (item_skills.getAllSkills() != null) {
         PhantomSkill[] var5;
         int var4 = (var5 = item_skills.getAllSkills()).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            PhantomSkill sk = var5[var3];
            L2Skill skill = sk.getSkill();
            if (skill == null) {
               _log.warn("Skip CastItemSkill id:" + sk.getId() + " ClassId:" + this.getActor().phantom_params.getClassAI().getClassId() + " ClassName:" + ClassesDictionary.getNameById(this.getActor().phantom_params.getClassAI().getClassId()));
            } else if (!this.actor.isSkillDisabled(skill.getId())) {
               if (sk.getCondType().equalsIgnoreCase("HP")) {
                  if (sk.getSecondCond() == 1 && this.actor.getCurrentHp() > (double)sk.getFirstCond() || sk.getSecondCond() == 0 && this.actor.getCurrentHp() < (double)sk.getFirstCond()) {
                     this.actor.setTarget(this.actor);
                     this.actor.getCharacter().doCast(skill);
                  }
               } else if (sk.getCondType().equalsIgnoreCase("MP")) {
                  if (sk.getSecondCond() == 1 && this.actor.getCurrentMp() > (double)sk.getFirstCond() || sk.getSecondCond() == 0 && this.actor.getCurrentMp() < (double)sk.getFirstCond()) {
                     this.actor.setTarget(this.actor);
                     this.actor.getCharacter().doCast(skill);
                  }
               } else if (sk.getCondType().equalsIgnoreCase("CP") && (sk.getSecondCond() == 1 && this.actor.getCurrentCp() > (double)sk.getFirstCond() || sk.getSecondCond() == 0 && this.actor.getCurrentCp() < (double)sk.getFirstCond())) {
                  this.actor.setTarget(this.actor);
                  this.actor.getCharacter().doCast(skill);
               }
            }
         }

      }
   }

   public void castHPMPCP(SkillsGroup item_skills) {
      if (item_skills.getAllSkills() != null) {
         PhantomSkill[] var5;
         int var4 = (var5 = item_skills.getAllSkills()).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            PhantomSkill sk = var5[var3];
            L2Skill skill = sk.getSkill();
            if (skill == null) {
               _log.warn("Skip CastItemSkill id:" + sk.getId() + " ClassId:" + this.getActor().phantom_params.getClassAI().getClassId() + " ClassName:" + ClassesDictionary.getNameById(this.getActor().phantom_params.getClassAI().getClassId()));
            } else if (!this.actor.isSkillDisabled(skill.getId())) {
               if (sk.getCondType().equalsIgnoreCase("HP")) {
                  if (sk.getSecondCond() == 1 && this.actor.getCurrentHp() > (double)sk.getFirstCond() || sk.getSecondCond() == 0 && this.actor.getCurrentHp() < (double)sk.getFirstCond()) {
                     this.actor.setTarget(this.actor);
                     this.actor.getCharacter().doCast(skill);
                  }
               } else if (sk.getCondType().equalsIgnoreCase("MP")) {
                  if (sk.getSecondCond() == 1 && this.actor.getCurrentMp() > (double)sk.getFirstCond() || sk.getSecondCond() == 0 && this.actor.getCurrentMp() < (double)sk.getFirstCond()) {
                     this.actor.setTarget(this.actor);
                     this.actor.getCharacter().doCast(skill);
                  }
               } else if (sk.getCondType().equalsIgnoreCase("CP") && (sk.getSecondCond() == 1 && this.actor.getCurrentCp() > (double)sk.getFirstCond() || sk.getSecondCond() == 0 && this.actor.getCurrentCp() < (double)sk.getFirstCond())) {
                  this.actor.setTarget(this.actor);
                  this.actor.getCharacter().doCast(skill);
               }
            }
         }

      }
   }

   public boolean castChargeSkill(L2PcInstance phantom, SkillsGroup item_skills) {
      PhantomSkill[] var6;
      int var5 = (var6 = item_skills.getAllSkills()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         PhantomSkill sk = var6[var4];
         if (sk.getCondType().equalsIgnoreCase("skill")) {
            L2Skill skill = sk.getSkill();
            if (!phantom.isSkillDisabled(skill.getId()) && (phantom.getAllEffectsList() == null || phantom.getDeathPenaltyBuffLevel() < sk.getSecondCond())) {
               phantom.setTarget(phantom);
               phantom.getCharacter().doCast(skill);
               return true;
            }
         }
      }

      return false;
   }

   public L2Skill getRandomDebuffIfTargetHasNotDebuffedAlready(L2PcInstance phantom, SkillsGroup skills) {
      L2Character target = phantom.phantom_params.getLockedTarget();
      if (target == null) {
         return null;
      } else {
         for(int i = 0; i < skills.getAllSkills().length; ++i) {
            L2Skill skill = skills.getSkillByIndex(i);
            if (skill.getId() != null && !phantom.isSkillDisabled(skill.getId())) {
               if (target.getAllEffectsList() == null || target.getAllEffectsList().isEmpty()) {
                  return skill;
               }

               try {
                  if (target.getAllEffectsList().get(skill.getId()) == null) {
                     return skill;
                  }
               } catch (Exception var7) {
               }
            }
         }

         return null;
      }
   }

   public L2Skill getRandomSkillFromGroup(L2PcInstance phantom, SkillsGroup skills) {
      for(int i = 0; i < skills.getAllSkills().length; ++i) {
         L2Skill casting_skill = skills.getSkillByIndex(i);
         if (!phantom.isSkillDisabled(casting_skill.getId())) {
            return casting_skill;
         }
      }

      return null;
   }

   public boolean castNukeSkill(L2Character target) {
      SkillsGroup nuke = this.getActor().phantom_params.getClassAI().getNukeSkills();
      L2Skill casting_skill = nuke.getRandomSkill().getSkill();
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId())) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castAoESkill(L2Character target) {
      SkillsGroup nuke = this.getActor().phantom_params.getClassAI().getAoESkills();
      L2Skill casting_skill = nuke.getRandomSkill().getSkill();
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId()) && target != null && target.isInRange(this.actor, casting_skill.getSkillRadius())) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castDetectionSkill(L2Character target) {
      SkillsGroup nuke = this.getActor().phantom_params.getClassAI().getDetectionSkills();
      L2Skill casting_skill = nuke.getRandomSkill().getSkill();
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId()) && target != null && target.isInRange(this.actor, casting_skill.getSkillRadius())) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castRareNukeSkill(L2Character target) {
      SkillsGroup nuke = this.getActor().phantom_params.getClassAI().getRareNukeSkills();
      L2Skill casting_skill = nuke.getRandomSkill().getSkill();
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId())) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castBuffSkill(L2Character target) {
      try {
         SkillsGroup buffs = this.getActor().phantom_params.getClassAI().getSelfBuffs();
         PhantomSkill _PS = buffs.getRandomSkill();
         L2Skill casting_skill = _PS.getSkill();
         if (casting_skill == null) {
            if (buffs.getAllSkills() != null && _PS.getId() != -1) {
               _log.warn("Skip castBuffSkill SkillId:" + _PS.getId());
               _log.warn("Skip castBuffSkill ClassId:" + this.getActor().phantom_params.getClassAI().getClassId());
               _log.warn("Skip castBuffSkill ClassName:" + ClassesDictionary.getNameById(this.getActor().phantom_params.getClassAI().getClassId()));
            }

            return false;
         }

         if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId()) && (target.getAllEffectsList() == null || target.getAllEffectsList().isEmpty() || !target.getAllEffectsList().isEmpty() && target.getAllEffectsList().contains(casting_skill.getId()))) {
            this.actor.setTarget(target);
            this.actor.getCharacter().doCast(casting_skill);
            return true;
         }
      } catch (Exception var5) {
      }

      return false;
   }

   public boolean castBuffSkillOnPartyMember() {
      SkillsGroup buffs = this.getActor().phantom_params.getClassAI().getSelfBuffs();
      L2Skill casting_skill = buffs.getRandomSkill().getSkill();
      Iterator var4 = this.actor.phantom_params.getPhantomPartyAI().getAllMembers().iterator();

      L2PcInstance member;
      do {
         do {
            do {
               if (!var4.hasNext()) {
                  return false;
               }

               member = (L2PcInstance)var4.next();
            } while(casting_skill == null);
         } while(this.actor.isSkillDisabled(casting_skill.getId()));
      } while(member.getAllEffectsList() != null && member.getAllEffectsList().get(casting_skill.getId()) != null);

      this.actor.setTarget(member);
      this.actor.getCharacter().doCast(casting_skill);
      return true;
   }

   public boolean castUltimateBuffSkill(L2Character target, int percent) {
      SkillsGroup u_buffs = this.getActor().phantom_params.getClassAI().getSelfUltimateBuffs();
      L2Skill casting_skill = u_buffs.getRandomSkill().getSkill();
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId()) && target.getCurrentHp() < (double)percent) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castResurrectSkill(L2PcInstance target) {
      SkillsGroup res = this.getActor().phantom_params.getClassAI().getResurrectSkills();
      L2Skill casting_skill = res.getRandomSkill().getSkill();
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId())) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castHealSkill(L2Character target, int percent, boolean isHealReduced) {
      SkillsGroup heals = this.getActor().phantom_params.getClassAI().getHealSkills();
      L2Skill casting_skill = heals.getRandomSkill().getSkill();
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId()) && target.getCurrentHp() < (double)percent && !isHealReduced) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castDebuffSkill(L2Character target) {
      SkillsGroup debuffs = this.getActor().phantom_params.getClassAI().getDebuffs();
      L2Skill casting_skill = this.getRandomDebuffIfTargetHasNotDebuffedAlready(this.actor, debuffs);
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId())) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castRareDebuffSkill(L2Character target) {
      SkillsGroup debuffs = this.getActor().phantom_params.getClassAI().getRareDebuffs();
      L2Skill casting_skill = this.getRandomDebuffIfTargetHasNotDebuffedAlready(this.actor, debuffs);
      if (casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId())) {
         this.actor.setTarget(target);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castSummonSkill() {
      SkillsGroup summon = this.getActor().phantom_params.getClassAI().getSummonSkills();
      L2Skill casting_skill = summon.getRandomSkill().getSkill();
      if (casting_skill != null && !this.actor.isSummoning() && !this.actor.isSkillDisabled(casting_skill.getId())) {
         this.actor.setTarget(this.actor);
         this.actor.getCharacter().doCast(casting_skill);
         return true;
      } else {
         return false;
      }
   }

   public boolean castSituationSkill(L2Character target) {
      SkillsGroup situation = this.getActor().phantom_params.getClassAI().getSituationSkills();
      PhantomSkill p_sk = null;

      try {
         p_sk = this.getSituationSkill(this.actor, situation);
      } catch (Exception var5) {
         if (Config.DEBAG_LVL > 0) {
            System.out.println("PhantomDefaultAI.castSituationSkill E: " + var5);
         }
      }

      if (p_sk == null) {
         return false;
      } else {
         L2Skill casting_skill = p_sk.getSkill();
         if (p_sk != null && casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId())) {
            if (p_sk.getTargetType().equalsIgnoreCase("self")) {
               this.actor.setTarget(this.actor);
               this.actor.getCharacter().doCast(casting_skill);
               return true;
            }

            if (p_sk.getTargetType().equalsIgnoreCase("enemy")) {
               this.actor.setTarget(target);
               this.actor.getCharacter().doCast(casting_skill);
               return true;
            }
         }

         return false;
      }
   }

   public boolean castSituationSkill(L2Character target, int height) {
      SkillsGroup situation = this.getActor().phantom_params.getClassAI().getSituationSkills();
      PhantomSkill p_sk = this.getSituationSkill(this.actor, situation);
      if (p_sk == null) {
         return false;
      } else {
         L2Skill casting_skill = p_sk.getSkill();
         if (p_sk != null && casting_skill != null && !this.actor.isSkillDisabled(casting_skill.getId())) {
            String t_type = p_sk.getTargetType();
            if (t_type.equalsIgnoreCase("area")) {
               if (!this.actor.getAroundPlayers(casting_skill.getSkillRadius(), height).isEmpty()) {
                  this.actor.setTarget(target);
                  this.actor.getCharacter().doCast(casting_skill);
                  return true;
               }
            } else {
               if (p_sk.getTargetType().equalsIgnoreCase("self")) {
                  this.actor.setTarget(this.actor);
                  this.actor.getCharacter().doCast(casting_skill);
                  return true;
               }

               if (p_sk.getTargetType().equalsIgnoreCase("enemy")) {
                  this.actor.setTarget(target);
                  this.actor.getCharacter().doCast(casting_skill);
                  return true;
               }
            }
         }

         return false;
      }
   }

   public PhantomSkill getSituationSkill(L2PcInstance phantom, SkillsGroup skills) {
      PhantomSkill[] var8;
      int var7 = (var8 = skills.getAllSkills()).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         PhantomSkill sk = var8[var6];
         L2Skill skill;
         if (sk.getCondType().equalsIgnoreCase("HP") && (sk.getSecondCond() == 1 && phantom.getCurrentHp() > (double)sk.getFirstCond() || sk.getSecondCond() == 0 && phantom.getCurrentHp() < (double)sk.getFirstCond())) {
            skill = sk.getSkill();
            if (!phantom.isSkillDisabled(skill.getId())) {
               return sk;
            }
         } else if (!sk.getCondType().equalsIgnoreCase("MP") || (sk.getSecondCond() != 1 || !(phantom.getCurrentMp() > (double)sk.getFirstCond())) && (sk.getSecondCond() != 0 || !(phantom.getCurrentMp() < (double)sk.getFirstCond()))) {
            if (sk.getCondType().equalsIgnoreCase("CP")) {
               if (sk.getSecondCond() == 1 && phantom.getCurrentCp() > (double)sk.getFirstCond() || sk.getSecondCond() == 0 && phantom.getCurrentCp() < (double)sk.getFirstCond()) {
                  skill = sk.getSkill();
                  if (!phantom.isSkillDisabled(skill.getId())) {
                     return sk;
                  }
               }
            } else if (sk.getCondType().equalsIgnoreCase("skill")) {
               L2PcInstance target;
               if (sk.getTargetType().equalsIgnoreCase("enemy")) {
                  if (phantom.phantom_params.getLockedTarget() == null) {
                     return null;
                  }

                  target = phantom.phantom_params.getLockedTarget().getPlayer();
               } else {
                  target = phantom;
               }

               if (target.getAllEffectsList() != null && target.getAllEffectsList().get(sk.getFirstCond()) != null) {
                  L2Effect eff = (L2Effect)target.getAllEffectsList().get(sk.getFirstCond());
                  skill = eff.getSkill();
                  if (!phantom.isSkillDisabled(sk.getSkill().getId()) && (sk.getSecondCond() == skill.getLevel() || sk.getSecondCond() == 0)) {
                     return sk;
                  }
               }
            }
         } else {
            skill = sk.getSkill();
            if (!phantom.isSkillDisabled(skill.getId())) {
               return sk;
            }
         }
      }

      return null;
   }
}
