package views;


import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xm.pc.mildsnews.MainActivity;
import com.xm.pc.mildsnews.R;

/**
 * Created by zhiyuan on 16/9/1.
 */
public class SlidingMenuUtils {

    public static void initSlidingMenu(MainActivity mainActivity) {
        // 初始化SlidingMenu对象
        SlidingMenu menu = new SlidingMenu(mainActivity);
        // 设置侧滑方式为左侧侧滑
        menu.setMode(SlidingMenu.LEFT);
        /*
         * 设置拖拽模式 SlidingMenu.TOUCHMODE_FULLSCREEN全屏触摸有效
         * SlidingMenu.TOUCHMODE_MARGIN 拖拽边缘有效 SlidingMenu.TOUCHMODE_NONE
         * 不响应触摸事件
         */
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        // 设置阴影的宽度
        menu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置阴影的图片
        menu.setShadowDrawable(R.mipmap.ic_launcher);
        // 设置sldingMenu的剩余大小---=内容显示页对应的dp大小
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置滑动时的渐变程度
        menu.setFadeDegree(0.35f);
        // 使SlidingMenu附加在Activity右边
        // SlidingMenu.SLIDING_CONTENT 将侧滑栏设置为在内容位置
        // SlidingMenu.SLIDING_WINDOW 将侧滑栏设置为在整个窗口呈现
        menu.attachToActivity(mainActivity, SlidingMenu.SLIDING_CONTENT);
        // 设置SlidingMenu关联的布局
        menu.setMenu(R.layout.menu);
        // 在SlidingMenu关联布局中查询控件
        TextView tv_test = (TextView) menu.findViewById(R.id.tv_test);
        // 简单设置SlidingMenu界面显示内容
        tv_test.setText("haha");
    }
}
