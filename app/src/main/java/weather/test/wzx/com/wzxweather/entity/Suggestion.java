package weather.test.wzx.com.wzxweather.entity;


import com.google.gson.annotations.SerializedName;

/**
 * Created by 王钟鑫 on 17/4/27.
 *
 * "suggestion":{"air":{"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"},
 * "comf":{"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"},
 * "cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},
 * "drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},
 * "flu":{"brf":"易发","txt":"天冷空气湿度大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。"},
 * "sport":{"brf":"较不宜","txt":"有降水，推荐您在室内进行低强度运动；若坚持户外运动，请选择合适的运动，并携带雨具。"},
 * "trav":{"brf":"适宜","txt":"温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！"},
 * "uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
 */

public class Suggestion {


    @SerializedName("comf")
    public Comfort comfort;
    public class Comfort{

        @SerializedName("txt")
        public String info;

    }
    @SerializedName("cw")
    public CardWash cardWash;
    public class  CardWash{
        @SerializedName("txt")
        public String info;
    }

    @SerializedName("sport")
    public Sport sport;
    public class  Sport{

        @SerializedName("txt")
        public String info;
    }
    @SerializedName("flu")
    public Fluen flu;
    public class  Fluen {
        @SerializedName("txt")
        public String info;
    }


}
