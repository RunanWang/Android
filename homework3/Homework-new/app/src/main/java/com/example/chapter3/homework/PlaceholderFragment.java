package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class PlaceholderFragment extends Fragment {

    private String[] listData = {"friendA", "friendB", "friendC", "friendD", "friendE", "friendF"};
    private ListView friendsList;
    private LottieAnimationView waitView;
    private MyListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View root = inflater.inflate(R.layout.fragment_placeholder, container, false);
        friendsList = root.findViewById(R.id.friendsList);
        waitView = root.findViewById(R.id.animation_view);
        adapter = new MyListAdapter();
        //friendsList.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                friendsList.setAdapter(adapter);
                ObjectAnimator alphaAnimatorWait = ObjectAnimator.ofFloat(waitView,
                        "alpha", 1.0f, 0.0f
                );
                alphaAnimatorWait.setDuration(400);

                ObjectAnimator alphaAnimatorList = ObjectAnimator.ofFloat(friendsList,
                        "alpha", 0.0f, 1.0f
                );
                alphaAnimatorList.setDuration(800);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(alphaAnimatorList, alphaAnimatorWait);
                animatorSet.start();
            }
        }, 3000);
    }

    public class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listData.length;
        }

        @Override
        public Object getItem(int position) {
            return listData[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent) {

            if (converView == null) {
                converView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1
                        , parent, false);
            }
//            Con c= (Con) getItem(position);
            TextView textView = (TextView) converView.findViewById(android.R.id.text1);
            textView.setText(listData[position]);
            //Log.d(TAG, "getView() called with: position = [" + position + "], " +
            //"converView = [" + listData[position] + "], parent = [" + parent + "]");
            return converView;
        }
    }
}
