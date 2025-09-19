package ru.catssoftware.fakes.ai.tasks.other;

import ru.catssoftware.Config;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class EndPeaceCooldownTask extends RunnableImpl {
   public L2PcInstance phantom;

   public EndPeaceCooldownTask(L2PcInstance ph) {
      this.phantom = ph;
   }

   public void runImpl() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(EndPeaceGoToTeleportTask$BuffTask.l("㛊沣鄼ᩅ溒៏傲\uab1aฃ礪\ude1d퐂勱骳Ựꨝ\uf172脪蹎ᗙ聖곀★懿伳寁峹쏓ꓹᖑꆲ⧟") + this.phantom.getName());
      }

      this.phantom.phantom_params.setIsGoToTeleport(true);
      ThreadPoolManager.getInstance().schedule(new EndPeaceGoToTeleportForcedTask(this.phantom), 15000L);
   }
}
