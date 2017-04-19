package weather.test.wzx.com.wzxweather.entity;

/**
 * Created by 王钟鑫 on 17/4/18.
 */

public class Citys {

    public void setId(int id) {
        mId = id;
    }

    private int mId;
    private String mCityName;

    public Citys(){

    }
    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public int getId() {
        return mId;
    }

}
