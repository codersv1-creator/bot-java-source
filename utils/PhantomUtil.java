package ru.catssoftware.fakes.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.objects.PhantomBuff;
import ru.catssoftware.fakes.objects.PhantomBuffScheme;
import ru.catssoftware.fakes.parsers.reworked.PhantomBuffParser;
import ru.catssoftware.fakes.tables.PhantomSpawnCoordinateInfo;
import ru.catssoftware.gameserver.ai.CtrlIntention;
import ru.catssoftware.gameserver.datatables.SkillTable;
import ru.catssoftware.gameserver.geodata.GeoEngine;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.L2Skill;
import ru.catssoftware.gameserver.model.L2Summon;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2MonsterInstance;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;

public class PhantomUtil {
   protected static final Logger _log = Logger.getLogger(PhantomUtil.class);
   public static final int CRYSTAL_NONE = 0;
   public static final int CRYSTAL_D = 1;
   public static final int CRYSTAL_C = 2;
   public static final int CRYSTAL_B = 3;
   public static final int CRYSTAL_A = 4;
   public static final int CRYSTAL_S = 5;

   public static boolean getAndSetLockedTarget(L2PcInstance phantom, int radius, boolean isPlayer) {
      try {
         if (phantom.phantom_params.getLockedTarget() != null) {
            return true;
         }

         List<L2PcInstance> players = null;
         List<L2MonsterInstance> monsters = null;
         L2Character target = null;
         if (isPlayer) {
            players = phantom.getAroundPlayers(radius, 600);
            if (players == null || players.size() == 0) {
               return false;
            }

            target = (L2Character)players.get(Rnd.get(players.size()));
         } else {
            monsters = phantom.getAroundMonster(radius, 600);
            if (monsters == null || monsters.size() == 0) {
               return false;
            }

            target = (L2Character)monsters.get(Rnd.get(monsters.size()));
         }

         if (target == null) {
            return false;
         }

         if (!GeoEngine.canSeeTarget(phantom, target, false)) {
            return false;
         }

         if (targetCond(phantom, target)) {
            phantom.phantom_params.setLockedTarget(target);
            return true;
         }
      } catch (Exception var6) {
         if (Config.DEBAG_LVL > 0) {
            _log.error(v.m("傟ᘞ썂⻫贀'脀⬜漁䘱眅\ueb23㐳눗\u2baf漢ᝌ慉\u3101䐓\ua9fb廈亦巆≛㛼ᙗ뺧ʢ銳噣ᔣ㺄"));
         }

         if (Config.DEBAG_LVL > 0) {
            var6.printStackTrace();
         }
      }

      return false;
   }

   public static boolean targetCond(L2PcInstance phantom, L2Character target) {
      boolean res = true;
      if (phantom.phantom_params.getPhantomAI().isHealer() && target.getCurrentHp() > 80.0D) {
         res = false;
      }

      if (!target.isPlayer() && !(target instanceof L2MonsterInstance)) {
         res = false;
      }

      if (target.isPlayer() && target.getActingPlayer().getAppearance().isInvisible()) {
         res = false;
      }

      if (target.isPlayer() && target.getPlayer().isOfflineTrade()) {
         res = false;
      }

      if (target.isDead()) {
         res = false;
      }

      if (phantom.phantom_params.getPhantomPartyAI() != null && target.isFantome() && target.getPlayer().phantom_params.getPhantomPartyAI() != null && target.getPlayer().phantom_params.getPhantomPartyAI().getPartyId() == phantom.getPlayer().phantom_params.getPhantomPartyAI().getPartyId()) {
         res = false;
      }

      if (phantom.getClan() != null && phantom.getClan() == target.getPlayer().getClan()) {
         res = false;
      }

      if (phantom.getParty() != null && target.isPlayer() && target.getPlayer().getParty() != null && phantom.getParty() == target.getPlayer().getParty()) {
         res = false;
      }

      return res;
   }

   public static void restorePhantomToHisPeaceLoc(L2PcInstance phantom) {
      LinkedList<PhantomSpawnCoordinateInfo> _locs = phantom.phantom_params.getAllPeaceCoordinates();
      Location loc = ((PhantomSpawnCoordinateInfo)_locs.get(Rnd.get(0, _locs.size() - 1))).loc;
      int collision = 100;

      for(int i = collision; i > 0; --i) {
         if (Config.GEODATA) {
            loc.setZ(GeoEngine.getHeight(loc, phantom.getInstanceId()));
            int _x = loc.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(collision);
            int _y = loc.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(collision);
            int _z = loc.getZ();
            if (GeoEngine.canMoveToCoord(loc.getX(), loc.getY(), loc.getZ(), _x, _y, _z, phantom.getInstanceId())) {
               loc = new Location(_x, _y, _z);
               break;
            }
         }
      }

      phantom.setInstanceId(0);
      phantom.setIsPendingRevive(true);
      phantom.teleToLocation(loc, true);
      if (phantom.getPet() != null) {
         L2Summon pet = phantom.getPet();
         pet.abortAttack();
         pet.abortCast();
         pet.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
         pet.teleToLocation(loc, false);
      }

   }

   public static void restorePhantomToHisBattleLoc(L2PcInstance phantom) {
      LinkedList<PhantomSpawnCoordinateInfo> _locs = phantom.phantom_params.getAllBattleCoordinates();
      if (_locs == null) {
         _locs = phantom.phantom_params.getAllFarmCoordinates();
      }

      Location loc = ((PhantomSpawnCoordinateInfo)_locs.get(Rnd.get(0, _locs.size() - 1))).loc;
      phantom.phantom_params.setIsGoToTeleport(false);
      phantom.phantom_params.setIsGoToTeleport2(false);
      int collision = 60;
      if (Config.GEODATA) {
         loc.setZ(GeoEngine.getHeight(loc, phantom.getInstanceId()));
         int _x = loc.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(collision);
         int _y = loc.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(collision);
         int _z = loc.getZ();
         if (GeoEngine.canMoveToCoord(loc.getX(), loc.getY(), loc.getZ(), _x, _y, _z, phantom.getInstanceId())) {
            phantom.getPosition().setXYZ(_x, _y, _z);
            return;
         }
      }

      phantom.setInstanceId(0);
      phantom.teleToLocation(loc, true);
      if (phantom.getPet() != null) {
         L2Summon pet = phantom.getPet();
         pet.abortAttack();
         pet.abortCast();
         pet.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
         pet.teleToLocation(loc, false);
      }

   }

   public static void doBuff(L2PcInstance phantom) {
      String scheme_name = phantom.phantom_params.getClassAI().getBuffScheme();
      PhantomBuffScheme scheme = PhantomBuffParser.getInstance().getBuffs(scheme_name);
      Iterator var4 = scheme.getBuffList().iterator();

      while(var4.hasNext()) {
         PhantomBuff buff = (PhantomBuff)var4.next();
         L2Skill skill = SkillTable.getInstance().getInfo(buff.id, buff.level);
         if (skill != null) {
            try {
               skill.getEffectsCustom(phantom, phantom);
            } catch (Exception var7) {
            }
         }
      }

   }

   public static Location getRandomPhantomLocation(L2PcInstance phantom) {
      LinkedList<PhantomSpawnCoordinateInfo> coordinates = null;
      if (phantom.isInsideZone((byte)1)) {
         coordinates = phantom.phantom_params.getAllPeaceCoordinates();
      } else {
         if (phantom.phantom_params.getAllBattleCoordinates() != null && !phantom.phantom_params.getAllBattleCoordinates().isEmpty()) {
            coordinates = phantom.phantom_params.getAllBattleCoordinates();
         }

         if (coordinates == null) {
            coordinates = phantom.phantom_params.getAllFarmCoordinates();
         }
      }

      double size = (double)coordinates.size();
      double chance_add = 100.0D / size;
      double chance = chance_add;
      int count = 1;

      for(Iterator var10 = coordinates.iterator(); var10.hasNext(); chance += chance_add) {
         PhantomSpawnCoordinateInfo info = (PhantomSpawnCoordinateInfo)var10.next();
         boolean getloc = Rnd.chance(chance);
         if (Config.DEBAG_LVL > 0) {
            System.out.println(v.m("ồ㺡᱘䅕ᷣ⨌\uecc4ᨥꖦ襠巴葫呡防볆") + count + v.m("Ẵ㺭᱂䅏") + getloc + v.m("Ẵ㺷᱅䄏\u1df4⩗") + size);
         }

         if (getloc) {
            return info.loc;
         }

         ++count;
      }

      return null;
   }

   public static int getGradeFromString(String grade) {
      if (grade != null && !grade.isEmpty()) {
         if (grade.equalsIgnoreCase(v.m("鶵"))) {
            return 1;
         } else if (grade.equalsIgnoreCase(v.m("鶲"))) {
            return 2;
         } else if (grade.equalsIgnoreCase(v.m("鶳"))) {
            return 3;
         } else if (grade.equalsIgnoreCase(v.m("鶰"))) {
            return 4;
         } else {
            return grade.equalsIgnoreCase(v.m("鶢")) ? 5 : 0;
         }
      } else {
         return -1;
      }
   }
}
