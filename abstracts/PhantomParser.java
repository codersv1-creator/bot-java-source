package ru.catssoftware.fakes.abstracts;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import ru.catssoftware.fakes.templates.PhantomSkill;
import ru.catssoftware.fakes.templates.SkillsGroup;
import ru.catssoftware.util.StatsSet;

public abstract class PhantomParser {
   private static final Logger _log = Logger.getLogger(PhantomParser.class);

   public ArrayList loadObjects(File file, String name) {
      return file == null ? null : this.parse(file, name);
   }

   protected ArrayList parse(File file, String name) {
      Document doc;
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setValidating(false);
         factory.setIgnoringComments(true);
         doc = factory.newDocumentBuilder().parse(file);
      } catch (Exception var7) {
         return null;
      }

      try {
         ArrayList collection = this.parseDocument(doc, name);
         return collection;
      } catch (Exception var6) {
         return null;
      }
   }

   protected ArrayList parseDocument(Document doc, String name) {
      ArrayList collection = new ArrayList();

      for(Node n = doc.getFirstChild(); n != null; n = n.getNextSibling()) {
         if (n.getNodeName().equalsIgnoreCase("list")) {
            for(Node d = n.getFirstChild(); d != null; d = d.getNextSibling()) {
               if (d.getNodeName().equalsIgnoreCase(name)) {
                  Object obj = this.parseObj(d);
                  if (obj != null) {
                     collection.add(obj);
                  }
               }
            }
         }
      }

      return collection;
   }

   protected SkillsGroup parseSkills(Node n) {
      return this.parseSkills(n, false);
   }

   protected SkillsGroup parseSkills(Node n, boolean item) {
      int skill_id = -1;
      int skill_lv = -1;
      int ench_max_value = -1;
      boolean is_enchantable = false;
      boolean is_random = false;
      int ench_route = -1;
      String cond_type = "none";
      String target = "none";
      int first_cond = -1;
      int second_cond = -1;
      n = n.getFirstChild();
      SkillsGroup group = new SkillsGroup();
      if (n == null) {
         return group;
      } else {
         for(; n != null; n = n.getNextSibling()) {
            String nodeName = n.getNodeName();
            if (nodeName.equalsIgnoreCase("skill")) {
               NamedNodeMap attrs = n.getAttributes();
               if (attrs.getNamedItem("id") != null) {
                  skill_id = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
               }

               if (attrs.getNamedItem("level") != null) {
                  skill_lv = Integer.parseInt(attrs.getNamedItem("level").getNodeValue());
               }

               if (attrs.getNamedItem("is_enchantable") != null) {
                  is_enchantable = Boolean.parseBoolean(attrs.getNamedItem("is_enchantable").getNodeValue());
               }

               if (attrs.getNamedItem("is_random") != null) {
                  is_random = Boolean.parseBoolean(attrs.getNamedItem("is_random").getNodeValue());
               }

               if (attrs.getNamedItem("ench_max_value") != null) {
                  ench_max_value = Integer.parseInt(attrs.getNamedItem("ench_max_value").getNodeValue());
               }

               if (attrs.getNamedItem("ench_route") != null) {
                  ench_route = Integer.parseInt(attrs.getNamedItem("ench_route").getNodeValue());
               }

               if (item && attrs.getNamedItem("cond") != null) {
                  String[] temp = attrs.getNamedItem("cond").getNodeValue().split(";");
                  cond_type = temp[0];
                  first_cond = Integer.parseInt(temp[1]);
                  if (temp.length == 3) {
                     second_cond = Integer.parseInt(temp[2]);
                  } else {
                     second_cond = -1;
                  }
               }

               StatsSet set = new StatsSet();
               set.set("id", skill_id);
               set.set("level", skill_lv);
               set.set("is_enchantable", is_enchantable);
               set.set("is_random", is_random);
               set.set("ench_max_value", ench_max_value);
               set.set("ench_route", ench_route);
               set.set("target", target);
               set.set("cond_type", cond_type);
               set.set("cond_first", first_cond);
               set.set("cond_second", second_cond);
               group.addSkill(new PhantomSkill(set));
            }
         }

         return group;
      }
   }

   protected SkillsGroup parseSpecialSkills(Node n) {
      int skill_id = -1;
      int skill_lv = -1;
      int ench_max_value = -1;
      boolean is_enchantable = false;
      boolean is_random = false;
      int ench_route = -1;
      String cond_type = "none";
      String target = "none";
      int first_cond = -1;
      int second_cond = -1;
      n = n.getFirstChild();
      SkillsGroup group = new SkillsGroup();
      if (n == null) {
         return group;
      } else {
         for(; n != null; n = n.getNextSibling()) {
            String nodeName = n.getNodeName();
            if (nodeName.equalsIgnoreCase("skill")) {
               NamedNodeMap attrs = n.getAttributes();
               if (attrs.getNamedItem("id") != null) {
                  skill_id = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
               }

               if (attrs.getNamedItem("level") != null) {
                  skill_lv = Integer.parseInt(attrs.getNamedItem("level").getNodeValue());
               }

               if (attrs.getNamedItem("is_enchantable") != null) {
                  is_enchantable = Boolean.parseBoolean(attrs.getNamedItem("is_enchantable").getNodeValue());
               }

               if (attrs.getNamedItem("is_random") != null) {
                  is_random = Boolean.parseBoolean(attrs.getNamedItem("is_random").getNodeValue());
               }

               if (attrs.getNamedItem("ench_max_value") != null) {
                  ench_max_value = Integer.parseInt(attrs.getNamedItem("ench_max_value").getNodeValue());
               }

               if (attrs.getNamedItem("ench_route") != null) {
                  ench_route = Integer.parseInt(attrs.getNamedItem("ench_route").getNodeValue());
               }

               if (attrs.getNamedItem("target") != null) {
                  target = attrs.getNamedItem("target").getNodeValue();
               }

               if (attrs.getNamedItem("cond") != null) {
                  String[] temp = attrs.getNamedItem("cond").getNodeValue().split(";");
                  cond_type = temp[0];
                  first_cond = Integer.parseInt(temp[1]);
                  second_cond = Integer.parseInt(temp[2]);
               }

               StatsSet set = new StatsSet();
               set.set("id", skill_id);
               set.set("level", skill_lv);
               set.set("is_enchantable", is_enchantable);
               set.set("is_random", is_random);
               set.set("ench_max_value", ench_max_value);
               set.set("ench_route", ench_route);
               set.set("target", target);
               set.set("cond_type", cond_type);
               set.set("cond_first", first_cond);
               set.set("cond_second", second_cond);
               group.addSkill(new PhantomSkill(set));
            }
         }

         return group;
      }
   }

   protected abstract Object parseObj(Node var1);

   public Object loadObject(File file, String name) {
      return file == null ? null : this.parseObj(file, name);
   }

   protected Object parseObj(File file, String name) {
      Document doc;
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setValidating(false);
         factory.setIgnoringComments(true);
         doc = factory.newDocumentBuilder().parse(file);
      } catch (Exception var6) {
         return null;
      }

      try {
         return this.parseDocumentObj(doc, name);
      } catch (Exception var5) {
         return null;
      }
   }

   protected Object parseDocumentObj(Document doc, String name) {
      for(Node n = doc.getFirstChild(); n != null; n = n.getNextSibling()) {
         if (n.getNodeName().equalsIgnoreCase("list")) {
            for(Node d = n.getFirstChild(); d != null; d = d.getNextSibling()) {
               if (d.getNodeName().equalsIgnoreCase(name)) {
                  Object obj = this.parseObj(d);
                  if (obj != null) {
                     return obj;
                  }
               }
            }
         }
      }

      return null;
   }
}
