package ru.catssoftware.fakes.objects;

import java.util.LinkedList;
import ru.catssoftware.fakes.objects.equip.WeaponObject;
import ru.catssoftware.fakes.objects.sets.JewelSet;
import ru.catssoftware.fakes.objects.sets.UnderwearSet;
import ru.catssoftware.fakes.templates.SkillsGroup;

public class PhantomClassAI {
   private SkillsGroup self_buff_skills;
   private SkillsGroup self_ultimate_buff_skills;
   private SkillsGroup debuff_skills;
   private SkillsGroup rare_debuff_skills;
   private SkillsGroup passive_skills;
   private SkillsGroup consumble_skills;
   private SkillsGroup resurrect_skills;
   private SkillsGroup heal_skills;
   private SkillsGroup percent_heal_skills;
   private SkillsGroup buffs_skills;
   private SkillsGroup support_skills;
   private SkillsGroup situation_skills;
   private SkillsGroup summon_skills;
   private SkillsGroup nuke_skills;
   private SkillsGroup aoe_skills;
   private SkillsGroup detection_skills;
   private SkillsGroup rare_nuke_skills;
   private String type;
   private String spawn_type;
   private String party_type;
   private String buff_scheme;
   private int class_id;
   private int henna_one;
   private int henna_two;
   private int henna_three;
   private UnderwearSet underwear;
   private JewelSet jewels;
   private LinkedList<WeaponObject> weapons;
   private LinkedList<PhantomClassAI.L2Invent> inventory_items;
   private LinkedList<PhantomClassAI.L2Sets> sets;

   public PhantomClassAI(int id) {
      this.class_id = id;
      this.situation_skills = new SkillsGroup();
      this.summon_skills = new SkillsGroup();
      this.self_buff_skills = new SkillsGroup();
      this.self_ultimate_buff_skills = new SkillsGroup();
      this.debuff_skills = new SkillsGroup();
      this.rare_debuff_skills = new SkillsGroup();
      this.passive_skills = new SkillsGroup();
      this.consumble_skills = new SkillsGroup();
      this.resurrect_skills = new SkillsGroup();
      this.heal_skills = new SkillsGroup();
      this.percent_heal_skills = new SkillsGroup();
      this.buffs_skills = new SkillsGroup();
      this.support_skills = new SkillsGroup();
      this.nuke_skills = new SkillsGroup();
      this.aoe_skills = new SkillsGroup();
      this.detection_skills = new SkillsGroup();
      this.rare_nuke_skills = new SkillsGroup();
      this.underwear = null;
      this.jewels = null;
      this.sets = new LinkedList();
      this.inventory_items = new LinkedList();
      this.weapons = new LinkedList();
   }

   public int getClassId() {
      return this.class_id;
   }

   public void setHennaOne(int id) {
      this.henna_one = id;
   }

   public void setHennaTwo(int id) {
      this.henna_two = id;
   }

   public void setHennaThree(int id) {
      this.henna_three = id;
   }

   public int[] getAllHennas() {
      int[] a = new int[]{this.henna_one, this.henna_two, this.henna_three};
      return a;
   }

   public int getHennaOne() {
      return this.henna_one;
   }

   public int getHennaTwo() {
      return this.henna_two;
   }

   public int getHennaThree() {
      return this.henna_three;
   }

   public void putSelfBuffs(SkillsGroup bs) {
      this.self_buff_skills = bs;
   }

   public void putUltimateBuffs(SkillsGroup bs) {
      this.self_ultimate_buff_skills = bs;
   }

   public void putDebuffs(SkillsGroup bs) {
      this.debuff_skills = bs;
   }

   public void putRareDebuffs(SkillsGroup bs) {
      this.rare_debuff_skills = bs;
   }

   public void putPassive(SkillsGroup bs) {
      this.passive_skills = bs;
   }

   public void putItemSkills(SkillsGroup bs) {
      this.consumble_skills = bs;
   }

   public void putResurrectSkills(SkillsGroup bs) {
      this.resurrect_skills = bs;
   }

   public SkillsGroup getSelfBuffs() {
      return this.self_buff_skills;
   }

   public SkillsGroup getSelfUltimateBuffs() {
      return this.self_ultimate_buff_skills;
   }

   public SkillsGroup getDebuffs() {
      return this.debuff_skills;
   }

   public SkillsGroup getRareDebuffs() {
      return this.rare_debuff_skills;
   }

   public SkillsGroup getPassive() {
      return this.passive_skills;
   }

   public SkillsGroup getItemUseSkills() {
      return this.consumble_skills;
   }

   public SkillsGroup getResurrectSkills() {
      return this.resurrect_skills;
   }

   public void putHeals(SkillsGroup bs) {
      this.heal_skills = bs;
   }

   public void putPercentHeals(SkillsGroup bs) {
      this.percent_heal_skills = bs;
   }

   public SkillsGroup getHealSkills() {
      return this.heal_skills;
   }

   public SkillsGroup getPercentHealSkills() {
      return this.percent_heal_skills;
   }

   public void putBuffs(SkillsGroup bs) {
      this.buffs_skills = bs;
   }

   public void putSituationSkills(SkillsGroup bs) {
      this.situation_skills = bs;
   }

   public void putSupports(SkillsGroup bs) {
      this.support_skills = bs;
   }

   public void putSummons(SkillsGroup bs) {
      this.summon_skills = bs;
   }

   public SkillsGroup getBuffSkills() {
      return this.buffs_skills;
   }

   public SkillsGroup getSituationSkills() {
      return this.situation_skills;
   }

   public SkillsGroup getSupportSkills() {
      return this.support_skills;
   }

   public SkillsGroup getSummonSkills() {
      return this.summon_skills;
   }

   public void putNukes(SkillsGroup s) {
      this.nuke_skills = s;
   }

   public void putAoE(SkillsGroup s) {
      this.aoe_skills = s;
   }

   public void putDetectionSkills(SkillsGroup s) {
      this.detection_skills = s;
   }

   public void putRareNukes(SkillsGroup s) {
      this.rare_nuke_skills = s;
   }

   public SkillsGroup getNukeSkills() {
      return this.nuke_skills;
   }

   public SkillsGroup getAoESkills() {
      return this.aoe_skills;
   }

   public SkillsGroup getDetectionSkills() {
      return this.detection_skills;
   }

   public SkillsGroup getRareNukeSkills() {
      return this.rare_nuke_skills;
   }

   public void setType(String t) {
      this.type = t;
   }

   public void setSpawnType(String t) {
      this.spawn_type = t;
   }

   public void setPartyType(String t) {
      this.party_type = t;
   }

   public void setBuffScheme(String t) {
      this.buff_scheme = t;
   }

   public String getType() {
      return this.type;
   }

   public String getSpawnType() {
      return this.spawn_type;
   }

   public String getPartyType() {
      return this.party_type;
   }

   public String getBuffScheme() {
      return this.buff_scheme;
   }

   public void setUnderwear(UnderwearSet set) {
      this.underwear = set;
   }

   public void setJewel(JewelSet set) {
      this.jewels = set;
   }

   public void addArmor(String armor, int shield) {
      this.sets.add(new PhantomClassAI.L2Sets(armor, shield));
   }

   public void addWeapon(WeaponObject weapon) {
      this.weapons.add(weapon);
   }

   public void addInventoryItem(int item_id, int count) {
      this.inventory_items.add(new PhantomClassAI.L2Invent(item_id, count));
   }

   public UnderwearSet getUnderwear() {
      return this.underwear;
   }

   public JewelSet getJewel() {
      return this.jewels;
   }

   public LinkedList<PhantomClassAI.L2Sets> getArmors() {
      return this.sets;
   }

   public LinkedList<WeaponObject> getWeapons() {
      return this.weapons;
   }

   public LinkedList<PhantomClassAI.L2Invent> getInventoryItems() {
      return this.inventory_items;
   }

   public int size() {
      return this.situation_skills.getAllSkills().length + this.self_buff_skills.getAllSkills().length + this.self_ultimate_buff_skills.getAllSkills().length + this.debuff_skills.getAllSkills().length + this.rare_debuff_skills.getAllSkills().length + this.passive_skills.getAllSkills().length + this.consumble_skills.getAllSkills().length + this.resurrect_skills.getAllSkills().length + this.heal_skills.getAllSkills().length + this.percent_heal_skills.getAllSkills().length + this.buffs_skills.getAllSkills().length + this.support_skills.getAllSkills().length + this.nuke_skills.getAllSkills().length + this.aoe_skills.getAllSkills().length + this.detection_skills.getAllSkills().length + this.rare_nuke_skills.getAllSkills().length;
   }

   public static class L2Invent {
      public int _items;
      public int _counts;

      L2Invent(int item1, int item2) {
         this._items = item1;
         this._counts = item2;
      }
   }

   public static class L2Sets {
      public String _armor;
      public int _sheild;

      L2Sets(String item1, int item2) {
         this._armor = item1;
         this._sheild = item2;
      }
   }
}
