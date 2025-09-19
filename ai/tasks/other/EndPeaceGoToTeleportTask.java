package ru.catssoftware.fakes.ai.tasks.other;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.utils.PhantomUtil;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class EndPeaceGoToTeleportTask extends RunnableImpl {
   public L2PcInstance phantom;

   public EndPeaceGoToTeleportTask(L2PcInstance ph) {
      this.phantom = ph;
   }

   public void runImpl() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(EndPeaceGoToTeleportTask$BuffTask.l("\uf437簭깙쥜手䠡ᆹꪴꈊ┴\ue324㍲ᄚᆒዅ裲䮤䒲跛倌㤨뙇䜌譽ꢊ拤믊ꏑ餪Ⓑ㪭ဇ짥跢컩靆") + this.phantom.getName());
      }

      PhantomUtil.restorePhantomToHisBattleLoc(this.phantom.getPlayer());
   }
}
