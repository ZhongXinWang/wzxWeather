package weather.test.wzx.com.wzxweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.bases.SingleBaseWeatherActivity;
import weather.test.wzx.com.wzxweather.fragment.SetingFragment;

public class SetingActivity extends SingleBaseWeatherActivity{

    @Override
    public Fragment createFragment() {
        return SetingFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_weather);
        super.onCreate(savedInstanceState);
    }
    public static void startAction(Context context){

        Intent intent = new Intent(context,SetingActivity.class);
        context.startActivity(intent);
    }
}
