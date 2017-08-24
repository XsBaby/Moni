package com.xushuai.moni;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xushuai.moni.adapter.MyAdapter;
import com.xushuai.moni.bean.MyBean;
import com.xushuai.moni.utils.GetData;
import com.xushuai.moni.utils.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetData {
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                MyAdapter adapter = new MyAdapter(MainActivity.this, list);
                recycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recycleView.setAdapter(adapter);
                recycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        page = 1;
                        list.clear();
                        initData();
                        recycleView.refreshComplete();
                    }

                    @Override
                    public void onLoadMore() {
                        page++;
                        initData();
                        Toast.makeText(MainActivity.this,"加载更多",Toast.LENGTH_LONG).show();
                        recycleView.loadMoreComplete();
                    }
                });

                adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        Toast.makeText(MainActivity.this, list.get(position).getIntroduction(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClickListener(int position) {
                        Toast.makeText(MainActivity.this, list.get(position).getIntroduction(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };
    private XRecyclerView recycleView;
    private List<MyBean.DataBean> list = new ArrayList<>();
    String url = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page=";
    private int page = 1;
    private int refreshTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //查找控件
        initView();

        //添加数据
        initData();
    }

    private void initView() {
        recycleView = (XRecyclerView) findViewById(R.id.recycleView);
    }

    private void initData() {
        HttpUtil.sendOkHttpRequest(MyBean.class, this, url + page);
    }

    @Override
    public void getData(Object o) {
        list = (List<MyBean.DataBean>) o;
        Log.d("zzz", list.toString());
        mHandler.sendEmptyMessage(1);
    }
}