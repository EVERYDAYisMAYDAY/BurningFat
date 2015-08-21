package com.milov.fat.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by milov on 2015/8/21.
 * ���������洢��Ļ�Ŀ���Լ�dp��px֮���ת������
 */
public class DisplayUtil {
    /**
     * ��һʵ��
     */
    public static DisplayUtil displayUtil=null;
    /**
     * ��Ļ��(px)
     */
    public static float SCREEN_WIDTH;
    /**
     * ��Ļ��(px)
     */
    public static float SCREEN_HEIGHT;
    /**
     * ��Ļ�ܶ�
     */
    private static float scale;

    private DisplayUtil(Context context){
        DisplayMetrics dpMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dpMetrics);
        SCREEN_WIDTH = dpMetrics.widthPixels;
        SCREEN_HEIGHT = dpMetrics.heightPixels;
        scale = dpMetrics.density;
    }

    /**
     * ��ȡ��һʵ��
     * @param context
     * @return
     */
    public static DisplayUtil getInstance(Context context){
        if(displayUtil==null)
            displayUtil = new DisplayUtil(context);
        return displayUtil;
    }

    /**
     * dpתpx
     * @param dp
     * @return
     */
    public int dp2px(int dp){
        return (int)(dp*scale+0.5f);
    }
}
