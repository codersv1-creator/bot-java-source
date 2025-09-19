package ru.catssoftware.fakes.parsers.reworked;

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
import ru.catssoftware.fakes.objects.equip.ArmorObject;
import ru.catssoftware.fakes.objects.sets.ArmorSet;
import ru.catssoftware.util.StatsSet;

public class PhantomArmorParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(PhantomArmorParser.class);
   private static ArrayList<ArmorSet> _armors;
   private static PhantomArmorParser _instance;

   public static PhantomArmorParser getInstance() {
      if (_instance == null) {
         _instance = new PhantomArmorParser();
      }

      return _instance;
   }

   public ArmorSet getArmorSet(String name) {
      Iterator var3 = _armors.iterator();

      while(var3.hasNext()) {
         ArmorSet set = (ArmorSet)var3.next();
         if (set.getName().equalsIgnoreCase(name)) {
            return set;
         }
      }

      return null;
   }

   public void allParse() {
      _armors = new ArrayList();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/items/armor");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            ArmorSet s = (ArmorSet)this.loadObject(file, "PhantomSet");
            if (s != null) {
               _armors.add(s);
            }
         }

         _log.info("ArmorHolder: Loaded " + _armors.size() + " phantom armor sets from XML files.");
      }
   }

   protected ArmorSet parseObj(Node setObject) {
      NamedNodeMap attrs = setObject.getAttributes();
      String set_name = attrs.getNamedItem("set_name").getNodeValue();
      ArmorObject[] arms = new ArmorObject[5];

      try {
         Node first = setObject.getFirstChild();

         for(setObject = first; setObject != null; setObject = setObject.getNextSibling()) {
            if (setObject.getNodeName().equalsIgnoreCase("helm")) {
               arms[0] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("chest")) {
               arms[1] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("gaiter")) {
               arms[2] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("gloves")) {
               arms[3] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("boots")) {
               arms[4] = parseArmor(setObject);
            }
         }

         return new ArmorSet(set_name, arms);
      } catch (Exception var6) {
         _log.error("Error loading armor set id: " + set_name, var6);
         return null;
      }
   }

   protected static ArmorObject parseArmor(Node n) {
      if (n == null) {
         return null;
      } else {
         NamedNodeMap attrs = n.getAttributes();
         StatsSet set = new StatsSet();
         String id = attrs.getNamedItem("item_id").getNodeValue();
         String enchant = attrs.getNamedItem("enchant").getNodeValue();
         String is_random = attrs.getNamedItem("is_random").getNodeValue();
         String attribute_min = "0";
         if (attrs.getNamedItem("attribute_min") != null) {
            attribute_min = attrs.getNamedItem("attribute_min").getNodeValue();
         }

         String attribute_max = "0";
         if (attrs.getNamedItem("attribute_max") != null) {
            attribute_max = attrs.getNamedItem("attribute_max").getNodeValue();
         }

         String is_att_random = "false";
         if (attrs.getNamedItem("is_att_random") != null) {
            is_att_random = attrs.getNamedItem("is_att_random").getNodeValue();
         }

         set.set("item_id", id);
         set.set("enchant", enchant);
         set.set("is_random", is_random);
         set.set("attribute_min", attribute_min);
         set.set("attribute_max", attribute_max);
         set.set("is_att_random", is_att_random);
         return new ArmorObject(set);
      }
   }
}
