package weather.test.wzx.com.wzxweather.entity;

/**
 * Created by 王钟鑫 on 17/4/18.
 */

public class CitySelect {

    private int mId;
    private int mCityId;
    private int mIsSelect;

    public CitySelect(){

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
}
