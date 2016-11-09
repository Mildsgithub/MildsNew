package base;

import android.text.TextUtils;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import app.MyApplication;
import utils.CommonUtils;
import utils.MD5Encoder;
import utils.NetUtils;

public abstract class BaseData {
    /**
     * 网络出错
     */
    public static  final  int Error_Net=100;
    /**
     * 请求出错
     */
    public static  final  int Error_Request=200;

    public static final  long LONGTTIME=1000*60*60*72;
    public static final  long SHORTTIME=1000*60*10;
    public static final  long NOTIME=0;





    public void getData(String path, long time) {

        if(time==0){
            getDataFromNet(path,time);
        }else{
            //先看本地是否有数据
            String data = getDataFromLocal(path, time);
            if (TextUtils.isEmpty(data)) {
                //再看网络
                getDataFromNet(path, time);
            } else {
                setResultData(data);
            }
        }


    }

    public abstract void setResultData(String data);

    private void getDataFromNet(final String path, final long time) {
        Toast.makeText(MyApplication.getContext(),"WANG", Toast.LENGTH_LONG).show();
        //先判断网络状态
        int netWorkType = NetUtils.getNetWorkType(MyApplication.getContext());
        if(netWorkType!=NetUtils.NETWORKTYPE_INVALID){

            RequestParams requestParams=new RequestParams(path);
            //开始请求网络
            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(final String json) {
                    setResultData(json);

                    CommonUtils.executeRunnalbe(new Runnable() {
                        @Override
                        public void run() {
                            //写数据导本地
                            writeDataToLocal(json, path, time);

                        }
                    });

                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    setFailResult(Error_Request);
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        }else{
            //网络问题
            setFailResult(Error_Net);
        }


    }

    protected abstract void setFailResult(int error_Net);

    private void writeDataToLocal(String json, String path, long time) {
        File cacheDir = MyApplication.getContext().getCacheDir();

        File file = null;
        try {
            file = new File(cacheDir, MD5Encoder.encode(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            //21408348372737+1000*60*60
            //dkgkawerghosrghseligs
            //sergohelshgoeshgoserhogsh
            //3238230280333333
            bufferedWriter.write(System.currentTimeMillis() + time + "\r\n");

            bufferedWriter.write(json);

            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter!=null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从本地获取信息
     * @param path
     * @param time
     * @return
     */
    private String getDataFromLocal(String path, long time) {
        File cacheDir = MyApplication.getContext().getCacheDir();
        File file = null;
        try {
            file = new File(cacheDir, MD5Encoder.encode(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader=null;
        try {
            bufferedReader=new BufferedReader(new FileReader(file.getAbsolutePath()));
            //之前时间＋有效时间
            long t = Long.parseLong(bufferedReader.readLine());
            //在有效时间之内
            //90 +10
            if(System.currentTimeMillis()-t<0){
                StringBuilder stringBuilder=new StringBuilder();
                String line=null;
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader!=null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
