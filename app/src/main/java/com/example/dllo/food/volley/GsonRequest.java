package com.example.dllo.food.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by XiaoyuLu on 16/10/27.
 *
 * 自定义的请求类, 返回值是泛型, 可以直接返回自己的的实体数据类
 */
public class GsonRequest<T> extends Request<T>{

    private final Response.Listener<T> tListener;
    private Gson gson;
    private Class<T> tClass;

    /* 必须写 的构造方法 自己添加了两个参数*/
    public GsonRequest(int method, Class<T> tClass, // Class<T>这一参数需要自己往这里添加
                       String url, Response.Listener<T> tListener, // Response.Listener<T> 也是自己手动添加的参数
                       Response.ErrorListener listener) {

        super(method, url, listener);

        // 对成功的监听进行赋值
        this.tListener = tListener;

        this.gson = new Gson();
        this.tClass = tClass;

    }

    /* 自定义的又一个 构造方法 */
    public GsonRequest(Class<T> tClass, String url,
                       Response.Listener<T> tListener,
                       Response.ErrorListener errorListener){

        this(Method.GET, tClass, url, tListener, errorListener);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed; // 请求成功的字符串
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            parsed = new String(response.data);
        }

        // 解析
        T t = gson.fromJson(parsed, tClass);
        return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        tListener.onResponse(response);
    }
}
