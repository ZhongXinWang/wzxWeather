package weather.test.wzx.com.wzxweather.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 王钟鑫 on 17/4/27.
 *
 * {"aqi":
 * {"city":
 * {"aqi":"38","pm10":"38","pm25":"24","qlty":"优"
 * }
 * }
 *
 */

public class AQI {

    public AQICity city;

    public class  AQICity{

        //空气质量指数
        public String aqi;
        //首要污染物
        public String pm25;
        //质量
        @SerializedName("qlty")
        public String qult;

    }
}
