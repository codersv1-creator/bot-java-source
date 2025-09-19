package ru.catssoftware.fakes;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;
import ru.catssoftware.Config;
import ru.catssoftware.fakes.dao.PhantomDAO;
import ru.catssoftware.fakes.handlers.admincommand.AdminPhantom;
import ru.catssoftware.fakes.holders.PhantomColorHolder;
import ru.catssoftware.fakes.holders.PhantomPhrasesHolder;
import ru.catssoftware.fakes.holders.PhantomsHolder;
import ru.catssoftware.fakes.objects.PhantomPartyObject;
import ru.catssoftware.fakes.parsers.ClassAIParser;
import ru.catssoftware.fakes.parsers.PartyAIParser;
import ru.catssoftware.fakes.parsers.PhantomSpawnParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomAccessoryParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomArmorParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomBuffParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomJewelParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomShieldParser;
import ru.catssoftware.fakes.parsers.reworked.PhantomUnderwearParser;
import ru.catssoftware.fakes.tasks.PhantomTask;
import ru.catssoftware.fakes.utils.PhantomInitUtil;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.datatables.ClanTable;
import ru.catssoftware.gameserver.gmaccess.gmController;
import ru.catssoftware.gameserver.model.L2Clan;
import ru.catssoftware.gameserver.model.L2ClanMember;
import ru.catssoftware.gameserver.model.L2Party;
import ru.catssoftware.gameserver.model.L2Spawn;
import ru.catssoftware.gameserver.model.L2World;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.tools.random.Rnd;
import ru.catssoftware.util.Console;

public class PhantomsEngine {
   private static final Logger _log = Logger.getLogger(PhantomsEngine.class);
   private static ConcurrentLinkedQueue<L2PcInstance> _fakes = new ConcurrentLinkedQueue();
   private static ConcurrentLinkedQueue<Integer> _all_party_charId = new ConcurrentLinkedQueue();
   private static PhantomsEngine _instance;

   public static PhantomsEngine getInstance() {
      return _instance;
   }

   public static void init() {
      _instance = new PhantomsEngine();
      _instance.load();
   }

   public void load() {
      if (Config.ALLOW_FAKE_PLAYERS_PLUS && Config.GEODATA) {
         gmController.getInstance().regCommand(new AdminPhantom());
         PhantomDAO.getInstance().loadClans();
         PhantomDAO.getInstance().loadCharIdAndClassId();
         Console.printSection(oo.q("\ue781㤫믩챪䱬♆\uead8ꋌﶅᨂ笘굏"));
         PhantomAccessoryParser.getInstance().allParse();
         PhantomArmorParser.getInstance().allParse();
         PhantomJewelParser.getInstance().allParse();
         PhantomShieldParser.getInstance().allParse();
         PhantomUnderwearParser.getInstance().allParse();
         PhantomBuffParser.getInstance().allParse();
         PhantomColorHolder.load();
         PhantomPhrasesHolder.load();
         ClassAIParser.getInstance().allParse();
         PhantomSpawnParser.getInstance().allParse();
         PhantomsHolder.load();
         PartyAIParser.getInstance().allParse();
         if (PhantomsHolder.isFakesReady()) {
            ThreadPoolManager.getInstance().schedule(new PhantomTask(), (long)Config.FAKE_PLAYERS_DELAY_BEFORE_FIRST_WAVE_SPAWN * 1000L);
         }
      }

   }

   public void restoreFakeParties() {
      int count_p = 0;

      try {
         Iterator var4 = PartyAIParser.getInstance().getAllParties().iterator();

         label91:
         while(true) {
            PhantomPartyObject party;
            do {
               if (!var4.hasNext()) {
                  break label91;
               }

               party = (PhantomPartyObject)var4.next();
            } while(!party.isSpawnEnabled());

            for(int i = 0; i < party.getObjIds().length; ++i) {
               int charId = party.getObjIds()[i];
               if (charId != 0) {
                  String ai_type = PhantomInitUtil.getAIbyClassId(party.getClassList()[i]);
                  L2PcInstance phantom;
                  if (getInstance().isPhantomAlreadySpawned(charId)) {
                     phantom = L2World.getInstance().getPlayer(charId);
                     phantom.phantom_params.getPhantomAI().abortAITask();
                     this.despawnPhantom(phantom);
                     phantom.setOnlineStatus(false);
                     phantom.kick();
                  }

                  phantom = PhantomInitUtil.restoreFake(charId, ai_type);
                  if (phantom != null) {
                     Location loc = party.getAnySpawnLocation();
                     loc = new Location(loc.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_SPAWN_COLLISION_X), loc.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_SPAWN_COLLISION_Y), loc.getZ());
                     loc = L2Spawn.findPointToStay(loc.getX(), loc.getY(), loc.getZ(), phantom.getTemplate().getCollisionRadius() * 3, phantom.getTemplate().getCollisionRadius() * 5, phantom.getInstanceId());
                     phantom.getPosition().setXYZ(loc);
                     if (party.getPartyLeader() == null) {
                        party.setPartyLeader(phantom);
                        party.getPartyLeader().setParty(new L2Party(party.getPartyLeader(), 1));
                     }

                     phantom.phantom_params.setPhantomPartyAI(party);
                     party.addPartyMember(phantom);
                     phantom.setHandicapAttack(party.getHandicapAttack());
                     phantom.setHandicapDefence(party.getHandicapDefence());
                  }
               }
            }

            L2Clan clan = ClanTable.getInstance().getClan(party.getClanId());

            L2PcInstance member;
            for(Iterator var12 = party.getAllMembers().iterator(); var12.hasNext(); member.phantom_params.getPhantomAI().startAITask(500L)) {
               member = (L2PcInstance)var12.next();
               if (member != party.getPartyLeader()) {
                  party.getPartyLeader().getParty().addPartyMember(member);
               }

               if (party.getClanId() != -1 && clan != null) {
                  new L2ClanMember(clan, member.getName(), member.getLevel(), member.getClassId().getId(), member.getObjectId(), 0, 0, member.getTitle(), member.getAppearance().getSex() ? 1 : 0, -128);
                  member.setSubPledgeType(0);
                  member.setClan(clan);
                  member.setLvlJoinedAcademy(0);
                  member.setApprentice(0);
                  PhantomDAO.getInstance().setClanToPhantom(member.getObjectId());
                  if (party.getClanId() != 0) {
                     member.setClan(ClanTable.getInstance().getClan(party.getClanId()));
                  }
               }
            }

            party.spawnPartyBattle();
            party.changePartyState(1);
            party.startAITask(5000L);
            ++count_p;
         }
      } catch (Exception var9) {
         if (Config.DEBAG_LVL > 0) {
            _log.error(oo.q("勍㰬癣얄䫱懄娣\uda75焀\uda12籺覘\uf8d5諭\u2b6e\ue2f9鬄綠莣낂晒⸠縻\uf803钣枮熿歽ㅡ䩼箂꿣\u193c"));
         }

         if (Config.DEBAG_LVL > 0) {
            var9.printStackTrace();
         }
      }

      _log.info(oo.q("勘㰪癥얃䫫懎婴\uda26然\uda0c籼覆\uf8d5諭⬤\ue2ab") + count_p + oo.q("劽㰴癣얘䫱懂娫\uda75煫"));
   }

   public boolean isPhantomAlreadySpawned(int charId) {
      return _fakes.contains(charId);
   }

   public void addPhantom(L2PcInstance phantom) {
      _fakes.add(phantom);
   }

   public void despawnPhantom(L2PcInstance phantom) {
      _fakes.remove(phantom);
   }

   public ConcurrentLinkedQueue<L2PcInstance> getPhantom() {
      return _fakes;
   }

   public L2PcInstance getPhantom(int objId) {
      Iterator var3 = _fakes.iterator();

      while(var3.hasNext()) {
         L2PcInstance p = (L2PcInstance)var3.next();
         if (p.getObjectId() == objId) {
            return p;
         }
      }

      return null;
   }

   public void despawnPhantom(int id) {
      Iterator iter = _fakes.iterator();

      while(iter.hasNext()) {
         L2PcInstance p = (L2PcInstance)iter.next();
         if (p.getObjectId() == id) {
            iter.remove();
            break;
         }
      }

   }

   public ConcurrentLinkedQueue<Integer> getAllPartyObjID() {
      return _all_party_charId;
   }
}
