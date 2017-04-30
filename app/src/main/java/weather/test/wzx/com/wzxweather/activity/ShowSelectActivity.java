package weather.test.wzx.com.wzxweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.bases.SingleBaseWeatherActivity;
import weather.test.wzx.com.wzxweather.fragment.ShowSelectFragment;
import weather.test.wzx.com.wzxweather.util.ActivityQuenu;

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
    private long startTime;
    @Override
    public void onBackPressed() {

        long endTime = 0;
        Toast.makeText(this,startTime+"",Toast.LENGTH_SHORT).show();
        if(startTime == 0){

            startTime = System.currentTimeMillis();
            //Toast.makeText(this,startTime+"",Toast.LENGTH_SHORT).show();

        }else{

            endTime = System.currentTimeMillis();

        }

        if(endTime != 0 && (endTime - startTime < 10000)){

            ActivityQuenu.finishAll();

        }else{

            startTime = 0;
            endTime = 0;

        }
    }
}
