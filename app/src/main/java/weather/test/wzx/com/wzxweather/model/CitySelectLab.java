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

public class CitySelectLab {

    private Context mContext;
    private OpenSqlLiteHelp mSqlLiteHelp = null;
    private SQLiteDatabase db = null;

    public CitySelectLab(Context context){

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
