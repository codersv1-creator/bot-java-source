package ru.catssoftware.fakes.ai.tasks.other;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.utils.PhantomUtil;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class EndPeaceGoToTeleportForcedTask extends RunnableImpl {
   public L2PcInstance phantom;

   public EndPeaceGoToTeleportForcedTask(L2PcInstance ph) {
      this.phantom = ph;
   }

   public void runImpl() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(EndPeaceGoToTeleportTask$BuffTask.l("㡭場溠\ua7e0ꖃ傎긝㰺⌅陦㶯\u0092\ueed2\uda06耏靖벤ꙣꊢᛈ⏢\ue433Ȍ颊䃢䣂\ud9e7秨⸑翐䬚᳐얲枳韨媺ዷ諅淆\ue0a7贌즌") + this.phantom.getName());
      }

      if (!this.phantom.getPlayer().isInsideZone((byte)0)) {
         this.phantom.phantom_params.setIsGoToTeleport2(true);
         PhantomUtil.restorePhantomToHisBattleLoc(this.phantom.getPlayer());
      }

   }
}
