package com.ipx.ipx.test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ipx.ipx.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 测试用,学习ListView
 */
public class ListViewActivity extends AppCompatActivity {
    private ListView mListView;
    private ListView mListView1;
    private ListView mListView2;
    private TextView mTextView;

    //定义一个String数组用来显示ListView的内容
    private final static String[] LIST_VIEW_VALUES = new String[]{
            "first", "second", "third", "fourth", "fifth"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mTextView = (TextView) findViewById(R.id.textView);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<>(this,
                //普通的列表
                //android.R.layout.simple_list_item_1,
                //带选择的样式,选择模式还是需要setChoiceMode
                //android.R.layout.simple_list_item_checked,
                //带CheckBox,选择模式还是需要setChoiceMode
                //android.R.layout.simple_list_item_multiple_choice,
                //带RadioButton,选择模式还是需要setChoiceMode
                android.R.layout.simple_list_item_single_choice,
                LIST_VIEW_VALUES));
        //设置多选模式
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //设置单选模式
//        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //处理ListView的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //点击后显示点击的内容
                String listViewValue = "点击了" + (arg2 + 1) + "行,内容为:" + ((TextView) arg1).getText();
                mTextView.setText(listViewValue);

                //多选的话,从arg0获取状态
                ((ListView) arg0).getCheckedItemPositions();
            }
        });


        mListView1 = (ListView) findViewById(R.id.listView1);
        /*定义一个动态数组*/
        List<HashMap<String, Object>> listItem = new ArrayList<>();
        /*在数组中存放数据*/
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemImage", android.R.drawable.sym_def_app_icon);//加入图片
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemText", "这是第" + i + "行");
            listItem.add(map);
        }
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listItem,//需要绑定的数据
                R.layout.item,//每一行的布局
                //动态数组中的数据源的键对应到定义布局的View中
                new String[]{"ItemImage", "ItemTitle", "ItemText"},
                new int[]{
                        R.id.ItemImage, R.id.ItemTitle, R.id.ItemText
                }
        );

        mListView1.setAdapter(mSimpleAdapter);//为ListView绑定适配器

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                mTextView.setText("你点击了第" + arg2 + "行");//设置标题栏显示点击的行
            }
        });


        mListView2 = (ListView) findViewById(R.id.listView2);
        MyAdapter adapter = new MyAdapter(this);
        mListView2.setAdapter(adapter);
        /*为ListView添加点击事件*/
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.v("MyListViewBase", "你点击了ListView条目" + arg2);//在LogCat中输出信息

            }
        });
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel://15216448315"));
        startActivity(intent);
    }
}


/**
 * 新建一个类继承BaseAdapter，实现视图与数据的绑定
 */
class MyAdapter extends BaseAdapter {
    //得到一个LayoutInfalter对象用来导入布局
    private LayoutInflater mInflater;

    /*构造函数*/
    public MyAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
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

    /*书中详细解释该方法*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        Log.v("MyListViewBase", "getView " + position + " " + convertView);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item1,
                    null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.title = convertView.findViewById(R.id.ItemTitle);
            holder.text = convertView.findViewById(R.id.ItemText);
            holder.bt = convertView.findViewById(R.id.ItemButton);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.title.setText(getDate().get(position).get("ItemTitle").toString());
        holder.text.setText(getDate().get(position).get("ItemText").toString());

            /*为Button添加点击事件*/
        holder.bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("MyListViewBase", "你点击了按钮" + getDate().get(position));                                //打印Button的点击信息

            }
        });

        return convertView;
    }

    /*添加一个得到数据的方法，方便使用*/
    private ArrayList<HashMap<String, Object>> getDate() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        /*为动态数组添加数据*/
        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("key", i);
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemText", "这是第" + i + "行");
            listItem.add(map);
        }
        return listItem;
    }

}

/*存放控件*/
class ViewHolder {
    public TextView title;
    public TextView text;
    public Button bt;
}