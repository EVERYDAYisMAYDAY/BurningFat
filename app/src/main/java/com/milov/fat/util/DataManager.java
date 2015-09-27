package com.milov.fat.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Milov on 2015/9/25.
 */
public class DataManager {

    public static final int MALE = 1, FEMALE = 2;
    public static final int GENDER = 10, HEIGHT = 11, WEIGHT = 12, GOAL = 13;
    /**
     * 用于获取应用数据
     */
    private static DataManager dataManager;
    /**
     * 设置用户信息及任务时的选择区间
     */
    public String[] heights,weights,goals;
    /**
     * 月健康数据SP,用户信息SP（含任务信息）；
     */
    private SharedPreferences monthDataSP,selfDataSP;
    /**
     * SharedPreferences.Editor
     */
    private SharedPreferences.Editor monthDataEditor,selfDataEditor;

    private DataManager(Context context){
        monthDataSP = context.getSharedPreferences("monthData", Activity.MODE_PRIVATE);
        monthDataEditor = monthDataSP.edit();
        selfDataSP = context.getSharedPreferences("selfInfo",Activity.MODE_PRIVATE);
        selfDataEditor = selfDataSP.edit();

        initSelfInfoInterval();
    }

    /**
     * 获取单一实例
     */
    public static DataManager getInstance(Context context){
        if(dataManager == null )
            dataManager = new DataManager(context);
        return dataManager;
    }

    /**
     * 初始化用户信息以及任务的选择区间
     */
    private void initSelfInfoInterval(){
        heights = new String[51];
        for(int i = 0; i <= 50;i++){
            heights[i]=i+150+"cm";
            if(i==0)
                heights[0] = "≤150cm";
            else if(i==50)
                heights[50] = "≥200cm";
        }
        weights = new String[61];
        for(int i = 0; i <= 60;i++){
            weights[i]=i+40+"kg";
            if(i==0)
                weights[0] = "≤40kg";
            else if(i==60)
                weights[60] = "≥100kg";
        }
        goals = new String[20];
        for(int i = 0; i <= 19;i++){
            goals[i]="-"+ (i+1) + "kg";
        }
    }

    /**
     * 存储个人信息数据，包含任务目标
     * @param gender 性别{1,2}({男，女})，-1为不存储
     * @param height 身高[0,50]([150cm,200cm])，-1为不存储
     * @param weight 体重[0,60]([40kg,100kg])，-1为不存储
     * @param goal 任务目标[0,19]([-1kg,-20kg])，-1为不存储
     */
    public void saveSelfData(int gender,int height,int weight,int goal){
        if(gender!=-1) selfDataEditor.putInt("gender",gender);
        if(height!=-1) selfDataEditor.putInt("height",height);
        if(weight!=-1) selfDataEditor.putInt("weight",weight);
        if(goal!=-1) selfDataEditor.putInt("goal",goal);
        selfDataEditor.commit();
    }

    /**
     * 获取个人信息数据，通过类型获取对应数据
     * @param type 信息类型
     * @return 信息数据
     */
    public int getSelfData(int type){
        switch (type){
            case GENDER:
                //0代表第一次启动应用程序或者自从启动应用程序后还没有设置过个人信息
                return selfDataSP.getInt("gender",0);
            case HEIGHT:
                return selfDataSP.getInt("height",0);
            case WEIGHT:
                return selfDataSP.getInt("weight",0);
            case GOAL:
                return selfDataSP.getInt("goal",0);
            default:
                return -1;
        }
    }
    
    /**
     * 保存月健康数据
     * @param date 日期
     * @param cal 卡路里消耗值
     */
    public void saveMonthData(String date,int cal){
        monthDataEditor.putInt(date,cal);
        monthDataEditor.commit();
    }

    /**
     * 获取月健康数据
     * @param date 日期
     * @return 卡路里消耗值
     */
    public int getMonthData(String date){
        return monthDataSP.getInt(date,-1);
    }
}
