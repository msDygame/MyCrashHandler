package com.dygame.myuncaughtexceptionhandler;

import android.app.ActivityManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
{
    Button pCrashButton1 ;
    Button pCrashButton2 ;
    protected static String TAG ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Uncaught Exception Handler(Crash Exception)
        MyCrashHandler pCrashHandler = MyCrashHandler.getInstance();
        pCrashHandler.init(getApplicationContext());
        TAG = pCrashHandler.getTag() ;
        //
        pCrashButton1 = (Button)findViewById(R.id.button1) ;
        pCrashButton2 = (Button)findViewById(R.id.button2) ;
        pCrashButton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //get current memory usage in android
                ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
                ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                activityManager.getMemoryInfo(mi);
                long availableMegs = mi.availMem / 1048576L;//1MB
                //Percentage can be calculated for API 16+
                long percentAvail = 100 * mi.availMem / mi.totalMem;
                //
                String sz = "availableMemMB=" + availableMegs + ",percentAvailableMem=" + percentAvail + "%" ;
                Toast.makeText(v.getContext(),sz,Toast.LENGTH_LONG).show() ;
                Log.i(TAG, sz) ;
                //make crash
                double db = 12 / 0 ;
            }
        });
        pCrashButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Another way to calculate memory usage of currently running application.
                long freeSize = 0L;
                long totalSize = 0L;
                long usedSize = -1L;
                try
                {
                    Runtime info = Runtime.getRuntime();
                    freeSize = info.freeMemory();
                    totalSize = info.totalMemory();
                    usedSize = (totalSize - freeSize)  ;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                //
                String sz = "usedMemMB=" + (usedSize / 1048576L) + ",totalMemMB=" + (totalSize / 1048576L) ;
                Toast.makeText(v.getContext(),sz,Toast.LENGTH_LONG).show() ;
                Log.i(TAG, sz) ;
                //make crash
                throw new NullPointerException() ;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
