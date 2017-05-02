package weather.test.wzx.com.wzxweather.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import weather.test.wzx.com.wzxweather.db.OpenSqlLiteHelp;
import weather.test.wzx.com.wzxweather.entity.CitySelect;
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

    public boolean insertCity(CitySelect citys){

        String sql = "insert into "+ Schema.CitySelect.TABLENAME+" ("+Schema.CitySelect.CityColumn.CITYID+","+ Schema.CityTable.CityColumn.NAME+","+Schema.CitySelect.CityColumn.ISSELECT+") values (?,?,?)";

        try{

          db =  mSqlLiteHelp.getWritableDatabase();


          db.execSQL(sql,new String[]{citys.getCityId()+"",citys.getCityName(),citys.getIsSelect()+""});


            LogUtil.d("CitySelectLab",sql);
            return true;

        }catch (Exception e){


            e.printStackTrace();
            LogUtil.d("CitySelectLab",e.getMessage());
            close();

            return false;

        }


    }

    //检测是否已存在
    public boolean isExistsCityId(int cityId){

        //查询是否存在已选择字段
        Cursor cursor = null;

        try {

            db =  mSqlLiteHelp.getWritableDatabase();
             cursor = db.query(Schema.CitySelect.TABLENAME, null, Schema.CitySelect.CityColumn.CITYID + "=" + cityId, null, null, null, null);


            if(cursor.moveToFirst()){

                LogUtil.d("isExistsCityId",cursor.toString());
                return true;

            }
        }catch (Exception e){

            e.printStackTrace();
            if(cursor != null) {

                cursor.close();
            }
            close();
            LogUtil.d("isExistsCityId","is exists");

            return false;

        }

        return false;


    }
    //select

    public List<CitySelect>  getAllCitySelects(){


        String sql = "select *from "+ Schema.CitySelect.TABLENAME +" order by "+Schema.CitySelect.CityColumn.ISSELECT +" desc";
        List<CitySelect> list;

        try{

            db =  mSqlLiteHelp.getReadableDatabase();
           Cursor cursor = db.rawQuery(sql,null);

           list =  toCitysList(cursor);

            return list.size()>0?list:null;

        }catch (Exception e){

            LogUtil.d("CitySelectLab",sql);
            close();
        }

        return null;

    }

    private List<CitySelect> toCitysList(Cursor cursor){

        List<CitySelect> cityses = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0 ) {
            try {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {

                    CitySelect citys = new CitySelect();
                    citys.setId(cursor.getInt(cursor.getColumnIndex(Schema.CitySelect.CityColumn.ID)));
                    citys.setCityName(cursor.getString(cursor.getColumnIndex(Schema.CityTable.CityColumn.NAME)));
                    citys.setCityId(cursor.getInt(cursor.getColumnIndex(Schema.CitySelect.CityColumn.CITYID)));
                    citys.setIsSelect(cursor.getInt(cursor.getColumnIndex(Schema.CitySelect.CityColumn.ISSELECT)));
                    cityses.add(citys);
                    cursor.moveToNext();
                }
            } finally {

                cursor.close();
            }

        }
        return cityses;

    }
    public boolean deleteById(int id){

        try {

            db = mSqlLiteHelp.getWritableDatabase();
            int flag = db.delete(Schema.CitySelect.TABLENAME,Schema.CitySelect.CityColumn.ID+"="+id,null);
            LogUtil.d("delete",flag+"");
            if(flag > 0){

                return true;
            }

        }catch (Exception e){

            LogUtil.d("CitySelectLab","delete");
        }
        return false;
    }
    public boolean updateById(int id){

        try {

            db = mSqlLiteHelp.getWritableDatabase();
            db.execSQL("update "+Schema.CitySelect.TABLENAME+" set "+Schema.CitySelect.CityColumn.ISSELECT+"=0");
            db.execSQL("update "+Schema.CitySelect.TABLENAME+" set "+Schema.CitySelect.CityColumn.ISSELECT+"=1 where "+Schema.CitySelect.CityColumn.ID+"="+id);

            return true;

        }catch (Exception e){

            LogUtil.d("CitySelectLab","delete");
        }
        return false;
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
