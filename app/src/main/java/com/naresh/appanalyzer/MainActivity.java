package com.naresh.appanalyzer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.os.Handler;
import android.widget.TextView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;


import weka.gui.Main;


public class MainActivity extends Activity {

    ImageButton appAnalyze,workingMechanism,aboutUs;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //to set the ids for the main screen buttons
        appAnalyze=(ImageButton) findViewById(R.id.startAnalyze);
        workingMechanism=(ImageButton) findViewById(R.id.workingMechanism);
        aboutUs=(ImageButton) findViewById(R.id.aboutUs);
        //to set the onclick listener to each of the buttons
        appAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress=ProgressDialog.show(MainActivity.this,null,"Loading Apps List");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent actionIntent = new Intent(MainActivity.this, Analyzer.class);
                        MainActivity.this.startActivity(actionIntent);
                        progress.dismiss();
                    }
                },SPLASH_DISPLAY_LENGTH);


            }
        });

        workingMechanism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder wm=new AlertDialog.Builder(MainActivity.this);
                wm.setTitle("How it Works?");
                wm.setMessage(" AppAnalyzer scans all the installed applications on the phone and extracts the system permissions used by the App. It uses the Weka library (Machine Learning framework) to test and classify the permission set of the application with the trained dataset which is integrated with this App. Using the trained dataset and the testset(permissions extracted), AppAnalyzer predicts the nature of the App whether it is safe to use or vulnerable");
                wm.setPositiveButton("OK", null);
                wm.show();

            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder au=new AlertDialog.Builder(MainActivity.this);
                au.setTitle("About Us");
                au.setMessage(" This App is developed by Team1 ");
                au.setPositiveButton("OK", null);
                au.show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
