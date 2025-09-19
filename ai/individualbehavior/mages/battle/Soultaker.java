package ru.catssoftware.fakes.ai.individualbehavior.mages.battle;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.battle.BattleMageTask;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class Soultaker extends PhantomDefaultAI {
   public void doCast() {
      L2PcInstance phantom = this.getActor();
      if (Config.DEBAG_LVL > 0) {
         System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿹") + phantom.getName());
      }

      if (!this.castSummonSkill()) {
         if (Config.DEBAG_LVL > 0) {
            System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿨㳻") + phantom.getName());
         }

         if (!this.castUltimateBuffSkill(phantom, 50)) {
            if (Config.DEBAG_LVL > 0) {
               System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿫㳻") + phantom.getName());
            }

            L2Character target = phantom.phantom_params.getLockedTarget();
            if (target != null) {
               if (Config.DEBAG_LVL > 0) {
                  System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿪㳻") + phantom.getTarget().getName());
               }
            } else if (Config.DEBAG_LVL > 0) {
               System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿪㳻䙨\uf707\ue990梔\u1c4b軶\ue9ab扢"));
            }

            if (!this.castSituationSkill(target)) {
               if (Config.DEBAG_LVL > 0) {
                  System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿭㳻") + phantom.getTarget().getName());
               }

               if (!this.castAoESkill(target)) {
                  if (Config.DEBAG_LVL > 0) {
                     System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿬㳻") + phantom.getTarget().getName());
                  }

                  int skill_chance = 15;
                  if (!Rnd.chance(skill_chance) || !this.castRareDebuffSkill(target)) {
                     if (Config.DEBAG_LVL > 0) {
                        System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿯㳻") + phantom.getTarget().getName());
                     }

                     int skill_chance = skill_chance + 5;
                     if (!Rnd.chance(skill_chance) || !this.castDebuffSkill(target)) {
                        if (Config.DEBAG_LVL > 0) {
                           System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿮㳻") + phantom.getTarget().getName());
                        }

                        skill_chance += 5;
                        if (!Rnd.chance(skill_chance) || !this.castRareNukeSkill(target)) {
                           if (Config.DEBAG_LVL > 0) {
                              System.out.println(StormScreamer$MysticMuse.A("ፚ㏉Ꮻ劅Ꚏ佗\uec65䐓勊巹\ue180鼓䭛ﺵ\uf0e8\ue3e2魇ꠜ쿡㳻") + phantom.getTarget().getName());
                           }

                           this.castNukeSkill(target);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void startAITask(long delay) {
      this.startAITask(new BattleMageTask(this.getActor()), delay);
   }

   public boolean isNuker() {
      return true;
   }

   public boolean isDisabler() {
      return true;
   }
}
