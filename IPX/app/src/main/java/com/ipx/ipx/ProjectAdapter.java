package com.ipx.ipx;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Adapter,绑定项目数据到listView中
 */
public class ProjectAdapter extends BaseAdapter {

    //上下文对象
    private Context context;
    //传入接口调用得到的项目列表数据
    private JSONArray jsonArray;
    //bitmap哈希,缓存项目图片,上下滚动不会再次加载,key为项目id,value为图片资源
    private Map<Long, Bitmap> bitmapHashMap = new HashMap<>();

    /**
     * 获取图片数据的handler,等待数据传输完成
     */
    private Handler imageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    /*构造函数*/
    public ProjectAdapter(Context context, JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ProjectViewHolder holder;
        //观察convertView随ListView滚动情况
        Log.v("ProjectAdapter", "getView " + position + " " + convertView);
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.project, null);
            holder = new ProjectViewHolder();
            /*得到各个控件的对象*/
            holder.image = convertView.findViewById(R.id.project_image);
            holder.title = convertView.findViewById(R.id.project_name);
            holder.type = convertView.findViewById(R.id.project_type);
            holder.country = convertView.findViewById(R.id.project_country);
            holder.firstRegion = convertView.findViewById(R.id.project_first_region);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            //取出ViewHolder对象
            holder = (ProjectViewHolder) convertView.getTag();
        }
        /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final Map<String, Object> projectInfo = getDate().get(position);
        holder.title.setText(projectInfo.get("title").toString());
        holder.type.setText(ProjectType.getNameByCode(
                Integer.valueOf(projectInfo.get("projectType").toString())));
        holder.country.setText(projectInfo.get("countryName").toString());
        holder.firstRegion.setText(projectInfo.get("regionFirstName").toString());
        //设置图片信息
        final Long projectId = Long.parseLong(projectInfo.get("projectId").toString());
        final String projectImageURL =
                null == projectInfo.get("frontImage") ? "" :
                        projectInfo.get("frontImage").toString();
        Bitmap bitmap = bitmapHashMap.get(projectId);
        if (bitmap != null) {
            //已经加载了图片，直接显示
            Bitmap projectImage = Bitmap.createScaledBitmap(bitmap,
                    80,
                    80,
                    false);
            holder.image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.image.setImageBitmap(projectImage);
            holder.image.setImageBitmap(bitmap);
        } else if (null != projectImageURL && !"".equals(projectImageURL.trim())) {
            // 从网络加载
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    //没有加载图片，从网络加载
                    Bitmap bitmap = BitmapUtil.getHttpBitmap(projectImageURL);
                    bitmapHashMap.put(projectId, bitmap);
                    imageHandler.sendEmptyMessage(0);
                }
            }.start();
        }
        //由于在ListView的item添加了点击事件,就不需要再设置图片点击事件了
//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //点击该图片,跳转到详情页面
//                Intent intent = new Intent();
//                //第一个参数指的就是要跳转的那个Activity
//                //第二个指的是跳到的那个Activity
//                intent.setClass(context, DetailActivity.class);
//                //传递返回的projectId到跳转后的详情页面
//                intent.putExtra("projectId", projectId);
//                //主要是获取通讯录的内容
//                context.startActivity(intent); // 启动Activity
//            }
//        });

        return convertView;
    }

    /*添加一个得到数据的方法，方便使用*/
    @SuppressWarnings("unchecked")
    private ArrayList<Map<String, Object>> getDate() {
        ArrayList<Map<String, Object>> listItem = new ArrayList<>();
        for (int i = 0, length = jsonArray.size(); i < length; i++) {
            JSONObject projectListInfo = jsonArray.getJSONObject(i);
            listItem.add(JSON.parseObject(projectListInfo.toJSONString(), Map.class));
        }
        return listItem;
    }


    @Override
    public int getCount() {
        //返回数组的长度
        return getDate().size();
    }

    @Override
    public Object getItem(int i) {
        return getDate().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}

/*存放控件*/
class ProjectViewHolder {
    public ImageView image;
    public TextView title;
    public TextView type;
    public TextView country;
    public TextView firstRegion;
}