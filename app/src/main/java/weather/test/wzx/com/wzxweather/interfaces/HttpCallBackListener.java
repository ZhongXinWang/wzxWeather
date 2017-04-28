package weather.test.wzx.com.wzxweather.interfaces;

import java.io.InputStream;

/**
 * Created by 王钟鑫 on 17/4/17.
 */

public interface HttpCallBackListener {

    void onSuccess(InputStream inputStream);
    void onFailour(Exception e);

}
