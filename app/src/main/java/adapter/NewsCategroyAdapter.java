package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xm.pc.mildsnews.R;

import java.util.ArrayList;

import Bean.NewsCategoryRoot;
import base.NewsBaseAdapter;
import utils.CommonUtils;
import utils.ImageLoaderUtils;


public class NewsCategroyAdapter extends NewsBaseAdapter<NewsCategoryRoot.DataBean> {
    /**
     * 普通条目类型
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * 大图片条目类型
     */
    public static final int TYPE_LARGE_PIC = 1;
    /**
     * 没有图片类型
     */
    public static final int TYPE_NO_PIC = 2;
    /**
     * 多图片类型
     */
    public static final int TYPE_MULITY_PIC = 3;
    /*
    视频类型
     */
    public static final int TYPE_VIDEO = 4;
    private final DisplayImageOptions options;


    public NewsCategroyAdapter(Context context, ArrayList<NewsCategoryRoot.DataBean> dataList) {
        super(context, dataList);
        options = ImageLoaderUtils.initOptions();
    }

    /**
     * 获取某个索引值下的条目类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //如果有视频，类型是视频类型
        if (dataList.get(position).isHas_video()) {
            return TYPE_VIDEO;
            //如果没有图片，就是没图片类型
        } else if (!dataList.get(position).isHas_image()) {
            return TYPE_NO_PIC;
        }
        //如果有大图片，大图片类型
        if (dataList.get(position).getLarge_image_list() != null && dataList.get(position).getLarge_image_list().size() > 0) {
            return TYPE_LARGE_PIC;
        }
        //如果图片集合数据大于1 多图片类型
        if (dataList.get(position).getImage_list() != null && dataList.get(position).getImage_list().size() > 1) {
            return TYPE_MULITY_PIC;
        }
        //普通类型
        return TYPE_NORMAL;
    }

    /**
     * 获取listView要展示的条目类型个数
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 5;
    }

    /**
     * 用来展示ListView条目数据
     *

     * @param viewGroup
     * @return 0  1 video 2
     * //
     */

    @Override
    public View adapterView(int position, View convertView, ViewGroup viewGroup) {
        //获取当前条目的类型
        int itemViewType = getItemViewType(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            switch (itemViewType) {
                case TYPE_NORMAL:
                    convertView = CommonUtils.inflate(R.layout.type_normal_item);
                    viewHolder.iv_news_pic = (ImageView) convertView.findViewById(R.id.iv_news_pic);
                    break;
                case TYPE_LARGE_PIC:
                    convertView = CommonUtils.inflate(R.layout.type_large_pic_item);
                    viewHolder.iv_news_pic = (ImageView) convertView.findViewById(R.id.iv_news_pic);
                    break;
                case TYPE_VIDEO:
                    convertView = CommonUtils.inflate(R.layout.type_video_item);
                    viewHolder.iv_news_pic = (ImageView) convertView.findViewById(R.id.iv_news_pic);
                    viewHolder.vv_new_movie = (VideoView) convertView.findViewById(R.id.vv_news_movie);
                    viewHolder.iv_news_play_movie = (ImageView) convertView.findViewById(R.id.iv_news_movie_play);
                    break;
                case TYPE_NO_PIC:
                    convertView = CommonUtils.inflate(R.layout.type_no_pic_item);
                    break;
                case TYPE_MULITY_PIC:
                    convertView = CommonUtils.inflate(R.layout.type_multiple_pic_item);
                    viewHolder.iv_news_pic1 = (ImageView) convertView.findViewById(R.id.iv_news_pic1);
                    viewHolder.iv_news_pic2 = (ImageView) convertView.findViewById(R.id.iv_news_pic2);
                    viewHolder.iv_news_pic3 = (ImageView) convertView.findViewById(R.id.iv_news_pic3);
                    break;
            }
            assert convertView != null;
            viewHolder.tv_news_comment = (TextView) convertView.findViewById(R.id.tv_news_comment);
            viewHolder.tv_news_source = (TextView) convertView.findViewById(R.id.tv_news_source);
            viewHolder.tv_news_time = (TextView) convertView.findViewById(R.id.tv_news_time);
            viewHolder.iv_news_delete = (ImageView) convertView.findViewById(R.id.iv_news_delete);
            viewHolder.tv_news_type = (TextView) convertView.findViewById(R.id.tv_news_type);
            viewHolder.tv_news_title = (TextView) convertView.findViewById(R.id.tv_news_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (itemViewType) {
            case TYPE_NORMAL:
                ImageLoader.getInstance().displayImage(dataList.get(position).getMiddle_image().url_list.get(0).url,viewHolder.iv_news_pic,options);

                break;
            case TYPE_LARGE_PIC:
                ImageLoader.getInstance().displayImage(dataList.get(position).getLarge_image_list().get(0).url,viewHolder.iv_news_pic,options);

                break;
            case TYPE_VIDEO:
                viewHolder.iv_news_pic.setScaleType(ImageView.ScaleType.CENTER);
                ImageLoader.getInstance().displayImage(dataList.get(position).getLarge_image_list().get(0).url,viewHolder.iv_news_pic,options);

                break;
            case TYPE_NO_PIC:
                break;
            case TYPE_MULITY_PIC:
                ImageLoader.getInstance().displayImage(dataList.get(position).getImage_list().get(0).url,viewHolder.iv_news_pic1,options);
                ImageLoader.getInstance().displayImage(dataList.get(position).getImage_list().get(1).url,viewHolder.iv_news_pic2,options);
                ImageLoader.getInstance().displayImage(dataList.get(position).getImage_list().get(2).url,viewHolder.iv_news_pic3,options);
                break;
        }
        viewHolder.tv_news_comment.setText(dataList.get(position).getComment_count()+"");
        viewHolder.tv_news_source.setText(dataList.get(position).getSource());
        viewHolder.tv_news_time.setText(dataList.get(position).getPublish_time()+"");

        viewHolder.tv_news_type.setText(dataList.get(position).getArticle_type()+"");
        viewHolder.tv_news_title.setText(dataList.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView tv_news_title, tv_news_comment, tv_news_source, tv_news_time,
                tv_news_type;
        ImageView iv_news_pic, iv_news_play_movie, iv_news_pic1, iv_news_pic2,
                iv_news_pic3, iv_news_delete;
        VideoView vv_new_movie;
    }
}
