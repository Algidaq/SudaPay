package com.example.sudapay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Window;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;

public class Utils {

    public static String normalConnection(String url, Context context , ArrayList<NameValuePair> posts)
    {
        String response="";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse httpResponse;
        HttpEntity entity;

        try{

            HttpPost post =new HttpPost(url);
            UrlEncodedFormEntity encode = new UrlEncodedFormEntity(posts);
            post.setEntity(encode);
            httpResponse = client.execute(post);
            entity = httpResponse.getEntity();
            response = EntityUtils.toString(entity);
            System.out.println("response"+response);
            EntityUtils.consume(entity);
            client.close();
            ((CloseableHttpResponse) httpResponse).close();

        }catch(Exception e)
        {
            e.printStackTrace();
//            Toast.makeText(context, " Check You network Connection ", Toast.LENGTH_SHORT).show();
        }


        return response;
    }



    public static Bitmap normalConnection(String url)
    {
        Bitmap response=null;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse httpResponse;
        HttpEntity entity;

        try{

            HttpGet get = new HttpGet(url);
            httpResponse = client.execute(get);
            entity = httpResponse.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);
            response = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            System.out.println("response"+response);
            EntityUtils.consume(entity);
            client.close();
            ((CloseableHttpResponse) httpResponse).close();

        }catch(Exception e)
        {
            e.printStackTrace();
//            Toast.makeText(context, " Check You network Connection ", Toast.LENGTH_SHORT).show();
        }


        return response;
    }

    public static boolean checkNetwork(Context context)
    {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info= manager.getAllNetworkInfo();
        Network net = null;




        for(int i=0;i<info.length;i++)
            if(info[i].getState() == NetworkInfo.State.CONNECTED || info[i].isConnected())
            {
                return true;
            }


        return false;

    }

    public static void ChanageBarColor(Window window)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(window.getContext().getResources().getColor(R.color.darkPrimaryColor));
        }
    }
}
