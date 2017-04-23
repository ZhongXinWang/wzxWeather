package weather.test.wzx.com.wzxweather.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.bases.BaseActivity;
import weather.test.wzx.com.wzxweather.fragment.WeatherFragment;
import weather.test.wzx.com.wzxweather.util.SelectDownPopuWindow;


public class WeatherActivity extends BaseActivity {

    private Toolbar mToolbar;
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
        mToolbar = (Toolbar) findViewById(R.id.tools);
        mToolbar.setTitle("知乎天气");
        mImageView = (ImageView) findViewById(R.id.bg);
        setSupportActionBar(mToolbar);
        Glide.with(this).load("http://cn.bing.com/az/hprichbg/rb/GlacierBay_ZH-CN14440689690_1920x1080.jpg").asBitmap().centerCrop().into(mImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.weather_menus,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case  R.id.adds:

                AddCityActivity.startAction(WeatherActivity.this);

                break;
            case  R.id.sets:
                break;
            case R.id.share:
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
