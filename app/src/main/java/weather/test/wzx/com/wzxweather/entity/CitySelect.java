package weather.test.wzx.com.wzxweather.entity;

/**
 * Created by 王钟鑫 on 17/4/18.
 */

public class CitySelect {

    private int mId;
    private int mCityId;
    private int mIsSelect;
    private String cityName;
    private String mPriName;
    public CitySelect(){

    }
    public String getPriName() {
        return mPriName;
    }
    public void setPriName(String priName) {
        mPriName = priName;
    }
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getCityId() {
        return mCityId;
    }

    public void setCityId(int cityId) {
        mCityId = cityId;
    }

    public int getIsSelect() {
        return mIsSelect;
    }

    public void setIsSelect(int isSelect) {
        mIsSelect = isSelect;
    }

    public String toString(){

        return getCityName()+"("+getPriName()+")";
    }
}
