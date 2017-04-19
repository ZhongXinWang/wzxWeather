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

    }

    //add

    public void insertCity(Citys citys){

        String sql = "insert into "+CityDB.CityTable.TABLENAME+" ("+CityDB.CityTable.CityColumn.NAME+") values(?)";

        try{

            db =  mSqlLiteHelp.getWritableDatabase();

          db.execSQL(sql,new String[]{citys.getCityName()});

            close();
        }catch (Exception e){


            close();
            LogUtil.d("CityLab","error");
        }

    }
    //select

    public List<Citys>  getAllCitys(){


        String sql = "select * from "+CityDB.CityTable.TABLENAME;
        List<Citys> list;

        try{

            db =  mSqlLiteHelp.getReadableDatabase();

           Cursor cursor = db.rawQuery(sql,null);

           list =  toCitysList(cursor);

            close();
            return list.size()>0?list:null;
        }catch (Exception e){


            LogUtil.d("CityLab","error");
            close();

        }

        return null;

    }

    public List<Citys> toCitysList(Cursor cursor){

        List<Citys> cityses = new ArrayList<>();


        while(cursor.moveToNext()){

            Citys citys = new Citys();



            citys.setId(cursor.getInt(cursor.getColumnIndex(CityDB.CityTable.CityColumn.ID)));
            citys.setCityName(cursor.getString(cursor.getColumnIndex(CityDB.CityTable.TABLENAME)));
            LogUtil.d("test",cursor.getString(cursor.getColumnIndex(CityDB.CityTable.TABLENAME)));
            cityses.add(citys);
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
