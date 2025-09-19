package ru.catssoftware.fakes.holders;

import gnu.trove.map.hash.TIntObjectHashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import org.apache.log4j.Logger;
import ru.catssoftware.L2DatabaseFactory;
import ru.catssoftware.fakes.PhantomsEngine;
import ru.catssoftware.fakes.dao.PhantomDAO;
import ru.catssoftware.fakes.tables.PhantomInfo;
import ru.catssoftware.tools.random.Rnd;

public class PhantomsHolder {
   private static final Logger _log = Logger.getLogger(PhantomsHolder.class);
   private static TIntObjectHashMap<PhantomInfo> _fakes;

   public static void load() {
      _fakes = new TIntObjectHashMap();
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         con.setTransactionIsolation(1);
         st = con.prepareStatement("SELECT * FROM phantoms");
         rs = st.executeQuery();
         rs.setFetchSize(250);

         while(rs.next()) {
            PhantomInfo info = new PhantomInfo(rs.getString("char_name"), rs.getString("title"), rs.getInt("x"), rs.getInt("y"), rs.getInt("z"), rs.getInt("clanid"), rs.getInt("charId"), rs.getInt("sex"), rs.getInt("race"));
            _fakes.put(rs.getInt("charId"), info);
         }
      } catch (Exception var12) {
         _log.warn("PhantomsEngine: could not load chars from DB: " + var12);
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }

            if (st != null) {
               st.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var11) {
         }

      }

      _log.info("Engine: Cached " + _fakes.size() + " players.");
   }

   public static int getRandomFake() {
      int index = Rnd.get(_fakes.size());
      PhantomInfo info = (PhantomInfo)_fakes.values()[index];
      return info.charId;
   }

   public static boolean isFakesReady() {
      return _fakes != null && !_fakes.isEmpty();
   }

   public static PhantomInfo getFake(int objId) {
      return (PhantomInfo)_fakes.get(objId);
   }

   public static TIntObjectHashMap<PhantomInfo> getFakes() {
      return _fakes;
   }

   public static PhantomInfo getFakeByClassId(int class_id, int clan_id) {
      Iterator var3 = _fakes.valueCollection().iterator();

      while(true) {
         PhantomInfo info;
         int fakeSubId;
         do {
            do {
               if (!var3.hasNext()) {
                  return null;
               }

               info = (PhantomInfo)var3.next();
               fakeSubId = PhantomDAO.getInstance().getSubclassByObjId(info.charId);
            } while(fakeSubId != class_id);
         } while(clan_id != -1 && info.clanId != clan_id);

         if (!PhantomsEngine.getInstance().getAllPartyObjID().contains(info.charId)) {
            if (!PhantomsEngine.getInstance().isPhantomAlreadySpawned(info.charId)) {
               return info;
            }

            if (PhantomsEngine.getInstance().getPhantom(info.charId).phantom_params.getPhantomPartyAI() == null) {
               PhantomsEngine.getInstance().despawnPhantom(info.charId);
               return info;
            }
         }
      }
   }

   public static TIntObjectHashMap<PhantomInfo> getAllFakes() {
      return _fakes;
   }
}
