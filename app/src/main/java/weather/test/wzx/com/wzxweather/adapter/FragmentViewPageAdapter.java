package weather.test.wzx.com.wzxweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import weather.test.wzx.com.wzxweather.entity.CitySelect;
import weather.test.wzx.com.wzxweather.fragment.WeatherFragment;

/**
 * Created by 王钟鑫 on 2017/5/6.
 */

public class FragmentViewPageAdapter extends FragmentPagerAdapter {

    private List<CitySelect> mList;
    public FragmentViewPageAdapter(FragmentManager fragmentManager, List<CitySelect> list) {
        super(fragmentManager);
        this.mList = list;
    }
    @Override
    public Fragment getItem(int position) {


        return WeatherFragment.newInstance(mList.get(position).getCityName());
    }
    @Override
    public int getCount() {
        return mList.size();
    }
}
