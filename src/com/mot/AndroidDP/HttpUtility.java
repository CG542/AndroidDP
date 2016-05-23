package com.mot.AndroidDP;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkmr38 on 5/10/2016.
 */
public class HttpUtility {

    private static String urlBase = "http://yxzhm.com/api/DP/";

    public static boolean ValidateUser(String name, String password) {
        String url = urlBase + "Login";
        String para = String.format("loginname=%s&password=%s", name, password);

        boolean result = Boolean.valueOf(PostRequest(url, para));
        return result;
    }

    public static List<String> GetAllDPNames(){
        String url=urlBase+"GetAllDPNames";
        String para=String.format("loginname=%s&password=%s", GlobalPara.UserName, GlobalPara.PSW);

        List<String> list=new ArrayList<>();

        String result = GetRequest(url, para);
        try {
            JSONObject js=new JSONObject(result);
            JSONArray jsArray=js.optJSONArray("DP");
            for (int i=0;i<jsArray.length();i++){
                JSONObject temp = (JSONObject)jsArray.get(i);
                list.add(temp.optString("name"));
            }
           System.out.print(js.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> GetAllSettings(){
        String url=urlBase+"GetAllSettings";
        String para=String.format("loginname=%s&password=%s", GlobalPara.UserName, GlobalPara.PSW);

        List<String> list=new ArrayList<>();

        String result = GetRequest(url, para);
        try {
            JSONObject js=new JSONObject(result);
            JSONArray jsArray=js.optJSONArray("settingEntity");
            for (int i=0;i<jsArray.length();i++){
                JSONObject temp = (JSONObject)jsArray.get(i);
                list.add(temp.optString("profilename"));
            }
            System.out.print(js.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void SetDPConfig(String dpName,String profileName){
        String url=urlBase+"SetDPConfig";
        String para = String.format("loginname=%s&password=%s&dpname=%s&profilename=%s"
                , GlobalPara.UserName, GlobalPara.PSW,dpName,profileName);
        PostRequest(url, para);
    }

    public static void UploadSetting(String profileName,String setting){
        String url=urlBase+"UploadSetting";
        String para = String.format("loginname=%s&password=%s&profilename=%s&setting=%s"
                , GlobalPara.UserName, GlobalPara.PSW,profileName,setting);
        PostRequest(url, para);
    }

    static String resultStr = "";

    private static String GetRequest(String url, String para) {

        Thread webThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url + "?" + para);
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    resultStr = getContent((InputStream) conn.getContent());


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        webThread.start();
        try {
            webThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultStr;
    }

    private static String PostRequest(String url, String para) {

        Thread webThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    byte[] data = para.getBytes("UTF-8");
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    //conn.setRequestProperty("Accept", "*/*");
                    //conn.setRequestProperty("UserAgent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length));
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.getOutputStream().write(data);
                    int code = conn.getResponseCode();
                    resultStr = getContent(conn.getInputStream());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            webThread.start();
            webThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultStr;


    }

    private static String getContent(InputStream stream) throws IOException {
        InputStreamReader in = new InputStreamReader(stream);
        BufferedReader buff = new BufferedReader(in);

        StringBuilder contentBuffer = new StringBuilder();
        String line = null;
        try {
            do {
                line = buff.readLine();
                if (line != null) {
                    contentBuffer.append(line);
                }
            } while (line != null);
        } catch (Exception ex) {
            throw ex;
        }
        return contentBuffer.toString();
    }
}
