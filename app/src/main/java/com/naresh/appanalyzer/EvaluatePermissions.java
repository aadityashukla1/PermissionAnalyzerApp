package com.naresh.appanalyzer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;

//weka packages
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;



public class EvaluatePermissions extends Activity {

    public String appPermissions;
    InputStream is;
    String results;
    String resultText,totalPermissions=null,predictedSafe=null,predictedVulnerable=null;
    String[] resultLines;
    public int startingIndex=57,endingIndex=59,resultNumValue;
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_permissions);
        //ImageView resultPic = (ImageView) findViewById(R.id.imageValue);
        ImageButton getMoreInfo = (ImageButton) findViewById(R.id.moreInfo);
        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.mainLayout);
        getMoreInfo.setVisibility(View.INVISIBLE);
        try {
            is = getResources().openRawResource(R.raw.td);
            Bundle bundle = getIntent().getExtras();
            String[] arg = bundle.getStringArray("arg1");
            //class instance
            if (arg != null) {
                EvaluatePermissions obj = new EvaluatePermissions();
                results = obj.ARFFOnPermissions(arg, is);

                resultText = results.substring(57, 60);
                resultLines = results.split("\\n");
                predictedSafe = resultLines[1];
                predictedVulnerable = resultLines[2];
                totalPermissions = resultLines[8];
                resultNumValue = Integer.parseInt(resultText.replaceAll("\\D+", ""));
                if (resultNumValue > 50) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.safe, options);
                    linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.safe));
                } else {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.vulnerable, options);
                    linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.vulnerable));

                }

                final Bundle bundle1 = new Bundle();
                bundle1.putString("a1", totalPermissions);
                bundle1.putString("a2", predictedSafe);
                bundle1.putString("a3", predictedVulnerable);
                getMoreInfo.setVisibility(View.VISIBLE);
                getMoreInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressDialog progress = ProgressDialog.show(EvaluatePermissions.this, null, "Please Wait");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent finalIntent = new Intent(EvaluatePermissions.this, GetMoreInfo.class);
                                finalIntent.putExtras(bundle1);
                                startActivity(finalIntent);
                                progress.dismiss();
                            }
                        }, SPLASH_DISPLAY_LENGTH);

                    }
                });

            } else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.safe, options);
                linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.safe));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluate_permissions, menu);
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
    public String ARFFOnPermissions(String[] permissions, InputStream is) throws IOException, Exception
    {
        String result;
        //analyzer class instance
        AnalyzeNature instance=new AnalyzeNature();
        //declaration of variables and types needed for creating ARFF file using WEKA API
        FastVector attributes;
        FastVector classAttribute;
        Instances data;
        double[] dataValue;
        BufferedWriter arffWriter;
        // To set up the attributes
        attributes=new FastVector();
        // To define the permissions attribute
        attributes.addElement(new Attribute("permissionvalue",(FastVector)null));
        // To define the class attribute
        classAttribute=new FastVector(2);
        // Two class attribute value-genuine and malware
        classAttribute.addElement("genuine");
        classAttribute.addElement("malware");
        // adding the class attribute to dataset
        attributes.addElement(new Attribute("class",classAttribute));
        // To create the instances object
        data=new Instances("AndroidAppClassifier",attributes,0);

        for(int i=0;i<permissions.length;i++)
        {
            //it is essential to initialize double array everytime when the method is  called
            dataValue=new double[data.numAttributes()];
            dataValue[0]=data.attribute(0).addStringValue(permissions[i]);
            dataValue[1]=classAttribute.indexOf("genuine");
            //use the below line to set class option as malware for malicious apps
            //dataValue[1]=classAttribute.indexOf("malware");
            data.add(new Instance(1.0,dataValue));
        }
        appPermissions=data.toString();
        result=instance.analyzeApp(appPermissions, is);
        //System.out.println(results);

        return result;
    }

}
