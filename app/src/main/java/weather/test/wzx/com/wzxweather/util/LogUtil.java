package weather.test.wzx.com.wzxweather.util;

import android.util.Log;

/**
 * Created by 王钟鑫 on 17/4/17.
 */

public class LogUtil{
    //日志标记
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int ERROT = 4;
    public static final int WRAN = 5;
    public static final int NOTHING = 6;
    public static final int level = VERBOSE;

    public static void v(String tag,String msg){

        if(level <= VERBOSE){

            Log.v(tag,msg);
        }

    }
    public static void d(String tag,String msg){

        if(level <= DEBUG){

            Log.d(tag,msg);
        }

    }
    public static void i(String tag,String msg){

        if(level <= INFO){

            Log.i(tag,msg);
        }

    }
    public static void w(String tag,String msg){

        if(level <= WRAN){

            Log.w(tag,msg);
        }

    }
    public static void e(String tag,String msg){

        if(level <= ERROT){

            Log.e(tag,msg);
        }

    }
}
