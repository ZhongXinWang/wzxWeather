package weather.test.wzx.com.wzxweather.bases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import weather.test.wzx.com.wzxweather.util.ActivityQuenu;

public  abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityQuenu.addActivity(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除当前的程序
        ActivityQuenu.removeActivity(this);
    }
}
