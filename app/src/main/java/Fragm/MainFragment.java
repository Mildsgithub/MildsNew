package Fragm;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.xm.pc.mildsnews.R;

import java.util.ArrayList;

import Bean.NewsCategoryRoot;
import Xlistviews.XListView;
import adapter.NewsCategroyAdapter;
import base.BaseData;
import base.BaseFragment;
import utils.URLUtils;

public class MainFragment extends BaseFragment implements XListView.IXListViewListener {
    private XListView mListView;
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;
    private ListView listView;
    private NewsCategroyAdapter newsCategroyAdapter;

    @Override
    public View initView(LayoutInflater inflater) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, null);
        mListView = (XListView) v.findViewById(R.id.xListView);
        mListView.setPullLoadEnable(true);
        mListView.setAdapter(newsCategroyAdapter);
        mListView.setXListViewListener(MainFragment.this);
        mHandler = new Handler();
        return v;
    }

    @Override
    protected void initData() {
        String category = getArguments().getString("category");
        BaseData baseData = new BaseData() {
            @Override
            public void setResultData(String data) {
                showData(data);
            }
            @Override
            protected void setFailResult(int error_Net) {
            }
        };
        baseData.getData(URLUtils.URL_CATEGROY_PATH + category, BaseData.LONGTTIME);
    }
    private void showData(String data) {

        Gson gson = new Gson();
        NewsCategoryRoot newsCategoryRoot = gson.fromJson(data, NewsCategoryRoot.class);
        //展示
        ArrayList<NewsCategoryRoot.DataBean> dataBeanList = (ArrayList<NewsCategoryRoot.DataBean>) newsCategoryRoot.getData();
        newsCategroyAdapter = new NewsCategroyAdapter(getActivity(), dataBeanList);
        mListView.setAdapter(newsCategroyAdapter);

    }
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                newsCategroyAdapter.notifyDataSetChanged();
                mListView.setAdapter(newsCategroyAdapter);
                onLoad();
            }
        }, 2000);
    }
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newsCategroyAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }
    public static Fragment getInstance(String category) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }
}
