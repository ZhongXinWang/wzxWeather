package weather.test.wzx.com.wzxweather.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.activity.AddCityActivity;
import weather.test.wzx.com.wzxweather.entity.CitySelect;
import weather.test.wzx.com.wzxweather.entity.Citys;
import weather.test.wzx.com.wzxweather.entity.Weather;
import weather.test.wzx.com.wzxweather.interfaces.HttpCallBackListener;
import weather.test.wzx.com.wzxweather.model.CityLab;
import weather.test.wzx.com.wzxweather.model.CitySelectLab;
import weather.test.wzx.com.wzxweather.util.HttpConnection;
import weather.test.wzx.com.wzxweather.util.JSONUtil;
import weather.test.wzx.com.wzxweather.util.LogUtil;
import weather.test.wzx.com.wzxweather.util.SharedPreferencesUtil;
import weather.test.wzx.com.wzxweather.util.StaticVariable;

import static weather.test.wzx.com.wzxweather.util.JSONUtil.handleWeatherResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private View mView;
    private CityLab mCityLab;
    private TextView mTextView;
    private ImageView mImageView;
    private Toolbar mToolbar;
    private static String CITYNAME="cityName";
    //查询城市的名字
    private String mCityName = "";

    private CitySelectLab mCitySelect;
    //缓存数据的操作
    private SharedPreferencesUtil share;
    //从缓存数据里取出来的值
    private String weatherData;
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
        mCityLab = new CityLab(getActivity());
        mCitySelect = new CitySelectLab(getActivity());
        share = new SharedPreferencesUtil(getActivity());

        mCityName = (String) getArguments().get(CITYNAME);

        //mCityName  如果为空,表示不是从添加界面过来的,就需要从数据库去查找默认的城市
        if(mCityName == null){

            List<CitySelect> citySelect = mCitySelect.getAllCitySelects();

            mCityName = citySelect.get(0).getCityName();

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_weather, container, false);
        mToolbar = (Toolbar) mView.findViewById(R.id.tools);
        mToolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        //设置fragement的菜单可用
        setHasOptionsMenu(true);
        // https://free-api.heweather.com/v5/weather?city=漳州&key=404f2c3e24764f65baf1067846123098

        mImageView = (ImageView) mView.findViewById(R.id.bg);
        Glide.with(this).load("http://cn.bing.com/az/hprichbg/rb/GlacierBay_ZH-CN14440689690_1920x1080.jpg").asBitmap().centerCrop().into(mImageView);

        //从文件里面获取缓存的数据
        weatherData =  share.readSharePreference(mCityName);
        //表示本地没有缓存,从服务器去获取
        if(weatherData.equals("")){


            requestDataFromServer();

        }else{

            //有缓存就直接把数据解析出来,并显示

            Weather weather =  handleWeatherResponse(weatherData);
            showWeatherData(weather);

        }
        //初始化id
        init();


        return  mView;
    }

    //初始化参数
    private void init() {


    }
    //请求数据
    private void requestDataFromServer() {

        String url = "https://free-api.heweather.com/v5/weather?city="+mCityName+"&key=404f2c3e24764f65baf1067846123098";

        HttpConnection.httpConnHelp("", new HttpCallBackListener() {
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
                                share.insertSharePreference(m);
                                //显示数据
                                showWeatherData(weather);

                            }else{


                                Toast.makeText(getActivity(),"获取天气失败",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailour(Exception e) {

                LogUtil.d("weather","request weather fail");
            }
        });
    }
    //显示数据
    private void showWeatherData(Weather weather){

    }

    //当在载入activity的时候触发
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //优先从数据库读取数据,如果数据没有读到就从数据库哪里去查
        queryCitys();
    }

    //获取所有的城市
    private void queryCitys() {


        List<Citys> lists = mCityLab.getAllCitys();
        //表示还没有省份的数据,就去服务器上面加载数据
        if(lists == null){


            queryCitysFromService();

        }

    }
    //从服务器上面获取
    private void queryCitysFromService() {


        //调用自己写的httpurl

        HttpConnection.httpConnHelp(StaticVariable.CITYURL, new HttpCallBackListener() {
            @Override
            public void onSuccess(InputStream str) {


                try {

                    List<Citys> cityses = JSONUtil.JsonToCity(HttpConnection.inputStreamToString(str).toString());

                    mCityLab.insertAllCitys(cityses);


                } catch (JSONException e) {

                    e.printStackTrace();

                }catch (Exception e){

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailour(Exception e) {

                LogUtil.d("Weather",e.getMessage());

            }
        });

    }

    //创建菜单,和activity不同,这里没有return 只有super来通过activity来创建菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.weather_menus, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    //设置菜单的响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            //添加菜单的事件响应
            case  R.id.adds:

                AddCityActivity.startAction(getActivity());

                break;
            //设置事件响应
            case  R.id.sets:

                break;
            //分享事件响应
            case R.id.share:

                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
