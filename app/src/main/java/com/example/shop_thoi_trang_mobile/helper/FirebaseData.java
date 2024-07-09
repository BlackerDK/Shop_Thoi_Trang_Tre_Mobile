package com.example.shop_thoi_trang_mobile.helper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class FirebaseData {
    public static void setData(Context context, String data){
        try {
            FileOutputStream fos = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getData(Context context){
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("data.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static void setName(Context context, String data){
        try {
            FileOutputStream fos = context.openFileOutput("name.txt", Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getName(Context context){
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("name.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
