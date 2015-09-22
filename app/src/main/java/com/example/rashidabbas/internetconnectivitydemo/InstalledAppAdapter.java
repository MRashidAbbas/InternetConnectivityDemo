package com.example.rashidabbas.internetconnectivitydemo;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rashidabbas on 17/09/2015.
 */
public class InstalledAppAdapter extends BaseAdapter

{
    Activity context ;
    List<AppModel> list ;

    public InstalledAppAdapter(Activity context, List<AppModel> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder ;
        if (convertView == null)
        {
            convertView = context.getLayoutInflater().inflate(R.layout.row_item , parent , false);
            viewHolder = new ViewHolder();
            viewHolder.TvAppName = (TextView) convertView.findViewById(R.id.TvAppName);
            viewHolder.TvDataUsage = (TextView) convertView.findViewById(R.id.TvDataUsage);
            viewHolder.AppIcon = (ImageView) convertView.findViewById(R.id.IvIcon);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.TvAppName.setText(list.get(position).getAppName());
        viewHolder.TvDataUsage.setText(list.get(position).getDataUsage());
        viewHolder.AppIcon.setImageDrawable(list.get(position).getAppIcon());
        return convertView;
    }

    private class ViewHolder
    {
        TextView TvAppName , TvDataUsage ;
        ImageView AppIcon ;
    }
}
