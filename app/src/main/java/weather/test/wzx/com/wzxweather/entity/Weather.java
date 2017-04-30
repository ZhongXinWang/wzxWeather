package weather.test.wzx.com.wzxweather.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 王钟鑫 on 17/4/27.
 *
 * GAON 解析的根节点
 */


public class Weather {

    public AQI aqi;

    public Basic basic;

    @SerializedName("now")
    public Now now;

    @SerializedName("suggestion")
    public Suggestion suggestion;

    //daily_forecast里面有多组数,所以要用集合来创建
    @SerializedName("daily_forecast")
    public List<Forecast> forecasts;

    //返回的状态 ok表示成功
    public  String status;

}
