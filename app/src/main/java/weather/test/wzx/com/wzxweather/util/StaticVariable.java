package weather.test.wzx.com.wzxweather.util;

/**
 * Created by 王钟鑫 on 17/4/19.
 *
 * 静态产量的类
 */

public class StaticVariable {

    //读取城市的常量类
    public static String CITYURL="https://cdn.heweather.com/china-city-list.json";

    //缓存天气情况的文件名
    public static  String WEATHERDATA="weatherData";

   //天气图标
    public static String ICON = "https://cdn.heweather.com/cond_icon/";
    //设置手动刷新的时间
    public static int DISTANT=1*60*60*1000;
   //背景图片的地址
    public static String BGURL="http://guolin.tech/api/bing_pic";


    //保存的字段
    public static String IS_AUTO_UPDATE= "is_auto_update";
    //服务更新的时间,一天
    public static int TIME=24*60*60*1000;
}
