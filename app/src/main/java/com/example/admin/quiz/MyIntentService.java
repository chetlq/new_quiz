package com.example.admin.quiz;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
//import java.util.logging.Handler;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends Service {//IntentService {

    public static final int notify = 5000;  //interval between two services(Here Service run every 5 seconds)
    int count = 0;  //number of times service is display
    private Handler mHandler = new Handler(  );   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
        Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
    }

    //class TimeDisplay for handling task
    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // display toast
                    Toast.makeText(MyIntentService.this, "Service is running", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}
//    private final String TAG = "IntentServiceLogs";
//    /**
//     * A constructor is required, and must call the super IntentService(String)
//     * constructor with a name for the worker thread.
//     */
//    public MyIntentService() {
//        super("MyIntentService");
//    }
//
//    /**
//     * The IntentService calls this method from the default worker thread with
//     * the intent that started the service. When this method returns, IntentService
//     * stops the service, as appropriate.
//     */
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        // Normally we would do some work here, like download a file.
//        // For our sample, we just sleep for 5 seconds.
//        int tm = intent.getIntExtra("time", 0);
//        String label = intent.getStringExtra("task");
//        Log.i(TAG, "onHandleIntent start: " + label);
//        try {
//            TimeUnit.SECONDS.sleep(tm);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.i(TAG, "onHandleIntent end: " + label);
//    }
//
//    @Override
//    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
//        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
//        return super.onStartCommand( intent, flags, startId );
//    }
//
//
//}
