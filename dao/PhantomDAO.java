package ru.catssoftware.fakes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javolution.util.FastMap;
import org.apache.log4j.Logger;
import ru.catssoftware.L2DatabaseFactory;
import ru.catssoftware.gameserver.datatables.ClanTable;
import ru.catssoftware.gameserver.model.L2Clan;
import ru.catssoftware.tools.random.Rnd;

public class PhantomDAO {
   private static final Logger _log = Logger.getLogger(PhantomDAO.class);
   private static final PhantomDAO _instance = new PhantomDAO();
   private static int[] clan_ids;
   public static FastMap<Integer, Integer> CLASSID_AND_OID;
   public static final String SELECT_ALL_CLAN_IDS = "SELECT clan_id FROM clan_data WHERE is_fake = 1";

   public static PhantomDAO getInstance() {
      return _instance;
   }

   public void loadClans() {
      clan_ids = getInstance().getAllPhantomClanIDsFromDB();
      _log.info("Loaded: " + clan_ids.length + " phantom clans.");
   }

   public void Generate() {
      CLASSID_AND_OID = (new FastMap()).shared("PhantomDAO.CLASSID_AND_OID");
   }

   public boolean isPhantom(int charId) {
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("SELECT char_name FROM phantoms WHERE charId=?");
         statement.setInt(1, charId);
         rset = statement.executeQuery();
         if (!rset.next()) {
            return false;
         }
      } catch (Exception var15) {
         _log.error("CharNameTable.getSubclassByObjId(Int): " + var15, var15);
         return false;
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
         } catch (SQLException var14) {
         }

      }

      return true;
   }

   public void setClanToPhantom(int charId) {
      Connection con = null;
      PreparedStatement statement = null;
      Object rset = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("UPDATE phantoms SET clanid = ? WHERE charId=?");
         statement.setInt(1, clan_ids[Rnd.get(clan_ids.length)]);
         statement.setInt(2, charId);
         statement.execute();
      } catch (Exception var14) {
         _log.warn("Could not restore char sub-classes: " + var14);
         _log.error("", var14);
      } finally {
         try {
            if (rset != null) {
               ((ResultSet)rset).close();
            }

            if (statement != null) {
               statement.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var13) {
         }

      }

   }

   public ArrayList<L2Clan> getAllPhantomClansFromDB() {
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;
      ArrayList _clans = new ArrayList();

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("SELECT clan_id FROM clan_data WHERE is_fake = 1");
         statement.setInt(1, 1);
         rset = statement.executeQuery();

         while(rset.next()) {
            _clans.add(ClanTable.getInstance().getClan(rset.getInt("clan_id")));
         }

         ArrayList var7 = _clans;
         return var7;
      } catch (Exception var15) {
         _log.error("PhantomDataDAO.getOwner(Residence, String)", var15);
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
         } catch (SQLException var14) {
         }

      }

      return null;
   }

   public int[] getAllPhantomClanIDsFromDB() {
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;
      ArrayList _clans = new ArrayList();

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("SELECT clan_id FROM clan_data WHERE is_fake = 1");
         rset = statement.executeQuery();

         while(rset.next()) {
            L2Clan clan = ClanTable.getInstance().getClan(rset.getInt("clan_id"));
            if (clan != null) {
               _clans.add(clan);
               _log.info("Loaded: " + rset.getInt("clan_id") + "; " + clan.getName() + " phantom clan");
            }
         }

         int[] res = new int[_clans.size()];
         int i = 0;

         for(Iterator var8 = _clans.iterator(); var8.hasNext(); ++i) {
            L2Clan clan = (L2Clan)var8.next();
            res[i] = clan.getClanId();
         }

         int[] var10 = res;
         return var10;
      } catch (Exception var18) {
         _log.error("PhantomDataDAO.getOwner(Residence, String)", var18);
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
         } catch (SQLException var17) {
         }

      }

      return null;
   }

   public int getSubclassByObjId(int charId) {
      int result = 0;
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("SELECT class_id FROM phantoms WHERE charId=?");
         statement.setInt(1, charId);
         rset = statement.executeQuery();
         if (rset.next()) {
            result = rset.getInt(1);
         }
      } catch (Exception var15) {
         _log.error("CharNameTable.getSubclassByObjId(Int): " + var15, var15);
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
         } catch (SQLException var14) {
         }

      }

      return result;
   }

   public int getCharIdByClassId(int ClassId) {
      return CLASSID_AND_OID.containsValue(ClassId) ? (Integer)CLASSID_AND_OID.get(ClassId) : -1;
   }

   public FastMap<Integer, Integer> getCharIdAndClassId() {
      return CLASSID_AND_OID;
   }

   public void loadCharIdAndClassId() {
      CLASSID_AND_OID = (new FastMap()).shared("PhantomDAO.CLASSID_AND_OID");
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("SELECT * FROM phantoms");
         rset = statement.executeQuery();

         while(rset.next()) {
            CLASSID_AND_OID.put(rset.getInt(1), rset.getInt(9));
         }
      } catch (Exception var13) {
         _log.error("CharNameTable.getSubclassByObjId(Int): " + var13, var13);
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
         } catch (SQLException var12) {
         }

      }

   }

   public boolean isHasEquip(int charId) {
      int result = false;
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("SELECT class_id FROM items WHERE char_charId=?");
         statement.setInt(1, charId);
         rset = statement.executeQuery();
         if (rset.next()) {
            int var17 = rset.getInt(1);
         }
      } catch (Exception var15) {
         _log.error("CharNameTable.getSubclassByObjId(Int): " + var15, var15);
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
         } catch (SQLException var14) {
         }

      }

      return false;
   }

   public void DeletingItems(int charId) {
      int result = false;
      Connection con = null;
      PreparedStatement statement = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("DELETE FROM items WHERE owner_id=?");
         statement.setInt(1, charId);
         statement.execute();
      } catch (Exception var14) {
         _log.error("CharNameTable.getSubclassByObjId(Int): " + var14, var14);
      } finally {
         try {
            if (statement != null) {
               statement.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var13) {
         }

      }

   }

   public int getObjectIdByName(String name) {
      int result = 0;
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement("SELECT charId FROM phantoms WHERE char_name=?");
         statement.setString(1, name);
         rset = statement.executeQuery();
         if (rset.next()) {
            result = rset.getInt(1);
         }
      } catch (Exception var15) {
         _log.error("PhantomNameTable.getObjectIdByName(String): " + var15, var15);
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
         } catch (SQLException var14) {
         }

      }

      return result;
   }
}
