package weather.test.wzx.com.wzxweather.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weather.test.wzx.com.wzxweather.R;
import weather.test.wzx.com.wzxweather.adapter.FragmentViewPageAdapter;
import weather.test.wzx.com.wzxweather.bases.BaseActivity;
import weather.test.wzx.com.wzxweather.entity.CitySelect;
import weather.test.wzx.com.wzxweather.entity.Citys;
import weather.test.wzx.com.wzxweather.interfaces.HttpCallBackListener;
import weather.test.wzx.com.wzxweather.model.CityLab;
import weather.test.wzx.com.wzxweather.model.CitySelectLab;
import weather.test.wzx.com.wzxweather.util.ActivityQuenu;
import weather.test.wzx.com.wzxweather.util.HttpConnection;
import weather.test.wzx.com.wzxweather.util.JSONUtil;
import weather.test.wzx.com.wzxweather.util.LogUtil;
import weather.test.wzx.com.wzxweather.util.SharedPreferencesUtil;
import weather.test.wzx.com.wzxweather.util.StaticVariable;


public class WeatherActivity extends BaseActivity{

    private static String CITYNAME="cityName";
    private ViewPager mViewPager;
    private List<CitySelect> mList;
    private CitySelectLab mSelectLab;
    private FragmentViewPageAdapter adapter;
    private Toolbar mToolbar;
    private CityLab mCityLab;
    //Location
    private AMapLocationClient mLocationClient;
    private AMapLocationListener mLocationListent;
    private AMapLocationClientOption mLocationOption;
    private SharedPreferencesUtil share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mToolbar = (Toolbar)findViewById(R.id.tools);
        mToolbar.setTitle("新新天气");
        setSupportActionBar(mToolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewPage);
        mCityLab = new CityLab(this);
        mSelectLab = new CitySelectLab(this);
        share =new SharedPreferencesUtil(this);
        //第一次加载的时候载入数据
        if(share.readSharePreference(StaticVariable.ISREAD).equals("")) {
            queryCitys();
        }
        initLocation();
        mList = mSelectLab.getAllCitySelects();
        if(mList != null){
            Intent i = getIntent();
            String cityName = i.getStringExtra(CITYNAME);
            setAdapter();
            if(!TextUtils.isEmpty(cityName)) {
                for (int j = 0; j < mList.size(); j++) {

                    if (cityName.equals(mList.get(j).getCityName())) {

                        mViewPager.setCurrentItem(j);
                    }
                }
            }
        }/*else{
            CitySelect citySelect = new CitySelect();
            citySelect.setCityName("诏安");
            List<CitySelect> tempList = new ArrayList<>();
            tempList.add(citySelect);
            mList = tempList;
            setAdapter();
        }
         */
    }
    private void setAdapter(){

        adapter = new FragmentViewPageAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(adapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menus,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void initLocation() {
        mLocationClient = new AMapLocationClient(WeatherActivity.this);
        mLocationListent = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation != null){
                    if(aMapLocation.getErrorCode() == 0){
                        String priName = aMapLocation.getProvince();
                        String cityName = aMapLocation.getDistrict();

                        int cityId = mCityLab.getCityId(priName,cityName);
                        LogUtil.d("MyTest",priName+"----"+cityName);
                        mSelectLab = new CitySelectLab(WeatherActivity.this);
                        if(cityId != -1){
                            //检测改地区是否存在
                            if(!mSelectLab.isExistsCityId(cityId)){
                                //不存在的话就添加数据,并且跳到展示界面
                                CitySelect select = new CitySelect();
                                select.setCityId(cityId);
                                select.setIsSelect(1);
                                mSelectLab.insertCity(select);
                                mList = mSelectLab.getAllCitySelects();
                                adapter = new FragmentViewPageAdapter(getSupportFragmentManager(),mList);
                                mViewPager.setAdapter(adapter);
                            }
                        }
                    }else{
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }

                }
            }
        };
        mLocationClient.setLocationListener(mLocationListent);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);

        checkPerission();
    }
    private void checkPerission(){

        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if(ContextCompat.checkSelfPermission(WeatherActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        }
        if(ContextCompat.checkSelfPermission(WeatherActivity.this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){

            permissionList.add(Manifest.permission.READ_PHONE_STATE);

        }
        if(!permissionList.isEmpty()){

            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            LogUtil.d("permission",permissions[0].toString());
            ActivityCompat.requestPermissions(WeatherActivity.this,permissions,1);
        }else{

            requestLocation();
        }
    }
    private void requestLocation(){

        mLocationClient.startLocation();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            //添加菜单的事件响应
            case  R.id.adds:
                ShowSelectActivity.startAction(WeatherActivity.this);
                break;
            //设置事件响应
            case  R.id.sets:
                SetingActivity.startAction(WeatherActivity.this);
                break;
            //分享事件响应
            case R.id.share:

                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtil.d("locationRequest","requestLocation");
        switch (requestCode){
            case 1:
                if(grantResults.length > 0){

                    for(int result:grantResults){

                        if(result != PackageManager.PERMISSION_GRANTED){

                            Toast.makeText(WeatherActivity.this,"必须同意权限才能定位功能",Toast.LENGTH_SHORT).show();
                            ActivityQuenu.finishAll();
                        }

                    }

                    requestLocation();
                }
                break;
            default:

        }
    }

    //获取所有的城市
    private void queryCitys() {
        List<Citys> lists = mCityLab.getAllCitys();
        //表示还没有省份的数据,就去服务器上面加载数据
        LogUtil.d("query","hello");
        if(lists == null){
            queryCitysFromService();
        }
    }
    //从服务器上面获取
    private void queryCitysFromService() {
        //调用自己写的httpurl
        HttpConnection.httpConnHelp(StaticVariable.CITYURL, new HttpCallBackListener() {
            @Override
            public void onSuccess(InputStream str) {
                try {

                    List<Citys> cityses = JSONUtil.JsonToCity(HttpConnection.inputStreamToString(str).toString());
                    mCityLab.insertAllCitys(cityses);
                    Map<String,String> m = new HashMap<String, String>();
                    m.put(StaticVariable.ISREAD,"read");
                    share.insertSharePreference(m);

                } catch (JSONException e) {

                    e.printStackTrace();

                }catch (Exception e){

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailour(Exception e) {

                LogUtil.d("Weather",e.getMessage());

            }
        });

    }
}
