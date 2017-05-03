package weather.test.wzx.com.wzxweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.widget.Toast;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.bases.SingleBaseWeatherActivity;
import weather.test.wzx.com.wzxweather.fragment.AddCityFragment;

public class AddCityActivity extends SingleBaseWeatherActivity {


    @Override
    public Fragment createFragment() {

        return AddCityFragment.newInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        Toast.makeText(AddCityActivity.this,"下拉换页浏览",Toast.LENGTH_SHORT).show();
    }
    public static void startAction(Context context){
        Intent intent = new Intent(context,AddCityActivity.class);
        context.startActivity(intent);
    }

}
