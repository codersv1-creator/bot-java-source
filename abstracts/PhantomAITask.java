package ru.catssoftware.fakes.abstracts;

import java.util.Collections;
import java.util.List;
import ru.catssoftware.Config;
import ru.catssoftware.extension.GameExtensionManager;
import ru.catssoftware.extension.ObjectExtension;
import ru.catssoftware.fakes.ai.tasks.other.ChatTask;
import ru.catssoftware.fakes.ai.tasks.other.EndPeaceGoToTeleportTask;
import ru.catssoftware.fakes.utils.PhantomUtil;
import ru.catssoftware.gameserver.ThreadPoolManager;
import ru.catssoftware.gameserver.ai.CtrlIntention;
import ru.catssoftware.gameserver.geodata.GeoEngine;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.L2ItemInstance;
import ru.catssoftware.gameserver.model.Location;
import ru.catssoftware.gameserver.model.actor.instance.L2NpcInstance;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.network.serverpackets.L2GameServerPacket;
import ru.catssoftware.gameserver.network.serverpackets.MoveToPawn;
import ru.catssoftware.gameserver.network.serverpackets.MyTargetSelected;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;
import ru.catssoftware.tools.random.Rnd;

public abstract class PhantomAITask extends RunnableImpl {
   public L2PcInstance phantom;

   public PhantomAITask(L2PcInstance ph) {
      this.phantom = ph;
   }

   public void clientNotifyDead() {
   }

   public boolean doAction() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println("doAction() doAction() doAction() doAction() doAction()");
      }

      this.equipDisarmedWeapon();
      if (Config.DEBAG_LVL > 0) {
         System.out.println(this.phantom.getPlayer().getName() + " Peice zone is: " + this.phantom.getPlayer().isInsideZone((byte)1));
      }

      if (this.phantom.getPlayer().isInsideZone((byte)1)) {
         this.doPeaceAction();
         return true;
      } else {
         return false;
      }
   }

   public void doPeaceAction() {
      if (Config.DEBAG_LVL > 0) {
         System.out.println("doPeaceAction() doPeaceAction() doPeaceAction() doPeaceAction()");
      }

      if (Rnd.get(1, 1000000) <= Config.FAKE_CHANCE_TO_TALK_SOCIAL) {
         ThreadPoolManager.getInstance().scheduleAi(new ChatTask(this.phantom, Rnd.chance(80) ? 1 : 0, 1), (long)Rnd.get(500, 2000), true);
      }

      if (!this.phantom.isOutOfControl() && !this.phantom.isMovementDisabled()) {
         if (!this.phantom.phantom_params.isGoToTeleport2()) {
            if (Rnd.get(1, 1000000) <= 80000) {
               this.phantom.phantom_params.getPhantomAI().doBuffCast();
            }

            L2NpcInstance _npc = null;
            List _npcs;
            if (this.phantom.phantom_params.isGoToTeleport()) {
               _npcs = this.phantom.getAroundL2Teleporter(10000);
               if (_npcs.isEmpty()) {
                  this.phantom.phantom_params.setIsGoToTeleport2(true);
                  ThreadPoolManager.getInstance().schedule(new EndPeaceGoToTeleportTask(this.phantom), (long)Rnd.get(1000, 6000));
                  return;
               }

               Collections.shuffle(_npcs);
               _npc = (L2NpcInstance)_npcs.get(0);
               _npcs.clear();
            } else {
               _npcs = this.phantom.getAroundNPC(1000);
               if (!_npcs.isEmpty()) {
                  Collections.shuffle(_npcs);
                  _npc = (L2NpcInstance)_npcs.get(0);
                  _npcs.clear();
               }
            }

            if ((_npc == null || Rnd.get(1, 1000000) > 8000 || _npc.isAutoAttackable(this.phantom)) && !this.phantom.phantom_params.isGoToTeleport()) {
               if (Rnd.get(1, 1000000) <= Config.FAKE_SOCIAL_CHANCE) {
                  switch(Rnd.get(0, 1)) {
                  case 0:
                     if (Rnd.get(1, 1000000) <= Config.FAKE_SIT_CHANCE && !this.phantom.isSitting()) {
                        this.phantom.sitDown();
                     } else if (Rnd.get(1, 1000000) <= Config.FAKE_SIT_CHANCE && this.phantom.isSitting()) {
                        this.phantom.standUp();
                     }
                     break;
                  case 1:
                     this.goRandomSteps(this.phantom);
                  }

               }
            } else if (!_npc.canInteractForFantome(this.phantom)) {
               if (GeoEngine.canSeeTarget(this.phantom, _npc, false)) {
                  this.goRandomSteps(this.phantom);
                  this.phantom.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, _npc);
               } else if (GeoEngine.canMoveToCoord(this.phantom.getX(), this.phantom.getY(), this.phantom.getZ(), _npc.getX(), _npc.getY(), _npc.getZ(), this.phantom.getInstanceId())) {
                  if (!this.goRandomSteps(this.phantom)) {
                     _npcs = this.phantom.getAroundNPC(1000);
                     if (!_npcs.isEmpty()) {
                        Collections.shuffle(_npcs);
                        _npc = (L2NpcInstance)_npcs.get(0);
                        _npcs.clear();
                        if (GeoEngine.canSeeTarget(this.phantom, _npc, false)) {
                           this.phantom.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, _npc);
                           return;
                        }

                        this.phantom.moveToLocationF(_npc.getX(), _npc.getY(), _npc.getZ(), 0, false);
                        return;
                     }
                  }

               } else {
                  this.goRandomSteps(this.phantom);
               }
            } else {
               this.phantom.sendPacket((L2GameServerPacket)(new MyTargetSelected(_npc.getObjectId(), this.phantom.getLevel() - _npc.getLevel())));
               if (GameExtensionManager.getInstance().handleAction(this, ObjectExtension.Action.NPC_ONACTION, this.phantom) == null) {
                  if (_npc.getNpcId() == 29025) {
                     this.phantom.disableMove(1000 + this.phantom.getColRadius() + _npc.getColRadius());
                  } else {
                     this.phantom.disableMove(36 + this.phantom.getColRadius() + _npc.getColRadius());
                  }

                  if (_npc.getNpcId() == 29025) {
                     this.phantom.sendPacket((L2GameServerPacket)(new MoveToPawn(this.phantom, _npc, 1000 + this.phantom.getColRadius() + _npc.getColRadius())));
                  } else {
                     this.phantom.sendPacket((L2GameServerPacket)(new MoveToPawn(this.phantom, _npc, 36 + this.phantom.getColRadius() + _npc.getColRadius())));
                  }

                  _npc.onRandomAnimation(this.phantom);
                  if (this.phantom.phantom_params.isGoToTeleport()) {
                     this.phantom.phantom_params.setIsGoToTeleport2(true);
                     ThreadPoolManager.getInstance().schedule(new EndPeaceGoToTeleportTask(this.phantom), (long)Rnd.get(1000, 6000));
                  }

               }
            }
         }
      }
   }

   public boolean goRandomSteps(L2PcInstance _phantom) {
      if (Rnd.get(1, 1000000) <= Config.FAKE_WALK_CHANCE) {
         Location loc;
         if (PhantomUtil.getRandomPhantomLocation(_phantom).distance(_phantom.getLoc()) > _phantom.getComebackDistanceLoc()) {
            loc = Location.findPointToStay(PhantomUtil.getRandomPhantomLocation(_phantom), Rnd.get((int)_phantom.getComebackDistanceLoc()), (int)_phantom.getComebackDistanceLoc());
            _phantom.moveToLocationF(loc.getX(), loc.getY(), loc.getZ(), 0, false);
            return true;
         }

         loc = new Location(_phantom.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_STEP_RANGE), _phantom.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(Config.FAKE_STEP_RANGE), _phantom.getZ(), 0);
         if (GeoEngine.canMoveToCoord(_phantom.getX(), _phantom.getY(), _phantom.getZ(), loc.getX(), loc.getY(), loc.getZ(), _phantom.getInstanceId())) {
            Location locc = Location.findPointToStay((Location)loc, 50, 70);
            _phantom.moveToLocationF(locc.getX(), locc.getY(), locc.getZ(), 0, false);
            return true;
         }
      }

      return false;
   }

   public boolean returnToAllowedLocation(int distance) {
      Location s_loc = PhantomUtil.getRandomPhantomLocation(this.phantom);
      if (this.phantom.getLoc().distance(s_loc) > (double)distance) {
         this.phantom.moveToLocationF(s_loc.getX(), s_loc.getY(), s_loc.getZ(), distance / 2, true);
         return true;
      } else {
         return false;
      }
   }

   public int getComebackDistanceL() {
      return (int)this.phantom.getComebackDistanceLoc();
   }

   public void randomMove(int min_range, int max_range) {
      if (Config.DEBAG_LVL > 0) {
         System.out.println("PhantomAITask.randomMove(): " + this.phantom.getName());
      }

      Location loc = new Location(this.phantom.getX() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(min_range, max_range), this.phantom.getY() + (Rnd.chance(50) ? 1 : -1) * Rnd.get(min_range, max_range), this.phantom.getZ(), 0);
      if (GeoEngine.canMoveToCoord(this.phantom.getX(), this.phantom.getY(), this.phantom.getZ(), loc.getX(), loc.getY(), loc.getZ(), this.phantom.getInstanceId())) {
         if (Config.DEBAG_LVL > 0) {
            System.out.println("movetof5");
         }

         this.phantom.moveToLocationF(loc.getX(), loc.getY(), loc.getZ(), 0, true);
      }

   }

   public void fallBack(int range) {
      if (Config.DEBAG_LVL > 0) {
         System.out.println("PhantomAITask.fallBack(): " + this.phantom.getName());
      }

      L2Character target = this.phantom.phantom_params.getLockedTarget();
      if (target != null && target.isInRange(this.phantom, range)) {
         Location loc = this.getFallBackLoc(target, this.phantom);
         if (GeoEngine.canSeeCoord(this.phantom.getX(), this.phantom.getY(), this.phantom.getZ(), loc.getX(), loc.getY(), loc.getZ(), false, this.phantom.getInstanceId())) {
            if (Config.DEBAG_LVL > 0) {
               System.out.println("movetof6");
            }

            this.phantom.moveToLocationF(loc.getX(), loc.getY(), loc.getZ(), 0, true);
         }

      }
   }

   public Location getFallBackLoc(L2Character attacker, L2PcInstance phantom) {
      int posX = phantom.getX();
      int posY = phantom.getY();
      int posZ = phantom.getZ();
      int signx = posX < attacker.getX() ? -1 : 1;
      int signy = posY < attacker.getY() ? -1 : 1;
      int range = (int)(0.71D * phantom.calculateAttackDelay() / 1000.0D * (double)phantom.getRunSpeed());
      posX += signx * range;
      posY += signy * range;
      posZ = GeoEngine.getHeight(posX, posY, posZ, phantom.getInstanceId());
      return new Location(posX, posY, posZ, 0);
   }

   protected void equipDisarmedWeapon() {
      if (this.phantom.getActiveWeaponInstance() == null && this.phantom.isDisarmed()) {
         L2ItemInstance weapon = this.phantom.phantom_params.getPhantomWeapon();
         this.phantom.getInventory().equipItem(weapon);
      }

   }
}
