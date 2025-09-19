package ru.catssoftware.fakes.objects;

import java.util.Iterator;
import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.abstracts.PhantomDefaultPartyAI;
import ru.catssoftware.fakes.utils.PhantomUtil;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.StatsSet;

public class PhantomPartyObject extends PhantomDefaultPartyAI {
   protected static final Logger _log = Logger.getLogger(PhantomPartyObject.class);

   public PhantomPartyObject(StatsSet set) {
      super(set);
   }

   public void doBattleAction() {
   }

   public void doPeaceAction() {
   }

   public void spawnPartyBattle() {
      Location spawn = this.getAnySpawnLocation();
      Iterator var3 = this._all_members.iterator();

      while(var3.hasNext()) {
         L2PcInstance p = (L2PcInstance)var3.next();
         p.getPosition().setXYZ(spawn.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_SPAWN_COLLISION_X), spawn.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_SPAWN_COLLISION_Y), spawn.getZ());
         p.spawnMe();
         p.setOnlineStatus(true);
         if (Config.DEBAG_LVL > 0) {
            System.out.println(PhantomPartyObject$PhantomPartyObject.o("\u1ccb蓎辊윎啕쉨\udfb2ᆐ风診쭔戮㮔甊呧⨢섥懄狢\u098e") + p.getName());
         }
      }

   }

   public void spawnPartyPeace() {
      Location spawn = this.getAnySpawnPeaceLocation();
      Iterator var3 = this._all_members.iterator();

      while(var3.hasNext()) {
         L2PcInstance p = (L2PcInstance)var3.next();
         p.doRevive();
         p.setKarma(0);
         PhantomUtil.doBuff(p);
         p.getStatus().setCurrentHp((double)p.getMaxHp());
         p.getStatus().setCurrentMp((double)p.getMaxMp());
         p.getPosition().setXYZ(spawn.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_SPAWN_COLLISION_X), spawn.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_SPAWN_COLLISION_Y), spawn.getZ());
         p.spawnMe();
         p.setOnlineStatus(true);
         if (Config.DEBAG_LVL > 0) {
            System.out.println(PhantomPartyObject$PhantomPartyObject.o("㹂쯮ﮃꌾ\uf446ㄬ趻ƞ⬧厸ᦒ쵱戱ላ釪ᢸ蒥壎⫏") + p.getName());
         }
      }

   }

   public void onPartyMemberDebuffed(L2PcInstance member) {
   }

   public void onPartyMemberAttacked(L2PcInstance member, L2Character attacker) {
   }

   public void getAndSetTarget(L2PcInstance phantom) {
   }
}
