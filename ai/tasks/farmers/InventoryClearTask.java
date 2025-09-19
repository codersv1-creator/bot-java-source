package ru.catssoftware.fakes.ai.tasks.farmers;

import java.util.ArrayList;
import java.util.Iterator;
import ru.catssoftware.Config;
import ru.catssoftware.gameserver.model.L2ItemInstance;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class InventoryClearTask extends RunnableImpl {
   private L2PcInstance phantom;

   public InventoryClearTask(L2PcInstance ph) {
      this.phantom = ph;
   }

   public void runImpl() {
      try {
         if (this.phantom == null || !this.phantom.isFantome()) {
            return;
         }

         ArrayList<Integer> items_to_trade = new ArrayList();
         L2ItemInstance[] var5;
         int var4 = (var5 = this.phantom.getInventory().getItems()).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            L2ItemInstance item = var5[var3];
            if (!Config.FAKE_PLAYERS_NON_SELLABLE_ITEMS.contains(item.getItemId())) {
               items_to_trade.add(item.getItemId());
            }
         }

         int id;
         L2ItemInstance var9;
         for(Iterator var8 = items_to_trade.iterator(); var8.hasNext(); var9 = this.phantom.getInventory().getItemByItemId(id)) {
            id = (Integer)var8.next();
         }
      } catch (Exception var6) {
         if (Config.DEBAG_LVL > 0) {
            var6.printStackTrace();
         }
      }

   }
}
