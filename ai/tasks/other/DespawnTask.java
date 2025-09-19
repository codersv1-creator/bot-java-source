package ru.catssoftware.fakes.ai.tasks.other;

import ru.catssoftware.fakes.PhantomsEngine;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;
import ru.catssoftware.tools.random.Rnd;

public class DespawnTask extends RunnableImpl {
   public L2PcInstance phantom;

   public DespawnTask(L2PcInstance ph) {
      this.phantom = ph;
   }

   public void runImpl() {
      if (!this.phantom.isDead() && this.phantom.isInCombat()) {
         ThreadPoolManager.getInstance().schedule(new DespawnTask(this.phantom), (long)Rnd.get(3000, 6000));
      } else {
         this.phantom.phantom_params.getPhantomAI().abortAITask();
         this.phantom.phantom_params.getPhantomAI().abortBuffTask();
         PhantomsEngine.getInstance().despawnPhantom(this.phantom);
         this.phantom.setOnlineStatus(false);
         this.phantom.kick();
         this.phantom = null;
      }

   }
}
