package ru.catssoftware.fakes.ai.tasks.party;

import ru.catssoftware.fakes.parsers.PartyAIParser;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class PartyTask extends RunnableImpl {
   private int id;

   public PartyTask(int id) {
      this.id = id;
   }

   public void runImpl() {
      PartyAIParser.getInstance().getPartyAIByID(1).doAction();
   }
}
