package weather.test.wzx.com.wzxweather.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王钟鑫 on 17/4/17.
 */

public class JSONUtil {


    //获取城市
    public static void JsonHelp(String json) throws JSONException {

        StringBuilder stringCity = new StringBuilder();

        JSONArray jsonArray = new JSONArray(json);
        for(int i = 0 ; i < jsonArray.length();i++){

            JSONObject object = jsonArray.getJSONObject(i);
            stringCity.append(object.get("cityZh"));
        }

        Log.d("JsonObject",stringCity.toString());

    }

}
