package ru.catssoftware.fakes.ai.tasks.other;

import ru.catssoftware.fakes.tables.PhantomSpawnInfo;
import ru.catssoftware.fakes.utils.PhantomInitUtil;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class SpawnTask extends RunnableImpl {
   public PhantomSpawnInfo spawn_info;
   public String ai_type;

   public SpawnTask(PhantomSpawnInfo spawn_info, String ai_type) {
      this.spawn_info = spawn_info;
      this.ai_type = ai_type;
   }

   public void runImpl() {
      PhantomInitUtil.spawnPhantomInWave(this.spawn_info, this.ai_type);
   }
}
