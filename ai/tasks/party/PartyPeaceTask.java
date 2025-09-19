package ru.catssoftware.fakes.ai.tasks.party;

import ru.catssoftware.fakes.parsers.PartyAIParser;
import ru.catssoftware.gameserver.taskmanager.utils.RunnableImpl;

public class PartyPeaceTask extends RunnableImpl {
   private int id;

   public PartyPeaceTask(int id) {
      this.id = id;
   }

   public void runImpl() {
      PartyAIParser.getInstance().getPartyAIByID(this.id).changePartyState(1);
      PartyAIParser.getInstance().getPartyAIByID(this.id).spawnPartyBattle();
   }
}
