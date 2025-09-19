package ru.catssoftware.fakes.ai.individualbehavior.warriors;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.tasks.battle.BattleTask;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class Warrior extends PhantomDefaultAI {
   public void doCast() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ"));
      }

      L2PcInstance phantom = this.getActor();
      if (!this.castUltimateBuffSkill(phantom, 100)) {
         if (Config.DEBAG_LVL > 0) {
            System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ⽳"));
         }

         L2Character target = phantom.phantom_params.getLockedTarget();
         if (!this.castSituationSkill(target)) {
            if (Config.DEBAG_LVL > 0) {
               System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ⽰"));
            }

            if (!this.castAoESkill(target)) {
               if (Config.DEBAG_LVL > 0) {
                  System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ⽱"));
               }

               int skill_chance = 10;
               if (!Rnd.chance(skill_chance) || !this.castRareDebuffSkill(target)) {
                  if (Config.DEBAG_LVL > 0) {
                     System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ⽶"));
                  }

                  int skill_chance = skill_chance + 10;
                  if (!Rnd.chance(skill_chance) || !this.castDebuffSkill(target)) {
                     if (Config.DEBAG_LVL > 0) {
                        System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ⽷"));
                     }

                     skill_chance += 5;
                     if (!Rnd.chance(skill_chance) || !this.castRareNukeSkill(target)) {
                        if (Config.DEBAG_LVL > 0) {
                           System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ⽴"));
                        }

                        skill_chance += 5;
                        if (Rnd.chance(skill_chance)) {
                           if (Config.DEBAG_LVL > 0) {
                              System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ⽵"));
                           }

                           this.castNukeSkill(target);
                        }

                        if (Config.DEBAG_LVL > 0) {
                           System.out.println(g.o("솶\uea1e옓麰陻눋ᡦ⽺"));
                        }

                     }
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
