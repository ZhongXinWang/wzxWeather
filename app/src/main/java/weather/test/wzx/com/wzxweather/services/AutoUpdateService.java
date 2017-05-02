package weather.test.wzx.com.wzxweather.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.test.wzx.com.wzxweather.entity.CitySelect;
import weather.test.wzx.com.wzxweather.entity.Weather;
import weather.test.wzx.com.wzxweather.interfaces.HttpCallBackListener;
import weather.test.wzx.com.wzxweather.model.CitySelectLab;
import weather.test.wzx.com.wzxweather.util.HttpConnection;
import weather.test.wzx.com.wzxweather.util.JSONUtil;
import weather.test.wzx.com.wzxweather.util.LogUtil;
import weather.test.wzx.com.wzxweather.util.SharedPreferencesUtil;
import weather.test.wzx.com.wzxweather.util.StaticVariable;


public class AutoUpdateService extends Service {
    private SharedPreferencesUtil share;
    private CitySelectLab mCitySelectLab;
    private  String mCityName = "三元";
    public AutoUpdateService() {
        share = new SharedPreferencesUtil(AutoUpdateService.this);
        mCitySelectLab = new CitySelectLab(AutoUpdateService.this);
    }

    //主要的方法设置再这里
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        loadBg();
        requestDataFromServer();
        //设置定时器
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime()+60*1000;
        Intent i = new Intent(this,AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);
        alarmManager.cancel(pi);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //请求数据
    private void requestDataFromServer() {


        List<CitySelect> citySelect = mCitySelectLab.getAllCitySelects();
        if(citySelect != null) {

            mCityName = citySelect.get(0).getCityName();

        }
        //更新默认数据的天气
        String url = "https://free-api.heweather.com/v5/weather?city="+mCityName+"&key=404f2c3e24764f65baf1067846123098";
        HttpConnection.httpConnHelp(url, new HttpCallBackListener() {
            @Override
            public void onSuccess(InputStream inputStream) {
                try {
                    final StringBuilder stringBuilder =  HttpConnection.inputStreamToString(inputStream);
                    final Weather weather = JSONUtil.handleWeatherResponse(stringBuilder.toString());
                            if(weather != null && "ok".equals(weather.status)){
                                Map<String,String> m = new HashMap<String, String>();
                                m.put(mCityName,stringBuilder.toString());
                                m.put(mCityName+"time",System.currentTimeMillis()+"");
                                share.insertSharePreference(m);
                                //显示数据
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailour(Exception e) {

                LogUtil.d("service","request weather fail");
            }
        });
    }
    private void loadBg(){
        HttpConnection.httpConnHelp(StaticVariable.BGURL, new HttpCallBackListener() {
            @Override
            public void onSuccess(InputStream inputStream) {
                try {
                    final  String url =  HttpConnection.inputStreamToString(inputStream).toString();
                    LogUtil.d("URLS",url);
                    //把地址缓存起来
                    Map<String,String> m = new HashMap<String, String>();
                    m.put("bg",url);
                    share.insertSharePreference(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailour(Exception e) {

                LogUtil.d("BGError",e.getMessage());
            }
        });
    }
}
