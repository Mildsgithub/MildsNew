package views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xm.pc.mildsnews.R;

import utils.CommonUtils;

/**
 * Created by zhiyuan on 16/9/1.
 */
public class ViewPagerIndicator extends HorizontalScrollView implements View.OnClickListener, LazyViewPager.OnPageChangeListener {
    private LazyViewPager viewPager;
    private LinearLayout linearLayout;

    /**
     * new 时候用
     *
     * @param context
     */

    public ViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
    }

    /**
     * 布局里边引用
     *
     * @param context
     * @param attrs
     */
    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setViewPager(LazyViewPager viewPager) {
        this.viewPager = viewPager;
        //对viewPager设置滑动监听
        viewPager.setOnPageChangeListener(this);

        //获取adapter
        PagerAdapter adapter =
                viewPager.getAdapter();
        //创建线性布局 用来添加View条目
        linearLayout = new LinearLayout(getContext());

        //根据条目个数，往线性布局中添加view条目
        for (int i = 0; i < adapter.getCount(); i++) {
            //实例化展示条目
            View view = CommonUtils.inflate(R.layout.indicator_item);
            //设置监听
            view.setOnClickListener(this);
            TextView tv_indicator_title = (TextView) view.findViewById(R.id.tv_indicator_title);
            TextView tv_indicator_line = (TextView) view.findViewById(R.id.tv_indicator_line);
            //初始颜色处理
            if (i == 0) {
                tv_indicator_line.setVisibility(View.VISIBLE);
                tv_indicator_title.setTextColor(Color.RED);
            } else {
                tv_indicator_title.setTextColor(Color.GRAY);
                tv_indicator_line.setVisibility(View.INVISIBLE);
            }
            //获取到条目标题，然后设置给TextView
            tv_indicator_title.setText(adapter.getPageTitle(i));

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            layoutParams.setMargins(CommonUtils.dip2px(5),CommonUtils.dip2px(5),CommonUtils.dip2px(5),CommonUtils.dip2px(5));
            linearLayout.addView(view, layoutParams);


        }
        //将线性布局添加到当前的indicator中
        this.addView(linearLayout);

    }

    @Override
    public void onClick(View view) {

        for (int i=0;i<linearLayout.getChildCount();i++){

            View view1 = linearLayout.getChildAt(i);
            TextView tv_indicator_line = (TextView) view1.findViewById(R.id.tv_indicator_line);
            TextView tv_indicator_title = (TextView) view1.findViewById(R.id.tv_indicator_title);
            //判断当前点击的条目是该条目，就设置viewpager滑动到该索引位置处
            if(view==linearLayout.getChildAt(i)){
                viewPager.setCurrentItem(i);
                tv_indicator_line.setVisibility(View.VISIBLE);
                tv_indicator_title.setTextColor(Color.RED);

            }else{
                tv_indicator_line.setVisibility(View.INVISIBLE);
                tv_indicator_title.setTextColor(Color.GRAY);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i=0;i<linearLayout.getChildCount();i++){

            View view1 = linearLayout.getChildAt(i);
            TextView tv_indicator_line = (TextView) view1.findViewById(R.id.tv_indicator_line);
            TextView tv_indicator_title = (TextView) view1.findViewById(R.id.tv_indicator_title);
            if(position==i){
                //设置滑动位置为大约第二个位置处
                this.scrollTo((int) view1.getX()-linearLayout.getChildAt(0).getWidth(),0);
                tv_indicator_line.setVisibility(View.VISIBLE);
                tv_indicator_title.setTextColor(Color.RED);

            }else{
                tv_indicator_line.setVisibility(View.INVISIBLE);
                tv_indicator_title.setTextColor(Color.GRAY);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
