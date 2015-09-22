package com.example.rashidabbas.internetconnectivitydemo;

import android.graphics.drawable.Drawable;

/**
 * Created by rashidabbas on 17/09/2015.
 */
public class AppModel
{
    private String AppName , DataUsage ;
    private Drawable AppIcon ;

    public AppModel(String appName, String dataUsage, Drawable appIcon)
    {
        AppName = appName;
        DataUsage = dataUsage;
        AppIcon = appIcon;
    }

    public String getAppName()
    {
        return AppName;
    }

    public String getDataUsage()
    {
        return DataUsage;
    }

    public Drawable getAppIcon()
    {
        return AppIcon;
    }
}
