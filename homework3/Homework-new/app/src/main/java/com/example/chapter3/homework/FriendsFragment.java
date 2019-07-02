package com.example.chapter3.homework;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {
    private static final String TAG = "FriendsFragment";
    private String[] listData = {"friendA", "friendB", "friendC", "friendD", "friendE", "friendF"};

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_friends, container, false);
        ListView friendsList = root.findViewById(R.id.friendsList);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                root, android.R.layout.simple_list_item_1, data);
//        friendsList.setAdapter(adapter);
        MyListAdapter adapter = new MyListAdapter();
        friendsList.setAdapter(adapter);

        return root;
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
            Log.d(TAG, "getView() called with: position = [" + position + "], " +
                    "converView = [" + listData[position] + "], parent = [" + parent + "]");
            return converView;
        }
    }
}
