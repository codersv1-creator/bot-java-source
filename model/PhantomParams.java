package ru.catssoftware.fakes.model;

import java.util.LinkedList;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomDefaultAI;
import ru.catssoftware.fakes.ai.individualbehavior.mages.battle.Archmage;
import ru.catssoftware.fakes.ai.individualbehavior.mages.battle.MysticMuse;
import ru.catssoftware.fakes.ai.individualbehavior.mages.battle.Soultaker;
import ru.catssoftware.fakes.ai.individualbehavior.mages.battle.StormScreamer;
import ru.catssoftware.fakes.ai.individualbehavior.mages.healers.Cardinal;
import ru.catssoftware.fakes.ai.individualbehavior.mages.summoners.ArcanaLord;
import ru.catssoftware.fakes.ai.individualbehavior.mages.summoners.ElementalMaster;
import ru.catssoftware.fakes.ai.individualbehavior.mages.summoners.SpectralMaster;
import ru.catssoftware.fakes.ai.individualbehavior.mages.supports.Dominator;
import ru.catssoftware.fakes.ai.individualbehavior.mages.supports.Doomcryer;
import ru.catssoftware.fakes.ai.individualbehavior.mages.supports.EvaSaint;
import ru.catssoftware.fakes.ai.individualbehavior.mages.supports.Hierophant;
import ru.catssoftware.fakes.ai.individualbehavior.mages.supports.ShilienSaint;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.Warrior;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.archers.GhostSentinel;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.archers.MoonlightSentinel;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.archers.Saggittarius;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.charging.Duelist;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.charging.GrandKhavatari;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.daggers.Adventurer;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.daggers.GhostHunter;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.daggers.WindRider;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.supports.SpectralDancer;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.supports.SwordMuse;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.tanks.EvaTemplar;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.tanks.HellKnight;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.tanks.PhoenixKnight;
import ru.catssoftware.fakes.ai.individualbehavior.warriors.tanks.ShilienTemplar;
import ru.catssoftware.fakes.objects.PhantomClassAI;
import ru.catssoftware.fakes.objects.PhantomPartyObject;
import ru.catssoftware.fakes.tables.PhantomSpawnCoordinateInfo;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.L2ItemInstance;
import ru.catssoftware.gameserver.model.L2World;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2MonsterInstance;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.model.base.ClassId;

public class PhantomParams {
   private L2PcInstance actor;
   private volatile PhantomDefaultAI _phantomAi;
   private volatile PhantomPartyObject _phantomPartyAi;
   private boolean _isResurrecting = false;
   private boolean _isGoToTeleport = false;
   private boolean _isGoToTeleport2 = false;
   private L2Character _lockedTarget = null;
   private L2Character _attacker = null;
   private L2Character _subTarget = null;
   private Location _lockedTargetFirstLocation = null;
   private double peace_cooldown = 0.0D;
   private LinkedList<PhantomSpawnCoordinateInfo> battle_coordinates;
   private LinkedList<PhantomSpawnCoordinateInfo> farm_coordinates;
   private LinkedList<PhantomSpawnCoordinateInfo> peace_coordinates;
   private L2ItemInstance phantom_weapon = null;
   PhantomClassAI class_ai;
   private int level_loc = 1;

   public PhantomParams(L2PcInstance player) {
      this.actor = player;
      this.battle_coordinates = new LinkedList();
      this.farm_coordinates = new LinkedList();
      this.peace_coordinates = new LinkedList();
   }

   public void setPeaceCooldown(double val) {
      this.peace_cooldown = val;
   }

   public double getPeaceCooldown() {
      return this.peace_cooldown;
   }

   public void setBattleCoords(LinkedList<PhantomSpawnCoordinateInfo> coords) {
      this.battle_coordinates.addAll(coords);
   }

   public void setFarmCoords(LinkedList<PhantomSpawnCoordinateInfo> coords) {
      this.farm_coordinates.addAll(coords);
   }

   public void setPeaceCoords(LinkedList<PhantomSpawnCoordinateInfo> coords) {
      this.peace_coordinates.addAll(coords);
   }

   public LinkedList<PhantomSpawnCoordinateInfo> getAllBattleCoordinates() {
      return this.battle_coordinates;
   }

   public LinkedList<PhantomSpawnCoordinateInfo> getAllFarmCoordinates() {
      return this.farm_coordinates;
   }

   public LinkedList<PhantomSpawnCoordinateInfo> getAllPeaceCoordinates() {
      return this.peace_coordinates;
   }

   public void setLockedTarget(L2Character target) {
      try {
         this._lockedTarget = target;
      } catch (Exception var3) {
         if (Config.DEBAG_LVL > 0) {
            var3.printStackTrace();
         }
      }

   }

   public L2Character getLockedTarget() {
      return this._lockedTarget;
   }

   public void setSubTarget(L2Character target) {
      this._subTarget = target;
   }

   public L2Character getSubTarget() {
      return this._subTarget;
   }

   public boolean isLockedTargetHere() {
      if (this._lockedTarget == null) {
         return false;
      } else if (this._lockedTarget instanceof L2MonsterInstance) {
         return true;
      } else {
         return L2World.getInstance().getPlayer(this._lockedTarget.getName()) != null;
      }
   }

   public void setAttackerTarget(L2Character target) {
      this._attacker = target;
   }

   public L2Character getAttackerTarget() {
      return this._attacker;
   }

   public void setLockedTargetFirstLocation(Location loc) {
      this._lockedTargetFirstLocation = loc;
   }

   public Location getLockedTargetFirstLocation() {
      return this._lockedTargetFirstLocation;
   }

   public void setIsResurrecting(boolean val) {
      this._isResurrecting = val;
   }

   public boolean isResurrecting() {
      return this._isResurrecting;
   }

   public void setIsGoToTeleport(boolean val) {
      this._isGoToTeleport = val;
   }

   public boolean isGoToTeleport() {
      return this._isGoToTeleport;
   }

   public void setIsGoToTeleport2(boolean val) {
      this._isGoToTeleport2 = val;
   }

   public boolean isGoToTeleport2() {
      return this._isGoToTeleport2;
   }

   public void setPhantomWeapon(L2ItemInstance weap) {
      this.phantom_weapon = weap;
   }

   public L2ItemInstance getPhantomWeapon() {
      return this.phantom_weapon;
   }

   public void setPhantomClassAI(PhantomClassAI class_ai) {
      this.class_ai = class_ai;
   }

   public PhantomClassAI getClassAI() {
      return this.class_ai;
   }

   public int getLevelLoc() {
      return this.level_loc;
   }

   public void setLevelLoc(int lvl) {
      this.level_loc = lvl;
   }

   public void setPhantomAI() {
      Object ai;
      if (this.actor.getClassId() == ClassId.stormScreamer) {
         ai = new StormScreamer();
      } else if (this.actor.getClassId() == ClassId.soultaker) {
         ai = new Soultaker();
      } else if (this.actor.getClassId() == ClassId.archmage) {
         ai = new Archmage();
      } else if (this.actor.getClassId() == ClassId.mysticMuse) {
         ai = new MysticMuse();
      } else if (this.actor.getClassId() == ClassId.arcanaLord) {
         ai = new ArcanaLord();
      } else if (this.actor.getClassId() == ClassId.spectralMaster) {
         ai = new SpectralMaster();
      } else if (this.actor.getClassId() == ClassId.elementalMaster) {
         ai = new ElementalMaster();
      } else if (this.actor.getClassId() == ClassId.dominator) {
         ai = new Dominator();
      } else if (this.actor.getClassId() == ClassId.doomcryer) {
         ai = new Doomcryer();
      } else if (this.actor.getClassId() == ClassId.duelist) {
         ai = new Duelist();
      } else if (this.actor.getClassId() == ClassId.ghostHunter) {
         ai = new GhostHunter();
      } else if (this.actor.getClassId() == ClassId.adventurer) {
         ai = new Adventurer();
      } else if (this.actor.getClassId() == ClassId.ghostSentinel) {
         ai = new GhostSentinel();
      } else if (this.actor.getClassId() == ClassId.moonlightSentinel) {
         ai = new MoonlightSentinel();
      } else if (this.actor.getClassId() == ClassId.sagittarius) {
         ai = new Saggittarius();
      } else if (this.actor.getClassId() == ClassId.grandKhauatari) {
         ai = new GrandKhavatari();
      } else if (this.actor.getClassId() == ClassId.windRider) {
         ai = new WindRider();
      } else if (this.actor.getClassId() == ClassId.evaTemplar) {
         ai = new EvaTemplar();
      } else if (this.actor.getClassId() == ClassId.shillienTemplar) {
         ai = new ShilienTemplar();
      } else if (this.actor.getClassId() == ClassId.phoenixKnight) {
         ai = new PhoenixKnight();
      } else if (this.actor.getClassId() == ClassId.hellKnight) {
         ai = new HellKnight();
      } else if (this.actor.getClassId() == ClassId.cardinal) {
         ai = new Cardinal();
      } else if (this.actor.getClassId() == ClassId.evaSaint) {
         ai = new EvaSaint();
      } else if (this.actor.getClassId() == ClassId.hierophant) {
         ai = new Hierophant();
      } else if (this.actor.getClassId() == ClassId.shillienSaint) {
         ai = new ShilienSaint();
      } else if (this.actor.getClassId() == ClassId.spectralDancer) {
         ai = new SpectralDancer();
      } else if (this.actor.getClassId() == ClassId.swordMuse) {
         ai = new SwordMuse();
      } else {
         ai = new Warrior();
      }

      this.setPhantomAI((PhantomDefaultAI)ai);
   }

   public void setPhantomAI(PhantomDefaultAI ai) {
      this._phantomAi = ai;
      this._phantomAi.setActor(this.actor);
   }

   public PhantomDefaultAI getPhantomAI() {
      return this._phantomAi;
   }

   public void setPhantomPartyAI(PhantomPartyObject ai) {
      this._phantomPartyAi = ai;
   }

   public PhantomPartyObject getPhantomPartyAI() {
      return this._phantomPartyAi;
   }
}
