package ru.catssoftware.fakes.ai.tasks.other;

import ru.catssoftware.Config;
import ru.catssoftware.fakes.holders.PhantomPhrasesHolder;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.network.clientpackets.Say2;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class ChatTask extends RunnableImpl {
   private int _type;
   private int _chat;
   private L2PcInstance phantom;

   public ChatTask(L2PcInstance ph, int chat, int type) {
      this.phantom = ph;
      this._chat = chat;
      this._type = type;
   }

   public void runImpl() {
      try {
         if (this.phantom == null || !this.phantom.isFantome()) {
            return;
         }

         String random_phrase = this._type == 2 ? PhantomPhrasesHolder.getRandomOnDeadPhrase() : (this._type == 3 ? PhantomPhrasesHolder.getRandomOnKillPhrase() : PhantomPhrasesHolder.getRandomPeacePhrase());
         this.phantom.sayString(random_phrase, this._chat);
         Say2.useChatHandlerF(this.phantom, this._chat, random_phrase);
      } catch (Exception var2) {
         if (Config.DEBAG_LVL > 0) {
            var2.printStackTrace();
         }
      }

   }
}
