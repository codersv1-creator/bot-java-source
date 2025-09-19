package ru.catssoftware.fakes.abstracts;

import java.util.concurrent.ScheduledFuture;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.ai.tasks.other.BuffTask;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public abstract class PhantomAbstractAI {
   protected ScheduledFuture<?> _aiTask = null;
   protected ScheduledFuture<?> _buffTask = null;
   protected L2PcInstance actor;

   public abstract void startAITask(long var1);

   public void startAITask(Runnable r, long delay) {
      try {
         this.abortAITask();
         this._aiTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(r, delay, delay);
      } catch (Exception var5) {
         if (Config.DEBAG_LVL > 0) {
            var5.printStackTrace();
         }
      }

   }

   public void abortAITask() {
      if (this._aiTask != null) {
         this._aiTask.cancel(true);
         this._aiTask = null;
      }

   }

   public void startBuffTask(long delay) {
      this.startBuffTask(new BuffTask(this.actor), delay);
   }

   public void startBuffTask(Runnable r, long delay) {
      try {
         this.abortBuffTask();
         this._buffTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(r, delay, delay);
      } catch (Exception var5) {
         if (Config.DEBAG_LVL > 0) {
            var5.printStackTrace();
         }
      }

   }

   public void abortBuffTask() {
      if (this._buffTask != null) {
         this._buffTask.cancel(true);
         this._buffTask = null;
      }

   }

   public void startDespawnTask(long delay) {
      try {
         long respawn_time = Rnd.get(delay - 5000L, delay + 15000L);
         if (Config.DEBAG_LVL > 0) {
            System.out.println("DeSpawN: " + this.getActor().getName());
         }
      } catch (Exception var5) {
         if (Config.DEBAG_LVL > 0) {
            var5.printStackTrace();
         }
      }

   }

   public void setActor(L2PcInstance ac) {
      this.actor = ac;
   }

   public L2PcInstance getActor() {
      return this.actor;
   }

   public boolean isNuker() {
      return false;
   }

   public boolean isHealer() {
      return false;
   }

   public boolean isSupport() {
      return false;
   }

   public boolean isDisabler() {
      return false;
   }

   public boolean isTank() {
      return false;
   }
}
