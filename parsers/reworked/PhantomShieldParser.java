package ru.catssoftware.fakes.parsers.reworked;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomParser;
import ru.catssoftware.fakes.objects.equip.ShieldObject;
import ru.catssoftware.fakes.objects.sets.Shields;
import ru.catssoftware.util.StatsSet;

public class PhantomShieldParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(PhantomShieldParser.class);
   private static ArrayList<Shields> _shield;
   private static PhantomShieldParser _instance;

   public static PhantomShieldParser getInstance() {
      if (_instance == null) {
         _instance = new PhantomShieldParser();
      }

      return _instance;
   }

   public Shields getShields() {
      return (Shields)_shield.get(0);
   }

   public void allParse() {
      _shield = new ArrayList();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/items/shield");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            Shields s = (Shields)this.loadObject(file, "PhantomShields");
            if (s != null) {
               _shield.add(s);
            }
         }

         _log.info("ShieldHolder: Loaded " + _shield.size() + " phantom shields from XML files.");
      }
   }

   protected Shields parseObj(Node setObject) {
      try {
         Node first = setObject.getFirstChild();
         LinkedList<ShieldObject> shields = new LinkedList();

         for(setObject = first; setObject != null; setObject = setObject.getNextSibling()) {
            if (setObject.getNodeName().equalsIgnoreCase("item")) {
               shields.add(parseArmor(setObject));
            }
         }

         return new Shields(shields);
      } catch (Exception var4) {
         _log.error("Error loading shields", var4);
         return null;
      }
   }

   protected static ShieldObject parseArmor(Node n) {
      if (n == null) {
         return null;
      } else {
         NamedNodeMap attrs = n.getAttributes();
         StatsSet set = new StatsSet();
         String id = attrs.getNamedItem("id").getNodeValue();
         String item_id = attrs.getNamedItem("item_id").getNodeValue();
         String enchant = attrs.getNamedItem("enchant").getNodeValue();
         String is_random = attrs.getNamedItem("is_random").getNodeValue();
         set.set("id", id);
         set.set("item_id", item_id);
         set.set("enchant", enchant);
         set.set("is_random", is_random);
         return new ShieldObject(set);
      }
   }
}
