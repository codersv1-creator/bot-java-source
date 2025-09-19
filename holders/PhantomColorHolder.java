package ru.catssoftware.fakes.holders;

import java.util.ArrayList;
import ru.catssoftware.Config;
import ru.catssoftware.tools.random.Rnd;

public class PhantomColorHolder {
   public static ArrayList<Integer> _nameColors;
   public static ArrayList<Integer> _titleColors;
   public static int _nameColCount;
   public static int _titleColCount;

   public static void load() {
      _nameColors = Config.FAKE_PLAYERS_NAME_CLOLORS_ALT;
      _titleColors = Config.FAKE_PLAYERS_TITLE_CLOLORS_ALT;
      _nameColCount = _nameColors.size();
      _titleColCount = _titleColors.size();
   }

   public static int getRandomNameColor() {
      return (Integer)_nameColors.get(Rnd.get(_nameColCount));
   }

   public static int getRandomTitleColor() {
      return (Integer)_titleColors.get(Rnd.get(_titleColCount));
   }
}
