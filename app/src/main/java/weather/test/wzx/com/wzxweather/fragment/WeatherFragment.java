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

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.activity.AddCityActivity;
import weather.test.wzx.com.wzxweather.entity.Citys;
import weather.test.wzx.com.wzxweather.interfaces.HttpCallBackListener;
import weather.test.wzx.com.wzxweather.model.CityLab;
import weather.test.wzx.com.wzxweather.util.HttpConnection;
import weather.test.wzx.com.wzxweather.util.JSONUtil;
import weather.test.wzx.com.wzxweather.util.LogUtil;
import weather.test.wzx.com.wzxweather.util.StaticVariable;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private View mView;
    private CityLab mCityLab;
    private TextView mTextView;
    private ImageView mImageView;
    private Toolbar mToolbar;
    public WeatherFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityLab = new CityLab(getActivity());



    }

    public static WeatherFragment newInstance() {

        Bundle args = new Bundle();
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_weather, container, false);
        mToolbar = (Toolbar) mView.findViewById(R.id.tools);
        mToolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
       // https://free-api.heweather.com/v5/weather?city=漳州&key=404f2c3e24764f65baf1067846123098

       /* HttpConnection.httpConnHelp("https://free-api.heweather.com/v5/weather?city=漳州&key=404f2c3e24764f65baf1067846123098", new HttpCallBackListener() {
            @Override
            public void onSuccess(InputStream inputStream) {


                try {

                   StringBuilder stringBuilder =  HttpConnection.inputStreamToString(inputStream);

                    LogUtil.d("GSONResponse",JSONUtil.handleWeatherResponse(stringBuilder.toString()).basic.cityName);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailour(Exception e) {

                LogUtil.d("weather","request weather fail");
            }
        });
        */
        mImageView = (ImageView) mView.findViewById(R.id.bg);



        Glide.with(this).load("http://cn.bing.com/az/hprichbg/rb/GlacierBay_ZH-CN14440689690_1920x1080.jpg").asBitmap().centerCrop().into(mImageView);

        return  mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //优先从数据库读取数据,如果数据没有读到就从数据库哪里去查
        queryCitys();
    }

    private void queryCitys() {


        List<Citys> lists = mCityLab.getAllCitys();
        //表示还没有省份的数据,就去服务器上面加载数据
        if(lists == null){


            queryCitysFromService();

        }

    }

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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.weather_menus, menu);
       LogUtil.d("MyMenu","fffff");
        super.onCreateOptionsMenu(menu,inflater);
    }

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
