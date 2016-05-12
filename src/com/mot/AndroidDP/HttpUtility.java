package com.mot.AndroidDP;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bkmr38 on 5/10/2016.
 */
public class HttpUtility {

    private static String urlBase="http://yxzhm.com/api/DP/";
    public static boolean ValidateUser(String name,String password){
        String url=urlBase+"Login";
        String para=String.format("loginname=%s&password=%s",name,password);

        boolean result=Boolean.valueOf(PostRequest(url,para));
        return result;
    }

    private static String GetRequest(String url, String para) {

        try {
            URL u = new URL(url + "?" + para);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            String context=getContent((InputStream) conn.getContent());
            return context;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String PostRequest(String url, String para) {
        try {
            URL u = new URL(url);
            byte[] data = para.getBytes("UTF-8");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("UserAgent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
            conn.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length));
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.getOutputStream().write(data);
            int code = conn.getResponseCode();
            String content = getContent(conn.getInputStream());
            return content;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
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
                    contentBuffer.append(line + "\r\n");
                }
            } while (line != null);
        } catch (Exception ex) {
            throw ex;
        }
        return contentBuffer.toString();
    }
}
