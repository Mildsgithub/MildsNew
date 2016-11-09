package base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;


public abstract class NewsBaseAdapter<T> extends BaseAdapter {
    public  final Context context;
    public  final ArrayList<T> dataList;

    public NewsBaseAdapter(Context context, ArrayList<T> dataList){
        this.context=context;
        this.dataList=dataList;

    }



    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return adapterView(i,view,viewGroup);
    }

    public abstract  View adapterView(int i, View view, ViewGroup viewGroup) ;
}
