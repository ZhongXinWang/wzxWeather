package weather.test.wzx.com.wzxweather.interfaces;

/**
 * Created by 王钟鑫 on 17/4/17.
 */

public interface HttpCallBackListener {

    void onSuccess(StringBuilder str);
    void onFailour(Exception e);

}
