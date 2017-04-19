package weather.test.wzx.com.wzxweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import weather.test.wzx.com.wzxweather.model.CityDB;

/**
 * Created by 王钟鑫 on 17/4/18.
 */

public class OpenSqlLiteHelp  extends SQLiteOpenHelper {

    String SQL="create table "+ CityDB.CityTable.TABLENAME+" ("+

            CityDB.CityTable.CityColumn.ID+" integer autoincrement primary key ,"+
            CityDB.CityTable.CityColumn.NAME +" varchar(30) not null"+
            ")";

    public OpenSqlLiteHelp(Context context,String dbName,int version){

        super(context,dbName,null,version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists "+CityDB.CityTable.TABLENAME);

    }
}
