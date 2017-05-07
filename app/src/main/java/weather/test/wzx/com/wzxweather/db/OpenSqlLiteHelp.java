package weather.test.wzx.com.wzxweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import weather.test.wzx.com.wzxweather.model.Schema;

/**
 * Created by 王钟鑫 on 17/4/18.
 */

public class OpenSqlLiteHelp  extends SQLiteOpenHelper {

    public static final String CITY="create table "+ Schema.CityTable.TABLENAME+" ("+

             Schema.CityTable.CityColumn.ID+" integer  primary key autoincrement,"+
             Schema.CityTable.CityColumn.NAME +" text,"+Schema.CityTable.CityColumn.PRINAMR+" text)";

    public static final String CITYSELECT="create table "+ Schema.CitySelect.TABLENAME+" ("+

             Schema.CitySelect.CityColumn.ID+" integer  primary key autoincrement,"+
             Schema.CitySelect.CityColumn.ISSELECT+" integer default 0,"+
             Schema.CitySelect.CityColumn.CITYID +" integer )";

    public OpenSqlLiteHelp(Context context, String dbName, SQLiteDatabase.CursorFactory factory,int version){

        super(context,dbName,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(CITY);
            sqLiteDatabase.execSQL(CITYSELECT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists "+ Schema.CityTable.TABLENAME);
        sqLiteDatabase.execSQL("drop table if exists "+ Schema.CitySelect.TABLENAME);
        onCreate(sqLiteDatabase);

    }
}
