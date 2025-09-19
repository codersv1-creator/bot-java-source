package ru.catssoftware.fakes.ai.tasks.farmers;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomAITask;
import ru.catssoftware.fakes.abstracts.PhantomDefaultPartyAI;
import ru.catssoftware.fakes.utils.PhantomUtil;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;

public class FarmTask extends PhantomAITask {
   public FarmTask(L2PcInstance ph) {
      super(ph);
   }

   public void runImpl() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(FarmTask$FarmTask.G("芊☥\ueab0墟ज़\ud86c\uf7ce\udaf0쫦삛অޭ\uefb9줜ꄣ鍪픿砚"));
      }

      try {
         if (this.phantom.isOutOfControl()) {
            return;
         }

         if (!this.phantom.phantom_params.getPhantomAI().isNeedToGetNewTarget()) {
            this.doAction();
         } else if (this.phantom.phantom_params.getPhantomAI().isNeedToGetNewTarget()) {
            for(int radius = 300; radius < this.getComebackDistanceL(); radius += 300) {
               if (PhantomUtil.getAndSetLockedTarget(this.phantom, radius, true)) {
                  this.doAction();
                  break;
               }
            }
         }

         PhantomDefaultPartyAI party_ai = this.phantom.phantom_params.getPhantomPartyAI();
         if (party_ai != null && party_ai.getMoving() == 1 || this.phantom.isMoving() || this.phantom.isAttackingNow() || this.phantom.isCastingNow()) {
            return;
         }

         this.returnToAllowedLocation(this.getComebackDistanceL());
      } catch (Exception var2) {
         if (Config.DEBAG_LVL > 0) {
            var2.printStackTrace();
         }
      }

   }

   public void checkLevelAndSetFarmLoc() {
      int level = this.phantom.getLevel();
      Location[] locs = new Location[3];
      Location fake_loc = PhantomUtil.getRandomPhantomLocation(this.phantom);
      Location current_loc = this.phantom.getLoc();
      if (this.phantom.getExp() < 200000L) {
         locs[0] = new Location(0, 0, 0);
         locs[1] = new Location(0, 0, 0);
         locs[2] = new Location(0, 0, 0);
      }

   }

   public boolean doAction() {
      super.doAction();
      return true;
   }
}
