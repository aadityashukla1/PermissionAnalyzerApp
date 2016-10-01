package com.naresh.appanalyzer;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;


public class Analyzer extends ListActivity {

    public PackageManager packageManager=null;
    public List<ApplicationInfo> appsList=null;
    public ApplicationAdaptor appListAdaptor=null;
    String[] requestedPermissions=null;
    AnalyzeNature inst=new AnalyzeNature();
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzer);
        packageManager=getPackageManager();
        new LoadApplications().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_analyzer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try
        {
            ApplicationInfo appInfo=appsList.get(position);
            PackageManager packageManager=getPackageManager();
            PackageInfo packageInfo=packageManager.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);
            requestedPermissions = packageInfo.requestedPermissions;
            final Bundle bundle=new Bundle();
            bundle.putStringArray("arg1", requestedPermissions);
            final ProgressDialog progress=ProgressDialog.show(Analyzer.this,null,"Analyzing the App.. Please Wait");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent analyzeIntent = new Intent(Analyzer.this, EvaluatePermissions.class);
                    analyzeIntent.putExtras(bundle);
                    startActivity(analyzeIntent);
                    progress.dismiss();
                }
            }, SPLASH_DISPLAY_LENGTH);



        }
        catch(Exception e)
        {
            Toast.makeText(Analyzer.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list)
    {
        PackageManager pm=getPackageManager();
        ArrayList<ApplicationInfo> appsList=new ArrayList<ApplicationInfo>();
        for(ApplicationInfo info:list)
        {
            try {

                PackageInfo packageInfo=pm.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
                if((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0) {
                    appsList.add(info);
                }
                //to set the inputstream reader for training set

            }
            catch(PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        return appsList;
    }
    public  class LoadApplications extends AsyncTask<Void,Void,Void>
    {
        public ProgressDialog progress=null;

        @Override
        protected Void doInBackground(Void... params) {
            appsList=checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            appListAdaptor=new ApplicationAdaptor(Analyzer.this,R.layout.app_list_adapter_layout, appsList);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            progress=ProgressDialog.show(Analyzer.this,null,"Loading Apps List.. Please Wait");
            progress.dismiss();
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void result)
        {
            try {
                setListAdapter(appListAdaptor);
                progress=ProgressDialog.show(Analyzer.this,null,"Analyzing the App.. Please Wait");
                progress.dismiss();
                super.onPostExecute(result);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progress=ProgressDialog.show(Analyzer.this,null,"Analyzing the App.. Please Wait");
            progress.dismiss();
            super.onProgressUpdate(values);
        }
    }
}
