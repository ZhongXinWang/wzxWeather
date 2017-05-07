package weather.test.wzx.com.wzxweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.bases.SingleBaseWeatherActivity;
import weather.test.wzx.com.wzxweather.fragment.ShowSelectFragment;

public class ShowSelectActivity extends SingleBaseWeatherActivity{

    @Override
    public Fragment createFragment() {

        return ShowSelectFragment.newInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
    }
    public static void startAction(Context context){

        Intent intent = new Intent(context,ShowSelectActivity.class);
        context.startActivity(intent);
    }
}
