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
import ru.catssoftware.fakes.objects.PhantomClassAI;
import ru.catssoftware.fakes.objects.equip.WeaponObject;
import ru.catssoftware.fakes.parsers.reworked.PhantomJewelParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomUnderwearParser;
import ru.catssoftware.util.StatsSet;

public class ClassAIParser extends PhantomParser {
   private static final Logger _log = Logger.getLogger(ClassAIParser.class);
   private static ArrayList<PhantomClassAI> class_list;
   private static ClassAIParser _instance;

   public static ClassAIParser getInstance() {
      if (_instance == null) {
         _instance = new ClassAIParser();
      }

      return _instance;
   }

   public PhantomClassAI getClassAI(int class_id) {
      Iterator var3 = class_list.iterator();

      while(var3.hasNext()) {
         PhantomClassAI ai = (PhantomClassAI)var3.next();
         if (ai.getClassId() == class_id) {
            return ai;
         }
      }

      return null;
   }

   public ArrayList<PhantomClassAI> allParse() {
      class_list = new ArrayList();
      File dir = new File(Config.DATAPACK_ROOT, "config/fake/ai");
      if (!dir.exists()) {
         _log.info("Dir " + dir.getAbsolutePath() + " not exists");
         return class_list;
      } else {
         Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            ArrayList<PhantomClassAI> s = this.loadObjects(file, "ClassObject");
            if (s != null && !s.isEmpty()) {
               class_list.addAll(s);
            }
         }

         _log.info("ClassAIHolder: Loaded " + class_list.size() + " phantoms classes from XML files.");
         return class_list;
      }
   }

   protected PhantomClassAI parseObj(Node setObject) {
      NamedNodeMap attrs = setObject.getAttributes();
      int class_id = Integer.parseInt(attrs.getNamedItem("class_id").getNodeValue());

      try {
         Node first = setObject.getFirstChild();
         PhantomClassAI class_ai = new PhantomClassAI(class_id);

         for(setObject = first; setObject != null; setObject = setObject.getNextSibling()) {
            if (setObject.getNodeName().equalsIgnoreCase("ai_type")) {
               this.parseAi(setObject, class_ai);
            } else if (setObject.getNodeName().equalsIgnoreCase("henna")) {
               this.parseHenna(setObject, class_ai);
            } else if (setObject.getNodeName().equalsIgnoreCase("items")) {
               this.parseItems(setObject, class_ai);
            } else if (setObject.getNodeName().equalsIgnoreCase("nuke_skills")) {
               class_ai.putNukes(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("aoe_skills")) {
               class_ai.putAoE(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("detection_skills")) {
               class_ai.putDetectionSkills(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("rare_nuke_skills")) {
               class_ai.putRareNukes(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("buff_skills")) {
               class_ai.putBuffs(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("situation_skills")) {
               class_ai.putSituationSkills(this.parseSpecialSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("support_skills")) {
               class_ai.putSupports(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("summon_skills")) {
               class_ai.putSummons(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("heal_skills")) {
               class_ai.putHeals(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("percent_heal_skills")) {
               class_ai.putPercentHeals(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("self_buff_skills")) {
               class_ai.putSelfBuffs(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("ultimate_self_buff_skills")) {
               class_ai.putUltimateBuffs(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("debuff_skills")) {
               class_ai.putDebuffs(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("rare_debuff_skills")) {
               class_ai.putRareDebuffs(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("passive_skills")) {
               class_ai.putPassive(this.parseSkills(setObject));
            } else if (setObject.getNodeName().equalsIgnoreCase("consumble_skills")) {
               class_ai.putItemSkills(this.parseSkills(setObject, true));
            } else if (setObject.getNodeName().equalsIgnoreCase("resurrect_skills")) {
               class_ai.putResurrectSkills(this.parseSkills(setObject));
            }
         }

         return class_ai;
      } catch (Exception var6) {
         _log.error("Error loading set for class: " + class_id, var6);
         return null;
      }
   }

   protected void parseHenna(Node n, PhantomClassAI class_ai) {
      if (n != null) {
         NamedNodeMap attrs = n.getAttributes();
         String id1 = "-1";
         String id2 = "-1";
         String id3 = "-1";
         if (attrs.getNamedItem("id1") != null) {
            id1 = attrs.getNamedItem("id1").getNodeValue();
         }

         if (attrs.getNamedItem("id2") != null) {
            id2 = attrs.getNamedItem("id2").getNodeValue();
         }

         if (attrs.getNamedItem("id3") != null) {
            id3 = attrs.getNamedItem("id3").getNodeValue();
         }

         class_ai.setHennaOne(Integer.parseInt(id1));
         class_ai.setHennaTwo(Integer.parseInt(id2));
         class_ai.setHennaThree(Integer.parseInt(id3));
      }
   }

   protected void parseItems(Node n, PhantomClassAI class_ai) {
      if (n != null) {
         new ArrayList();
         new ArrayList();
         new ArrayList();

         for(Node sub = n.getFirstChild(); sub != null; sub = sub.getNextSibling()) {
            NamedNodeMap attrs;
            if (sub.getNodeName().equalsIgnoreCase("underwear")) {
               attrs = sub.getAttributes();
               String underwear = attrs.getNamedItem("name").getNodeValue();
               class_ai.setUnderwear(PhantomUnderwearParser.getInstance().getSetByName(underwear));
            } else if (sub.getNodeName().equalsIgnoreCase("jewels")) {
               attrs = sub.getAttributes();
               String jewels = attrs.getNamedItem("name").getNodeValue();
               class_ai.setJewel(PhantomJewelParser.getInstance().getSetByName(jewels));
            } else {
               NamedNodeMap attrs2;
               Node item;
               int count;
               String id;
               if (sub.getNodeName().equalsIgnoreCase("sets")) {
                  for(item = sub.getFirstChild(); item != null; item = item.getNextSibling()) {
                     if (item.getNodeName().equalsIgnoreCase("set")) {
                        attrs2 = item.getAttributes();
                        id = attrs2.getNamedItem("name").getNodeValue();
                        count = Integer.parseInt(attrs2.getNamedItem("shield").getNodeValue());
                        class_ai.addArmor(id, count);
                     }
                  }
               } else if (sub.getNodeName().equalsIgnoreCase("PhantomWeapons")) {
                  for(item = sub.getFirstChild(); item != null; item = item.getNextSibling()) {
                     if (item.getNodeName().equalsIgnoreCase("weapon")) {
                        attrs2 = item.getAttributes();
                        id = attrs2.getNamedItem("id").getNodeValue();
                        String enchant = attrs2.getNamedItem("enchant").getNodeValue();
                        String is_random = attrs2.getNamedItem("is_random").getNodeValue();
                        String augment_chance = attrs2.getNamedItem("augment_chance").getNodeValue();
                        String attribute_min = attrs2.getNamedItem("attribute_min").getNodeValue();
                        String attribute_max = attrs2.getNamedItem("attribute_max").getNodeValue();
                        String attribute = attrs2.getNamedItem("attribute").getNodeValue();
                        String is_att_random = attrs2.getNamedItem("is_att_random").getNodeValue();
                        StatsSet set = new StatsSet();
                        set.set("id", id);
                        set.set("enchant", enchant);
                        set.set("is_random", is_random);
                        set.set("augment_chance", augment_chance);
                        set.set("attribute_min", attribute_min);
                        set.set("attribute_max", attribute_max);
                        set.set("attribute", attribute);
                        set.set("is_att_random", is_att_random);
                        class_ai.addWeapon(new WeaponObject(set, class_ai.getClassId()));
                     }
                  }
               } else if (sub.getNodeName().equalsIgnoreCase("PhantomInventoryItems")) {
                  for(item = sub.getFirstChild(); item != null; item = item.getNextSibling()) {
                     if (item.getNodeName().equalsIgnoreCase("item")) {
                        attrs2 = item.getAttributes();
                        int item_id = Integer.parseInt(attrs2.getNamedItem("id").getNodeValue());
                        count = Integer.parseInt(attrs2.getNamedItem("count").getNodeValue());
                        class_ai.addInventoryItem(item_id, count);
                     }
                  }
               }
            }
         }

      }
   }

   protected void parseAi(Node n, PhantomClassAI class_ai) {
      if (n != null) {
         NamedNodeMap attrs = n.getAttributes();
         String type = attrs.getNamedItem("type").getNodeValue();
         String spawn_type = attrs.getNamedItem("spawn_type").getNodeValue();
         String party_type = attrs.getNamedItem("party_type").getNodeValue();
         String buff_scheme = attrs.getNamedItem("buff_scheme").getNodeValue();
         class_ai.setType(type);
         class_ai.setSpawnType(spawn_type);
         class_ai.setPartyType(party_type);
         class_ai.setBuffScheme(buff_scheme);
      }
   }
}
