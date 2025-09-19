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
import ru.catssoftware.fakes.abstracts.PhantomParser;
import ru.catssoftware.fakes.tables.PhantomSpawnCoordinateInfo;
import ru.catssoftware.fakes.tables.PhantomSpawnInfo;
import ru.catssoftware.fakes.tables.PhantomSpawnObjectInfo;
import ru.catssoftware.gameserver.model.Location;

public class PhantomSpawnParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(PhantomSpawnParser.class);
   private static ArrayList<PhantomSpawnObjectInfo> _fakesSpawnLoc = new ArrayList();
   private static PhantomSpawnParser _instance;

   public static PhantomSpawnParser getInstance() {
      if (_instance == null) {
         _instance = new PhantomSpawnParser();
      }

      return _instance;
   }

   public static ArrayList<PhantomSpawnObjectInfo> getSpawnLocs() {
      return _fakesSpawnLoc;
   }

   public void allParse() {
      _fakesSpawnLoc.clear();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/spawn");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            ArrayList<PhantomSpawnObjectInfo> s = this.loadObjects(file, "SpawnObject");
            if (s != null && !s.isEmpty()) {
               _fakesSpawnLoc.addAll(s);
            }
         }

         _log.info("SpawnHolder: Loaded " + _fakesSpawnLoc.size() + " phantom spawns from XML files.");
      }
   }

   protected PhantomSpawnObjectInfo parseObj(Node spawnObject) {
      NamedNodeMap attrs = spawnObject.getAttributes();
      String ai = attrs.getNamedItem("ai_type").getNodeValue();
      String is_enabled = "false";
      if (attrs.getNamedItem("is_spawn_enabled") != null) {
         is_enabled = attrs.getNamedItem("is_spawn_enabled").getNodeValue();
      }

      try {
         boolean is_spawn_enabled = Boolean.parseBoolean(is_enabled);
         PhantomSpawnObjectInfo spawn_object = new PhantomSpawnObjectInfo(ai, is_spawn_enabled);

         for(Node spawn = spawnObject.getFirstChild(); spawn != null; spawn = spawn.getNextSibling()) {
            if (spawn.getNodeName().equalsIgnoreCase("spawn")) {
               NamedNodeMap attributes = spawn.getAttributes();
               int count = Integer.parseInt(attributes.getNamedItem("count").getNodeValue());
               int peace_cooldown = Integer.parseInt(attributes.getNamedItem("peace_cooldown").getNodeValue());
               int comeback_distance = Integer.parseInt(attributes.getNamedItem("comeback_distance").getNodeValue());
               int handicap_attack = Integer.parseInt(attributes.getNamedItem("handicap_attack").getNodeValue());
               int handicap_defence = Integer.parseInt(attributes.getNamedItem("handicap_defence").getNodeValue());
               PhantomSpawnInfo spawn_info = new PhantomSpawnInfo(count, (double)peace_cooldown, comeback_distance, (double)handicap_attack, (double)handicap_defence);

               for(Node coord = spawn.getFirstChild(); coord != null; coord = coord.getNextSibling()) {
                  if (coord.getNodeName().equalsIgnoreCase("coord_farm")) {
                     spawn_info.addFarmCoordinate(this.parseSpawn(coord, ai));
                  } else if (coord.getNodeName().equalsIgnoreCase("coord_battle")) {
                     spawn_info.addBattleCoordinate(this.parseSpawn(coord, ai));
                  } else if (coord.getNodeName().equalsIgnoreCase("coord_peace")) {
                     spawn_info.addPeaceCoordinate(this.parseSpawn(coord, ai));
                  }
               }

               spawn_object.addSpawn(spawn_info);
            }
         }

         return spawn_object;
      } catch (Exception var16) {
         _log.error("Error loading spawn for ai: " + ai, var16);
         return null;
      }
   }

   protected PhantomSpawnCoordinateInfo parseSpawn(Node n, String ai) {
      if (n == null) {
         return null;
      } else {
         String point = "";
         int i_x = 0;
         int i_y = 0;
         int i_z = 0;
         int i_priority = 0;
         NamedNodeMap attrs = n.getAttributes();
         if (attrs.getNamedItem("spawn_point") != null) {
            point = attrs.getNamedItem("spawn_point").getNodeValue();
            i_x = Integer.parseInt(point.split(";")[0]);
            i_y = Integer.parseInt(point.split(";")[1]);
            i_z = Integer.parseInt(point.split(";")[2]);
         }

         if (attrs.getNamedItem("point_priority") != null) {
            i_priority = Integer.parseInt(attrs.getNamedItem("point_priority").getNodeValue());
         }

         return new PhantomSpawnCoordinateInfo(new Location(i_x, i_y, i_z), i_priority);
      }
   }
}
