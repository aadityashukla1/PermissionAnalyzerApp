package com.naresh.appanalyzer;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class GetMoreInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_more_info);

        Bundle bundle=getIntent().getExtras();
        String totalPermissions=bundle.getString("a1");
        String safePermissions=bundle.getString("a2");
        String vulnerablePermissions=bundle.getString("a3");

        TextView tp=(TextView) findViewById(R.id.totalPermissons);
        TextView sp=(TextView) findViewById(R.id.safePermissions);
        TextView vp=(TextView) findViewById(R.id.vulnerablePermissions);
        tp.setText(totalPermissions);
        sp.setText(safePermissions);
        vp.setText(vulnerablePermissions);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_more_info, menu);
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
