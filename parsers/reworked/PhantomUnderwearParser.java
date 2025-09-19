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
import ru.catssoftware.fakes.objects.sets.UnderwearSet;
import ru.catssoftware.util.StatsSet;

public class PhantomUnderwearParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(PhantomUnderwearParser.class);
   private static ArrayList<UnderwearSet> _underwear;
   private static PhantomUnderwearParser _instance;

   public static PhantomUnderwearParser getInstance() {
      if (_instance == null) {
         _instance = new PhantomUnderwearParser();
      }

      return _instance;
   }

   public UnderwearSet getSetByName(String name) {
      Iterator var3 = _underwear.iterator();

      while(var3.hasNext()) {
         UnderwearSet set = (UnderwearSet)var3.next();
         if (set.getName().equalsIgnoreCase(name)) {
            return set;
         }
      }

      return null;
   }

   public void allParse() {
      _underwear = new ArrayList();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/items/underwear");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            UnderwearSet s = (UnderwearSet)this.loadObject(file, "PhantomUnderwear");
            if (s != null) {
               _underwear.add(s);
            }
         }

         _log.info("UnderwearHolder: Loaded " + _underwear.size() + " underwear sets from XML files.");
      }
   }

   protected UnderwearSet parseObj(Node setObject) {
      NamedNodeMap attrs = setObject.getAttributes();
      String name = attrs.getNamedItem("name").getNodeValue();
      ArmorObject[] arms = new ArmorObject[2];

      try {
         Node first = setObject.getFirstChild();

         for(setObject = first; setObject != null; setObject = setObject.getNextSibling()) {
            if (setObject.getNodeName().equalsIgnoreCase("shirt")) {
               arms[0] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("belt")) {
               arms[1] = parseArmor(setObject);
            }
         }

         return new UnderwearSet(name, arms);
      } catch (Exception var6) {
         _log.error("Error loading armor set id: " + name, var6);
         return null;
      }
   }

   protected static ArmorObject parseArmor(Node n) {
      if (n == null) {
         return null;
      } else {
         NamedNodeMap attrs = n.getAttributes();
         StatsSet set = new StatsSet();
         String item_id = attrs.getNamedItem("item_id").getNodeValue();
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

         set.set("item_id", item_id);
         set.set("enchant", enchant);
         set.set("is_random", is_random);
         set.set("attribute_min", attribute_min);
         set.set("attribute_max", attribute_max);
         set.set("is_att_random", is_att_random);
         return new ArmorObject(set);
      }
   }
}
