package com.ipx.ipx.test;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/8/8.
 */

public class TestFragmentPagerAdapter extends FragmentPagerAdapter {

    public TestFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private Handler handler = new Handler(){
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
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

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 0;
    }
}
