package com.example.helloworld.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.homework.model.Message;
import com.example.helloworld.homework.model.PullParser;

import java.util.List;


/**
 * 大作业:实现一个抖音消息页面,所需资源已放在res下面
 */
public class Exercises3 extends AppCompatActivity {
    private static final String TAG = "Exercises3";
    private List<Message> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        ListView list = findViewById(R.id.list_item);
        ImageView imFans = findViewById(R.id.imFans);
        ImageView imGood = findViewById(R.id.imGood);
        ImageView imMy = findViewById(R.id.imMy);
        ImageView imCom = findViewById(R.id.imCom);
        try {
            data = PullParser.getMessage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: " + data);
        Log.d(TAG, "onCreate: " + data.size());
        //ArrayAdapter<Message> adapter = new ArrayAdapter<>(this, R.layout.im_list_item, data);
        list.setAdapter(new ListViewAdapter(this, data));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Exercises3.this, JumpActivity.class);
                intent.putExtra("position", position);
//                Intent intent = null;
//                try {
//                    intent = new Intent(ListViewActivity.this, Class.forName("com.ss.android.ugc.chapter1.MainActivity"));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                startActivity(intent);
            }
        });
        imCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Exercises3.this, Jump2Activity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
            }
        });
        imMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Exercises3.this, Jump2Activity.class);
                intent.putExtra("position", 2);
                startActivity(intent);
            }
        });
        imGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Exercises3.this, Jump2Activity.class);
                intent.putExtra("position", 3);
                startActivity(intent);
            }
        });
        imFans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Exercises3.this, Jump2Activity.class);
                intent.putExtra("position", 4);
                startActivity(intent);
            }
        });
    }

    public static class ListViewAdapter extends BaseAdapter {
        private List<Message> data;

        private Context mContext;

        public ListViewAdapter(Context context, List<Message> data) {
            mContext = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.im_list_item, null);
                Log.d("ListViewAdapter",
                        "getView() called with: position = [" + position + "], convertView = [" + convertView + "], parent = [" +
                                parent + "]");
            } else {
                //!=null
                view = convertView;
            }
            TextView tvTime = view.findViewById(R.id.tv_time);
            tvTime.setText(data.get(position).getTime());
            TextView tvDes = view.findViewById(R.id.tv_description);
            tvDes.setText(data.get(position).getDescription());
            TextView tvTitle = view.findViewById(R.id.tv_title);
            tvTitle.setText(data.get(position).getTitle());
            // tvName = view.findViewById(R.id.iv_avatar_header);
            //tvName.setImageResource(data.get(position).getIcon());
            return view;
        }
    }
}
