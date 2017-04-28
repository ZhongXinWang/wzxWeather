package weather.test.wzx.com.wzxweather.entity;

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

        public String aqi;
        public String pm25;

    }
}
