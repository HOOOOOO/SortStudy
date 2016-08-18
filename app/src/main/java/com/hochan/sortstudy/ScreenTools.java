
package com.hochan.sortstudy;

import android.content.Context;


/**
 * dp、px转换工具类 通过测试这个类计算的值和XML中的dp值是一致的
 *
 * Created by zhendong_chen on 2016/7/14.
 */
public class ScreenTools {

    public static int dip2px(Context context, float f) {
        return (int) (0.5D + (double) (f * getDensity(context)));
    }

    public static int dip2px(Context context, int i) {
        return (int) (0.5D + (double) (getDensity(context) * (float) i));
    }

    public static int get480Height(Context context, int i) {
        return (i * getScreenWidth(context)) / 480;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScal(Context context) {
        return (100 * getScreenWidth(context)) / 480;
    }

    public static int getScreenDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public static float getXdpi(Context context) {
        return context.getResources().getDisplayMetrics().xdpi;
    }

    public static float getYdpi(Context context) {
        return context.getResources().getDisplayMetrics().ydpi;
    }

    public static int px2dip(Context context, float f) {
        float f1 = getDensity(context);
        return (int) (((double) f - 0.5D) / (double) f1);
    }

    public static int px2dip(Context context, int i) {
        float f = getDensity(context);
        return (int) (((double) i - 0.5D) / (double) f);
    }

}