package ru.catssoftware.fakes.abstracts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.PhantomsEngine;
import ru.catssoftware.fakes.ai.tasks.party.PartyTask;
import ru.catssoftware.fakes.holders.PhantomsHolder;
import ru.catssoftware.fakes.model.ClassesDictionary;
import ru.catssoftware.fakes.objects.PartyType;
import ru.catssoftware.fakes.tables.PhantomInfo;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.geodata.GeoEngine;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.StatsSet;

public abstract class PhantomDefaultPartyAI {
   protected static final Logger _log = Logger.getLogger(PhantomDefaultPartyAI.class);
   protected ScheduledFuture<?> _mainTask = null;
   protected ScheduledFuture<?> _subTask = null;
   protected ArrayList<L2PcInstance> _all_members = new ArrayList();
   protected int[] _all_charId;
   protected int[] _class_list;
   protected L2PcInstance _partyLeader = null;
   protected int _clan_id;
   protected int _party_id;
   protected int _party_cooldown;
   protected int _regroupToLeaderChance;
   protected int _regroupToPlaceChance;
   protected int _randomMoveChance;
   protected double _handicap_defence;
   protected double _handicap_attack;
   protected boolean _is_spawn_enabled;
   protected byte _is_moving;
   protected ArrayList<L2PcInstance> _nukers;
   protected ArrayList<L2PcInstance> _healers;
   protected ArrayList<L2PcInstance> _supports;
   protected ArrayList<L2PcInstance> _disablers;
   protected ArrayList<L2PcInstance> _tanks;
   protected PartyType _party_type;
   protected ArrayList<Location> _lovelyPlaces;
   protected ArrayList<Location> _spawnPlaces;
   protected ArrayList<Location> _spawnPeacePlaces;
   protected int _partyState;

   public abstract void doPeaceAction();

   public abstract void doBattleAction();

   public abstract void spawnPartyBattle();

   public abstract void spawnPartyPeace();

   public abstract void onPartyMemberDebuffed(L2PcInstance var1);

   public abstract void onPartyMemberAttacked(L2PcInstance var1, L2Character var2);

   public PhantomDefaultPartyAI(StatsSet set) {
      this._party_id = set.getInteger("partyId", 0);
      this._party_type = PartyType.valueOf(set.getString("partyType", "suspended"));
      this._is_spawn_enabled = set.getBool("isSpawnEnabled", false);
      this._class_list = set.getIntegerArray("class_lists");
      this._clan_id = set.getInteger("partyClan", -1);
      this._party_cooldown = (int)TimeUnit.SECONDS.toMillis((long)set.getInteger("partyCooldown", 0));
      this._handicap_defence = (double)set.getInteger("handicapDefence", 5);
      this._handicap_attack = (double)set.getInteger("handicapAttack", 5);
      this._regroupToLeaderChance = set.getInteger("regroupToLeaderChance", 1);
      this._regroupToPlaceChance = set.getInteger("regroupToPlaceChance", 1);
      this._randomMoveChance = set.getInteger("randomMoveChance", 25);
      this._all_charId = new int[this._class_list.length];

      for(int i = 0; i < this._class_list.length; ++i) {
         PhantomInfo phantom_info = PhantomsHolder.getFakeByClassId(this._class_list[i], this._clan_id);
         if (phantom_info != null) {
            this._all_charId[i] = phantom_info.charId;
            PhantomsEngine.getInstance().getAllPartyObjID().add(phantom_info.charId);
         } else {
            _log.info("Wrong: " + this._class_list[i] + ";" + this._clan_id);
         }
      }

      this._nukers = new ArrayList();
      this._healers = new ArrayList();
      this._supports = new ArrayList();
      this._disablers = new ArrayList();
      this._tanks = new ArrayList();
      this._lovelyPlaces = new ArrayList();
      this._spawnPlaces = new ArrayList();
      this._spawnPeacePlaces = new ArrayList();
      this._partyState = 0;
   }

   public void startAITask(long delay) {
      try {
         this.abortMainTask();
         this._mainTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new PartyTask(this.getPartyId()), delay, delay);
      } catch (Exception var4) {
         if (Config.DEBAG_LVL > 0) {
            var4.printStackTrace();
         }
      }

   }

   public void initSubTask(Runnable r, long delay) {
      try {
         this.abortSubTask();
         this._subTask = ThreadPoolManager.getInstance().schedule(r, delay);
      } catch (Exception var5) {
         if (Config.DEBAG_LVL > 0) {
            var5.printStackTrace();
         }
      }

   }

   public void abortMainTask() {
      if (this._mainTask != null) {
         this._mainTask.cancel(true);
         this._mainTask = null;
      }

   }

   public void abortSubTask() {
      if (this._subTask != null) {
         this._subTask.cancel(true);
         this._subTask = null;
      }

   }

   public void doAction() {
      if (this._partyState == 0) {
         this.doPeaceAction();
      } else {
         this.doBattleAction();
      }

   }

   public void changePartyState(int state) {
      this._partyState = state;
   }

   public int getPartyState() {
      return this._partyState;
   }

   public byte getMoving() {
      return this._is_moving;
   }

   public void setPartyLeader(L2PcInstance pl) {
      this._partyLeader = pl;
   }

   public L2PcInstance getPartyLeader() {
      return this._partyLeader;
   }

   public Location getPartyLoc() {
      return this._partyLeader.getLoc();
   }

   public int getClanId() {
      return this._clan_id;
   }

   public int getRegroupToLeaderChance() {
      return this._regroupToLeaderChance;
   }

   public int getRegroupToPlaceChance() {
      return this._regroupToPlaceChance;
   }

   public int getRandomMoveChance() {
      return this._randomMoveChance;
   }

   public int getPartyId() {
      return this._party_id;
   }

   public PartyType getPartyType() {
      return this._party_type;
   }

   public int getPartyCooldown() {
      return this._party_cooldown;
   }

   public double getHandicapDefence() {
      return this._handicap_defence > 0.0D ? this._handicap_defence : 100.0D;
   }

   public double getHandicapAttack() {
      return this._handicap_attack > 0.0D ? this._handicap_attack : 100.0D;
   }

   public void setHandicapAttack(double val) {
      this._handicap_attack = val;
   }

   public void setHandicapDefence(double val) {
      this._handicap_defence = val;
   }

   public boolean isSpawnEnabled() {
      return this._is_spawn_enabled;
   }

   public int[] getObjIds() {
      return this._all_charId;
   }

   public int[] getClassList() {
      return this._class_list;
   }

   public void addPartyMember(L2PcInstance actor) {
      if (actor.phantom_params.getPhantomAI().isHealer()) {
         this._healers.add(actor);
      } else if (actor.phantom_params.getPhantomAI().isNuker()) {
         this._nukers.add(actor);
      } else if (actor.phantom_params.getPhantomAI().isSupport()) {
         this._supports.add(actor);
      } else if (actor.phantom_params.getPhantomAI().isDisabler()) {
         this._disablers.add(actor);
      } else if (actor.phantom_params.getPhantomAI().isTank()) {
         this._tanks.add(actor);
      }

      this._all_members.add(actor);
   }

   public ArrayList<L2PcInstance> getAllMembers() {
      return this._all_members;
   }

   public L2PcInstance getAnyNuker() {
      return this._nukers.isEmpty() ? null : (L2PcInstance)this._nukers.get(Rnd.get(this._nukers.size()));
   }

   public L2PcInstance getAnyHealer() {
      return this._healers.isEmpty() ? null : (L2PcInstance)this._healers.get(Rnd.get(this._healers.size()));
   }

   public L2PcInstance getAnySupport() {
      return this._supports.isEmpty() ? null : (L2PcInstance)this._supports.get(Rnd.get(this._supports.size()));
   }

   public L2PcInstance getAnyDisabler() {
      return this._disablers.isEmpty() ? null : (L2PcInstance)this._disablers.get(Rnd.get(this._disablers.size()));
   }

   public L2PcInstance getAnyTank() {
      return this._tanks.isEmpty() ? null : (L2PcInstance)this._tanks.get(Rnd.get(this._tanks.size()));
   }

   public L2PcInstance getAnyMember() {
      return this._all_members.isEmpty() ? null : (L2PcInstance)this._all_members.get(Rnd.get(this._all_members.size()));
   }

   public L2PcInstance getAnyResurrectMan() {
      L2PcInstance p;
      Iterator var2;
      if (!this._healers.isEmpty()) {
         var2 = this._healers.iterator();

         while(var2.hasNext()) {
            p = (L2PcInstance)var2.next();
            if (!p.isDead()) {
               return p;
            }
         }
      }

      if (!this._supports.isEmpty()) {
         var2 = this._supports.iterator();

         while(var2.hasNext()) {
            p = (L2PcInstance)var2.next();
            if (!p.isDead()) {
               return p;
            }
         }
      }

      return null;
   }

   public boolean isMainAssistTaken() {
      L2Character target_c = this.getAnyNuker().phantom_params.getLockedTarget();
      if (target_c == null) {
         return false;
      } else if (!target_c.isPlayer()) {
         return false;
      } else {
         L2PcInstance target = (L2PcInstance)target_c;
         Iterator var4 = this._all_members.iterator();

         L2PcInstance pl;
         do {
            if (!var4.hasNext()) {
               return true;
            }

            pl = (L2PcInstance)var4.next();
         } while(pl.phantom_params.getPhantomAI().isHealer() || pl.phantom_params.getPhantomAI().isSupport() || target == pl.phantom_params.getLockedTarget());

         return false;
      }
   }

   public boolean isSubAssistTakenByHealers(L2PcInstance target) {
      if (this.getAnyHealer() == null) {
         return true;
      } else {
         Iterator var3 = this._healers.iterator();

         while(var3.hasNext()) {
            L2PcInstance pl = (L2PcInstance)var3.next();
            if (target != pl.phantom_params.getSubTarget()) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isSubAssistTakenBySupports(L2PcInstance target) {
      if (this.getAnySupport() == null) {
         return true;
      } else {
         Iterator var3 = this._supports.iterator();

         while(var3.hasNext()) {
            L2PcInstance pl = (L2PcInstance)var3.next();
            if (target != pl.phantom_params.getSubTarget()) {
               return false;
            }
         }

         return true;
      }
   }

   public void setSubAssist(L2Character target) {
      if (target.getPlayer() != null) {
         L2PcInstance attacker = target.getPlayer();
         L2PcInstance p;
         Iterator var5;
         if (attacker.getParty() != null) {
            label95: {
               var5 = attacker.getParty().getPartyMembers().iterator();

               int id;
               L2PcInstance s;
               Iterator var7;
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!var5.hasNext()) {
                                 break label95;
                              }

                              p = (L2PcInstance)var5.next();
                           } while(p.isDead());
                        } while(p.isSleeping());
                     } while(p.getDistance(this.getPartyLoc()) > 1500.0D);

                     id = p.getClassId().getId();
                     if (!this._healers.isEmpty() && ClassesDictionary.isHealer(id)) {
                        var7 = this._healers.iterator();

                        while(true) {
                           if (!var7.hasNext()) {
                              break label95;
                           }

                           s = (L2PcInstance)var7.next();
                           s.phantom_params.setSubTarget(target);
                        }
                     }
                  } while(this._supports.isEmpty());
               } while(!ClassesDictionary.isHealer(id) && !ClassesDictionary.isMageSupport(id));

               var7 = this._supports.iterator();

               while(var7.hasNext()) {
                  s = (L2PcInstance)var7.next();
                  s.phantom_params.setSubTarget(target);
               }
            }
         }

         if (!this.isSubAssistTakenByHealers(attacker) || !this.isSubAssistTakenBySupports(attacker)) {
            var5 = this._healers.iterator();

            while(var5.hasNext()) {
               p = (L2PcInstance)var5.next();
               p.phantom_params.setSubTarget(target);
            }

            var5 = this._supports.iterator();

            while(var5.hasNext()) {
               p = (L2PcInstance)var5.next();
               p.phantom_params.setSubTarget(target);
            }
         }

      }
   }

   public L2PcInstance getDeadPartyMember() {
      Iterator var2 = this._healers.iterator();

      L2PcInstance p;
      while(var2.hasNext()) {
         p = (L2PcInstance)var2.next();
         if (p.isDead()) {
            return p;
         }
      }

      var2 = this._all_members.iterator();

      while(var2.hasNext()) {
         p = (L2PcInstance)var2.next();
         if (p.isDead()) {
            return p;
         }
      }

      return null;
   }

   public int getDeadPartyMembersCount() {
      int res = 0;
      Iterator var3 = this._all_members.iterator();

      while(var3.hasNext()) {
         L2PcInstance p = (L2PcInstance)var3.next();
         if (p.isDead()) {
            ++res;
         }
      }

      return res;
   }

   public double getPartyPercentDead() {
      double all = (double)this._all_members.size();
      double dead = 0.0D;
      Iterator var6 = this._all_members.iterator();

      while(var6.hasNext()) {
         L2PcInstance p = (L2PcInstance)var6.next();
         if (p.isDead()) {
            ++dead;
         }
      }

      return dead / all;
   }

   public void clearAssists() {
      Iterator var2 = this._all_members.iterator();

      while(var2.hasNext()) {
         L2PcInstance p = (L2PcInstance)var2.next();
         p.phantom_params.setLockedTarget((L2Character)null);
         p.phantom_params.setAttackerTarget((L2Character)null);
         p.phantom_params.setSubTarget((L2Character)null);
      }

   }

   public void takeMainAssist(L2Character target) {
      try {
         Iterator var3 = this._all_members.iterator();

         L2PcInstance attacker;
         while(var3.hasNext()) {
            attacker = (L2PcInstance)var3.next();
            if (!attacker.phantom_params.getPhantomAI().isHealer() && !attacker.phantom_params.getPhantomAI().isSupport()) {
               attacker.phantom_params.setLockedTarget(target);
            }
         }

         attacker = target.getPlayer();
         if (attacker != null && attacker.getParty() != null) {
            Iterator var5 = attacker.getParty().getPartyMembers().iterator();

            int id;
            do {
               do {
                  L2PcInstance p;
                  do {
                     do {
                        do {
                           if (!var5.hasNext()) {
                              return;
                           }

                           p = (L2PcInstance)var5.next();
                        } while(p.isDead());
                     } while(p.isSleeping());
                  } while(p.getDistance(this.getPartyLoc()) > 1500.0D);

                  id = p.getClassId().getId();
               } while(this._disablers.isEmpty());
            } while(!ClassesDictionary.isHealer(id) && !ClassesDictionary.isMageSupport(id));

            Iterator var7 = this._disablers.iterator();

            while(var7.hasNext()) {
               L2PcInstance h = (L2PcInstance)var7.next();
               h.phantom_params.setLockedTarget(target);
            }
         }
      } catch (Exception var8) {
         if (Config.DEBAG_LVL > 0) {
            var8.printStackTrace();
         }
      }

   }

   public void regroup() {
      L2PcInstance leader = this.getAnyHealer();
      if (leader == null) {
         leader = this.getPartyLeader();
      }

      Location loc = leader.getLoc();
      if (Config.DEBAG_LVL > 0) {
         System.out.println("PhantomDefaultPartyAI.regroup() :" + leader.getName());
      }

      this.moveToLocation(loc);
   }

   public void moveToLovelyPlace(L2PcInstance pl) {
      if (Config.DEBAG_LVL > 0) {
         System.out.println("PhantomDefaultPartyAI.moveToLovelyPlace :" + pl.getName());
      }

      this.moveToLocation(this.getNearestLovelyLocation(pl.getLoc()));
   }

   public void moveToLocation(Location loc) {
      if (loc != null) {
         Iterator var3 = this._all_members.iterator();

         while(var3.hasNext()) {
            L2PcInstance p = (L2PcInstance)var3.next();
            if (!p.isOutOfControl()) {
               if (Config.DEBAG_LVL > 0) {
                  System.out.println("PhantomDefaultPartyAI.moveToLocation :" + p.getName());
               }

               p.moveToLocation(loc.getX(), loc.getY(), loc.getZ(), 200, false);
            }
         }

      }
   }

   public void addLovelyLocation(Location loc) {
      this._lovelyPlaces.add(loc);
   }

   public Location getAnyLovelyLocation() {
      return (Location)this._lovelyPlaces.get(Rnd.get(this._lovelyPlaces.size()));
   }

   public Location getNearestLovelyLocation(Location loc) {
      Location result = this.getAnyLovelyLocation();
      Iterator var6 = this._lovelyPlaces.iterator();

      while(var6.hasNext()) {
         Location place = (Location)var6.next();
         double distance = loc.distance(result);
         if (distance > place.distance(loc)) {
            result = place;
         }
      }

      return result;
   }

   public void addSpawnLocation(Location loc) {
      this._spawnPlaces.add(loc);
   }

   public void addSpawnPeaceLocation(Location loc) {
      this._spawnPeacePlaces.add(loc);
   }

   public Location getAnySpawnLocation() {
      return (Location)this._spawnPlaces.get(Rnd.get(this._spawnPlaces.size()));
   }

   public Location getAnySpawnPeaceLocation() {
      return (Location)this._spawnPeacePlaces.get(Rnd.get(this._spawnPeacePlaces.size()));
   }

   public boolean isPartyMember(L2PcInstance player) {
      return this._all_members.contains(player);
   }

   public void randomMove(L2PcInstance phantom, int min_range, int max_range) {
      if (Config.DEBAG_LVL > 0) {
         System.out.println("PhantomDefaultPartyAI.randomMove: " + phantom.getName());
      }

      Location loc = new Location(phantom.getX() + (Rnd.chance(500000) ? 1 : -1) * Rnd.get(min_range, max_range), phantom.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(min_range, max_range), phantom.getZ(), 0);
      if (GeoEngine.canSeeCoord(phantom.getX(), phantom.getY(), phantom.getZ(), loc.getX(), loc.getY(), loc.getZ(), false, phantom.getInstanceId())) {
         phantom.moveToLocation(loc.getX(), loc.getY(), loc.getZ(), 0, false);
      }

   }

   public Location getRandomMove(L2PcInstance phantom, int min_range, int max_range) {
      if (Config.DEBAG_LVL > 0) {
         System.out.println("getRandomMove: " + phantom.getName());
      }

      Location loc = new Location(phantom.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(min_range, max_range), phantom.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(min_range, max_range), phantom.getZ(), 0);
      return GeoEngine.canSeeCoord(phantom.getX(), phantom.getY(), phantom.getZ(), loc.getX(), loc.getY(), loc.getZ(), false, phantom.getInstanceId()) ? loc : null;
   }

   public int size() {
      return this._all_members.size();
   }
}
