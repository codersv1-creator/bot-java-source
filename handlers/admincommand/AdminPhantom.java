package ru.catssoftware.fakes.handlers.admincommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import ru.catssoftware.L2DatabaseFactory;
import ru.catssoftware.extension.GameExtensionManager;
import ru.catssoftware.extension.ObjectExtension;
import ru.catssoftware.fakes.dao.PhantomDAO;
import ru.catssoftware.fakes.holders.PhantomsHolder;
import ru.catssoftware.fakes.model.ClassesDictionary;
import ru.catssoftware.fakes.model.RacesDictionary;
import ru.catssoftware.fakes.tables.PhantomInfo;
import ru.catssoftware.fakes.utils.PhantomInitUtil;
import ru.catssoftware.gameserver.ai.CtrlIntention;
import ru.catssoftware.gameserver.geodata.GeoEngine;
import ru.catssoftware.gameserver.gmaccess.gmHandler;
import ru.catssoftware.gameserver.model.L2CharPosition;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2NpcInstance;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.network.L2GameClient;
import ru.catssoftware.gameserver.network.serverpackets.ActionFailed;
import ru.catssoftware.gameserver.network.serverpackets.L2GameServerPacket;
import ru.catssoftware.gameserver.network.serverpackets.MoveToPawn;
import ru.catssoftware.gameserver.network.serverpackets.MyTargetSelected;
import ru.catssoftware.tools.random.Rnd;

public class AdminPhantom extends gmHandler {
   private static final Logger _log = Logger.getLogger(AdminPhantom.class);
   private static final String[] commands = new String[]{hh.j("\ueb6a锆긑"), hh.j("\ueb6a锆긑懺㘫䎥䄹䢓\uef97꼴톦⍼匘꒲"), hh.j("\ueb6a锆긑懺㘫䎥䄹䢓\uef91꼴톷⍾"), hh.j("\ueb6a锆긑懺㘾䎥䄫䢾\uef86꼦톼⍄匟꒩으菃\uf250떪욘▃ᓾ玹ᣤ"), hh.j("\ueb6a锆긑懺㘾䎥䄫䢾\uef86꼦톼⍄匟꒩으菃\uf25f떯욗▔ᓒ玿\u18fb䚉㗞湘䩮㿘"), hh.j("\ueb6a锆긑懺㘾䎥䄫䢾\uef86꼦톼⍄匟꒩으菃\uf250떪욘▞ᓾ"), hh.j("\ueb6a锆긑懺㘾䎥䄫䢾\uef86꼦톼⍄匏꒧윫菹\uf240"), hh.j("\ueb6a锆긑懺㘼䎨䄬䢢\uef97꼺톹⍨匢꒲윺菩\uf25d떥욘▄ᓨ"), hh.j("\ueb6a锆긑懺㘼䎨䄬䢢\uef97꼺톹⍨匢꒢윭菰\uf256떲욜▟ᓡ玸"), hh.j("\ueb6e锓긖懑"), hh.j("\ueb6e锓긖懑㙾"), hh.j("\ueb6e锓긖懑㙿"), hh.j("\ueb6e锓긖懑㙸"), hh.j("\ueb6e锓긖懑㙹")};

   public AdminPhantom() {
      _log.info(hh.j("椂嘼嚚隞缾ਘ嚊랝\ue7fe溂孕〡ꒂ놽\udc71⑷县殝꺠荛\udae6뾱뚭儯섎\uf5e5›Ѧ샑쑲\u1cce붱莿泄워连ἢ"));
   }

   public void runCommand(L2PcInstance activeChar, String... params) {
      String command = params[0];
      StringTokenizer st = new StringTokenizer("");

      try {
         if (params[1] != null) {
            st = new StringTokenizer(params[1]);
         }
      } catch (Exception var10) {
      }

      L2PcInstance phantom;
      L2CharPosition pos;
      switch(command.hashCode()) {
      case -1371300987:
         if (command.equals(hh.j("腳Ќ㰻ࢬ뾆ם騄걆\ue391燱퓕씰"))) {
            if (activeChar.getTarget() == null) {
               activeChar.sendMessage(hh.j("腍Г㱯\u0887뾀\u05ca騗걼\ue397"));
            } else if (activeChar.getTarget().getPlayer() != null && activeChar.getTarget().getPlayer().isFantome()) {
               activeChar.sendMessage(hh.j("腓Д㰮\u089d뾕ח騝걐\ue38d燶퓙앵᠕䉢ꏣ솳\ue201뙂嶼紸\uee05") + activeChar.getTarget().getPlayer().getRace().name());
            } else {
               activeChar.sendMessage(hh.j("腊В㰬\u089c뾓\u05ca騕걺\ue397熰퓂씴᠕䉤ꏥ솢"));
            }
         }
         break;
      case -1254293599:
         if (command.equals(hh.j("腳Ќ㰻ࢬ뾓ם騖걫\ue386燣퓞씊᠅䉬ꏴ솉\ue24d뙂嶡給\uee7a쐏佣ﱭ衪\uef75맱샄"))) {
            setAndGetClassId(4);
            activeChar.sendMessage(hh.j("腂П㰻\u089a뾗י騄걼\ue387熪풖씶᠋䉢ꏳ솳\ue252똅"));
         }
         break;
      case -51580338:
         if (command.equals(hh.j("腳Ќ㰻ࢬ뾑א騑걷\ue397燿퓛씦ᠸ䉧ꏥ솺\ue244뙟嶪絭\uee49쐈"))) {
            doDelete();
            activeChar.sendMessage(hh.j("腌А㰫\u08d3뾓ם験걶\ue391燴퓅앵᠐䉦ꏲ솳\ue201뙏嶪絮\uee40쐘佪ﰢ"));
         }
         break;
      case 111220:
         if (command.equals(hh.j("腳Ќ㰻"))) {
            activeChar.sendMessage(hh.j("腐Ј㰮\u0881뾕֘騔걸\ue397燱풖씡᠕䉢ꏮ솥\ue247뙎嶽紣\uee04쑍"));
            doTruncate();
            activeChar.sendMessage(hh.j("腆В㰫\u08d3뾅י騄걸\ue3c3燤퓄씴᠉䉰ꏦ솳\ue253똋嶬絭\uee48쐜佣ﱩ衭\uef63릺"));
         }
         break;
      case 3556498:
         if (command.equals(hh.j("腷Й㰼\u0887"))) {
            if (activeChar.getTarget() == null) {
               activeChar.sendMessage(hh.j("腍Г㱯\u0887뾀\u05ca騗걼\ue397"));
            } else if (activeChar.getTarget() instanceof L2NpcInstance) {
               L2NpcInstance npc = (L2NpcInstance)activeChar.getTarget();
               activeChar.sendMessage(hh.j("腍Ь㰌\u08c9뿁") + npc.getName());
               npc.setRunning();
               npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(npc.getX() + Rnd.get(-200, 200), npc.getY() + Rnd.get(-200, 200), npc.getZ(), 0));
            } else {
               activeChar.sendMessage(activeChar.getTarget().getName());
               pos = new L2CharPosition(activeChar.getTarget().getPlayer().getX() + Rnd.get(-200, 200), activeChar.getTarget().getPlayer().getY() + Rnd.get(-200, 200), activeChar.getTarget().getPlayer().getZ(), 0);
               activeChar.getTarget().getPlayer().getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, pos);
            }
         }
         break;
      case 61776244:
         if (command.equals(hh.j("腳Ќ㰻ࢬ뾑א騑걷\ue397燿퓛씦ᠸ䉷ꏲ솣\ue24f뙈嶮絶\uee40"))) {
            doTruncate();
            activeChar.sendMessage(hh.j("腇Н㰻\u0892뿁\u05cc騂걸\ue38d燣퓐씰᠕䈣ꏣ솹\ue24c뙛嶣絧\uee51쐉伡"));
         }
         break;
      case 110251488:
         if (command.equals(hh.j("腷Й㰼\u0887뿓"))) {
            activeChar.sendMessage(hh.j("腢П㰻\u089a뾗ם騳걱\ue382燢풘씼᠔䉊ꏮ솥\ue248뙏嶪絘\uee4a쐂佪ﰤ衕\uef34많샘襅孥뿹挅搥䵿璐華뤛\ua6fb閌骠੨踽谷\uf3b2") + activeChar.isInsideZone((byte)1));
            if (activeChar.getTarget() == null) {
               activeChar.sendMessage(hh.j("腍Г㱯\u0887뾀\u05ca騗걼\ue397"));
            } else if (activeChar.getTarget().getPlayer() != null && activeChar.getTarget().getPlayer().isFantome()) {
               if (activeChar.getTarget().getPlayer().phantom_params.getLockedTarget() == null) {
                  activeChar.sendMessage(hh.j("腓Д㰮\u089d뾕ח騝걐\ue38d燶퓙앵᠓䉢ꏲ솱\ue244뙟巯絫\uee56쑌佡ﱹ衵\uef6a"));
                  activeChar.sendMessage(activeChar.getTarget().getPlayer().getName() + hh.j("脣Ь㰪\u0892뾂ם驐걣\ue38c燾퓓앵᠎䉰ꎺ쇶") + activeChar.getTarget().getPlayer().isInsideZone((byte)1));
               } else {
                  activeChar.sendMessage(hh.j("腓Д㰮\u089d뾕ח騝걐\ue38d燶퓙앵᠓䉢ꏲ솱\ue244뙟巯絫\uee56쑖伯") + activeChar.getTarget().getPlayer().phantom_params.getLockedTarget().getName());
                  activeChar.sendMessage(activeChar.getTarget().getPlayer().getName() + hh.j("脣Ь㰪\u0892뾂ם驐걣\ue38c燾퓓앵᠎䉰ꎺ쇶") + activeChar.getTarget().getPlayer().isInsideZone((byte)1));
               }
            } else {
               activeChar.sendMessage(hh.j("腊В㰬\u089c뾓\u05ca騕걺\ue397熰퓂씴᠕䉤ꏥ솢"));
            }

            if (activeChar.getTarget() != null && activeChar.getTarget().getPlayer().getCharacter() instanceof L2Character) {
               activeChar.sendMessage(hh.j("腷Й㰼\u0887뿐֊"));
               activeChar.moveToLocation(activeChar.getTarget().getPlayer().getX() + Rnd.get(-300, 300), activeChar.getTarget().getPlayer().getY() + Rnd.get(-300, 300), activeChar.getTarget().getPlayer().getZ(), 0, true);
               pos = new L2CharPosition(activeChar.getTarget().getPlayer().getX() + Rnd.get(-200, 200), activeChar.getTarget().getPlayer().getY() + Rnd.get(-200, 200), activeChar.getTarget().getPlayer().getZ(), 0);
               activeChar.getTarget().getPlayer().getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, pos);
               activeChar.sendMessage(hh.j("腷Й㰼\u0887뿓։"));
            }
         }
         break;
      case 110251489:
         if (command.equals(hh.j("腷Й㰼\u0887뿒"))) {
            if (activeChar.getTarget() == null) {
               activeChar.sendMessage(hh.j("腍Г㱯\u0887뾀\u05ca騗걼\ue397"));
            } else if (activeChar.getTarget() instanceof L2PcInstance) {
               phantom = (L2PcInstance)activeChar.getTarget();
               List<L2NpcInstance> _npcs = phantom.getAroundNPC(10000);
               L2NpcInstance _npc = null;
               if (!_npcs.isEmpty()) {
                  _npc = (L2NpcInstance)_npcs.get(Rnd.get(0, _npcs.size() - 1));
               }

               activeChar.sendMessage(hh.j("腜В㰿\u0890뾒֖騃거\ue399燵풞야ᡝ䈣") + _npcs.size());
               if (_npc != null && Rnd.get(1, 1000000) <= 600000 && !_npc.isAutoAttackable(phantom)) {
                  if (!_npc.canInteractForFantome(phantom)) {
                     if (GeoEngine.canSeeTarget(phantom, _npc, false)) {
                        phantom.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, _npc);
                     } else {
                        phantom.sendPacket((L2GameServerPacket)ActionFailed.STATIC_PACKET);
                     }
                  } else {
                     phantom.sendPacket((L2GameServerPacket)(new MyTargetSelected(_npc.getObjectId(), phantom.getLevel() - _npc.getLevel())));
                     if (GameExtensionManager.getInstance().handleAction(this, ObjectExtension.Action.NPC_ONACTION, phantom) != null) {
                        return;
                     }

                     if (_npc.getNpcId() == 29025) {
                        phantom.disableMove(1000 + phantom.getColRadius() + _npc.getColRadius());
                     } else {
                        phantom.disableMove(36 + phantom.getColRadius() + _npc.getColRadius());
                     }

                     if (_npc.getNpcId() == 29025) {
                        phantom.sendPacket((L2GameServerPacket)(new MoveToPawn(phantom, _npc, 1000 + phantom.getColRadius() + _npc.getColRadius())));
                     } else {
                        phantom.sendPacket((L2GameServerPacket)(new MoveToPawn(phantom, _npc, 36 + phantom.getColRadius() + _npc.getColRadius())));
                     }

                     _npc.onRandomAnimation(phantom);
                     phantom.sendPacket((L2GameServerPacket)ActionFailed.STATIC_PACKET);
                  }
               } else {
                  activeChar.sendMessage(hh.j("腍Ь㰌\u08d3뾏\u05cd騜걵"));
               }
            }
         }
         break;
      case 110251490:
         if (command.equals(hh.j("腷Й㰼\u0887뿕")) && activeChar.getTarget() instanceof L2PcInstance && activeChar.getTarget().isFantome()) {
            phantom = (L2PcInstance)activeChar.getTarget();
            if (phantom.phantom_params.getPhantomWeapon() != null) {
               PhantomInitUtil.phantomShotActivate(phantom);
            }
         }
         break;
      case 110251491:
         if (command.equals(hh.j("腷Й㰼\u0887뿔")) && activeChar.getTarget() instanceof L2PcInstance) {
            phantom = (L2PcInstance)activeChar.getTarget();
            activeChar.sendMessage(hh.j("腀Г㰣\u089f뾈\u05cb騙걶\ue38d燘퓓씼᠀䉫ꏴ쇬\ue201") + phantom.getCollisionHeight());
            activeChar.sendMessage(hh.j("蔕I㡲\u0cba믙ƅ鹀갦\ue3d9熰") + (phantom.getAppearance().getSex() ? hh.j("蔗L") : hh.j("蔞I㠍")));
            activeChar.sendMessage(hh.j("蔣L㠎ಲ믑ւ驐") + phantom.getAppearance().getFace());
         }
         break;
      case 761159379:
         if (command.equals(hh.j("腳Ќ㰻ࢬ뾓ם騖걫\ue386燣퓞씊᠕䉢ꏣ솳\ue252"))) {
            activeChar.sendMessage(hh.j("腑Н㰬\u0896뿁א騑걪\ue3c3燲퓓씰᠉䈣ꏡ솵\ue255뙂嶹絣\uee51쐉佫ﰢ"));
         }
         break;
      case 792421701:
         if (command.equals(hh.j("腳Ќ㰻ࢬ뾆ם騄걆\ue397燱퓄씲᠂䉷"))) {
            if (activeChar.getTarget() == null) {
               activeChar.sendMessage(hh.j("腍Г㱯\u0887뾀\u05ca騗걼\ue397"));
            } else if (activeChar.getTarget().getPlayer() != null && activeChar.getTarget().getPlayer().isFantome()) {
               if (activeChar.getTarget().getPlayer().phantom_params.getLockedTarget() == null) {
                  activeChar.sendMessage(hh.j("腓Д㰮\u089d뾕ח騝걐\ue38d燶퓙앵᠓䉢ꏲ솱\ue244뙟巯絫\uee56쑌佡ﱹ衵\uef6a"));
               } else {
                  activeChar.sendMessage(hh.j("腓Д㰮\u089d뾕ח騝걐\ue38d燶퓙앵᠓䉢ꏲ솱\ue244뙟巯絫\uee56쑖伯") + activeChar.getTarget().getPlayer().phantom_params.getLockedTarget().getName());
               }
            } else {
               activeChar.sendMessage(hh.j("腊В㰬\u089c뾓\u05ca騕걺\ue397熰퓂씴᠕䉤ꏥ솢"));
            }
         }
         break;
      case 953459711:
         if (command.equals(hh.j("腳Ќ㰻ࢬ뾓ם騖걫\ue386燣퓞씊᠅䉬ꏴ솉\ue242뙇嶮統\uee56쐉佼"))) {
            setAndGetClassId(3);
            activeChar.sendMessage(hh.j("腂П㰻\u089a뾗י騄걼\ue387熪풖씶᠋䉢ꏳ솳\ue252똅"));
         }
         break;
      case 1761883894:
         if (command.equals(hh.j("腳Ќ㰻ࢬ뾓ם騖걫\ue386燣퓞씊᠅䉬ꏴ솉\ue242뙇嶮絬\uee56"))) {
            st.nextToken();
            int percent = Integer.parseInt(st.nextToken());
            setAndGetClanId(percent);
            activeChar.sendMessage(hh.j("腂П㰻\u089a뾗י騄걼\ue387熪풖씶᠋䉢ꏮ솥\ue20f"));
         }
      }

   }

   public static void doDelete() {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         con.setTransactionIsolation(1);
         st = con.prepareStatement(hh.j("犎똶旔쁿\u2d2cੂ鴸\ue795\uf516㶪▶뉑念䨎䭙օ躶㏺\uaafbἪ\u0cff馘呪\uf565떑熗뺅픴㏟抟聦Ꮢ쥈舴ࢪ寤ꔙ귋궣湷\ue29e雙罼瘂\uea3f䛏\ue340"));
         st.setString(1, hh.j("犻똛斬쁜ⵌਠ鴯\ue79b\uf553㶙▖뉱"));
         rs = st.executeQuery();
         rs.setFetchSize(250);

         while(rs.next()) {
            L2GameClient.deleteCharByObjId(rs.getInt(hh.j("犾똛旹쁈\u2d26ੲ")));
         }
      } catch (Exception var12) {
         _log.warn(hh.j("犍똛旹쁔ⴛ\u0a79鵵\ue7cc\uf573㶂▃뉷忖䩋䬀\u05cd躴㏧ꫯἥ೯駝呶\uf579뗅燠뺡픞㏬抾聦Ꮠ쥃舶\u08b7寢ꕗ귙궎湶\ue292隔罝癠\uea38䛏") + var12);
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }

            if (st != null) {
               st.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var11) {
         }

      }

   }

   public static void doTruncate() {
      Connection con = null;
      PreparedStatement st = null;
      ResultSet rs = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         con.setTransactionIsolation(1);
         st = con.prepareStatement(hh.j("損\u0c5eﲸ䳲\uefa6\uf593ﾍڻ\ud84d췡쁝\u0e79執줶켨ﾩ褆⣿傾묾꒣䇫\u0bd1典ᆒ뿣⛳㍂俖䳙\uf78b✔찫騶֫虻뫃\u0a29쎷䄭즿䁪鋏\u0c52饺텝ᦒ"));
         st.setString(1, hh.j("搸\u0c73ﳀ䳑\uefc6\uf5f1ﾚڵ\ud808췒쁽๙"));
         rs = st.executeQuery();
         rs.setFetchSize(250);

         while(rs.next()) {
            PhantomInfo info = new PhantomInfo(rs.getString(hh.j("搽\u0c73ﲕ䳅\uefba\uf5a9ￌۼ\ud808")), rs.getString(hh.j("搪\u0c72ﲀ䳛\uef80")), rs.getInt(hh.j("搦")), rs.getInt(hh.j("搧")), rs.getInt(hh.j("搤")), rs.getInt(hh.j("搽\u0c77ﲕ䳙\uef8c\uf5a3")), rs.getInt(hh.j("搽\u0c73ﲕ䳅\uefac\uf5a3")), rs.getInt(hh.j("搭౾ﲌ")), rs.getInt(hh.j("搬౺ﲗ䳒")));
            int class_id = getClassId(info.charId);
            parseRewrite(info, class_id);
            L2GameClient.deleteCharByObjId(info.charId);
         }
      } catch (Exception var13) {
         _log.warn(hh.j("搎\u0c73ﲕ䳙\uef91\uf5a8\uffc0ۢ\ud828췉쁨\u0e5f埔쥳콱￡褄⣢傪묱꒳䆮்兤ᇆ뾔⛗㍨俥䳸\uf78b✖찠騴ֶ虽몍\u0a3b쎚䄬즳䀧鋮ర饽텝") + var13);
      } finally {
         try {
            if (rs != null) {
               rs.close();
            }

            if (st != null) {
               st.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var12) {
         }

      }

   }

   public static int getClassId(int charId) {
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;

      int var6;
      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement(hh.j("銭몬家젯虾䦪㼚Ӧ䴐\udaf6\udd36픇\ue0b6钏⟔玣\udef8\u0d55ⱳ\uea4b鷚ɲꢎ\ue34f爥\ua8deො濗壑쯖䏑뺁甯먵ꍾꠑ蚳떡귱靿듚쬏邶쐡\ua9fd"));
         statement.setInt(1, charId);
         rset = statement.executeQuery();
         if (!rset.next()) {
            return 0;
         }

         var6 = rset.getInt(hh.j("銝명宛젙虎䦗㽞"));
      } catch (Exception var15) {
         _log.warn(hh.j("銽몆宏젆虙䧞㽔Ӫ䴈\udab7\udd37픑\ue0ac钟➛玗\udecfഺⱝ\uea03鷘ɨ꣏\ue34e爱\ua8dfඅ濑壏쯄䎂뺥甂먃ꌖ꡴") + var15);
         _log.error("", var15);
         return 0;
      } finally {
         try {
            if (rset != null) {
               rset.close();
            }

            if (statement != null) {
               statement.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var14) {
         }

      }

      return var6;
   }

   public static void parseRewrite(PhantomInfo info, int class_id) {
      Connection con = null;
      PreparedStatement st = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         st = con.prepareStatement(hh.j("迭\udb28に创卐谠뚱棳\ueadaᘋ㌨ឤ鞇\uf436\uf753잳\ue311#烏䐔\udfbfￒ貧챌䡇䖇堨\uea30Ẩ䔜찝\udd15ꠝ㨕\uf4fc용Ⴛᰜ㵜᧤\uf7f9攉ᇄ㛷됄\ue19f\u0a46값읲\ue5c0މ\uebd5讼◓킅\ue322䤏뱭옣熄ⵃꝏ럗\ueefc螐\u0885\u08b9ွ䔟濄셂닔鍩䢣ⅹ\uf4acǐ왎\u19ca\uf286뚜暷魎卍深偆Ẵྗ囿份\u1757햋\uab18\uf54cβ㲶详⼡⻎望»훆戌⼢훧"));
         st.setInt(1, info.charId);
         st.setString(2, info.name);
         st.setInt(3, info.sex);
         st.setString(4, info.title);
         st.setInt(5, info.x);
         st.setInt(6, info.y);
         st.setInt(7, info.z);
         st.setInt(8, info.clanId);
         st.setInt(9, class_id);
         st.setInt(10, info.race);
         st.execute();
      } catch (Exception var13) {
         _log.warn(hh.j("迴\udb0eす到卶谛뛼棉\uead1ᘱ㌀\u17ed鞙\uf43b\uf708쟽\ue306#烗䐋\udffbￚ貪챋䡒䗕堓\uea31ỳ䔍찜\udd00ꠊ㩪\uf4f1욠Ⴗᰋ㴃ᦷ\uf7f5攟ᆜ㛬둍\ue1af੨걍읾") + var13);
      } finally {
         try {
            if (st != null) {
               st.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var12) {
         }

      }

   }

   public static void setAndGetClassId(int prof) {
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement(hh.j("ͧ塰䋝স蛑왥鸲\ueb3b\uecd1磳䤞\ueabbづﯜ킲\udc3f㍇첃컵ឡ\ua7cd犆"));
         rset = statement.executeQuery();

         while(rset.next()) {
            int class_id;
            if (prof == 4) {
               class_id = ClassesDictionary.getRandomAwakBotClass();
            } else {
               class_id = ClassesDictionary.getRandomBotClass();
            }

            PreparedStatement stAdd = con.prepareStatement(hh.j("͡塥䋕়蛆왴鸲\ueb61\uec99磔䤢\uea80ぇﮑ킱\udc77㍵첨컕\u17ee\ua7c3犙ॎ壍러被▪\ude82퇮\uf775硬쎀鈺\uda81䩍쿓閹祅嵿迒 踵㸨㵿\ue862瘬"));
            stAdd.setInt(1, class_id);
            stAdd.setInt(2, rset.getInt(hh.j("͗塝䋰এ蛛왕")));
            stAdd.execute();
         }
      } catch (Exception var14) {
         _log.warn(hh.j("ͷ塚䋤\u0991蛶옑鹼\ueb7e\uec85碕䤾\uea91せﮈ킭\udc25㍃쳍컢ឦ\ua7c1犇ए壍럪袖◮\ude85톿\uf72b砿쎤鈗\udab7䨥쾶") + var14);
         _log.error("", var14);
      } finally {
         try {
            if (rset != null) {
               rset.close();
            }

            if (statement != null) {
               statement.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var13) {
         }

      }

   }

   public static void setAndGetClanId(int percent) {
      if (!PhantomsHolder.isFakesReady()) {
         PhantomsHolder.load();
      }

      Iterator var2 = PhantomsHolder.getAllFakes().valueCollection().iterator();

      while(var2.hasNext()) {
         PhantomInfo phantom = (PhantomInfo)var2.next();
         if (Rnd.get(0, 1) == percent) {
            PhantomDAO.getInstance().setClanToPhantom(phantom.charId);
         }
      }

   }

   public void setAndGetRaces() {
      Connection con = null;
      PreparedStatement statement = null;
      ResultSet rset = null;
      boolean var4 = false;

      try {
         con = L2DatabaseFactory.getInstance().getConnection();
         statement = con.prepareStatement(hh.j("ᢠ뤐\uddb7\udcbb䐔㇝\ufdec囼\u0cce糔\uf223⩄ฯ㩲쓢䂭곪磯쟺㮈輲㉽"));
         rset = statement.executeQuery();

         while(rset.next()) {
            int race = RacesDictionary.getRaceByClassId(rset.getInt(hh.j("ᢐ뤹\udd9a\udc8d䐤㇖ﶥ嚲")));
            PreparedStatement stAdd = con.prepareStatement(hh.j("ᢦ뤅\uddbf\udcbf䐃㇌\ufdec嚦ಆ糳\uf21f⩿ญ㨿쓡䃥곘磄쟚㯇輭㉯ꐀ镙팷굆鲐ﬥ藄\uf23c쭁균臈豖턒⣐\udcd5홏圭嘸죳\u1af9"));
            stAdd.setInt(1, race);
            stAdd.setInt(2, rset.getInt(hh.j("ᢐ뤽\udd9a\udc8c䐞\u31ed")));
            stAdd.execute();
         }
      } catch (Exception var14) {
         _log.warn(hh.j("ᢦ뤻\udd9a\udc9c䐻\u31ec\ufdec嚢\u0c81粲\uf212⩤ฏ㨿쓻䂱겫磳쟯㮄輺㈴ꑃ") + var14);
         _log.error("", var14);
      } finally {
         try {
            if (rset != null) {
               rset.close();
            }

            if (statement != null) {
               statement.close();
            }

            if (con != null) {
               con.close();
            }
         } catch (SQLException var13) {
         }

      }

   }

   public String[] getCommandList() {
      return commands;
   }
}
