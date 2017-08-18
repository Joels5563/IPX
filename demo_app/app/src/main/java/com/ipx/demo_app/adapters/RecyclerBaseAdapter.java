package com.ipx.demo_app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipx.demo_app.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView中Adapter的基类<br/>
 * <p>
 * D--数据集中的类型
 * V--ViewHolder类型
 */
public abstract class RecyclerBaseAdapter<D, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    /**
     * 数据集
     */
    protected final List<D> dataSet = new ArrayList<>();

    private OnItemClickListener<D> itemClickListener;

    /**
     * 加载布局文件
     *
     * @param viewGroup 父级视图容器
     * @param layoutId  布局文件id<br/>
     *                  是否将root附加到布局文件的根视图上 <br/>
     *                  inflate()会返回一个View对象<br/>
     *                  如果第三个参数attachToRoot为true，就将这个viewGroup作为根对象返回<br/>
     *                  否则仅仅将这个viewGroup对象的LayoutParams属性附加到layoutId对象的根布局对象<br/>
     *                  也就是布局文件layoutId的最外层的View上，比如是一个LinearLayout或者其它的Layout对象
     * @return view
     */
    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

    /**
     * 设置item点击监听事件
     *
     * @param itemClickListener 点击监听事件
     */
    public void setOnItemClickListener(OnItemClickListener<D> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public V onCreateViewHolder(ViewGroup viewGroup, int position) {
        return null;
    }

    /**
     * 绑定数据,主要分为两步,绑定数据与设置每项的点击事件处理
     *
     * @param viewHolder VH
     * @param position   索引
     */
    @Override
    public void onBindViewHolder(V viewHolder, int position) {
        final D item = getItem(position);
        bindDataToItemView(viewHolder, item);
        setUpItemViewClickListener(viewHolder, item);
    }

    /**
     * item点击事件
     *
     * @param viewHolder VH
     * @param item       item
     */
    private void setUpItemViewClickListener(V viewHolder, final D item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(item);
                }
            }
        });
    }

    /**
     * 将数据绑定到ItemView上,子类实现
     *
     * @param viewHolder VH
     * @param item       item
     */
    protected abstract void bindDataToItemView(V viewHolder, D item);

    /**
     * 获取item的数量
     *
     * @return item的数量
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * 根据索引获取列表中的对象
     *
     * @param position 索引
     * @return 索引位置的对象
     */
    protected D getItem(int position) {
        return dataSet.get(position);
    }

    /**
     * 添加数据集
     *
     * @param items 数据集
     */
    public void addItems(List<D> items) {
        //去除已经存在的数据
        items.removeAll(dataSet);
        //更新数据
        dataSet.addAll(items);
        //通知RecyclerView刷新页面
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }

}
