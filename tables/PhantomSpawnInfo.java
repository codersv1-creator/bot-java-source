package ru.catssoftware.fakes.tables;

import java.util.Iterator;
import java.util.LinkedList;

public class PhantomSpawnInfo {
   private int count;
   private double peace_cooldown;
   private int comeback_distance;
   private double handicap_attack;
   private double handicap_defence;
   private LinkedList<PhantomSpawnCoordinateInfo> battle_coordinates;
   private LinkedList<PhantomSpawnCoordinateInfo> peace_coordinates;
   private LinkedList<PhantomSpawnCoordinateInfo> farm_coordinates;

   public PhantomSpawnInfo(int count, double peace_cooldown, int comeback_distance, double handicap_attack, double handicap_defence) {
      this.count = count;
      this.peace_cooldown = peace_cooldown;
      this.comeback_distance = comeback_distance;
      this.handicap_attack = handicap_attack;
      this.handicap_defence = handicap_defence;
      this.battle_coordinates = new LinkedList();
      this.peace_coordinates = new LinkedList();
      this.farm_coordinates = new LinkedList();
   }

   public int getCount() {
      return this.count;
   }

   public double getPeaceCooldown() {
      return this.peace_cooldown;
   }

   public int getComebackDistance() {
      return this.comeback_distance;
   }

   public double getHandicapAttack() {
      return this.handicap_attack;
   }

   public double getHandicapDefence() {
      return this.handicap_defence;
   }

   public void addBattleCoordinate(PhantomSpawnCoordinateInfo info) {
      this.addCoordinate(info, this.battle_coordinates);
   }

   public void addPeaceCoordinate(PhantomSpawnCoordinateInfo info) {
      this.addCoordinate(info, this.peace_coordinates);
   }

   public void addFarmCoordinate(PhantomSpawnCoordinateInfo info) {
      this.addCoordinate(info, this.farm_coordinates);
   }

   private void addCoordinate(PhantomSpawnCoordinateInfo info, LinkedList<PhantomSpawnCoordinateInfo> coordinates) {
      if (info != null) {
         if (coordinates.isEmpty()) {
            coordinates.addLast(info);
         }

         if (info.point_priority > ((PhantomSpawnCoordinateInfo)coordinates.getLast()).point_priority) {
            coordinates.addLast(info);
         } else if (info.point_priority < ((PhantomSpawnCoordinateInfo)coordinates.getFirst()).point_priority) {
            coordinates.addFirst(info);
         } else {
            int pos = 0;

            for(Iterator var5 = coordinates.iterator(); var5.hasNext(); ++pos) {
               PhantomSpawnCoordinateInfo b_info = (PhantomSpawnCoordinateInfo)var5.next();
               if (b_info.point_priority >= info.point_priority) {
                  break;
               }
            }

            coordinates.add(pos + 1, info);
         }

      }
   }

   public LinkedList<PhantomSpawnCoordinateInfo> getAllBattleCoordinates() {
      return this.battle_coordinates;
   }

   public LinkedList<PhantomSpawnCoordinateInfo> getAllPeaceCoordinates() {
      return this.peace_coordinates;
   }

   public LinkedList<PhantomSpawnCoordinateInfo> getAllFarmCoordinates() {
      return this.farm_coordinates;
   }

   public void clean() {
      PhantomSpawnCoordinateInfo info;
      Iterator var2;
      if (this.farm_coordinates != null) {
         var2 = this.farm_coordinates.iterator();

         while(var2.hasNext()) {
            info = (PhantomSpawnCoordinateInfo)var2.next();
            info.clean();
         }

         this.farm_coordinates.clear();
         this.farm_coordinates = null;
      }

      if (this.battle_coordinates != null) {
         var2 = this.battle_coordinates.iterator();

         while(var2.hasNext()) {
            info = (PhantomSpawnCoordinateInfo)var2.next();
            info.clean();
         }

         this.battle_coordinates.clear();
         this.battle_coordinates = null;
      }

      if (this.peace_coordinates != null) {
         var2 = this.peace_coordinates.iterator();

         while(var2.hasNext()) {
            info = (PhantomSpawnCoordinateInfo)var2.next();
            info.clean();
         }

         this.peace_coordinates.clear();
         this.peace_coordinates = null;
      }

   }
}
