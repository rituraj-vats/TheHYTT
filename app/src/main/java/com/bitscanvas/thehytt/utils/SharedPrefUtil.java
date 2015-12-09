package com.bitscanvas.thehytt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * @author vatsritu
 * Created by vatsritu on 20/09/15.
 */
public class SharedPrefUtil {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefrenceEditor;
    private Context context;
    private Gson gson;

    public SharedPrefUtil(Context context){
        this.context=context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefrenceEditor = sharedPreferences.edit();
    }

    public boolean save(String key,Object object){
        gson = new Gson();
        String value = gson.toJson(object);
        prefrenceEditor.putString(key, value);
        return prefrenceEditor.commit();
    }

    public Object get(String key,Class c){
        Object obj = null;
        try {
            String value = sharedPreferences.getString(key, "");
            if(!CommonUtil.isEmpty(value))
            obj = JSONConvertor.jsonToObject(value,c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void delete(String key){
        prefrenceEditor.remove(key);
    }

    public boolean saveSring(String key,String value){
        prefrenceEditor.putString(key, value);
        return prefrenceEditor.commit();
    }

    public String get(String key){
        String value = sharedPreferences.getString(key, "");
        return value;
    }
}
