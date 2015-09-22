package com.example.rashidabbas.internetconnectivitydemo;

import android.app.Activity;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.text.Format;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class InstalledApps extends AppCompatActivity
{

    Activity context ;
    ListView LvInstalledApps ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_apps);
        Init();
        GetInstalledApps();
    }

    //**********Init ************
    private void Init()
    {
        context = InstalledApps.this ;
        LvInstalledApps = (ListView) findViewById(R.id.LvInstalledApps);
    }

    //****************** Get Installed Apps ******************
    private void GetInstalledApps()
    {
        List<AppModel> list = new ArrayList<AppModel>();
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(0);
        for (int i = 0 ; i < applicationInfoList.size() ; i++)
        {
            //int UId = applicationInfoList.get(i).uid;
            String PckgName = applicationInfoList.get(i).packageName;
            try
            {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(PckgName , 0);
                String AppName = (String) packageManager.getApplicationLabel(applicationInfo);
                Drawable AppIcon = packageManager.getApplicationIcon(applicationInfo);
                double received = (double) TrafficStats.getUidRxBytes(applicationInfo.uid)/1024;
                double send = (double) TrafficStats.getUidTxBytes(applicationInfo.uid)/1024;
                double total = received + send;

                if (total > 0)
                {
                    list.add(new AppModel(AppName , String.format("%.2f" , total)+ " kb" ,AppIcon));
                }
            }
            catch (PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
                Toast.makeText(context, "Exp\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        if (list.size() > 0)
        {
            LvInstalledApps.setAdapter(new InstalledAppAdapter(context , list));
        }
        else
        {
            Toast.makeText(context, "No Apps", Toast.LENGTH_SHORT).show();
        }
    }
}
