package weather.test.wzx.com.wzxweather.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;

import weather.test.wzx.com.wzxweather.R;
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
            public void onSuccess(StringBuilder str) {


                try {

                    List<Citys> cityses = JSONUtil.JsonToCity(str.toString());

                    mCityLab.insertAllCitys(cityses);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailour(Exception e) {

                LogUtil.d("Weather",e.getMessage());

            }
        });

    }
}
