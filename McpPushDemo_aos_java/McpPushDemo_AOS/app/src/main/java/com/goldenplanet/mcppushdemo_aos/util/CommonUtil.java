package com.goldenplanet.mcppushdemo_aos.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Kim Namhoon on 2022/11/08.
 */

public class CommonUtil {
   public static String getAppVersionName(Context context) {
      try {
         PackageInfo pi = context.getPackageManager().getPackageInfo(
                 context.getPackageName(), 0);
         return pi.versionName;
      } catch (PackageManager.NameNotFoundException e) {
         return null;
      }
   }

   public static int getAppVersionCode(Context context) {
      try {
         PackageInfo pi = context.getPackageManager().getPackageInfo(
                 context.getPackageName(), 0);
         return pi.versionCode;
      } catch (PackageManager.NameNotFoundException e) {
         return (Integer) null;
      }
   }


   public static float getDevicePPI(Context ctx) {
      WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
      Display display = wm.getDefaultDisplay();

      Point size = new Point();
      display.getSize(size);
      int width = size.x;
      int height = size.y;
      LogMsg.d("width : " + width + ", height : " + height);
      float ppi = (float) ((float) Math.sqrt(width * width + height * height) / 10.1);
      LogMsg.d("PPI : " + ppi);

      return ppi;
   }
}
