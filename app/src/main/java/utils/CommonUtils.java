package utils;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.View;

import app.MyApplication;


/**
 * Created by zhiyuan on 16/8/31.
 */
public class CommonUtils {
    public static final String TAG = "TOPNEWS";
    private static SharedPreferences sharedPreferences;

    public static View inflate(int layoutId) {
        View view = View.inflate(MyApplication.getContext(), layoutId, null);
        return view;
    }

    /**
     * dip---px
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip) {
        //获取像素密度
        float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
        //
        int px = (int) (dip * density + 0.5f);
        return px;

    }

    /**
     * px-dip
     *
     * @param px
     * @return
     */
    public static int px2dip(int px) {
        //获取像素密度
        float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
        //
        int dip = (int) (px / density + 0.5f);
        return dip;

    }

    public static String getString(int stringId) {
        return MyApplication.getContext().getResources().getString(stringId);
    }

    public static Drawable getDrawable(int did) {
        return MyApplication.getContext().getResources().getDrawable(did
        );
    }

    public static void saveSp(String flag, String str) {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.getContext().getSharedPreferences(TAG, MyApplication.getContext().MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(flag, str);
        edit.commit();
    }

    public static String getSp(String flag) {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.getContext().getSharedPreferences(TAG, MyApplication.getContext().MODE_PRIVATE);
        }
        return sharedPreferences.getString(flag, "");
    }

    public static void runOnUIThread(Runnable runable) {
        //先判断当前属于子线程主线程
        if(android.os.Process.myTid()==MyApplication.getMainThreadId()){
            runable.run();
        }else{
            //子线程
            MyApplication.getHandler().post(runable);
        }
    }
    /**
     * 使用线程池执行runnable
     * @param runnable
     */
    public static void executeRunnalbe(Runnable runnable){
        MyApplication.getThreadPool().execute(runnable);
    }
}
