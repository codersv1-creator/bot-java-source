package ru.catssoftware.fakes.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.L2DatabaseFactory;
import ru.catssoftware.gameserver.datatables.CharTemplateTable;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.model.base.Experience;
import ru.catssoftware.gameserver.model.base.SubClass;
import ru.catssoftware.gameserver.templates.chars.L2PcTemplate;
import ru.catssoftware.tools.random.Rnd;

public class PhantomFactory {
   private static final Logger _log = Logger.getLogger(PhantomFactory.class);

   public static L2PcInstance createFake(int objectId) {
      L2PcInstance player = null;

      try {
         player = restoreFakeCharSubClasses(objectId);
         player.setHeading(Rnd.get(1, 65535));
         player.setKarma(0);
         player.setPvpKills(0);
         player.setPkKills(0);
         player.setOnlineTime(0L);
         player.setNoble(Rnd.get(0, 1) != 0);
         player.setHero(false);
         player.setSubPledgeType(0);
         player.setDeleteTimer(0L);
         player.setFistsWeaponItem(player.findFistsWeaponItem(player.getActiveClass()));
         player.setUptime(System.currentTimeMillis());
         player.setApprentice(0);
         player.setLvlJoinedAcademy(0);
         long pXp = player.getExp();
         long tXp = player.getActiveClass() > 118 ? Experience.LEVEL[80] : Experience.LEVEL[76];
         player.addExpAndSp(tXp - pXp, 0);
         player.getStatus().setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
         if (player instanceof L2PcInstance) {
            player.getStatus().setCurrentCp((double)player.getMaxCp());
         }

         try {
            player.stopAllTimers();
         } catch (Throwable var7) {
         }
      } catch (Exception var8) {
         if (Config.DEBAG_LVL > 0) {
            var8.printStackTrace();
         }
      }

      return player;
   }

   public static int getClassId(int objectId, int class_id) {
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;
      int classId = -1;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("SELECT class_id FROM phantoms WHERE charId=?");
         statement.setInt(1, objectId);
         rset = statement.executeQuery();

         do {
            if (!rset.next()) {
               return classId;
            }

            classId = rset.getInt("class_id");
         } while(classId == class_id);
      } catch (Exception var16) {
         _log.warn("Could not restore char sub-classes: " + var16);
         _log.error("", var16);
         return classId;
      } finally {
         try {
            if (rset != null) {
               rset.close();
            }

            if (statement != null) {
               statement.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var15) {
         }

      }

      return -1;
   }

   public static L2PcInstance restoreFakeCharSubClasses(int charId) {
      Connection con = null;
      PreparedStatement st = null;
      PreparedStatement st2 = null;
      PreparedStatement st3 = null;
      ResultSet rset = null;
      ResultSet rset2 = null;
      ResultSet rset3 = null;
      boolean var8 = false;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         st = con.prepareStatement("SELECT class_id FROM phantoms WHERE charId=?");
         st.setInt(1, charId);
         rset = st.executeQuery();
         rset.next();
         st2 = con.prepareStatement("SELECT sex FROM phantoms WHERE charId=?");
         st2.setInt(1, charId);
         rset2 = st2.executeQuery();
         rset2.next();
         SubClass subClass = new SubClass();
         boolean female = rset2.getInt("sex") != 0;
         int class_id = rset.getInt("class_id");
         subClass.setClassId(class_id);
         L2PcTemplate template = CharTemplateTable.getInstance().getTemplate(class_id);
         L2PcInstance player = L2PcInstance.createFakePlayer(charId, template, female);
         L2PcInstance var14 = player;
         return var14;
      } catch (Exception var22) {
         if (Config.DEBAG_LVL > 0) {
            var22.printStackTrace();
         }

         if (Config.DEBAG_LVL > 0) {
            _log.warn("Could not restore char sub-classes: " + var22);
         }

         if (Config.DEBAG_LVL > 0) {
            _log.error("", var22);
         }
      } finally {
         try {
            if (rset != null) {
               rset.close();
            }

            if (rset2 != null) {
               rset2.close();
            }

            if (st != null) {
               st.close();
            }

            if (st2 != null) {
               st2.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var21) {
         }

      }

      return null;
   }
}
