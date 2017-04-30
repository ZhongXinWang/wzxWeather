package weather.test.wzx.com.wzxweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.bases.SingleBaseWeatherActivity;
import weather.test.wzx.com.wzxweather.fragment.WeatherFragment;


public class WeatherActivity extends SingleBaseWeatherActivity {

    private long startTime;
    private static String CITYNAME="cityName";
    @Override
    public Fragment createFragment() {



        return WeatherFragment.newInstance(getIntent().getStringExtra(CITYNAME));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

    }

    public static void toAction(Context context,String cityName){


        Intent intent = new Intent(context,WeatherActivity.class);
        intent.putExtra(CITYNAME,cityName);
        context.startActivity(intent);

    }

}
