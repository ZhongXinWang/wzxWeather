package weather.test.wzx.com.wzxweather.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.entity.Citys;
import weather.test.wzx.com.wzxweather.model.CityLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private View mView;
    private CityLab mCityLab;
    private TextView mTextView;

    public WeatherFragment() {
        // Required empty public constructor
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

        //优先从数据库读取数据,如果数据没有读到就从数据库哪里去查

        mTextView = (TextView) mView.findViewById(R.id.fragments);

        Citys citys = new Citys();
        citys.setCityName("长沙");
        mCityLab.insertCity(citys);
        List<Citys> list = mCityLab.getAllCitys();

        if(list != null) {
             mTextView.setText(list.get(0).getCityName());


        }
        return  mView;
    }

}
