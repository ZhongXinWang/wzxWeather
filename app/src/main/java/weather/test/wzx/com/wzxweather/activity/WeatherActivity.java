package weather.test.wzx.com.wzxweather.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.bases.BaseActivity;
import weather.test.wzx.com.wzxweather.fragment.WeatherFragment;
import weather.test.wzx.com.wzxweather.util.SelectDownPopuWindow;


public class WeatherActivity extends BaseActivity {

    private ImageView mImageView;
    private  SelectDownPopuWindow down;
    @Override
    public Fragment createFragment() {

        return WeatherFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mImageView = (ImageView) findViewById(R.id.bg);



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
