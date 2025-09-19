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
import ru.catssoftware.fakes.objects.PhantomBuff;
import ru.catssoftware.fakes.objects.PhantomBuffScheme;

public class PhantomBuffParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(PhantomBuffParser.class);
   private static ArrayList<PhantomBuffScheme> _buffs;
   private static PhantomBuffParser _instance;

   public static PhantomBuffParser getInstance() {
      if (_instance == null) {
         _instance = new PhantomBuffParser();
      }

      return _instance;
   }

   public PhantomBuffScheme getBuffs(String name) {
      Iterator var3 = _buffs.iterator();

      while(var3.hasNext()) {
         PhantomBuffScheme buff_scheme = (PhantomBuffScheme)var3.next();
         if (buff_scheme.getName().equalsIgnoreCase(name)) {
            return buff_scheme;
         }
      }

      return null;
   }

   public void allParse() {
      _buffs = new ArrayList();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/buffs");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            PhantomBuffScheme s = (PhantomBuffScheme)this.loadObject(file, "PhantomBuffScheme");
            if (s != null) {
               _buffs.add(s);
            }
         }

         _log.info("BuffSchemeHolder: Loaded " + _buffs.size() + " phantom accessories from XML files.");
      }
   }

   protected PhantomBuffScheme parseObj(Node buffObject) {
      String name = buffObject.getAttributes().getNamedItem("name").getNodeValue();
      PhantomBuffScheme scheme = new PhantomBuffScheme(name);

      try {
         Node first = buffObject.getFirstChild();

         for(buffObject = first; buffObject != null; buffObject = buffObject.getNextSibling()) {
            if (buffObject.getNodeName().equalsIgnoreCase("buff")) {
               NamedNodeMap attributes = buffObject.getAttributes();
               int id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
               int lvl = Integer.parseInt(attributes.getNamedItem("lvl").getNodeValue());
               scheme.addBuff(new PhantomBuff(id, lvl));
            }
         }

         return scheme;
      } catch (Exception var8) {
         _log.error("Error loading accessory buff scheme name: " + name, var8);
         return null;
      }
   }
}
