package ru.catssoftware.fakes.tables;

import java.math.BigInteger;

public class PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo extends Thread {
   private static volatile Object[] q;
   private final int L;
   private static final BigInteger[] I = new BigInteger[1];

   PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo(int var1) {
      this.L = var1;
   }

   public void run() {
      i(this.L, (Object)null);
   }

   private static final void i(int var0, Object var1) {
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
         q = new Object[]{var26, var29, var40, var42, var38, null, null, null};
         break;
      case 1:
         var25 = new int[256];
         var28 = 0;

         for(var31 = 1; var28 < 256; ++var28) {
            var25[var28] = var31;
            var31 ^= var31 << 1 ^ (var31 >>> 7) * 283;
         }

         i(2, var25);
         break;
      case 2:
         var26 = (byte[])((byte[])q[0]);
         var26[0] = 99;
         var29 = (int[])((int[])var1);

         for(var31 = 0; var31 < 255; ++var31) {
            var36 = var29[255 - var31];
            var36 |= var36 << 8;
            var36 ^= var36 >> 4 ^ var36 >> 5 ^ var36 >> 6 ^ var36 >> 7;
            var26[var29[var31]] = (byte)(var36 ^ 99);
         }

         var40 = (int[])((int[])q[1]);
         var42 = (int[])((int[])q[2]);
         var38 = (int[])((int[])q[3]);
         int[] var43 = (int[])((int[])q[4]);

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
         char[] var27 = "鷳\ud8b7춫鳜캜\ua69b⨘긦銞\udb5b폼샬晕휫ⳣ꺈₦샘聏ர\uab69\ued2eᴿ筊Σ\ue804옚썠㛠졍핷ꯌ佶䍏˷ḱ\uf5cd彤큍⣥濒뮑挸᧦쨶代ᒷ⼜颓霭ꚵ꒑飽\uddafܽ㜶\udcab\u20c6\uf595퓁刺ￅ厤뫡鄩\ude9c\ueacd荍㹏逾䒒\uee5d嫀牤⺼\uf2f4\ueb00⦖啼벢\uea85軞 ᾄ\uf17f茜\ud99d應༨袡쀪ℍ蚯媸㻻壍⽓耳찅飍짥䄔㭬溫졓\udd3e엜\ue989ᅵ掸ﭬ繨쉷ꗟ觯瑿瀣\udc58䛇䣺襚䀰樘袁嫎\ue7ea︰Ḃ".toCharArray();
         byte[] var33 = new byte[var27.length * 2];

         for(var31 = 0; var31 < var27.length; ++var31) {
            var33[var31 * 2] = (byte)(var27[var31] & 255);
            var33[var31 * 2 + 1] = (byte)((var27[var31] & '\uff00') >> 8);
         }

         var27 = "\ue124䐎⬬꾰嘢\ue309黙貯晻ᯣ챈ⲛㆄ\ud9ff쉹\uef87돴赫בֿ괣ꀇﮠ텖눾啮㰔힒בֿ㌄瑞\u191f纰ᙳ朊㇇恐ᒍ潽ᡑ؈걾\uf66d\ueb3b멥䮈댑⦔骃弶澡뛽个Ἠ玏ㆥ厯鋠\ue01c\uf133ꝗ氁◽│ꊩ\ue896ⴗ\uf8c2뫉쵅砵\ue18cꏵ扁茐ݹᆼ套榒茌ᕈꈸ楛\u0a5f렞褁⋞ど纟쯇ｽ⤞\ud95f⬧療\uf33b궸ỻ娴ꍉ뵀㬯쁕迡ﭟʈ\ue1d7団墁\ueb39쯗錺쯁ጞ촓쮆䋘᠔㦰◮힕胹糓Ạ\ue47a蝷顂驼㫮".toCharArray();
         var34 = new byte[var27.length * 2];

         for(var36 = 0; var36 < var27.length; ++var36) {
            var34[var36 * 2] = (byte)(var27[var36] & 255);
            var34[var36 * 2 + 1] = (byte)((var27[var36] & '\uff00') >> 8);
         }

         byte[] var39 = new byte[16];

         try {
            var39[0] = var33[var34[66] & 255];
            var39[1] = var33[var34[146] & 255];
            var39[2] = var33[var34[236] & 255];
            var39[3] = var33[var34[225] & 255];
            var39[4] = var33[var34[85] & 255];
            var39[5] = var33[var34[59] & 255];
            var39[6] = var33[var34[194] & 255];
            var39[7] = var33[var34[9] & 255];
         } catch (Exception var20) {
         } finally {
            var39[8] = var33[var34[130] & 255];
            var39[9] = var33[var34[68] & 255];
            var39[10] = var33[var34[215] & 255];
            var39[11] = var33[var34[143] & 255];
            var39[12] = var33[var34[222] & 255];
            var39[13] = var33[var34[175] & 255];
            var39[14] = var33[var34[214] & 255];
            var39[15] = var33[var34[60] & 255];
         }

         i(5, var39);
         break;
      case 4:
         var25 = new int[]{173210362, 671382286, -93555226, 1849379077};
         long var32 = 0L ^ Long.MAX_VALUE - System.currentTimeMillis() >> 63 & 1L;
         var25[2] ^= (int)var32;
         q[6] = var25;
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

         q[5] = var38;
         i(6, (Object)null);
         break;
      case 6:
         var25 = new int[30];
         var28 = 0;

         for(var31 = 1; var28 < 30; ++var28) {
            var25[var28] = var31;
            var31 = var31 << 1 ^ (var31 >>> 7) * 283;
         }

         var29 = (int[])((int[])q[5]);
         var34 = (byte[])((byte[])q[0]);
         byte var35 = 44;

         for(int var37 = 4; var37 < var35; ++var37) {
            var41 = var29[(var37 - 1 >> 2) * 4 + (var37 - 1 & 3)];
            if (var37 % 4 == 0) {
               var41 = J(var34, E(var41, 8)) ^ var25[var37 / 4 - 1];
            }

            var29[(var37 >> 2) * 4 + (var37 & 3)] = var29[(var37 - 4 >> 2) * 4 + (var37 - 4 & 3)] ^ var41;
         }

         return;
      case 7:
         var2 = I;
         var3 = new BigInteger("10001", 16);
         var4 = new BigInteger("1edxxfizw3bb", 36);
         var5 = new BigInteger("es1lgcbar36v", 36);
         var6 = new BigInteger("92t1k9z7f2jt", 36);
         var7 = new BigInteger("1fm5fc8h91lul", 36);

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

         var10000 = (int[])((int[])q[6]);
         var10000[0] ^= var7.intValue();
         break;
      case 8:
         var2 = I;
         var3 = new BigInteger("10001", 16);
         var4 = new BigInteger("se8kzs004j66", 36);
         var5 = new BigInteger("18dqx6q0r6sst", 36);
         var6 = new BigInteger("1s1qhx119sbja", 36);
         var7 = new BigInteger("i4j39alej4ee", 36);

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

         var10000 = (int[])((int[])q[6]);
         var10000[1] ^= var7.intValue();
      }

   }

   private static final int E(int var0, int var1) {
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

   private static final int J(byte[] var0, int var1) {
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

   private static final void j27006() {
      i(0, (Object)null);
      v();
      PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo var0 = new PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo(1);
      var0.start();
      var0.join();
      PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo var1 = new PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo(3);
      var1.start();
      PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo var2 = new PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo(4);
      var2.start();
      var1.join();
      var2.join();
      PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo var3 = new PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo(7);
      PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo var4 = new PhantomSpawnCoordinateInfo$PhantomSpawnCoordinateInfo(8);
      var3.start();
      var4.start();
      var3.join();
      var4.join();
   }

   private static final void v() {
      boolean var6 = false;
      boolean var7 = false;
      byte var13 = 0;
      Throwable var10000 = null;

      label72:
      while(var13 == 0) {
         var13 = 2;
         StackTraceElement[] var0 = Thread.currentThread().getStackTrace();
         int var1 = var0.length;
         int var2 = -109092687;
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
                           q[7] = var3;
                           return;
                        }
                        break label94;
                     }

                     try {
                        q[7] = 4;
                     } catch (Throwable var9) {
                        var10000 = var9;
                        var10001 = false;
                        break label79;
                     }

                     if (var4.toString().hashCode() == var2) {
                        q[7] = var3;
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

                  q[7] = var3;
                  return;
               }

               ++var3;
            } catch (Exception var12) {
               continue label72;
            }
         }

         return;
      }

      q[7] = 3;
   }

   static final String V(Object var0) {
      boolean var21 = false;
      boolean var22 = false;
      if (q == null) {
         j27006();
      }

      StackTraceElement[] var10000 = Thread.currentThread().getStackTrace();
      StringBuilder var13 = new StringBuilder();
      String var12 = var10000[(Integer)q[7]].getClassName();
      var13 = var13.append(var12);
      var12 = var10000[(Integer)q[7]].getMethodName();
      int var1 = var13.append(var12).toString().hashCode();
      int[] var2 = (int[])((int[])q[6]);
      int var3 = var1 ^ var2[0];
      int var4 = var1 ^ var2[1];
      int var5 = var1 ^ var2[2];
      int var27 = var1 ^ var2[3];
      int[] var26 = (int[])((int[])q[5]);
      int[] var6 = (int[])((int[])q[1]);
      int[] var7 = (int[])((int[])q[2]);
      int[] var8 = (int[])((int[])q[3]);
      int[] var9 = (int[])((int[])q[4]);
      byte[] var10 = (byte[])((byte[])q[0]);
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
