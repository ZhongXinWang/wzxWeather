package weather.test.wzx.com.wzxweather.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import weather.test.wzx.com.wzxweather.db.OpenSqlLiteHelp;
import weather.test.wzx.com.wzxweather.entity.Citys;
import weather.test.wzx.com.wzxweather.util.LogUtil;

/**
 * Created by 王钟鑫 on 17/4/18.
 *
 *
 * 模型层主要封装对数据的操作
 */

public class CityLab {

    private Context mContext;
    private OpenSqlLiteHelp mSqlLiteHelp = null;
    private SQLiteDatabase db = null;

    public CityLab(Context context){

        this.mContext = context;
        mSqlLiteHelp = new OpenSqlLiteHelp(context, Schema.DBNAME,null,1);

    }

    //add

    public void insertCity(Citys citys){

        String sql = "insert into "+ Schema.CityTable.TABLENAME+" ("+ Schema.CityTable.CityColumn.NAME+") values (?)";

        try{


            db =  mSqlLiteHelp.getWritableDatabase();


          db.execSQL(sql,new String[]{citys.getCityName()});


        }catch (Exception e){


            close();
            LogUtil.d("CityLab",e.getMessage());
        }

    }
    //select
    public void insertAllCitys(List<Citys> cityses){

        String sql = "insert into "+ Schema.CityTable.TABLENAME+" ("+ Schema.CityTable.CityColumn.NAME+") values (?)";
        boolean result = true;
        if(cityses == null || cityses.size() == 0){

            result = false;

        }
        try{

            if(result) {


                db = mSqlLiteHelp.getWritableDatabase();
                db.beginTransaction();

                for (Citys citys:cityses){

                    try{

                        db.execSQL(sql,new String[]{citys.getCityName()});

                    }catch (Exception e){


                        result  = false;
                        break;

                    }



                }

                if(result){

                    db.setTransactionSuccessful();

                }

            }


        }catch (Exception e){


            LogUtil.d("insertAllError",e.getMessage());


        }finally {

            if(db != null){

                db.endTransaction();
                db.close();
                db = null;

            }
        }

    }
    //获取所有的城市


    public List<Citys>  getAllCitys(){


        String sql = "select *from "+ Schema.CityTable.TABLENAME;
        List<Citys> list;

        try{

            db =  mSqlLiteHelp.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,null);
            LogUtil.d("CityLab",sql);
            list =  toCitysList(cursor);

            return list.size()>0?list:null;

        }catch (Exception e){

            close();

        }

        return null;

    }
    //根据name查找城市
    public Citys  searchCitysByName(String name){

        String sql = "select *from "+ Schema.CityTable.TABLENAME +"where "+Schema.CityTable.CityColumn.NAME+"="+name;
        List<Citys> list;
        try{
            db =  mSqlLiteHelp.getReadableDatabase();
           Cursor cursor = db.rawQuery(sql,null);
            LogUtil.d("CityLab",sql);
           list =  toCitysList(cursor);

           if(list.size() > 0){

               return list.get(0);
           }
            return null;

        }catch (Exception e){



            close();

        }

        return null;

    }
    //进行分页显示
    public List<Citys> getLimitCitys(int offset,int num){


        //offset 表示从那一行开始取,num表示取的数目   如: 3 offset 2   会取到的offset    3,4,5    总共三条

        String sql = "select *from "+ Schema.CityTable.TABLENAME + " limit "+num+" offset "+offset;
        List<Citys> list;

        try{

            db =  mSqlLiteHelp.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql,null);
            LogUtil.d("CityLab",sql);
            list =  toCitysList(cursor);

            return list.size()>0?list:null;

        }catch (Exception e){



            close();

        }

        return null;


    }

    //进行转换
    private List<Citys> toCitysList(Cursor cursor){

        List<Citys> cityses = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0 ) {
            try {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {

                    Citys citys = new Citys();
                    citys.setId(cursor.getInt(cursor.getColumnIndex(Schema.CityTable.CityColumn.ID)));
                    citys.setCityName(cursor.getString(cursor.getColumnIndex(Schema.CityTable.CityColumn.NAME)));
                    cityses.add(citys);
                    cursor.moveToNext();
                }
            } finally {

                cursor.close();
            }

        }
        return cityses;

    }

    public void close(){


        if(db != null){

            db.close();
            db = null;
        }
        if(mSqlLiteHelp != null){

            mSqlLiteHelp.close();
            mSqlLiteHelp = null;
        }
    }
}
