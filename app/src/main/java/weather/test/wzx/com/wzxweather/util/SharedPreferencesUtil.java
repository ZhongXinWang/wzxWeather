package weather.test.wzx.com.wzxweather.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
/**
 * Created by 王钟鑫 on 17/4/29.
 *
 *
 * 操作文件的类
 *
 */

public class SharedPreferencesUtil {

    private Context mContext;

    public SharedPreferencesUtil(Context context){

        this.mContext = context;

    }
    //添加数据的缓存
    public  boolean insertSharePreference(Map<String,String> map){


        try {

            SharedPreferences.Editor edit = mContext.getSharedPreferences(StaticVariable.WEATHERDATA, Context.MODE_PRIVATE).edit();


          for(String key:map.keySet()){

              edit.putString(key,map.get(key));

              LogUtil.d("map",key+"-----"+map.get(key));

          }
            edit.commit();

            return true;

        }catch (Exception e){

            LogUtil.d("insertSharePreference",e.getMessage());

            return false;

        }

    }
    //读取文件里的值

    public String  readSharePreference(String key){


        SharedPreferences sharePreface = mContext.getSharedPreferences(StaticVariable.WEATHERDATA,Context.MODE_PRIVATE);

        String str = sharePreface.getString(key,"");

        return str;

    }

}
