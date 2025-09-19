package ru.catssoftware.fakes.holders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import ru.catssoftware.Config;
import ru.catssoftware.tools.random.Rnd;

public class PhantomPhrasesHolder {
   private static ArrayList<String> _fakesOnDeadPhrases = new ArrayList();
   private static ArrayList<String> _fakesOnKillPhrases = new ArrayList();
   private static ArrayList<String> _fakesPeacePhrases = new ArrayList();

   public static void load() {
      _fakesOnDeadPhrases.clear();
      _fakesOnKillPhrases.clear();
      _fakesPeacePhrases.clear();
      parseFile("dead", _fakesOnDeadPhrases);
      parseFile("kill", _fakesOnKillPhrases);
      parseFile("peace", _fakesPeacePhrases);
   }

   private static void parseFile(String file_name, ArrayList<String> phrases) {
      LineNumberReader lnr = null;
      BufferedReader br = null;
      FileReader fr = null;

      try {
         File Data = new File("./config/fake/" + file_name + ".talk");
         if (!Data.exists()) {
            return;
         }

         fr = new FileReader(Data);
         br = new BufferedReader(fr);
         lnr = new LineNumberReader(br);

         String line;
         while((line = lnr.readLine()) != null) {
            if (line.trim().length() != 0 && !line.startsWith("#")) {
               phrases.add(line);
            }
         }
      } catch (Exception var16) {
         if (Config.DEBAG_LVL > 0) {
            var16.printStackTrace();
         }
      } finally {
         try {
            if (fr != null) {
               fr.close();
            }

            if (br != null) {
               br.close();
            }

            if (lnr != null) {
               lnr.close();
            }
         } catch (Exception var15) {
         }

      }

   }

   public static String getRandomOnDeadPhrase() {
      return (String)_fakesOnDeadPhrases.get(Rnd.get(_fakesOnDeadPhrases.size()));
   }

   public static String getRandomOnKillPhrase() {
      return (String)_fakesOnKillPhrases.get(Rnd.get(_fakesOnKillPhrases.size()));
   }

   public static String getRandomPeacePhrase() {
      return (String)_fakesPeacePhrases.get(Rnd.get(_fakesPeacePhrases.size()));
   }
}
