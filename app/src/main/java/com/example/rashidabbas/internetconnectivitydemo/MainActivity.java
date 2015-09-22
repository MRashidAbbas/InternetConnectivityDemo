package com.example.rashidabbas.internetconnectivitydemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity
{

    Activity context ;
    Button BtnCheckConnection , BtnConnectionSpeed , BtnGetNetUsageInfo , BtnCommandLine;
    int Result  ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        BtnCheckConnection.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (IsNetAvailable())
                {
                    for (int i =0 ; i<10; i++)
                    {
                        Toast.makeText(context, "Connected " + i, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        BtnConnectionSpeed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                WifiManager wifiInfo = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifiInfo.getConnectionInfo();
                Toast.makeText(context, isConnectionFast(networkInfo.getType(), networkInfo.getSubtype(), info), Toast.LENGTH_SHORT).show();
            }
        });
        BtnGetNetUsageInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(context, InstalledApps.class));
            }
        });
        BtnCommandLine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(context , AdbCommandsDemo.class));
            }
        });
    }

    //**************************** Init *********************
    private void Init()
    {
        context = MainActivity.this ;
        BtnCheckConnection = (Button) findViewById(R.id.BtnCheckConnection);
        BtnConnectionSpeed = (Button) findViewById(R.id.BtnConnectionSpeed);
        BtnGetNetUsageInfo = (Button) findViewById(R.id.BtnGetNetUsageInfo);
        BtnCommandLine = (Button) findViewById(R.id.BtnCommandLine);
    }

    //************************** Internet Available *************************
    private boolean IsNetAvailable()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Runtime runtime = Runtime.getRuntime();
                    Process process = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                    Result = process.waitFor();
                }
                catch (Exception e)
                {
                    Toast.makeText(context, "Exp \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
        return (Result == 0);
    }

    //************************** Check Internet Connection Speed *************
    public static String isConnectionFast(int type, int subType, WifiInfo info){
        if(type== ConnectivityManager.TYPE_WIFI){
            if (info != null) {
                int linkSpeed = info.getLinkSpeed(); //measured using WifiInfo.LINK_SPEED_UNITS
                return linkSpeed+" wifi";
            }
            return "Wifi";
        }else if(type==ConnectivityManager.TYPE_MOBILE){
            switch(subType){
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return "50-100 kbps"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "14-64 kbps"; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "50-100 kbps"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return "400-1400 kbps"; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return "600-1400 kbps"; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "100 kbps"; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return "2-14 Mbps"; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return "700-1700 kbps"; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return "1-23 Mbps"; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return "400-7000 kbps"; // ~ 400-7000 kbps
			/*
			 * Above API level 7, make sure to set android:targetSdkVersion
			 * to appropriate level to use these
			 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return "1-2 Mbps"; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return "5 Mbps"; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return "10-20 Mbps"; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return "25 kbps"; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return "10+ Mbps"; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return "0";
            }
        }else{
            return "0";
        }
    }
}
