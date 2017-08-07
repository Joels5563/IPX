package com.ipx.ipx.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

import com.ipx.ipx.R;

/**
 * 测试fragment
 */
public class FragmentActivity extends AppCompatActivity {
    private View mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        //得到最外面系统自动布局FrameLayout
//        mainLayout  = findViewById(android.R.id.content);
        //得到自定义的布局
        mainLayout = findViewById(R.id.content);
        Log.d("TAG", "the parent of mainLayout is " + mainLayout);
        ViewParent viewParent = mainLayout.getParent();
        Log.d("TAG", "the parent of mainLayout is " + viewParent);

//        Display display = getWindowManager().getDefaultDisplay();
//        if (display.getWidth() > display.getHeight()) {
//            Fragment1 fragment1 = new Fragment1();
//            getFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment1).commit();
//        } else {
//            Fragment2 fragment2 = new Fragment2();
//            getFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment2).commit();
//        }
    }
}
