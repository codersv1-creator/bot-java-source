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
import ru.catssoftware.fakes.objects.equip.AccessoryObject;
import ru.catssoftware.fakes.objects.sets.AccessorySet;
import ru.catssoftware.util.StatsSet;

public class PhantomAccessoryParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(PhantomAccessoryParser.class);
   private static ArrayList<AccessorySet> _accessory;
   private static PhantomAccessoryParser _instance;

   public static PhantomAccessoryParser getInstance() {
      if (_instance == null) {
         _instance = new PhantomAccessoryParser();
      }

      return _instance;
   }

   public ArrayList<AccessorySet> getAccessories(String armor_set) {
      ArrayList<AccessorySet> result = new ArrayList();
      Iterator var4 = _accessory.iterator();

      while(var4.hasNext()) {
         AccessorySet accessory = (AccessorySet)var4.next();
         if (accessory.getSetAssign().equalsIgnoreCase(armor_set)) {
            result.add(accessory);
         }
      }

      return result;
   }

   public void allParse() {
      _accessory = new ArrayList();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/items/accessory");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            AccessorySet s = (AccessorySet)this.loadObject(file, "PhantomAccessory");
            if (s != null) {
               _accessory.add(s);
            }
         }

         _log.info("AccessoryHolder: Loaded " + _accessory.size() + " phantom accessories from XML files.");
      }
   }

   protected AccessorySet parseObj(Node setObject) {
      NamedNodeMap attrs = setObject.getAttributes();
      int id = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
      String set_assign = attrs.getNamedItem("set_assign").getNodeValue();
      AccessoryObject[] accs = new AccessoryObject[3];

      try {
         Node first = setObject.getFirstChild();

         for(setObject = first; setObject != null; setObject = setObject.getNextSibling()) {
            if (setObject.getNodeName().equalsIgnoreCase("male_hat")) {
               accs[0] = parseAccessory(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("female_hat")) {
               accs[1] = parseAccessory(setObject);
            } else if (setObject.getNodeName().equalsIgnoreCase("cloak")) {
               accs[2] = parseAccessory(setObject);
            }
         }

         return new AccessorySet(id, set_assign, accs);
      } catch (Exception var7) {
         _log.error("Error loading accessory set id: " + id, var7);
         return null;
      }
   }

   protected static AccessoryObject parseAccessory(Node n) {
      if (n == null) {
         return null;
      } else {
         NamedNodeMap attrs = n.getAttributes();
         new StatsSet();

         int item_id;
         try {
            item_id = Integer.parseInt(attrs.getNamedItem("item_id").getNodeValue());
         } catch (Exception var7) {
            item_id = 0;
            _log.error("Illegal bot accessory id");
         }

         int chance;
         try {
            chance = Integer.parseInt(attrs.getNamedItem("chance").getNodeValue());
         } catch (Exception var6) {
            chance = 0;
            _log.error("Illegal bot accessory chance");
         }

         return new AccessoryObject(item_id, chance);
      }
   }
}
