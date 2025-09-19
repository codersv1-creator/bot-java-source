package ru.catssoftware.fakes.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomDefaultPartyAI;
import ru.catssoftware.fakes.abstracts.PhantomParser;
import ru.catssoftware.fakes.ai.party.PhantomArcherPartyAI;
import ru.catssoftware.fakes.ai.party.PhantomDaggerPartyAI;
import ru.catssoftware.fakes.ai.party.PhantomLimitPartyAI;
import ru.catssoftware.fakes.ai.party.PhantomMagePartyAI;
import ru.catssoftware.fakes.ai.party.PhantomStopPartyAI;
import ru.catssoftware.fakes.ai.party.PhantomWarriorPartyAI;
import ru.catssoftware.fakes.objects.PartyType;
import ru.catssoftware.fakes.objects.PhantomPartyObject;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.util.StatsSet;

public class PartyAIParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(PartyAIParser.class);
   private static ArrayList<PhantomPartyObject> parties;
   private static PartyAIParser _instance;

   public static PartyAIParser getInstance() {
      if (_instance == null) {
         _instance = new PartyAIParser();
      }

      return _instance;
   }

   public ArrayList<PhantomPartyObject> getAllParties() {
      return parties;
   }

   public PhantomDefaultPartyAI getPartyAIByID(int id) {
      PhantomDefaultPartyAI res = null;
      Iterator var4 = parties.iterator();

      while(var4.hasNext()) {
         PhantomDefaultPartyAI obj = (PhantomDefaultPartyAI)var4.next();
         if (obj.getPartyId() == id) {
            res = obj;
            break;
         }
      }

      return res;
   }

   public ArrayList<PhantomPartyObject> allParse() {
      parties = new ArrayList();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/party_ai");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
         return parties;
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(true) {
            while(var4.hasNext()) {
               File file = (File)var4.next();
               ArrayList<PhantomPartyObject> s = this.loadObjects(file, "PartyObject");
               if (s != null && !s.isEmpty()) {
                  parties.addAll(s);
               } else {
                  _log.info("PartyAIHolder: Empty");
               }
            }

            _log.info("PartyAIHolder: Loaded " + parties.size() + " phantom parties from XML files.");
            return parties;
         }
      }
   }

   protected PhantomPartyObject parseObj(Node setObject) {
      StatsSet stats = new StatsSet();
      PhantomPartyObject party_ai = null;
      Location coord_s = null;
      Location coord_r = null;
      Location coord_p = null;

      try {
         for(Node first_child = setObject.getFirstChild(); first_child != null; first_child = first_child.getNextSibling()) {
            if (first_child.getNodeName().equalsIgnoreCase("config")) {
               for(Node set = first_child.getFirstChild(); set != null; set = set.getNextSibling()) {
                  if (set.getNodeName().equalsIgnoreCase("set")) {
                     NamedNodeMap attrs = set.getAttributes();
                     int inte = this.parseStatInt(attrs, "partyId");
                     if (inte != -1) {
                        stats.set("partyId", inte);
                     }

                     if (this.parseStat(attrs, "partyType") != null) {
                        stats.set("partyType", this.parseStat(attrs, "partyType"));
                     }

                     if (this.parseStat(attrs, "isSpawnEnabled") != null) {
                        stats.set("isSpawnEnabled", this.parseStatBool(attrs, "isSpawnEnabled"));
                     }

                     if (this.parseStat(attrs, "class_lists") != null) {
                        stats.set("class_lists", this.parseStat(attrs, "class_lists"));
                     }

                     inte = this.parseStatInt(attrs, "partyClan");
                     if (inte != -1) {
                        stats.set("partyClan", inte);
                     }

                     inte = this.parseStatInt(attrs, "partyCooldown");
                     if (inte != -1) {
                        stats.set("partyCooldown", inte);
                     }

                     inte = this.parseStatInt(attrs, "handicapDefence");
                     if (inte != -1) {
                        stats.set("handicapDefence", inte);
                     }

                     inte = this.parseStatInt(attrs, "handicapAttack");
                     if (inte != -1) {
                        stats.set("handicapAttack", inte);
                     }

                     inte = this.parseStatInt(attrs, "regroupToLeaderChance");
                     if (inte != -1) {
                        stats.set("regroupToLeaderChance", inte);
                     }

                     inte = this.parseStatInt(attrs, "regroupToPlaceChance");
                     if (inte != -1) {
                        stats.set("regroupToPlaceChance", inte);
                     }

                     inte = this.parseStatInt(attrs, "randomMoveChance");
                     if (inte != -1) {
                        stats.set("randomMoveChance", inte);
                     }
                  }
               }
            } else if (first_child.getNodeName().equalsIgnoreCase("coord_s")) {
               coord_s = this.parseCoords(first_child);
            } else if (first_child.getNodeName().equalsIgnoreCase("coord_r")) {
               coord_r = this.parseCoords(first_child);
            } else if (first_child.getNodeName().equalsIgnoreCase("coord_p")) {
               coord_p = this.parseCoords(first_child);
            }
         }

         PartyType partyType = PartyType.valueOf(stats.getString("partyType"));
         if (partyType == PartyType.mage) {
            party_ai = new PhantomMagePartyAI(stats);
         } else if (partyType == PartyType.archer) {
            party_ai = new PhantomArcherPartyAI(stats);
         } else if (partyType == PartyType.limit) {
            party_ai = new PhantomLimitPartyAI(stats);
         } else if (partyType == PartyType.dagger) {
            party_ai = new PhantomDaggerPartyAI(stats);
         } else if (partyType == PartyType.warrior) {
            party_ai = new PhantomWarriorPartyAI(stats);
         } else if (partyType == PartyType.stop) {
            party_ai = new PhantomStopPartyAI(stats);
         } else {
            party_ai = new PhantomPartyObject(stats);
         }

         ((PhantomPartyObject)party_ai).addSpawnLocation(coord_s);
         ((PhantomPartyObject)party_ai).addLovelyLocation(coord_r);
         ((PhantomPartyObject)party_ai).addSpawnPeaceLocation(coord_p);
      } catch (Exception var13) {
         if (Config.DEBAG_LVL > 0) {
            var13.printStackTrace();
         }
      } finally {
         ;
      }

      return (PhantomPartyObject)party_ai;
   }

   public int parseStatInt(NamedNodeMap map, String stat) {
      String val = this.parseStat(map, stat);
      if (val == null) {
         return -1;
      } else {
         try {
            return Integer.parseInt(val);
         } catch (Exception var5) {
            if (Config.DEBAG_LVL > 0) {
               var5.printStackTrace();
            }

            return -1;
         }
      }
   }

   public int[] parseStatIntArray(NamedNodeMap map, String stat) {
      String val = this.parseStat(map, stat);
      if (val == null) {
         return null;
      } else {
         try {
            String[] str_res = val.split(";");
            int[] res = new int[str_res.length];

            for(int i = 0; i < str_res.length; ++i) {
               res[i] = Integer.parseInt(str_res[i]);
            }

            return res;
         } catch (Exception var7) {
            if (Config.DEBAG_LVL > 0) {
               var7.printStackTrace();
            }

            return null;
         }
      }
   }

   public boolean parseStatBool(NamedNodeMap map, String stat) {
      String val = this.parseStat(map, stat);
      if (val == null) {
         return false;
      } else {
         try {
            return Boolean.parseBoolean(val);
         } catch (Exception var5) {
            if (Config.DEBAG_LVL > 0) {
               var5.printStackTrace();
            }

            return false;
         }
      }
   }

   public String parseStat(NamedNodeMap map, String stat) {
      if (map.getNamedItem("stat") == null) {
         return null;
      } else {
         return !map.getNamedItem("stat").getNodeValue().equalsIgnoreCase(stat) ? null : map.getNamedItem("val").getNodeValue();
      }
   }

   protected Location parseCoords(Node n) {
      if (n == null) {
         return null;
      } else {
         NamedNodeMap attrs = n.getAttributes();
         int x = Integer.parseInt(attrs.getNamedItem("x").getNodeValue());
         int y = Integer.parseInt(attrs.getNamedItem("y").getNodeValue());
         int z = Integer.parseInt(attrs.getNamedItem("z").getNodeValue());
         return new Location(x, y, z);
      }
   }
}
