package weather.test.wzx.com.wzxweather.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王钟鑫 on 17/4/29.
 *
 * 随时随地退出程序
 *
 */

public class ActivityQuenu {

    private static  List<Activity> mActivities = new ArrayList<>();

    //添加活动
    public static void addActivity(Activity activity){


        mActivities.add(activity);


    }

    //在销毁的时候调用该方法
    public  static void removeActivity(Activity activity){


        mActivities.remove(activity);

    }

    //当要退出程序的时候,可以调用该方法,结束所有的activity
    public static  void finishAll(){


        for(Activity activity:mActivities){


            if(!activity.isFinishing()) {

                activity.finish();

            }

        }

    }

}
