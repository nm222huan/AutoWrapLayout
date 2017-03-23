package com.example.fish.myapplication;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 获取屏幕大小工具方法
 * 
 * @author zhuotao
 */
public class ScreenUtils {

    public static int sScreenWidth = 0, sScreenHeight = 0;

    /**
     * 顶部状态栏的高度<br/>
     * 默认是25，初始化会设置setStatusBarHeight
     */
    public static int statusBarHeight = 25;

    public static void init(Context mContext) {
	if (null != mContext && sScreenWidth == 0) {
	    DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
	    if (null != displayMetrics) {
		sScreenWidth = displayMetrics.widthPixels;
		sScreenHeight = displayMetrics.heightPixels;
	    } else {
		if (mContext instanceof Activity) {
		    DisplayMetrics dm = new DisplayMetrics();
		    Activity activity = (Activity) mContext;
		    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		    sScreenWidth = dm.widthPixels;
		    sScreenHeight = dm.heightPixels;
		}
	    }
	    // 获取屏幕密度
	    setScale(mContext);
	    // 设置顶部状态栏的高度
	    setStatusBarHeight(mContext);
	}
    }

    /**
     * 获取屏幕宽度
     * 
     * @param context
     * @return
     */
    public static int getScreentWidth(Context context) {
	if (sScreenWidth <= 0) {
	    init(context);
	}
	return sScreenWidth;
    }

    /**
     * 获取屏幕高度
     * 
     * @param context
     * @return
     */
    public static int getScreentHeight(Context context) {
	if (sScreenHeight <= 0) {
	    init(context);
	}
	return sScreenHeight;
    }

    public static float getScale(float r) {
	if (sScreenWidth == 0) {
	    throw new RuntimeException("ScreenUtils not init");
	}
	return r * sScreenWidth / 720f;
    }

    public static float getWidth(float width) {
	return getScale(width);
    }

    public static float getHeight(float height) {
	if (sScreenHeight == 0) {
	    throw new RuntimeException("ScreenUtils not init");
	}
	return height * sScreenHeight / 1280f;
    }

    /**
     * 通过Context对象获取屏幕大小
     * 
     * @param context
     *            上下文对象
     * @return 屏幕大小数组 0:宽度 1:高度
     */
    public static int[] getDisplaySize(Context context) {
	int[] size = new int[2];
	WindowManager windowManager = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
	Display display = windowManager.getDefaultDisplay();
	size[0] = display.getWidth();
	size[1] = display.getHeight();
	return size;
    }

    /**
     * <br>
     * 功能简述:获取设备真实的显示尺寸 <br>
     * 功能详细描述: <br>
     * 注意:
     * 
     * @param context
     * @return 屏幕大小数组 0:宽度 1:高度
     */
    public static int[] getDisplayRealSize(Context context) {
	WindowManager windowManager = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
	Display display = windowManager.getDefaultDisplay();
	DisplayMetrics dm = new DisplayMetrics();
	int[] size = new int[2];

	display.getMetrics(dm);
	size[0] = dm.widthPixels;
	size[1] = dm.heightPixels;
	// includes window decorations (statusbar bar/navigation bar)
	if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
	    try {
		size[1] = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	// includes window decorations (statusbar bar/navigation bar)
	else if (Build.VERSION.SDK_INT >= 17) {
	    try {
		final Point realSize = new Point();
		Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
		size[1] = realSize.y;
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return size;
    }

    public static float sScale = 1;
    private static final float NUM_5F = 0.5f;

    /**
     * 获取手机的屏幕的密度
     * 
     * @param context
     */
    public static void setScale(Context context) {
	sScale = context.getResources().getDisplayMetrics().density;
    }

    /**
     * 将dp转换为px
     * 
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
	return (int) (dipValue * sScale + NUM_5F);
    }

    /**
     * 将px转换为dp
     * 
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
	return (int) (pxValue / sScale + NUM_5F);
    }

    /**
     * 获取dimen的值
     * 
     * @id 资源id 例如：R.dimen.xxx
     */
    public static int getDimenValue(Context context, int id) {
	return context.getResources().getDimensionPixelSize(id);
    }

    /**
     * <br>
     * 功能简述: 图片Config需要根据不同分辨率来获取 <br>
     * 功能详细描述: <br>
     * 注意:
     * 
     * @return
     */
    public static Config getImageConfig() {
	if (sScreenWidth >= 720 || sScreenHeight >= 1280) {
	    return Config.ARGB_8888;
	} else {
	    return Config.RGB_565;
	}
    }

    /**
     * 获取状态栏的高度
     * 
     * @param context
     */
    public static void setStatusBarHeight(Context context) {
	Class<?> c = null;
	Object obj = null;
	Field field = null;
	int x = 0;
	try {
	    c = Class.forName("com.android.internal.R$dimen");
	    obj = c.newInstance();
	    field = c.getField("status_bar_height");
	    x = Integer.parseInt(field.get(obj).toString());
	    statusBarHeight = context.getResources().getDimensionPixelSize(x);
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
    }
}
