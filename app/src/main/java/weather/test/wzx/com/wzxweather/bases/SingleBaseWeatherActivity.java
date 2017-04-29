package weather.test.wzx.com.wzxweather.bases;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import weather.test.wzx.com.wzxweather.R;

/**
 * Created by 王钟鑫 on 17/4/29.
 */

public abstract  class SingleBaseWeatherActivity extends BaseActivity {
    public abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Fragment fragment = null;
        FragmentManager manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.fragment_container);
        if(fragment == null){


            fragment = createFragment();
            manager.beginTransaction().add(R.id.fragment_container,fragment).commit();

        }
    }
}
