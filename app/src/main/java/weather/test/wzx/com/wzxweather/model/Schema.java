package weather.test.wzx.com.wzxweather.model;

/**
 * Created by 王钟鑫 on 17/4/18.
 */

 public class Schema {


    public final static  String DBNAME="weather.db";

    public static class  CityTable{

        public final static  String TABLENAME="citys";
        public static  class  CityColumn{

            public final static String ID="_id";
            public final static String NAME="cityName";

        }

    }
    public static class  CitySelect{

        public final static  String TABLENAME="selectCity";
        public static  class  CityColumn{

            public final static String ID="_id";
            public final static String CITYID="cityId";
            public  final  static String ISSELECT ="isSelect";

        }

    }


}
