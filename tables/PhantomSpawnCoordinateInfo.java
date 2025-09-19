package ru.catssoftware.fakes.tables;

import ru.catssoftware.gameserver.model.Location;

public class PhantomSpawnCoordinateInfo {
   public Location loc;
   public int point_priority;

   public PhantomSpawnCoordinateInfo(Location loc, int point_priority) {
      this.loc = loc;
      this.point_priority = point_priority;
   }

   public void clean() {
      this.loc = null;
      this.point_priority = 0;
   }
}
