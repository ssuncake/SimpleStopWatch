package ssuncake.simplestopwatch;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class SimpleStopWatchApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){return context;}
}
