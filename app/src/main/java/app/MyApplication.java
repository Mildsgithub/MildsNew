package app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import org.xutils.x;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.ImageLoaderUtils;

public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;
    private static Thread currentThread;
    private static ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = Process.myTid();
        currentThread = Thread.currentThread();
        executorService = Executors.newFixedThreadPool(5);
        //配置xutils初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);
        ImageLoaderUtils.initConfiguration(context);
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Thread getMainTread() {
        return currentThread;
    }

    public static ExecutorService getThreadPool() {
        return executorService;
    }
}
