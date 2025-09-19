package ru.catssoftware.fakes.tasks;

import java.util.Iterator;
import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.ai.tasks.other.SpawnTask;
import ru.catssoftware.fakes.parsers.PhantomSpawnParser;
import ru.catssoftware.fakes.tables.PhantomSpawnInfo;
import ru.catssoftware.fakes.tables.PhantomSpawnObjectInfo;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.tools.random.Rnd;

public class PhantomTask implements Runnable {
   private static final Logger _log = Logger.getLogger(PhantomTask.class);

   public void run() {
      _log.info(h.w("ᵓ랮\ue536뺢ၟ콣굔獢\ud808睿灟偮㹯覴\ue115᱅䇽㥹팴眢Ⳃ阉"));
      int count = false;
      int spawned = 0;

      try {
         if (PhantomSpawnParser.getSpawnLocs() != null && !PhantomSpawnParser.getSpawnLocs().isEmpty()) {
            Iterator var4 = PhantomSpawnParser.getSpawnLocs().iterator();

            label44:
            while(true) {
               PhantomSpawnObjectInfo spawn_object;
               do {
                  if (!var4.hasNext()) {
                     break label44;
                  }

                  spawn_object = (PhantomSpawnObjectInfo)var4.next();
               } while(!spawn_object.isSpawnEnabled());

               int count;
               for(Iterator var6 = spawn_object.getAllSpawns().iterator(); var6.hasNext(); spawned += count) {
                  PhantomSpawnInfo spawn_info = (PhantomSpawnInfo)var6.next();
                  count = spawn_info.getCount();

                  for(int k = 0; k < count; ++k) {
                     ThreadPoolManager.getInstance().schedule(new SpawnTask(spawn_info, spawn_object.getAIType()), (long)Rnd.get(Config.FAKE_PLAYERS_MIN_SPAWN_DELAY, Config.FAKE_PLAYERS_MAX_SPAWN_DELAY) * 1000L);
                  }
               }
            }
         }
      } catch (Exception var8) {
         if (Config.DEBAG_LVL > 0) {
            var8.printStackTrace();
         }
      }

      _log.info(h.w("ᵓ랮\ue536뺢ၟ콣굔獢\ud808睿灟偮㹯觱\ue102ᰑ") + spawned + h.w("ᴶ랰\ue53d뺪၈콣괜猱\ud855"));
   }
}
