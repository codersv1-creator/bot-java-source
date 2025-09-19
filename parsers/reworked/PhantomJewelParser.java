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
import ru.catssoftware.fakes.objects.sets.JewelSet;
import ru.catssoftware.util.StatsSet;

public class PhantomJewelParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(PhantomJewelParser.class);
   private static ArrayList<JewelSet> _jewel;
   private static PhantomJewelParser _instance;

   public static PhantomJewelParser getInstance() {
      if (_instance == null) {
         _instance = new PhantomJewelParser();
      }

      return _instance;
   }

   public JewelSet getSetByName(String name) {
      Iterator var3 = _jewel.iterator();

      while(var3.hasNext()) {
         JewelSet set = (JewelSet)var3.next();
         if (set.getName().equalsIgnoreCase(name)) {
            return set;
         }
      }

      return null;
   }

   public void allParse() {
      _jewel = new ArrayList();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/items/jewel");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            JewelSet s = (JewelSet)this.loadObject(file, "PhantomJewel");
            if (s != null) {
               _jewel.add(s);
            }
         }

         _log.info("JewelHolder: Loaded " + _jewel.size() + " phantom jewel sets from XML files.");
      }
   }

   protected JewelSet parseObj(Node setObject) {
      NamedNodeMap attrs = setObject.getAttributes();
      String name = attrs.getNamedItem("set_name").getNodeValue();
      ArmorObject[] arms = new ArmorObject[5];

      try {
         Node first = setObject.getFirstChild();

         for(setObject = first; setObject != null; setObject = setObject.getNextSibling()) {
            if (setObject.getNodeName().equalsIgnoreCase("earring_left")) {
               arms[0] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("earring_right")) {
               arms[1] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("ring_left")) {
               arms[2] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("ring_right")) {
               arms[3] = parseArmor(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("necklace")) {
               arms[4] = parseArmor(setObject);
            }
         }

         return new JewelSet(name, arms);
      } catch (Exception var6) {
         _log.error("Error loading jewel set id: " + name, var6);
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
         set.set("item_id", item_id);
         set.set("enchant", enchant);
         set.set("is_random", is_random);
         return new ArmorObject(set);
      }
   }
}
