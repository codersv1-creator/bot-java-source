package ru.catssoftware.fakes.tables;

import java.util.LinkedList;

public class PhantomSpawnObjectInfo {
   private String ai_type;
   private boolean is_spawn_enabled;
   LinkedList<PhantomSpawnInfo> spawns;

   public PhantomSpawnObjectInfo(String ai_type, boolean is_spawn_enabled) {
      this.ai_type = ai_type;
      this.is_spawn_enabled = is_spawn_enabled;
      this.spawns = new LinkedList();
   }

   public String getAIType() {
      return this.ai_type;
   }

   public boolean isSpawnEnabled() {
      return this.is_spawn_enabled;
   }

   public void addSpawn(PhantomSpawnInfo spawn) {
      if (spawn != null) {
         this.spawns.addLast(spawn);
      }

   }

   public LinkedList<PhantomSpawnInfo> getAllSpawns() {
      return this.spawns;
   }
}
