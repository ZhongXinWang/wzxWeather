package weather.test.wzx.com.wzxweather.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.entity.CitySelect;
import weather.test.wzx.com.wzxweather.entity.Forecast;
import weather.test.wzx.com.wzxweather.entity.Weather;
import weather.test.wzx.com.wzxweather.interfaces.HttpCallBackListener;
import weather.test.wzx.com.wzxweather.model.CityLab;
import weather.test.wzx.com.wzxweather.model.CitySelectLab;
import weather.test.wzx.com.wzxweather.services.AutoUpdateService;
import weather.test.wzx.com.wzxweather.util.HttpConnection;
import weather.test.wzx.com.wzxweather.util.JSONUtil;
import weather.test.wzx.com.wzxweather.util.LogUtil;
import weather.test.wzx.com.wzxweather.util.SharedPreferencesUtil;
import weather.test.wzx.com.wzxweather.util.StaticVariable;

import static weather.test.wzx.com.wzxweather.R.id.bg;
import static weather.test.wzx.com.wzxweather.R.id.info_weather;
import static weather.test.wzx.com.wzxweather.R.id.now_info_weather;
import static weather.test.wzx.com.wzxweather.R.id.now_quality;
import static weather.test.wzx.com.wzxweather.R.id.now_temp;
import static weather.test.wzx.com.wzxweather.util.JSONUtil.handleWeatherResponse;
/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private View mView;
    private CityLab mCityLab;
    private static String CITYNAME="cityName";
    //查询城市的名字
    private String mCityName = "";
    private CitySelectLab mCitySelect;
    //缓存数据的操作
    private SharedPreferencesUtil share;
    //从缓存数据里取出来的值
    private String weatherData;

    //head控件
    private ImageView mHeadBg;
    private TextView mHeadTemp;
    private TextView mHeadCityName;
    private TextView mHeadInfo;
    private TextView mHeadWindDir;
    private TextView mHeadWindStr;
    private TextView mHeadRelativeSize;
    private TextView mHeadBodySize;

    //未来三天的天气信息
    private LinearLayout mForecast_container;
    //aqi
    private LinearLayout line;
    private TextView mAQIQuality;
    private TextView mAQI;
    private TextView mPM;
    //suggest
    private TextView mSuggest_Car,mSuggest_Sport,mSuggest_Comfort,mSuggest_Flu;
    private SwipeRefreshLayout mRefreshLayout;

    public WeatherFragment() {
        // Required empty public constructor
    }
    //跳转
    public static WeatherFragment newInstance(String str) {
        Bundle args = new Bundle();
        args.putString(CITYNAME,str);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("Weatheractivity","TExt1");
        mCityLab = new CityLab(getActivity());
        mCitySelect = new CitySelectLab(getActivity());
        share = new SharedPreferencesUtil(getActivity());
        mCityName = (String) getArguments().get(CITYNAME);
        //设置fragement的菜单可用
        setHasOptionsMenu(true);
        //mCityName  如果为空,表示不是从添加界面过来的,就需要从数据库去查找默认的城市
        if(mCityName == null){
            //启动位置服务
            List<CitySelect> citySelect = mCitySelect.getAllCitySelects();
            if(citySelect == null) {
                //如果没有选择项就默认使用三元这个地区
                mCityName = "诏安";
            }else{
                //获取默认项
                mCityName = citySelect.get(0).getCityName();
            }
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtil.d("Weatheractivity","TExt2");
        mView = inflater.inflate(R.layout.fragment_weather, container, false);

        return  mView;
    }
    //初始化参数
    private void init() {
        //初始化头布局
        initHead();
        //初始化主体的布局
        initBody();
        //初始化刷新
        initRefresh();
        //初始化背景图片
        initBg();
        //启动服务
        initService();
    }


    private void initService() {

        if(!share.readSharePreference(StaticVariable.IS_AUTO_UPDATE).equals("") && share.readSharePreference(StaticVariable.IS_AUTO_UPDATE).equals("1")){

            Intent intent = new Intent(getActivity(), AutoUpdateService.class);
            getActivity().startService(intent);
        }

    }
    private void initBg() {
        mHeadBg = (ImageView) mView.findViewById(bg);
        String bgURL = share.readSharePreference("bg");
        LogUtil.d("bgURL",bgURL);
        String bgTime = share.readSharePreference("bgTime");
        long time = 0;
        if(!bgTime.equals("")){

            time =  Long.parseLong(bgTime);

        }else{
            time = System.currentTimeMillis();
        }
        long currentTime = System.currentTimeMillis();
        if(currentTime - time < StaticVariable.DISTANT) {
            loadBg();
        }else{

            Glide.with(getActivity()).load(bgURL).asBitmap().centerCrop().into(mHeadBg);

        }

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
                    m.put("bgTime",System.currentTimeMillis()+"");
                    share.insertSharePreference(m);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Glide.with(getActivity()).load(url).asBitmap().centerCrop().into(mHeadBg);

                        }
                    });
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
    private void initRefresh() {
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                long time = 0;
                if(!share.readSharePreference(mCityName+"time").equals("")){

                    time =  Long.parseLong(share.readSharePreference(mCityName+"time"));
                }else{
                   time = System.currentTimeMillis();
                }
                LogUtil.d("time",time+"");
                long currentTime = System.currentTimeMillis();
                if(currentTime - time < StaticVariable.DISTANT) {
                    Toast.makeText(getActivity(),"当前已是最新的数据了",Toast.LENGTH_SHORT).show();
                    mRefreshLayout.setRefreshing(false);
                }else {

                    initBg();
                    requestDataFromServer();

                }
            }
        });
    }
    //初始化头部控件
    private void initHead() {


        // https://free-api.heweather.com/v5/weather?city=漳州&key=404f2c3e24764f65baf1067846123098

        mHeadTemp = (TextView) mView.findViewById(R.id.head_temp);
        mHeadCityName = (TextView) mView.findViewById(R.id.city_name);
        mHeadInfo = (TextView) mView.findViewById(info_weather);
        mHeadWindDir= (TextView) mView.findViewById(R.id.direction);
        mHeadWindStr= (TextView) mView.findViewById(R.id.dengji);
        mHeadRelativeSize= (TextView) mView.findViewById(R.id.relative_size);
        mHeadBodySize= (TextView) mView.findViewById(R.id.body_size);

    }
    private void initBody() {
        mForecast_container = (LinearLayout) mView.findViewById(R.id.forecast_container);
        //aqi
        line = (LinearLayout) mView.findViewById(R.id.aqi);
        mAQI = (TextView) mView.findViewById(R.id.aqi_num);
        mAQIQuality = (TextView) mView.findViewById(R.id.aqi_quality);
        mPM = (TextView) mView.findViewById(R.id.aqi_pm);

        //suggest

        mSuggest_Car = (TextView) mView.findViewById(R.id.suggest_car);
        mSuggest_Comfort = (TextView) mView.findViewById(R.id.suggest_comfort);
        mSuggest_Sport = (TextView) mView.findViewById(R.id.suggest_sport);
        mSuggest_Flu = (TextView) mView.findViewById(R.id.suggest_flu);

    }
    //请求数据
    private void requestDataFromServer() {

        String url = "https://free-api.heweather.com/v5/weather?city="+mCityName+"&key=404f2c3e24764f65baf1067846123098";
        LogUtil.d("URL",url);
        HttpConnection.httpConnHelp(url, new HttpCallBackListener() {
            @Override
            public void onSuccess(InputStream inputStream) {
                try {
                    final StringBuilder stringBuilder =  HttpConnection.inputStreamToString(inputStream);
                    final Weather weather = JSONUtil.handleWeatherResponse(stringBuilder.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //请求成功把数据写入缓存中

                            if(weather != null && "ok".equals(weather.status)){

                                Map<String,String> m = new HashMap<String, String>();
                                m.put(mCityName,stringBuilder.toString());
                                //设置一个时间，防止频繁刷新,1小时后才可手动刷新
                                m.put(mCityName+"time",System.currentTimeMillis()+"");
                                share.insertSharePreference(m);
                                //显示数据
                                showWeatherData(weather);
                                //启动服务

                            }else{

                                Toast.makeText(getActivity(),"获取天气失败",Toast.LENGTH_SHORT).show();
                            }
                            mRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailour(Exception e) {

                LogUtil.d("weather","request weather fail");
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
    //显示数据
    private void showWeatherData(Weather weather){
        //设置头的数据信息
        mHeadTemp.setText(weather.now.temperature+"℃");
        mHeadCityName.setText(weather.basic.cityName.toString()+" | ");
        mHeadInfo.setText(weather.now.more.info.toString());
        mHeadWindDir.setText(weather.now.wind.direction.toString());
        mHeadWindStr.setText(weather.now.wind.dengji.toString()+"级");
        mHeadRelativeSize.setText(weather.now.relativeTemp.toString()+"℃");
        mHeadBodySize.setText(weather.now.bodyTemp.toString()+"℃");
        //设置主体信息
        //设置未来三天的信息
        //移除所有的里面所有的view
        mForecast_container.removeAllViews();
        //重新添加
        for (Forecast forecast:weather.forecasts){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_weather_forecast_item,mForecast_container,false);
            ImageView icon = (ImageView) view.findViewById(R.id.icon_now);
            TextView dates= (TextView) view.findViewById(R.id.forecast_date);
            TextView info_weather = (TextView) view.findViewById(now_info_weather);
            TextView quality = (TextView) view.findViewById(now_quality);
            TextView temp = (TextView) view.findViewById(now_temp);

            Glide.with(getActivity()).load(StaticVariable.ICON+forecast.more.icon_d+".png").asBitmap().centerCrop().into(icon);
            dates.setText(forecast.date);
            info_weather.setText(forecast.more.info);
            temp.setText(forecast.temperature.max+"/"+forecast.temperature.min+"℃");
            mForecast_container.addView(view);
        }
        if(weather.aqi != null){
            mAQI.setText(weather.aqi.city.aqi);
            mAQIQuality.setText(weather.aqi.city.qult);
            mPM.setText(weather.aqi.city.pm25);
        }else{

            line.setVisibility(View.GONE);

        }
        mSuggest_Car.setText(weather.suggestion.cardWash.info);
        mSuggest_Comfort.setText(weather.suggestion.comfort.info);
        mSuggest_Sport.setText(weather.suggestion.sport.info);
        mSuggest_Flu.setText(weather.suggestion.flu.info);
    }

    //当在载入activity的时候触发
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //优先从数据库读取数据,如果数据没有读到就从数据库哪里去查
        LogUtil.d("Weatheractivity","TExt3");
        //初始化id
        init();
        //从文件里面获取缓存的数据
        weatherData =  share.readSharePreference(mCityName);
        //表示本地没有缓存,从服务器去获取
        if(weatherData.equals("")){

            requestDataFromServer();

        }else{
            LogUtil.d("WeatherData",weatherData);
            //有缓存就直接把数据解析出来,并显示
            Weather weather =  handleWeatherResponse(weatherData);
            showWeatherData(weather);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
