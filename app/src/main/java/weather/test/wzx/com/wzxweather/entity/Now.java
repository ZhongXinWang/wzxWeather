package weather.test.wzx.com.wzxweather.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 王钟鑫 on 17/4/27.
 *
 * "now":{"cond":{"code":"101","txt":"多云"},"fl":"17","hum":"91","pcpn":"0","pres":"1015","tmp":"16","vis":"10","wind":{"deg":"80","dir":"南风","sc":"3-4","spd":"13"}},
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;//温度

    @SerializedName("cond")
    public More more;

    public class  More{

        @SerializedName("txt")
        public String info;//天气情况

    }

    @SerializedName("wind")
    public Winds wind;

    public class Winds{

        @SerializedName("dir")
        public String direction;

        //风力等级

        @SerializedName("sc")
        public String dengji;

    }
    //相对湿度
    @SerializedName("hum")
    public String relativeTemp;


    //体感温度
    @SerializedName("fl")
    public String bodyTemp;



}
