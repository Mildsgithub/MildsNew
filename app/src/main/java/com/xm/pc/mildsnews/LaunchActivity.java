package com.xm.pc.mildsnews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import Bean.NewsTitleRoot;
import base.BaseData;
import utils.CommonUtils;
import utils.URLUtils;
public class LaunchActivity extends AppCompatActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            handler.removeCallbacksAndMessages(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        //3秒跳过
        handler.sendEmptyMessageDelayed(0, 3000);

        //请求标题--缓存
        BaseData baseData = new BaseData() {
            @Override
            public void setResultData(String data) {
                //解析 热点，天气，推荐，
                Gson gson=new Gson();
                NewsTitleRoot newsTitleRoot = gson.fromJson(data,NewsTitleRoot.class);
                // 热点：news—hot，天气：news-WEATHER
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append("推荐:,");
                for (int i = 0; i <newsTitleRoot.getData().getData().size() ; i++) {
                    NewsTitleRoot.DataBean.MyDataBean myDataBean = newsTitleRoot.getData().getData().get(i);
                    String name = myDataBean.getName();
                    String category = myDataBean.getCategory();
                    stringBuilder.append(name+":"+category+",");
                }

                CommonUtils.saveSp("newsTitle",stringBuilder.toString());
            }
            @Override
            protected void setFailResult(int error_Net) {

            }
        };
        baseData.getData(URLUtils.URL_TITLE_PATH, BaseData.LONGTTIME);

        BaseData baseData1=new BaseData() {
            @Override
            public void setResultData(String data) {

            }

            @Override
            protected void setFailResult(int error_Net) {

            }
        };
        baseData1.getData(URLUtils.URL_CATEGROY_PATH+"news_hot ",BaseData.LONGTTIME);

        BaseData baseData2=new BaseData() {
            @Override
            public void setResultData(String data) {

            }

            @Override
            protected void setFailResult(int error_Net) {

            }
        };
        baseData2.getData(URLUtils.URL_CATEGROY_PATH,BaseData.LONGTTIME);
    }
}
