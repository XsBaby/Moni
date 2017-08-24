package com.xushuai.moni.utils;

import com.google.gson.Gson;
import com.xushuai.moni.bean.MyBean;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * date:2017/8/20
 * author:徐帅(acer)
 * funcation:OKHttp网络请求的工具类
 */
public class HttpUtil {

    //网络请求OKHttp
    public static <T> void sendOkHttpRequest(Class<T> clazz, final GetData getData, String url) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request build = requestBuilder.build();
        Call call = client.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String str = response.body().string();
                MyBean myBean = gson.fromJson(str, MyBean.class);
                List<MyBean.DataBean> data = myBean.getData();
                getData.getData(data);
            }
        });
    }
}