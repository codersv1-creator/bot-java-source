package ru.catssoftware.fakes.model;

import java.math.BigInteger;

public class RacesDictionary$PhantomLevelFarmParams extends Thread {
   private static volatile Object[] u;
   private final int O;
   private static final BigInteger[] o = new BigInteger[1];

   RacesDictionary$PhantomLevelFarmParams(int var1) {
      this.O = var1;
   }

   public void run() {
      b(this.O, (Object)null);
   }

   private static final void b(int var0, Object var1) {
      BigInteger[] var2;
      BigInteger var3;
      BigInteger var4;
      BigInteger var5;
      BigInteger var6;
      BigInteger var7;
      int var8;
      int var9;
      int[] var10000;
      int[] var25;
      byte[] var26;
      int var28;
      int[] var29;
      int var31;
      byte[] var34;
      int var36;
      int[] var38;
      int[] var40;
      int var41;
      int[] var42;
      switch(var0) {
      case 0:
         var26 = new byte[256];
         var29 = new int[256];
         var40 = new int[256];
         var42 = new int[256];
         var38 = new int[256];
         u = new Object[]{var26, var29, var40, var42, var38, null, null, null};
         break;
      case 1:
         var25 = new int[256];
         var28 = 0;

         for(var31 = 1; var28 < 256; ++var28) {
            var25[var28] = var31;
            var31 ^= var31 << 1 ^ (var31 >>> 7) * 283;
         }

         b(2, var25);
         break;
      case 2:
         var26 = (byte[])((byte[])u[0]);
         var26[0] = 99;
         var29 = (int[])((int[])var1);

         for(var31 = 0; var31 < 255; ++var31) {
            var36 = var29[255 - var31];
            var36 |= var36 << 8;
            var36 ^= var36 >> 4 ^ var36 >> 5 ^ var36 >> 6 ^ var36 >> 7;
            var26[var29[var31]] = (byte)(var36 ^ 99);
         }

         var40 = (int[])((int[])u[1]);
         var42 = (int[])((int[])u[2]);
         var38 = (int[])((int[])u[3]);
         int[] var43 = (int[])((int[])u[4]);

         for(var8 = 0; var8 < 256; ++var8) {
            var9 = var26[var8] & 255;
            int var10 = var9 << 1 ^ (var9 >>> 7) * 283;
            int var11 = (var9 ^ var10) << 24 ^ var9 << 16 ^ var9 << 8 ^ var10;
            var11 &= -1;
            var40[var8] = var11;
            var42[var8] = var11 << 8 | var11 >>> -8;
            var38[var8] = var11 << 16 | var11 >>> -16;
            var43[var8] = var11 << 24 | var11 >>> -24;
         }

         return;
      case 3:
         char[] var27 = "뇾\uf4ba\ue1a6냑磃↺ཱྀﾑ࿋븾牺\ua7b3웶⽡䓓\ue381筬ﲺ㹜\ueb39ꠧ턼\ua957嬀៝ボ灅䑁፹駺䠢캩\ueef0␐ꉔ\ue67b鷽ቭ讇ᒇ퇁嬘恶◴繞溩É\uf7c4臵你\u089f鏥궃땚䥨\ue663뮚㱃渲弛丹\ue8d9ᙶ裁ቿᩘꏯ䭿욳ᶘ\ud8b4醴\u196eᏡ⚣夙蔕걈\udd95慽\ue954╦轊\ue030瞺䤜ꟻ惖\udecf㐇䃉樓䉓둽ꗘ겐ሿ㦒鸙挰쟚⊵䧰揠\uf36d蜮晤♄稺㸻鄜顸瓯縔䞴뼺껨䫌⤖䜠화ᭅ뽶奨吳祦\ue7e3\udc1e".toCharArray();
         byte[] var33 = new byte[var27.length * 2];

         for(var31 = 0; var31 < var27.length; ++var31) {
            var33[var31 * 2] = (byte)(var27[var31] & 255);
            var33[var31 * 2 + 1] = (byte)((var27[var31] & '\uff00') >> 8);
         }

         var27 = "㴔뉿䯫\uecf5짤\ude9d辯㤰遣깥犇ᢔಽ蔬꽡獜쭭츕칓ﮱ忘\u1cbc欶\udd5f\uf2cd\u1ca8헬ူἥ뻽핗姳뎌麵\u09d5귘\udbf0ꑢ偩饤ᧁ\udaf6檶䁴\ua7f6६鄽\udc63\u0dfe\ue3b3秚\u175bඬ쐞藀ꝁ豗첉銟㴣뤳ꏼ枆䠛⫱\ue5d4⍅\uf243鮋ꐵ覡᠓扲閠쪔䌤惖\u0b7a踩纉꣩✁⡱銢坳㘁\ue9cc舞펍辀왨\ua7d4㕪坻쓍箴錀\u2b8d騺奀䄁\u08df칸\ufdd6礗ꁫ뿂\ue282빩壟\u2d69᧧㣻㿆톃䚲婮\uf80f䣖䑱᧰ﰁ鎜튵鱝褞\u0b59⼟".toCharArray();
         var34 = new byte[var27.length * 2];

         for(var36 = 0; var36 < var27.length; ++var36) {
            var34[var36 * 2] = (byte)(var27[var36] & 255);
            var34[var36 * 2 + 1] = (byte)((var27[var36] & '\uff00') >> 8);
         }

         byte[] var39 = new byte[16];

         try {
            var39[0] = var33[var34[243] & 255];
            var39[1] = var33[var34[239] & 255];
            var39[2] = var33[var34[79] & 255];
            var39[3] = var33[var34[163] & 255];
            var39[4] = var33[var34[216] & 255];
            var39[5] = var33[var34[152] & 255];
            var39[6] = var33[var34[69] & 255];
            var39[7] = var33[var34[246] & 255];
         } catch (Exception var20) {
         } finally {
            var39[8] = var33[var34[182] & 255];
            var39[9] = var33[var34[128] & 255];
            var39[10] = var33[var34[240] & 255];
            var39[11] = var33[var34[222] & 255];
            var39[12] = var33[var34[131] & 255];
            var39[13] = var33[var34[233] & 255];
            var39[14] = var33[var34[51] & 255];
            var39[15] = var33[var34[254] & 255];
         }

         b(5, var39);
         break;
      case 4:
         var25 = new int[]{-1356552202, -20366198, 2029574150, -1836718049};
         long var32 = 0L ^ Long.MAX_VALUE - System.currentTimeMillis() >> 63 & 1L;
         var25[2] ^= (int)var32;
         u[6] = var25;
         break;
      case 5:
         var26 = (byte[])((byte[])var1);
         byte var30 = 4;
         var36 = var30 + 6;
         var38 = new int[(var36 + 1) * 4];
         var31 = 0;
         var41 = 0;

         try {
            while(var41 < 16) {
               var38[(var31 >> 2) * 4 + var31 & 3] = var26[var41] & 255 | (var26[var41 + 1] & 255) << 8 | (var26[var41 + 2] & 255) << 16 | var26[var41 + 3] << 24;
               var41 += 4;
               ++var31;
            }
         } catch (Exception var24) {
         }

         u[5] = var38;
         b(6, (Object)null);
         break;
      case 6:
         var25 = new int[30];
         var28 = 0;

         for(var31 = 1; var28 < 30; ++var28) {
            var25[var28] = var31;
            var31 = var31 << 1 ^ (var31 >>> 7) * 283;
         }

         var29 = (int[])((int[])u[5]);
         var34 = (byte[])((byte[])u[0]);
         byte var35 = 44;

         for(int var37 = 4; var37 < var35; ++var37) {
            var41 = var29[(var37 - 1 >> 2) * 4 + (var37 - 1 & 3)];
            if (var37 % 4 == 0) {
               var41 = Y(var34, e(var41, 8)) ^ var25[var37 / 4 - 1];
            }

            var29[(var37 >> 2) * 4 + (var37 & 3)] = var29[(var37 - 4 >> 2) * 4 + (var37 - 4 & 3)] ^ var41;
         }

         return;
      case 7:
         var2 = o;
         var3 = new BigInteger("10001", 16);
         var4 = new BigInteger("19e51rnbg5o3d", 36);
         var5 = new BigInteger("14wmf5ssu7ull", 36);
         var6 = new BigInteger("1ksp28c85r0xa", 36);
         var7 = new BigInteger("1l1f780ttclrh", 36);

         for(var8 = 0; var8 < 4; ++var8) {
            for(var9 = 0; var9 < 8; ++var9) {
               var7 = var7.add(var5).xor(var6).modPow(var3, var4);
            }

            synchronized(var2) {
               while(var2[0] == null) {
                  var2.wait(5000L);
               }

               var5 = var5.xor(var2[0]);
               var2[0] = null;
            }
         }

         var10000 = (int[])((int[])u[6]);
         var10000[0] ^= var7.intValue();
         break;
      case 8:
         var2 = o;
         var3 = new BigInteger("10001", 16);
         var4 = new BigInteger("nqruykyx8j5h", 36);
         var5 = new BigInteger("5qr27ygr8x7c", 36);
         var6 = new BigInteger("h9qf71wnox8k", 36);
         var7 = new BigInteger("q9bpaczk5ebp", 36);

         for(var8 = 0; var8 < 4; ++var8) {
            for(var9 = 0; var9 < 8; ++var9) {
               var7 = var7.add(var5).xor(var6).modPow(var3, var4);
            }

            while(true) {
               Thread.yield();
               synchronized(var2) {
                  if (var2[0] == null) {
                     var2[0] = var7;
                     var2.notifyAll();
                     break;
                  }

                  var2.notifyAll();
               }
            }
         }

         var10000 = (int[])((int[])u[6]);
         var10000[1] ^= var7.intValue();
      }

   }

   private static final int e(int var0, int var1) {
      boolean var5 = false;
      int var2 = var0;
      int var3 = var1;
      int var4 = var1 + var0 >> 24;
      var5 = false;
      Object var10000 = null;

      while(true) {
         try {
            if (!var5) {
               var5 = true;
               var3 = var0 >>> var1 | var0 << -var1;
               return var3;
            }
         } catch (Throwable var7) {
            continue;
         }

         byte var8 = 0;
         var10000 = null;

         while(true) {
            try {
               if (var8 == 0) {
                  int var9 = var8 + 1;
                  var3 = var2 + var4;
               }

               return var3;
            } catch (Throwable var6) {
            }
         }
      }
   }

   private static final int Y(byte[] var0, int var1) {
      boolean var4 = false;
      int var2 = var0[10] << 16;
      var4 = false;
      Object var10000 = null;

      while(true) {
         try {
            if (!var4) {
               var4 = true;
               var2 = var0[var1 & 255] & 255 | (var0[var1 >> 8 & 255] & 255) << 8 | (var0[var1 >> 16 & 255] & 255) << 16 | var0[var1 >> 24 & 255] << 24;
               return var2;
            }
         } catch (Throwable var6) {
            continue;
         }

         var4 = false;
         var10000 = null;

         while(true) {
            try {
               if (!var4) {
                  var4 = true;
                  var2 = var0[var1 & 127] >> 8;
               }

               return var2;
            } catch (Throwable var5) {
            }
         }
      }
   }

   private static final void b28931() {
      b(0, (Object)null);
      R();
      RacesDictionary$PhantomLevelFarmParams var0 = new RacesDictionary$PhantomLevelFarmParams(1);
      var0.start();
      var0.join();
      RacesDictionary$PhantomLevelFarmParams var1 = new RacesDictionary$PhantomLevelFarmParams(3);
      var1.start();
      RacesDictionary$PhantomLevelFarmParams var2 = new RacesDictionary$PhantomLevelFarmParams(4);
      var2.start();
      var1.join();
      var2.join();
      RacesDictionary$PhantomLevelFarmParams var3 = new RacesDictionary$PhantomLevelFarmParams(7);
      RacesDictionary$PhantomLevelFarmParams var4 = new RacesDictionary$PhantomLevelFarmParams(8);
      var3.start();
      var4.start();
      var3.join();
      var4.join();
   }

   private static final void R() {
      boolean var6 = false;
      boolean var7 = false;
      byte var13 = 0;
      Throwable var10000 = null;

      label72:
      while(var13 == 0) {
         var13 = 2;
         StackTraceElement[] var0 = Thread.currentThread().getStackTrace();
         int var1 = var0.length;
         int var2 = 1847296418;
         int var3 = 1;

         while(var3 < var1) {
            StringBuilder var4 = new StringBuilder();
            var6 = false;
            var10000 = null;

            try {
               label94: {
                  boolean var10001;
                  label79: {
                     while(true) {
                        try {
                           try {
                              if (var6) {
                                 break;
                              }

                              var6 = true;
                              var4 = var4.append(var0[var3].getClassName()).append(var0[var3].getMethodName());
                           } catch (Exception var10) {
                              continue;
                           }
                        } catch (Throwable var11) {
                           var10000 = var11;
                           var10001 = false;
                           break label79;
                        }

                        if (var4.toString().hashCode() == var2) {
                           u[7] = var3;
                           return;
                        }
                        break label94;
                     }

                     try {
                        u[7] = 4;
                     } catch (Throwable var9) {
                        var10000 = var9;
                        var10001 = false;
                        break label79;
                     }

                     if (var4.toString().hashCode() == var2) {
                        u[7] = var3;
                        return;
                     }
                     break label94;
                  }

                  Throwable var5;
                  while(true) {
                     var5 = var10000;

                     try {
                        var5 = var5;
                        break;
                     } catch (Throwable var8) {
                        var10000 = var8;
                        var10001 = false;
                     }
                  }

                  if (var4.toString().hashCode() != var2) {
                     throw var5;
                  }

                  u[7] = var3;
                  return;
               }

               ++var3;
            } catch (Exception var12) {
               continue label72;
            }
         }

         return;
      }

      u[7] = 3;
   }

   static final String f(Object var0) {
      boolean var21 = false;
      boolean var22 = false;
      if (u == null) {
         b28931();
      }

      StackTraceElement[] var10000 = Thread.currentThread().getStackTrace();
      StringBuilder var13 = new StringBuilder();
      String var12 = var10000[(Integer)u[7]].getClassName();
      var13 = var13.append(var12);
      var12 = var10000[(Integer)u[7]].getMethodName();
      int var1 = var13.append(var12).toString().hashCode();
      int[] var2 = (int[])((int[])u[6]);
      int var3 = var1 ^ var2[0];
      int var4 = var1 ^ var2[1];
      int var5 = var1 ^ var2[2];
      int var27 = var1 ^ var2[3];
      int[] var26 = (int[])((int[])u[5]);
      int[] var6 = (int[])((int[])u[1]);
      int[] var7 = (int[])((int[])u[2]);
      int[] var8 = (int[])((int[])u[3]);
      int[] var9 = (int[])((int[])u[4]);
      byte[] var10 = (byte[])((byte[])u[0]);
      char[] var25 = ((String)var0).toCharArray();
      int var31 = 0;
      var10000 = null;

      label69:
      while(var31 == 0) {
         ++var31;
         int var11 = var25.length;
         int var28 = 0;

         while(var28 < var11) {
            if (var28 % 8 == 0) {
               boolean var29 = false;
               var29 = false;
               var29 = false;
               var29 = false;
               int var14 = var3 ^ var26[0];
               int var15 = var4 ^ var26[1];
               int var16 = var5 ^ var26[2];
               int var17 = var27 ^ var26[3];

               int var18;
               int var19;
               int var20;
               int var30;
               for(var30 = 4; var30 < 36; var30 += 4) {
                  var18 = var6[var14 & 255] ^ var7[var15 >> 8 & 255] ^ var8[var16 >> 16 & 255] ^ var9[var17 >>> 24] ^ var26[var30];
                  var19 = var6[var15 & 255] ^ var7[var16 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var14 >>> 24] ^ var26[var30 + 1];
                  var20 = var6[var16 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var14 >> 16 & 255] ^ var9[var15 >>> 24] ^ var26[var30 + 2];
                  var17 = var6[var17 & 255] ^ var7[var14 >> 8 & 255] ^ var8[var15 >> 16 & 255] ^ var9[var16 >>> 24] ^ var26[var30 + 3];
                  var30 += 4;
                  var14 = var6[var18 & 255] ^ var7[var19 >> 8 & 255] ^ var8[var20 >> 16 & 255] ^ var9[var17 >>> 24] ^ var26[var30];
                  var15 = var6[var19 & 255] ^ var7[var20 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var18 >>> 24] ^ var26[var30 + 1];
                  var16 = var6[var20 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var18 >> 16 & 255] ^ var9[var19 >>> 24] ^ var26[var30 + 2];
                  var17 = var6[var17 & 255] ^ var7[var18 >> 8 & 255] ^ var8[var19 >> 16 & 255] ^ var9[var20 >>> 24] ^ var26[var30 + 3];
               }

               var20 = var6[var14 & 255] ^ var7[var15 >> 8 & 255] ^ var8[var16 >> 16 & 255] ^ var9[var17 >>> 24] ^ var26[var30];
               var19 = var6[var15 & 255] ^ var7[var16 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var14 >>> 24] ^ var26[var30 + 1];
               var18 = var6[var16 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var14 >> 16 & 255] ^ var9[var15 >>> 24] ^ var26[var30 + 2];
               var17 = var6[var17 & 255] ^ var7[var14 >> 8 & 255] ^ var8[var15 >> 16 & 255] ^ var9[var16 >>> 24] ^ var26[var30 + 3];
               var16 = var30 + 4;
               var3 = var10[var20 & 255] & 255 ^ (var10[var19 >> 8 & 255] & 255) << 8 ^ (var10[var18 >> 16 & 255] & 255) << 16 ^ var10[var17 >>> 24] << 24 ^ var26[var16 + 0];
               var4 = var10[var19 & 255] & 255 ^ (var10[var18 >> 8 & 255] & 255) << 8 ^ (var10[var17 >> 16 & 255] & 255) << 16 ^ var10[var20 >>> 24] << 24 ^ var26[var16 + 1];
               var5 = var10[var18 & 255] & 255 ^ (var10[var17 >> 8 & 255] & 255) << 8 ^ (var10[var20 >> 16 & 255] & 255) << 16 ^ var10[var19 >>> 24] << 24 ^ var26[var16 + 2];
               var27 = var10[var17 & 255] & 255 ^ (var10[var20 >> 8 & 255] & 255) << 8 ^ (var10[var19 >> 16 & 255] & 255) << 16 ^ var10[var18 >>> 24] << 24 ^ var26[var16 + 3];
            }

            var21 = false;
            var10000 = null;

            try {
               label63:
               while(true) {
                  try {
                     if (!var21) {
                        var21 = true;
                        switch(var28 % 8) {
                        case 0:
                           var25[var28] = (char)(var3 >> 16 ^ var25[var28]);
                           break label63;
                        case 1:
                           var25[var28] = (char)(var3 ^ var25[var28]);
                           break label63;
                        case 2:
                           var25[var28] = (char)(var4 >> 16 ^ var25[var28]);
                           break label63;
                        case 3:
                           var25[var28] = (char)(var4 ^ var25[var28]);
                           break label63;
                        case 4:
                           var25[var28] = (char)(var5 >> 16 ^ var25[var28]);
                           break label63;
                        case 5:
                           var25[var28] = (char)(var5 ^ var25[var28]);
                           break label63;
                        case 6:
                           var25[var28] = (char)(var27 >> 16 ^ var25[var28]);
                           break label63;
                        case 7:
                           var25[var28] = (char)(var27 ^ var25[var28]);
                        }
                     }
                     break;
                  } catch (Exception var23) {
                  }
               }

               ++var28;
            } catch (Exception var24) {
               continue label69;
            }
         }

         return new String(var25);
      }

      return new String(var25);
   }
}
