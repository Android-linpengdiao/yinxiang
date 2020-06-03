package com.yinxiang.manager;

import android.content.Context;
import android.content.res.AssetManager;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.MyApplication;
import com.yinxiang.model.CityData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CityManager {

    private static CityManager mInstance;
    private Context context;

    public synchronized static CityManager getInstance() {
        if (mInstance == null) {
            synchronized (CityManager.class) {
                if (mInstance == null) {
                    mInstance = new CityManager();
                }
            }
        }
        return mInstance;
    }

    public CityManager() {
        this.context = MyApplication.getInstance();
    }

    public List<CityData> getAreaFirst() {
        List<CityData> list = new ArrayList<>();
        String area = getJson("area.json");
        try {
            JSONObject jsonObject = new JSONObject(area);
            for (int i = 1; i <= 65; i++) {
                JSONObject object = jsonObject.optJSONObject(String.valueOf(i));
                if (!CommonUtil.isBlank(object)) {
                    list.add(CityData.parseJsonToBean(object.toString()));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getJson(String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
