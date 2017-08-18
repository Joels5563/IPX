package com.ipx.demo_app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipx.demo_app.R;
import com.ipx.demo_app.beans.MenuItem;

/**
 * 菜单adapter
 */
public class MenuAdapter extends RecyclerBaseAdapter<MenuItem, MenuAdapter.MenuViewHolder> {

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new MenuViewHolder(inflateItemView(viewGroup,
                R.layout.menu_item));
    }

    @Override
    protected void bindDataToItemView(MenuViewHolder viewHolder, MenuItem item) {
        viewHolder.nameTextView.setText(item.getText());
        viewHolder.userImageView.setImageResource(item.getIconResId());
        viewHolder.itemView.setTag(item);
    }


    static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView userImageView;
        TextView nameTextView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.menu_icon_imageview);
            nameTextView = itemView.findViewById(R.id.menu_text_tv);
        }
    }
}
