package weather.test.wzx.com.wzxweather.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 王钟鑫 on 17/4/27.
 *
 * "daily_forecast":[
 * {
 * "astro":{"mr":"06:16","ms":"19:31","sr":"05:38","ss":"18:37"},
 * "cond":{"code_d":"305","code_n":"104","txt_d":"小雨","txt_n":"阴"},
 *
 * "date":"2017-04-27","hum":"78","pcpn":"8.2","pop":"100","pres":"1015",
 * "tmp":{"max":"18","min":"16"},"uv":"1","vis":"12",
 * "wind":{"deg":"116","dir":"无持续风向","sc":"微风","spd":"2"}},
 * {
 * "astro":{"mr":"07:07","ms":"20:37","sr":"05:37","ss":"18:38"},
 * "cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},
 *
 * "date":"2017-04-28","hum":"63","pcpn":"0.1","pop":"39","pres":"1016","tmp":{"max":"25","min":"15"},"uv":"12","vis":"19","wind":{"deg":"100","dir":"无持续风向","sc":"微风","spd":"2"}},
 * "astro":{"mr":"08:01","ms":"21:42","sr":"05:36","ss":"18:38"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},
 * "date":"2017-04-29","hum":"62","pcpn":"0.0","pop":"29","pres":"1016","tmp":{"max":"26","min":"17"},"uv":"12","vis":"20","wind":{"deg":"119","dir":"无持续风向","sc":"微风","spd":"1"}}],
 *
 * "hourly_forecast":[{"cond":{"code":"309","txt":"毛毛雨/细雨"},"date":"2017-04-27 22:00","hum":"75","pop":"77","pres":"1018","tmp":"18","wind":{"deg":"119","dir":"东南风","sc":"微风","spd":"4"}
 * }]
 *
 * 未来三天天气预报
 */

public class Forecast {


    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    public class Temperature{

        public String max;
        public String min;

    }

    @SerializedName("cond")
    public More more;
    public class More{

        @SerializedName("txt_d")
        public String info;

    }

}
