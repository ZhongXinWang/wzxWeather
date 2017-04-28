package weather.test.wzx.com.wzxweather.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 王钟鑫 on 17/4/27.
 *
 * 城市的基本信息
 *
 * "basic":{"city":"漳州","cnty":"中国","id":"CN101230601","lat":"24.510897","lon":"117.661801","update":{"loc":"2017-04-27 19:52","utc":"2017-04-27 11:52"}}
 *
 */

public class Basic {

    //添加GASon注解,避免和关键字冲突
    @SerializedName("city")
    public  String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{

        @SerializedName("loc")
        public String updateTime;

    }


}
