package ru.catssoftware.fakes.ai.tasks.other;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.utils.PhantomUtil;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;
import ru.catssoftware.tools.random.Rnd;

public class ResurrectTask extends RunnableImpl {
   public L2PcInstance phantom;

   public ResurrectTask(L2PcInstance ph) {
      this.phantom = ph;
   }

   public void runImpl() {
      if (this.phantom.isDead()) {
         this.phantom.decayMe();
         this.phantom.doRevive(100.0D);
         this.phantom.getStatus().setCurrentHpMp((double)this.phantom.getMaxHp(), (double)this.phantom.getMaxMp());
         this.phantom.getStatus().setCurrentCp((double)this.phantom.getMaxCp());
         PhantomUtil.restorePhantomToHisPeaceLoc(this.phantom.getPlayer());
         if (Config.DEBAG_LVL > 0) {
            System.out.println(EndPeaceGoToTeleportTask$BuffTask.l("Ⴀ䒑ꚫ\ue5d1ݳᑑ\uf33b콤煒ᇉ쩼ㇱ뺪㫛䙀鞠뒧囏\u12c6デ◁魀丘\ud82f\u19af") + this.phantom.getName());
         }

         this.phantom.spawnMe();
         this.phantom.setKarma(0);

         try {
            PhantomUtil.doBuff(this.phantom);
         } catch (Exception var2) {
         }

         this.phantom.phantom_params.setLockedTarget((L2Character)null);
         this.phantom.phantom_params.setSubTarget((L2Character)null);
         this.phantom.phantom_params.setAttackerTarget((L2Character)null);
         this.phantom.phantom_params.setIsResurrecting(false);
         this.phantom.phantom_params.getPhantomAI().startAITask(500L);
         ThreadPoolManager.getInstance().schedule(new EndPeaceCooldownTask(this.phantom), (long)Rnd.get((int)(this.phantom.phantom_params.getPeaceCooldown() * 2750.0D), (int)(this.phantom.phantom_params.getPeaceCooldown() * 4250.0D)));
      }
   }
}
