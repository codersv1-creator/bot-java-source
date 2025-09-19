package ru.catssoftware.fakes.utils;

import java.math.BigInteger;

public class v extends Thread {
   private static volatile Object[] D;
   private final int A;
   private static final BigInteger[] I = new BigInteger[1];

   v(int var1) {
      this.A = var1;
   }

   public void run() {
      D(this.A, (Object)null);
   }

   private static final void D(int var0, Object var1) {
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
         D = new Object[]{var26, var29, var40, var42, var38, null, null, null};
         break;
      case 1:
         var25 = new int[256];
         var28 = 0;

         for(var31 = 1; var28 < 256; ++var28) {
            var25[var28] = var31;
            var31 ^= var31 << 1 ^ (var31 >>> 7) * 283;
         }

         D(2, var25);
         break;
      case 2:
         var26 = (byte[])((byte[])D[0]);
         var26[0] = 99;
         var29 = (int[])((int[])var1);

         for(var31 = 0; var31 < 255; ++var31) {
            var36 = var29[255 - var31];
            var36 |= var36 << 8;
            var36 ^= var36 >> 4 ^ var36 >> 5 ^ var36 >> 6 ^ var36 >> 7;
            var26[var29[var31]] = (byte)(var36 ^ 99);
         }

         var40 = (int[])((int[])D[1]);
         var42 = (int[])((int[])D[2]);
         var38 = (int[])((int[])D[3]);
         int[] var43 = (int[])((int[])D[4]);

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
         char[] var27 = "왹茽阡읖蘃嫥\uf154鿘蹣鞣溔弫ꝗ暠뼎츀黳뵣捏\udf31涻廻\udcdc星\u2fe3˳躅㼞\uedac靈즊\ue734\uf21e\udc88쏵꾺映㿬渘啞賒漐ꗪꨳ\u0bd5厶㣷엫쮶ি킡\uf1d5픳ᆪ㍭\ue2b8ņ巽\udf66쳽佷\uf30b\uf6de妴\uea88妋᠒⏲큯\uf6ac萁\ue9c2羷\uda6e슩ॎ怸떜\u0b8b䀥ᔤ髰桮⸥㻡ﭴ\uf161\uf66c淴껩㡌謴ᛮ䔏৲﹚\ueb21뵁诃臵雏軶ꦲ柀绰聆\ue0f7Ⴓ㮦읯줚\ue3dfᑫ脚倧晿▕쀢ॐꅔ疚鵳Ѳ뷓磝霪\ue668띳".toCharArray();
         byte[] var33 = new byte[var27.length * 2];

         for(var31 = 0; var31 < var27.length; ++var31) {
            var33[var31 * 2] = (byte)(var27[var31] & 255);
            var33[var31 * 2 + 1] = (byte)((var27[var31] & '\uff00') >> 8);
         }

         var27 = "\u0014䤡鳸鵋嶒楁ⴀ\ue62f䀘႓\ude0fḜ狑놟흪踴鋖皹짛\ue9c4意䖅쎮͍瞻ᠪ貑瞵쉢캉ਗ਼ᩌ團ཥ荟\uf632긽悘罉\ude9a筎\ue3dc⇊䁏煎詒㒁측뭎墧⪡虭澣ᗞ眲拴췅鸯난䬦챱⌝烌孖\u16faힱ㩔꺨뽻ᗺ픕\u2b4f\ue39c竏멩\uf4bc莏ꛣ˝\uee6e䗗돶ᛮ茓㞕㞍韜삚뎫耣ᴳ땙\ue991ᴏ\uf307\uf27b孥폵ᖦ뿟穵윒쇢㗷鏳䉳싇㞧鎢ఛ渋厹닊鏃昋溂兾Ṝ揍䎙\udca3녰\uf117␐ꗧ\u209eᦑ藍".toCharArray();
         var34 = new byte[var27.length * 2];

         for(var36 = 0; var36 < var27.length; ++var36) {
            var34[var36 * 2] = (byte)(var27[var36] & 255);
            var34[var36 * 2 + 1] = (byte)((var27[var36] & '\uff00') >> 8);
         }

         byte[] var39 = new byte[16];

         try {
            var39[0] = var33[var34[199] & 255];
            var39[1] = var33[var34[87] & 255];
            var39[2] = var33[var34[94] & 255];
            var39[3] = var33[var34[17] & 255];
            var39[4] = var33[var34[66] & 255];
            var39[5] = var33[var34[11] & 255];
            var39[6] = var33[var34[101] & 255];
            var39[7] = var33[var34[168] & 255];
         } catch (Exception var20) {
         } finally {
            var39[8] = var33[var34[6] & 255];
            var39[9] = var33[var34[177] & 255];
            var39[10] = var33[var34[231] & 255];
            var39[11] = var33[var34[180] & 255];
            var39[12] = var33[var34[171] & 255];
            var39[13] = var33[var34[248] & 255];
            var39[14] = var33[var34[45] & 255];
            var39[15] = var33[var34[205] & 255];
         }

         D(5, var39);
         break;
      case 4:
         var25 = new int[]{1542883210, -26250498, 462885019, -564046106};
         long var32 = 0L ^ Long.MAX_VALUE - System.currentTimeMillis() >> 63 & 1L;
         var25[2] ^= (int)var32;
         D[6] = var25;
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

         D[5] = var38;
         D(6, (Object)null);
         break;
      case 6:
         var25 = new int[30];
         var28 = 0;

         for(var31 = 1; var28 < 30; ++var28) {
            var25[var28] = var31;
            var31 = var31 << 1 ^ (var31 >>> 7) * 283;
         }

         var29 = (int[])((int[])D[5]);
         var34 = (byte[])((byte[])D[0]);
         byte var35 = 44;

         for(int var37 = 4; var37 < var35; ++var37) {
            var41 = var29[(var37 - 1 >> 2) * 4 + (var37 - 1 & 3)];
            if (var37 % 4 == 0) {
               var41 = S(var34, z(var41, 8)) ^ var25[var37 / 4 - 1];
            }

            var29[(var37 >> 2) * 4 + (var37 & 3)] = var29[(var37 - 4 >> 2) * 4 + (var37 - 4 & 3)] ^ var41;
         }

         return;
      case 7:
         var2 = I;
         var3 = new BigInteger("10001", 16);
         var4 = new BigInteger("ct1nztadnnvz", 36);
         var5 = new BigInteger("ph4twyrcqxid", 36);
         var6 = new BigInteger("1qzjmy17rtkiw", 36);
         var7 = new BigInteger("1vq6pa8xxqst9", 36);

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

         var10000 = (int[])((int[])D[6]);
         var10000[0] ^= var7.intValue();
         break;
      case 8:
         var2 = I;
         var3 = new BigInteger("10001", 16);
         var4 = new BigInteger("qvoirss0z8j0", 36);
         var5 = new BigInteger("jsxeb35rjwih", 36);
         var6 = new BigInteger("1rvwfsdqes331", 36);
         var7 = new BigInteger("1jaontixq0g2r", 36);

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

         var10000 = (int[])((int[])D[6]);
         var10000[1] ^= var7.intValue();
      }

   }

   private static final int z(int var0, int var1) {
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

   private static final int S(byte[] var0, int var1) {
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

   private static final void v20288() {
      D(0, (Object)null);
      G();
      v var0 = new v(1);
      var0.start();
      var0.join();
      v var1 = new v(3);
      var1.start();
      v var2 = new v(4);
      var2.start();
      var1.join();
      var2.join();
      v var3 = new v(7);
      v var4 = new v(8);
      var3.start();
      var4.start();
      var3.join();
      var4.join();
   }

   private static final void G() {
      boolean var6 = false;
      boolean var7 = false;
      byte var13 = 0;
      Throwable var10000 = null;

      label72:
      while(var13 == 0) {
         var13 = 2;
         StackTraceElement[] var0 = Thread.currentThread().getStackTrace();
         int var1 = var0.length;
         int var2 = 1621260264;
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
                           D[7] = var3;
                           return;
                        }
                        break label94;
                     }

                     try {
                        D[7] = 4;
                     } catch (Throwable var9) {
                        var10000 = var9;
                        var10001 = false;
                        break label79;
                     }

                     if (var4.toString().hashCode() == var2) {
                        D[7] = var3;
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

                  D[7] = var3;
                  return;
               }

               ++var3;
            } catch (Exception var12) {
               continue label72;
            }
         }

         return;
      }

      D[7] = 3;
   }

   static final String m(Object var0) {
      boolean var21 = false;
      boolean var22 = false;
      if (D == null) {
         v20288();
      }

      StackTraceElement[] var10000 = Thread.currentThread().getStackTrace();
      StringBuilder var13 = new StringBuilder();
      String var12 = var10000[(Integer)D[7]].getClassName();
      var13 = var13.append(var12);
      var12 = var10000[(Integer)D[7]].getMethodName();
      int var1 = var13.append(var12).toString().hashCode();
      int[] var2 = (int[])((int[])D[6]);
      int var3 = var1 ^ var2[0];
      int var4 = var1 ^ var2[1];
      int var5 = var1 ^ var2[2];
      int var27 = var1 ^ var2[3];
      int[] var26 = (int[])((int[])D[5]);
      int[] var6 = (int[])((int[])D[1]);
      int[] var7 = (int[])((int[])D[2]);
      int[] var8 = (int[])((int[])D[3]);
      int[] var9 = (int[])((int[])D[4]);
      byte[] var10 = (byte[])((byte[])D[0]);
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
