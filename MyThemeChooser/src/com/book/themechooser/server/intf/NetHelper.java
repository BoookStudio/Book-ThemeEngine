/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.book.themechooser.server.intf;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import util.ThemeUtil;

/**
 * @author chenliang
 */
public class NetHelper {
    private static boolean DBG = true;
    private static String TAG = "NetHelper";

    public static InputStream getNetInputStream(String urlStr) {
        HttpURLConnection conn = null;
        try {
            if (DBG) Log.d(TAG, "urlStr=" + urlStr);
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(4000);
            conn.setReadTimeout(4000);
            conn.setRequestProperty("User-Agent", ThemeUtil.userAgent);
            conn.connect();
            if (conn.getResponseCode() == 200) {
                return conn.getInputStream();
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            conn.disconnect();
            conn = null;
        } catch (IOException e) {
            e.printStackTrace();
            conn.disconnect();
            conn = null;
        }
        return null;
    }

    public static String getNetString(String urlStr) {

        BufferedReader br = null;
        try {
            InputStream is = NetHelper.getNetInputStream(urlStr);
            if(is == null){
                return null;
            }
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder cnt = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null && !line.trim().equals("")) {
                cnt.append(line);
            }
            is.close();
            br.close();
            return cnt.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
