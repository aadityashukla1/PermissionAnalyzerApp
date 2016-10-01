package com.naresh.appanalyzer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.os.Handler;


public class AppAnalyzer extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /*
        ImageView splash=(ImageView) findViewById(R.id.splashScreen);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap splashImage = BitmapFactory.decodeResource(getResources(), R.drawable.frontpage, options);
        splash.setImageBitmap(splashImage);
        */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivityIntent=new Intent(AppAnalyzer.this,MainActivity.class);
                AppAnalyzer.this.startActivity(mainActivityIntent);
                AppAnalyzer.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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
