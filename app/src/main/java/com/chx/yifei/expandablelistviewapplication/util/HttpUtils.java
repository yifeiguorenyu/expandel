package com.chx.yifei.expandablelistviewapplication.util;

import android.util.Log;

import com.chx.yifei.expandablelistviewapplication.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    public static String doGet(String urlStr){
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(urlStr);
         conn = (HttpURLConnection) url.openConnection();
           conn.setReadTimeout(5000);
           conn.setConnectTimeout(5000);
           conn.setRequestMethod("GET");
           if(conn.getResponseCode()==200){
               is = conn.getInputStream();
              baos = new ByteArrayOutputStream();
               int len =-1;
               byte[] buf = new byte[512];
               while ((len = is.read(buf))!=-1){
                   baos.write(buf,0,len);
                   baos.flush();
               }
               Log.d(MainActivity.Tag, "doGet: "+baos.toString());
               return  baos.toString();
           }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null)
            {
                conn.disconnect();
            }
        }
        return null;
    }
}
