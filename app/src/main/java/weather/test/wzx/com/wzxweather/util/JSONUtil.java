package weather.test.wzx.com.wzxweather.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import weather.test.wzx.com.wzxweather.entity.Citys;

/**
 * Created by 王钟鑫 on 17/4/17.
 */

public class JSONUtil {


    //获取城市
    public static StringBuilder jsonHelp(String json) throws JSONException {

        StringBuilder stringCity = new StringBuilder();

        JSONArray jsonArray = new JSONArray(json);
        for(int i = 0 ; i < jsonArray.length();i++){

            JSONObject object = jsonArray.getJSONObject(i);
            stringCity.append(object.get("cityZh")+",");
        }

        Log.d("JsonObject",json.toString());

        return stringCity;
    }

    public static List<Citys> JsonToCity(String json) throws JSONException {

        List<Citys> list = new ArrayList<>();
       String stringBuilder =  jsonHelp(json).toString();
       stringBuilder = stringBuilder.substring(0,stringBuilder.length()-2);


        String[] str = stringBuilder.split(",");

        for(int i = 0 ; i < str.length;i++){


            Citys citys = new Citys();
            citys.setCityName(str[i]);
            list.add(citys);

        }

        return list.size()>0?list:null;

    }

}
