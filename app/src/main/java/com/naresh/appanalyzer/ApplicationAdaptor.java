package com.naresh.appanalyzer;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ApplicationAdaptor extends ArrayAdapter<ApplicationInfo> {
    private List<ApplicationInfo> appsList = null;
    private Context context;
    private PackageManager packageManager;

    public ApplicationAdaptor(Context context, int textViewResourceId,
                              List<ApplicationInfo> appsList) {
        super(context, textViewResourceId, appsList);
        this.context = context;
        this.appsList = appsList;
        packageManager = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.app_list_adapter_layout, null);
        }
        ApplicationInfo applicationInfo=appsList.get(position);
        if(null!= applicationInfo)
        {
            TextView appName=(TextView) view.findViewById(R.id.appName);
            ImageView iconView=(ImageView) view.findViewById(R.id.appIcon);

            appName.setText(applicationInfo.loadLabel(packageManager));
            iconView.setImageDrawable(applicationInfo.loadIcon(packageManager));
        }
        return  view;
    }

}
