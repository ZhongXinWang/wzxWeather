package weather.test.wzx.com.wzxweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.bases.SingleBaseWeatherActivity;
import weather.test.wzx.com.wzxweather.fragment.WeatherFragment;
import weather.test.wzx.com.wzxweather.util.ActivityQuenu;


public class WeatherActivity extends SingleBaseWeatherActivity {

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
    long startTime;
    @Override
    public void onBackPressed() {
        long endTime = 0;
        if(startTime == 0){

            startTime = System.currentTimeMillis();

        }else{
            endTime = System.currentTimeMillis();
        }
        if(startTime != 0 && endTime == 0){

            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();

        }else if(endTime != 0 && (endTime - startTime < 10000)){

            ActivityQuenu.finishAll();

        }else{
            startTime = 0;
            endTime = 0;
        }
    }
}
