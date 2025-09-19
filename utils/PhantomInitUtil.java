package ru.catssoftware.fakes.utils;

import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.PhantomsEngine;
import ru.catssoftware.fakes.dao.PhantomDAO;
import ru.catssoftware.fakes.factory.PhantomFactory;
import ru.catssoftware.fakes.holders.PhantomColorHolder;
import ru.catssoftware.fakes.holders.PhantomsHolder;
import ru.catssoftware.fakes.model.ClassesDictionary;
import ru.catssoftware.fakes.objects.PhantomClassAI;
import ru.catssoftware.fakes.objects.equip.AccessoryObject;
import ru.catssoftware.fakes.objects.equip.ArmorObject;
import ru.catssoftware.fakes.objects.equip.ShieldObject;
import ru.catssoftware.fakes.objects.equip.WeaponObject;
import ru.catssoftware.fakes.objects.sets.AccessorySet;
import ru.catssoftware.fakes.objects.sets.ArmorSet;
import ru.catssoftware.fakes.objects.sets.JewelSet;
import ru.catssoftware.fakes.objects.sets.UnderwearSet;
import ru.catssoftware.fakes.parsers.ClassAIParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomAccessoryParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomArmorParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomShieldParser;
import ru.catssoftware.fakes.tables.PhantomInfo;
import ru.catssoftware.fakes.tables.PhantomSpawnCoordinateInfo;
import ru.catssoftware.fakes.tables.PhantomSpawnInfo;
import ru.catssoftware.fakes.templates.PhantomSkill;
import ru.catssoftware.fakes.templates.SkillsGroup;
import ru.catssoftware.gameserver.datatables.ClanTable;
import ru.catssoftware.gameserver.datatables.HennaTable;
import ru.catssoftware.gameserver.datatables.ItemTable;
import ru.catssoftware.gameserver.datatables.xml.AugmentationData;
import ru.catssoftware.gameserver.model.L2Clan;
import ru.catssoftware.gameserver.model.L2ItemInstance;
import ru.catssoftware.gameserver.model.L2Object;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.network.serverpackets.L2GameServerPacket;
import ru.catssoftware.gameserver.network.serverpackets.StatusUpdate;
import ru.catssoftware.gameserver.skills.Stats;
import ru.catssoftware.tools.random.Rnd;

public class PhantomInitUtil {
   private static final Logger _log = Logger.getLogger(PhantomInitUtil.class);
   public static ArrayList<Integer> All_loaded_OID = new ArrayList();

   public static int getRandomFake(int val) {
      int index = getFakes().size();
      int charId = -1;

      for(int i = 0; i < index; ++i) {
         PhantomInfo info = (PhantomInfo)getFakes().values()[i];
         if (info.charId == val) {
            charId = info.charId;
         }
      }

      return charId;
   }

   public static synchronized boolean spawnPhantomInWave(PhantomSpawnInfo info, String ai_type) {
      if (info == null) {
         if (Config.DEBAG_LVL > 0) {
            System.out.println(v.m("瞍馥\uec5a붋둦ဍ병赉櫷섄䰓ꛃ\ue901\uf52d؞ᾧ햩⼍藘믫㪑뇶听\uf0a3盔渒₁\u0084雝\u12c1ꂈ"));
         }

         return false;
      } else {
         try {
            Location loc = null;
            if (info.getAllBattleCoordinates() != null && !info.getAllBattleCoordinates().isEmpty()) {
               loc = ((PhantomSpawnCoordinateInfo)info.getAllBattleCoordinates().getFirst()).loc;
            }

            if (loc == null) {
               loc = ((PhantomSpawnCoordinateInfo)info.getAllFarmCoordinates().getFirst()).loc;
            }

            double h_attack = info.getHandicapAttack();
            double h_defence = info.getHandicapDefence();
            double comeback_distance = (double)info.getComebackDistance();
            double peace_cooldown = info.getPeaceCooldown();
            int fakeObjId = -1;
            ArrayList<PhantomInitUtil.CharIdAndClass> CIAC = new ArrayList();
            Iterator var15 = PhantomDAO.getInstance().getCharIdAndClassId().entrySet().iterator();

            while(var15.hasNext()) {
               Entry<Integer, Integer> OID_CID = (Entry)var15.next();
               CIAC.add(new PhantomInitUtil.CharIdAndClass((Integer)OID_CID.getValue(), (Integer)OID_CID.getKey()));
            }

            Collections.shuffle(CIAC);

            for(var15 = CIAC.iterator(); var15.hasNext(); fakeObjId = -1) {
               PhantomInitUtil.CharIdAndClass OID_CID = (PhantomInitUtil.CharIdAndClass)var15.next();
               fakeObjId = getMatch0(ai_type, OID_CID.fakeSubId, OID_CID.fakeObjId);
               if (fakeObjId != -1 && !All_loaded_OID.contains(fakeObjId)) {
                  break;
               }
            }

            if (Config.DEBAG_LVL > 0) {
               System.out.println(v.m("瞍馥\uec5a붋둦ဍ병赉櫷섄䰓ꛃ\ue901\uf52d؞ᾧ햩⼍藜믫㪙뇱吕\uf0b8皍湟\u20c4Ð") + ai_type + v.m("矞馳\uec5a붗둭ဒ볛赂櫐섔䱆") + fakeObjId);
            }

            if (fakeObjId == -1) {
               if (Config.DEBAG_LVL > 0) {
                  System.out.println(v.m("瞍馥\uec5a붋둦ဍ병赉櫷섄䰓ꛃ\ue901\uf52d؞ᾧ햩⼍藟"));
               }

               return false;
            } else {
               All_loaded_OID.add(fakeObjId);
               if (Config.DEBAG_LVL > 0) {
                  System.out.println(v.m("瞍馥\uec5a붋둦ဍ병赉櫷섄䰓ꛃ\ue901\uf52d؞ᾧ햩⼍藞"));
               }

               if (Config.DEBAG_LVL > 0) {
                  System.out.println(v.m("瞍馥\uec5a붋둦ဍ병赉櫷섄䰓ꛃ\ue901\uf52d؞ᾧ햩⼍藑믱㫘") + ai_type + v.m("矞馔\uec75붸됨ဒ볛赂櫼섓䰵ꛊ\ue972\uf563") + fakeObjId);
               }

               L2PcInstance phantom = restoreFake(fakeObjId, ai_type);
               if (Config.DEBAG_LVL > 0) {
                  System.out.println(v.m("瞰馔\uec76붹됲ၽ") + phantom.getName());
               }

               if (Config.DEBAG_LVL > 0) {
                  System.out.println("");
               }

               if (phantom == null) {
                  return false;
               } else {
                  phantom.getPosition().setXYZInvisible(loc.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_SPAWN_COLLISION_X), loc.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_SPAWN_COLLISION_Y), loc.getZ());
                  phantom.phantom_params.setPeaceCooldown(peace_cooldown);
                  phantom.phantom_params.setBattleCoords(info.getAllBattleCoordinates());
                  phantom.phantom_params.setFarmCoords(info.getAllFarmCoordinates());
                  phantom.phantom_params.setPeaceCoords(info.getAllPeaceCoordinates());
                  phantom.spawnMe();
                  phantom.setOnlineStatus(true);
                  phantom.setComebackDistanceLoc(phantom.getLoc());
                  phantom.phantom_params.getPhantomAI().startAITask(500L);
                  phantom.phantom_params.getPhantomAI().startDespawnTask((long)Rnd.get(Config.FAKE_PLAYERS_MIN_DESPAWN_DELAY, Config.FAKE_PLAYERS_MAX_DESPAWN_DELAY) * 1000L);
                  phantom.setHandicapAttack(h_attack);
                  phantom.setHandicapDefence(h_defence);
                  phantom.setComebackDistance(comeback_distance);
                  phantom.setAutoMp(true);
                  phantom.setAutoHp(true);
                  phantom.setAutoCp(true);
                  phantom.setIsParalyzed(false);
                  if (Config.DEBAG_LVL > 0) {
                     _log.info(v.m("瞮馽\uec5a붒둼ဲ볔赍檹섙䰏Ꚏ\ue972\uf563") + phantom.getName() + v.m("矞馦\uec4b붝둿ဳ볜赌檷"));
                  }

                  return true;
               }
            }
         } catch (Exception var16) {
            return false;
         }
      }
   }

   public static L2PcInstance restoreFake(int objId, String ai_type) {
      if (Config.DEBAG_LVL > 0) {
         System.out.println(v.m("鹪곲㮉栺\u20f8滔ꏬʍ쥛䜖꩹⥁㒑읨柪桉셩룠ᚙ俫ᄲ볂汶솆꽆⦪㠷킒ᐝ\u171b") + objId);
      }

      PhantomInfo fake = PhantomsHolder.getFake(objId);
      if (Config.DEBAG_LVL > 0) {
         System.out.println(v.m("鹪곲㮉栺\u20f8滔ꏬʍ쥛䜖꩹⥁㒑읨柪桉셩룠ᚙ俫ᄲ볂汶솆꽆⦪㠷킑ᐝ\u171b") + fake.charId);
      }

      L2PcInstance phantom = null;

      try {
         if (fake == null) {
            return null;
         }

         phantom = PhantomFactory.createFake(objId);
         if (phantom == null) {
            if (Config.DEBAG_LVL > 0) {
               System.out.println(v.m("鹊곲㮉栺\u20f8滔ꏬˤ줈䝂ꨭ⥺㒐읭柪桉섺뢤ᛋ侾ᅼ벑氲쇡꼆⧠㡳킂ᐜᜀ萁돻礋罍糄穋㌼"));
            }

            return null;
         }

         if (Config.DEBAG_LVL > 0) {
            System.out.println(v.m("鹊곲㮉栺\u20f8滔ꏬˤ줔䝂ꨭ⥺㒐읭柪桉세뢦ᛉ侼ᅾ벓氰쇣꼄⧢㡱킀ᐞᜂ萃돹礉罏糆穉㌾㦑˼灦ऄ\ue4ab싙䟿\uef4c뮬䉱꼆"));
         }

         if (Config.DEBAG_LVL > 0) {
            System.out.println(v.m("鹪곲㮉栺\u20f8滔ꏬʍ쥛䜖꩹⥁㒑읨柪桉셩룠ᚙ俫ᄲ볂汶솆꽆⦪㠷킖ᐝ\u171b") + objId);
         }

         phantom.setFantome(true);
         if (Config.DEBAG_LVL > 0) {
            System.out.println(v.m("鹪곲㮉栺\u20f8滔ꏬʍ쥛䜖꩹⥁㒑읨柪桉셩룠ᚙ俫ᄲ볂汶솆꽆⦪㠷킕ᐝ\u171b") + objId);
         }

         if (Config.DEBAG_LVL > 0) {
            System.out.println(v.m("鹊곲㮉栺\u20f8滔ꏬ˪쥆䜏ꩬ⥣㒋음柣桁섲뢿ᛊ") + objId);
         }

         phantom.phantom_params.setPhantomAI();
         if (Config.DEBAG_LVL > 0) {
            System.out.println(v.m("鹜곻㮃栱₢滕ꏠʩ쥐䝟\uaa37") + fake.name);
         }

         phantom.setName(fake.name);
         phantom.setTitle(fake.title);
         setClan(phantom, fake.clanId);
         phantom.getAppearance().setNameColor(PhantomColorHolder.getRandomNameColor());
         phantom.getAppearance().setTitleColor(PhantomColorHolder.getRandomTitleColor());
         PhantomsEngine.getInstance().addPhantom(phantom);
         initializePhantom(phantom);
         phantom.setRunning();
         if (phantom.phantom_params.getPhantomWeapon() != null) {
            phantomShotActivate(phantom);
         }

         phantom.setKarma(0);
         PhantomUtil.doBuff(phantom);
         if (!phantom.isNoble()) {
            phantom.setNoble(true);
            phantom.sendSkillList();
            phantom.broadcastUserInfo();
         }
      } catch (Exception var5) {
      }

      return phantom;
   }

   public static void setClan(L2PcInstance phantom, int clanId) {
      if (clanId != -1 && ClanTable.getInstance().getClan(clanId) != null) {
         L2Clan clan = ClanTable.getInstance().getClan(clanId);
         phantom.setClan(clan);
         phantom.setLvlJoinedAcademy(0);
         phantom.setApprentice(0);
      }
   }

   public static int getMatch(String ai_type) {
      try {
         int fakeObjId = PhantomsHolder.getRandomFake();
         int fakeSubId = PhantomDAO.getInstance().getSubclassByObjId(fakeObjId);
         return getMatch0(ai_type, fakeSubId, fakeObjId);
      } catch (Exception var3) {
         if (Config.DEBAG_LVL > 0) {
            var3.printStackTrace();
         }

         return -1;
      }
   }

   public static TIntObjectHashMap<PhantomInfo> getFakes() {
      return PhantomsHolder.getFakes();
   }

   public static int getMatch0(String ai_type, int fakeSubId, int fakeObjId) {
      return (!ai_type.equalsIgnoreCase(v.m("讇핢颥浩\u0e89\udd0c簡")) || !ClassesDictionary.isWarrior(fakeSubId)) && (!ai_type.equalsIgnoreCase(v.m("讔핢颰浼\u0e85\udd11")) || !ClassesDictionary.isDagger(fakeSubId)) && (!ai_type.equalsIgnoreCase(v.m("讄핢颹浰")) || !ClassesDictionary.isTank(fakeSubId)) && (!ai_type.equalsIgnoreCase(v.m("讑핱颴浳\u0e85\udd11")) || !ClassesDictionary.isArcher(fakeSubId)) && (!ai_type.equalsIgnoreCase(v.m("讝핢颰浾")) || !ClassesDictionary.isMage(fakeSubId)) && (!ai_type.equalsIgnoreCase(v.m("讃핶颺浶\u0e8f\udd0d簶挼")) || !ClassesDictionary.isSummoner(fakeSubId)) && (!ai_type.equalsIgnoreCase(v.m("讘핦颶海\u0e85\udd11")) || !ClassesDictionary.isHealer(fakeSubId)) && (!ai_type.equalsIgnoreCase(v.m("讝핢颰浾\u0ebf\udd10簦挾\ue2a1ᷞ蒖⒘")) || !ClassesDictionary.isMageSupport(fakeSubId)) && (!ai_type.equalsIgnoreCase(v.m("讝핦颻浾\u0e85\udd3c簠挻\ue2a1᷁蒋⒞鬙")) || !ClassesDictionary.isMeleeSupport(fakeSubId)) ? -1 : fakeObjId;
   }

   public static String getAIbyClassId(int fakeSubId) {
      if (ClassesDictionary.isWarrior(fakeSubId)) {
         return v.m("\uf2a3ᓎ\uf2bf᱉潰읽墢");
      } else if (ClassesDictionary.isDagger(fakeSubId)) {
         return v.m("\uf2b0ᓎ\uf2aaᱜ潼읠");
      } else if (ClassesDictionary.isTank(fakeSubId)) {
         return v.m("\uf2a0ᓎ\uf2a3᱐");
      } else if (ClassesDictionary.isArcher(fakeSubId)) {
         return v.m("\uf2b5ᓝ\uf2ae᱓潼읠");
      } else if (ClassesDictionary.isMage(fakeSubId)) {
         return v.m("\uf2b9ᓎ\uf2aaᱞ");
      } else if (ClassesDictionary.isSummoner(fakeSubId)) {
         return v.m("\uf2a7ᓚ\uf2a0᱖潶일墵ᇈ");
      } else if (ClassesDictionary.isHealer(fakeSubId)) {
         return v.m("\uf2bcᓊ\uf2ac᱗潼읠");
      } else if (ClassesDictionary.isMageSupport(fakeSubId)) {
         return v.m("\uf2b9ᓎ\uf2aaᱞ潆읡墥ᇊ⟭ឋ醎譮");
      } else {
         return ClassesDictionary.isMeleeSupport(fakeSubId) ? v.m("\uf2b9ᓊ\uf2a1ᱞ潼읍墣ᇏ⟭ប醓譨徕") : v.m("\uf2baᓚ\uf2a1᱗");
      }
   }

   public static void initializePhantom(L2PcInstance phantom) {
      PhantomClassAI class_ai = ClassAIParser.getInstance().getClassAI(phantom.getClassId().getId());
      if (class_ai == null) {
         _log.error(v.m("䧰\uf419\ueacd\uf0fb뿞㼣掀派\ua799\uf479ࠟḱᘤꅜ┦⤓谟\uf474씑") + phantom.getClassId().getId() + v.m("䦕\uf405\ueade\uf0f9뿉㽜") + ClassesDictionary.getNameById(phantom.getClassId().getId()) + v.m("䦛\uf44b\ueaf1\uf0e1뿀㽯揉洑\ua7f0\uf434"));
      } else {
         phantom.phantom_params.setPhantomClassAI(class_ai);
         PhantomDAO.getInstance().DeletingItems(phantom.getCharId());
         addHenna(phantom, class_ai.getHennaOne());
         addHenna(phantom, class_ai.getHennaTwo());
         addHenna(phantom, class_ai.getHennaThree());
         int size = class_ai.getArmors().size();
         int rnd = Rnd.get(1, size) - 1;
         int i = 0;
         Iterator var6 = class_ai.getArmors().iterator();

         while(true) {
            while(var6.hasNext()) {
               PhantomClassAI.L2Sets armor = (PhantomClassAI.L2Sets)var6.next();
               if (i < rnd) {
                  ++i;
               } else {
                  ArmorSet arm_set = PhantomArmorParser.getInstance().getArmorSet(armor._armor);
                  if (arm_set != null) {
                     Stats[] elements_one = new Stats[]{Stats.WIND, Stats.WATER, Stats.HOLY};
                     Stats[] elements_two = new Stats[]{Stats.EARTH, Stats.FIRE, Stats.UNHOLY};
                     equipArmorItem(arm_set.getHelm(), phantom, elements_one);
                     equipArmorItem(arm_set.getChest(), phantom, elements_one);
                     equipArmorItem(arm_set.getGaiter(), phantom, elements_one);
                     equipArmorItem(arm_set.getGloves(), phantom, elements_two);
                     equipArmorItem(arm_set.getBoots(), phantom, elements_two);
                     int sizea;
                     if (armor._sheild != -1) {
                        ShieldObject shield = PhantomShieldParser.getInstance().getShields().getShield(armor._sheild);
                        sizea = shield.getItemId();
                        L2ItemInstance item = ItemTable.getInstance().createDummyItem(sizea);
                        L2ItemInstance item2 = phantom.getInventory().addItem(v.m("䧥\uf403\ueade\uf0fa뿘㽬掄"), item, phantom, (L2Object)null);
                        phantom.getInventory().equipItemAndRecord(item2);
                     }

                     ArrayList<AccessorySet> list = PhantomAccessoryParser.getInstance().getAccessories(armor._armor);
                     if (!list.isEmpty() && list.size() == 1) {
                        equipAccessory(((AccessorySet)list.get(0)).getAccessory(phantom.getAppearance().getSex()), phantom);
                        equipAccessory(((AccessorySet)list.get(0)).getCloak(), phantom);
                     } else if (!list.isEmpty()) {
                        sizea = list.size();
                        int rnda = Rnd.get(1, sizea) - 1;
                        int ia = 0;
                        Iterator var15 = list.iterator();

                        while(var15.hasNext()) {
                           AccessorySet set = (AccessorySet)var15.next();
                           if (ia < rnda) {
                              ++ia;
                           } else {
                              equipAccessory(set.getAccessory(phantom.getAppearance().getSex()), phantom);
                              equipAccessory(set.getCloak(), phantom);
                           }
                        }
                     }
                  }
               }
            }

            UnderwearSet underwear = class_ai.getUnderwear();
            if (underwear != null) {
               equipArmorItem(underwear.getShirt(), phantom);
               equipArmorItem(underwear.getBelt(), phantom);
            }

            JewelSet jewel = class_ai.getJewel();
            equipJewelItem(jewel.getEarringL(), phantom);
            equipJewelItem(jewel.getEarringR(), phantom);
            equipJewelItem(jewel.getRingL(), phantom);
            equipJewelItem(jewel.getRingR(), phantom);
            equipJewelItem(jewel.getNecklace(), phantom);
            Iterator var20 = class_ai.getInventoryItems().iterator();

            while(var20.hasNext()) {
               PhantomClassAI.L2Invent item = (PhantomClassAI.L2Invent)var20.next();
               phantom.getInventory().addItem(v.m("䧥\uf403\ueade\uf0fa뿘㽬掄"), item._items, item._counts, phantom, (L2Object)null);
            }

            if (class_ai.getWeapons().size() == 1) {
               equipWeaponItem((WeaponObject)class_ai.getWeapons().getFirst(), phantom);
            } else {
               int sizew = class_ai.getWeapons().size();
               int rndw = Rnd.get(1, sizew) - 1;
               int iw = 0;
               Iterator var25 = class_ai.getWeapons().iterator();

               while(var25.hasNext()) {
                  WeaponObject weapon = (WeaponObject)var25.next();
                  if (iw < rndw) {
                     ++iw;
                  } else {
                     equipWeaponItem(weapon, phantom);
                  }
               }
            }

            addSkills(phantom, class_ai);
            return;
         }
      }
   }

   public static void addHenna(L2PcInstance phantom, int henna) {
      if (henna > 0) {
         phantom.addHenna(HennaTable.getInstance().getTemplate(henna));
      }

   }

   public static void addSkills(L2PcInstance phantom, PhantomClassAI class_ai) {
      try {
         SkillsGroup debuffs = class_ai.getDebuffs();
         SkillsGroup r_debuffs = class_ai.getRareDebuffs();
         SkillsGroup situation = class_ai.getSituationSkills();
         SkillsGroup aoe_skills = class_ai.getAoESkills();
         SkillsGroup detection_skills = class_ai.getDetectionSkills();
         SkillsGroup buffs = class_ai.getSelfBuffs();
         SkillsGroup u_buffs = class_ai.getSelfUltimateBuffs();
         SkillsGroup passive = class_ai.getPassive();
         SkillsGroup i_skills = class_ai.getItemUseSkills();
         SkillsGroup resurrect_skills = class_ai.getResurrectSkills();
         SkillsGroup heals = class_ai.getHealSkills();
         SkillsGroup p_heals = class_ai.getPercentHealSkills();
         SkillsGroup nukes = class_ai.getNukeSkills();
         SkillsGroup r_nukes = class_ai.getRareNukeSkills();
         SkillsGroup buff = class_ai.getBuffSkills();
         SkillsGroup supp = class_ai.getSupportSkills();
         addSkillGroup(phantom, debuffs);
         addSkillGroup(phantom, r_debuffs);
         addSkillGroup(phantom, situation);
         addSkillGroup(phantom, aoe_skills);
         addSkillGroup(phantom, detection_skills);
         addSkillGroup(phantom, buffs);
         addSkillGroup(phantom, u_buffs);
         addSkillGroup(phantom, passive);
         addSkillGroup(phantom, i_skills);
         addSkillGroup(phantom, resurrect_skills);
         addSkillGroup(phantom, heals);
         addSkillGroup(phantom, p_heals);
         addSkillGroup(phantom, nukes);
         addSkillGroup(phantom, r_nukes);
         addSkillGroup(phantom, buff);
         addSkillGroup(phantom, supp);
      } catch (Exception var18) {
         if (Config.DEBAG_LVL > 0) {
            var18.printStackTrace();
         }
      }

   }

   private static void addSkillGroup(L2PcInstance phantom, SkillsGroup group) {
      PhantomSkill[] var5;
      int var4 = (var5 = group.getAllSkills()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         PhantomSkill ph_skill = var5[var3];
         phantom.addSkill(ph_skill.getSkill(), false);
      }

   }

   public static void phantomShotActivate2(L2PcInstance phantom) {
      int shot_id;
      if (phantom.phantom_params.getPhantomWeapon().isWeapon()) {
         shot_id = getBlessedSpiritShotIdByWeaponGrade(phantom.phantom_params.getPhantomWeapon());
      } else {
         shot_id = getSoulShotIdByWeaponGrade(phantom.phantom_params.getPhantomWeapon());
      }

      if (shot_id == 0) {
         _log.error(v.m("\ue979\ue791렫耷⬥ࠇ瀣묮합옟燌\ue7fe뢍攏\ue905뎛뒣扇ⱘ➾䮨艮࠾霑\uda17\uf5bcꤪ"));
      }

      phantom.getInventory().addItem(v.m("\ue97a\ue796렿耵⬢ࠀ瀡"), shot_id, 30, phantom, (L2Object)null);
      if (shot_id != 0) {
         phantom.addAutoSoulShot(shot_id);
         phantom.getActiveWeaponInstance().setChargedSpiritshot(2);
      }

   }

   public static void phantomShotActivate(L2PcInstance phantom) {
      int shot_id = getBlessedSpiritShotIdByWeaponGrade(phantom.phantom_params.getPhantomWeapon());
      int shot_id2 = getSoulShotIdByWeaponGrade(phantom.phantom_params.getPhantomWeapon());
      if (shot_id == 0 || shot_id2 == 0) {
         _log.error(v.m("⃐⼻\ue1afᅛ敲狾灢ፎ椌\ud9d8艏䀄釐❙늌冱䡟檶T摦벊욕᯼뀮\uab89ℨ萍"));
      }

      if (shot_id != 0) {
         phantom.getInventory().addItem(v.m("⃓⼼\ue1bbᅙ敵狹灠"), shot_id, 30000, phantom, (L2Object)null);
         phantom.addAutoSoulShot(shot_id);
         phantom.getActiveWeaponInstance().setChargedSpiritshot(2);
      }

      if (shot_id2 != 0) {
         phantom.getInventory().addItem(v.m("⃓⼼\ue1bbᅙ敵狹灠"), shot_id2, 30000, phantom, (L2Object)null);
         phantom.addAutoSoulShot(shot_id2);
         phantom.getActiveWeaponInstance().setChargedSpiritshot(2);
      }

   }

   public static void equipAccessory(AccessoryObject item, L2PcInstance phantom) {
      try {
         if (item != null) {
            int id = item.getId();
            L2ItemInstance accessory = ItemTable.getInstance().createDummyItem(id);
            L2ItemInstance accessory2 = phantom.getInventory().addItem(v.m("愆蘗잇璡㪨\uf18b⌑"), accessory, phantom, (L2Object)null);
            phantom.getInventory().equipItemAndRecord(accessory2);
         }
      } catch (Exception var5) {
         _log.error(v.m("愓蘍잔璠㪮\uf1c4⌝粉㱑鋝떜⊓⛖祐馈⪸妗ᣳ샛뙁") + item.getId() + v.m("慶蘙잉璽㫼\uf187⌐粋㱁鋋뗏⊉⛝輻駑") + phantom.getClassId().getId());
      }

   }

   public static void equipWeaponItem(WeaponObject weapon, L2PcInstance phantom) {
      try {
         int id = weapon.getId();
         L2ItemInstance item = ItemTable.getInstance().createDummyItem(id);
         item.setEnchantLevel(weapon.getEnchant());
         StatusUpdate playerSU = new StatusUpdate(item.getObjectId());
         playerSU.addAttribute(weapon.getId(), weapon.getAttributeEnchant());
         phantom.sendPacket((L2GameServerPacket)playerSU);
         playerSU = null;
         if (weapon.getAugmentSuccess()) {
            item.setAugmentation(AugmentationData.getInstance().generateRandomAugmentation(8, 3, 44));
         }

         phantom.phantom_params.setPhantomWeapon(item);
         L2ItemInstance item2 = phantom.getInventory().addItem(v.m("勫鯦\ue852㛍ꯎ\uf42a혼"), item, phantom, (L2Object)null);
         phantom.getInventory().equipItemAndRecord(item2);
      } catch (Exception var6) {
         _log.error(v.m("勾鯼\ue841㛌ꯈ\uf465혦\uddf4틷⢸\uddf9͙梙蓝ﮆ燩뜌") + weapon.getId() + v.m("力鯨\ue85c㛑\uab9a\uf426혽\uddf0틥⢻\uddb6͞棝蒎\ufbc2") + phantom.getClassId().getId());
      }

   }

   public static void equipJewelItem(ArmorObject jewel, L2PcInstance phantom) {
      if (jewel != null) {
         try {
            int id = jewel.getItemId();
            L2ItemInstance item = ItemTable.getInstance().createDummyItem(id);
            item.setEnchantLevel(jewel.getEnchant());
            L2ItemInstance item2 = phantom.getInventory().addItem(v.m("鹧㮝㴅װྼ麬兖"), item, phantom, (L2Object)null);
            phantom.getInventory().equipItemAndRecord(item2);
         } catch (Exception var5) {
            _log.error(v.m("鹲㮇㴖ױྺ黣兑\uebdb衧ꬋ嚞㠟环륱Ḋ\uf179") + jewel.getId() + v.m("鸗㮓㴋\u05ec\u0fe8麠兗\uebdf衣\uab1d囒㡖玢뤯Ḑ") + phantom.getClassId().getId());
         }

      }
   }

   public static void equipArmorItem(ArmorObject item, L2PcInstance phantom) {
      try {
         if (item != null && item.getId() != -1) {
            L2ItemInstance helmet = activateArmor(item);
            L2ItemInstance helmet2 = phantom.getInventory().addItem(v.m("⨼찷ꃺ馱呅抉睵"), helmet, phantom, (L2Object)null);
            phantom.getInventory().equipItemAndRecord(helmet2);
         }
      } catch (Exception var4) {
         _log.error(v.m("⨩찭ꃩ馰呃拆睹\uf0b2肻\uf16d孙俻㸹\u05c8拠翜") + item.getId() + v.m("⩌찹ꃴ馭向抅睴\uf0a1肥\uf171嬋侲㸴֖拺") + phantom.getClassId().getId());
      }

   }

   public static void equipArmorItem(ArmorObject item, L2PcInstance phantom, Stats[] elements) {
      try {
         if (item != null && item.getId() != -1) {
            L2ItemInstance helmet = activateArmor(item, elements);
            L2ItemInstance helmet2 = phantom.getInventory().addItem(v.m("⨼찷ꃺ馱呅抉睵"), helmet, phantom, (L2Object)null);
            phantom.getInventory().equipItemAndRecord(helmet2);
         }
      } catch (Exception var5) {
         _log.error(v.m("⨩찭ꃩ馰呃拆睹\uf0b2肻\uf16d孙俻㸹\u05c8拠翜") + item.getId() + v.m("⩌찹ꃴ馭向抅睴\uf0a1肥\uf171嬋侲㸴֖拺") + phantom.getClassId().getId());
      }

   }

   public static L2ItemInstance activateArmor(ArmorObject armor, Stats[] elements) {
      L2ItemInstance item = activateArmor(armor);
      return item;
   }

   public static L2ItemInstance activateArmor(ArmorObject armor) {
      int id = armor.getItemId();
      L2ItemInstance item = ItemTable.getInstance().createDummyItem(id);
      item.setEnchantLevel(armor.getEnchant());
      return item;
   }

   public static int getSoulShotIdByWeaponGrade(L2ItemInstance item) {
      int grade = item.getItem().getCrystalType();
      if (grade == 0) {
         return 1835;
      } else if (grade == 1) {
         return 1463;
      } else if (grade == 2) {
         return 1464;
      } else if (grade == 3) {
         return 1465;
      } else if (grade == 4) {
         return 1466;
      } else {
         return grade == 5 ? 1467 : 0;
      }
   }

   public static int getBlessedSpiritShotIdByWeaponGrade(L2ItemInstance item) {
      int grade = item.getItem().getCrystalType();
      if (grade == 0) {
         return 3947;
      } else if (grade == 1) {
         return 3948;
      } else if (grade == 2) {
         return 3949;
      } else if (grade == 3) {
         return 3950;
      } else if (grade == 4) {
         return 3951;
      } else {
         return grade == 5 ? 3952 : 0;
      }
   }

   public static class CharIdAndClass {
      public int fakeSubId;
      public int fakeObjId;

      public CharIdAndClass(int fakeSubId, int fakeObjId) {
         this.fakeSubId = fakeSubId;
         this.fakeObjId = fakeObjId;
      }
   }
}
