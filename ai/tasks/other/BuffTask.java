package ru.catssoftware.fakes.ai.tasks.other;

import ru.catssoftware.fakes.utils.PhantomUtil;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class BuffTask extends RunnableImpl {
   private L2PcInstance _phantom;

   public BuffTask(L2PcInstance phantom) {
      this._phantom = phantom;
   }

   public void runImpl() {
      if (!this._phantom.isDead()) {
         PhantomUtil.doBuff(this._phantom);
         this._phantom.setKarma(0);
      }

   }
}
