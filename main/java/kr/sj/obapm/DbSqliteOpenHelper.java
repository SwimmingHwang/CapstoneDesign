package kr.sj.obapm;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbSqliteOpenHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mDB;
    private static final int DATABASE_VERSION = 6;
    public static final String DBFILE_USER = "userinfo.db" ;



    public DbSqliteOpenHelper(Context context) {
        super(context, DBFILE_USER, null, DATABASE_VERSION);
        mDB = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.SQL_CREATE_USER_TBL);
        db.execSQL(DBContract.SQL_CREATE_USER_MEAL_TBL);
        db.execSQL(DBContract.SQL_CREATE_USER_NUTRIENT_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(DBContract.SQL_DROP_USER_TBL);
        db.execSQL(DBContract.SQL_DROP_USER_MEAL_TBL);
        db.execSQL(DBContract.SQL_DROP_USER_NUTRIENT_TBL);
        onCreate(db) ;
    }

    public void close(){
        mDB.close();
    }

    public long insertUserColumn(String userid, String pwd, String name, String gender, int age ,float h ,float w ){
        ContentValues values = new ContentValues();
        values.put(DBContract.COL_MID , userid);
        values.put(DBContract.COL_MPWD, pwd);
        values.put(DBContract.COL_NAME, name);
        values.put(DBContract.COL_GEN , gender);
        values.put(DBContract.COL_AGE , age);
        values.put(DBContract.COL_H , h);
        values.put(DBContract.COL_W , w);
        return mDB.insert(DBContract.TBL_USER, null, values);
    }

    public long insertUserNutColumn(float cal ,float carbo, float pro, float fat, float vita, float diet, float sodi,float calcium  ){
        ContentValues values = new ContentValues();
        values.put(DBContract.COL_CAL , cal);
        values.put(DBContract.COL_CARBO , carbo);
        values.put(DBContract.COL_PROTEIN , pro);
        values.put(DBContract.COL_FAT     , fat);
        values.put(DBContract.COL_VITAMIN , vita);
        values.put(DBContract.COL_DIETARY , diet);
        values.put(DBContract.COL_SODIUM  , sodi);
        values.put(DBContract.COL_CALCIUM , calcium);

        return mDB.insert(DBContract.TBL_USER_NUTRIENT, null, values);
    }

}
//https://m.blog.naver.com/PostView.nhn?blogId=nife0719&logNo=221035148567&proxyReferer=http%3A%2F%2Fwww.google.co.kr%2Furl%3Fsa%3Dt%26rct%3Dj%26q%3D%26esrc%3Ds%26source%3Dweb%26cd%3D6%26ved%3D2ahUKEwjRksCj0dHeAhUO6LwKHQ0pAxUQFjAFegQICBAB%26url%3Dhttp%253A%252F%252Fm.blog.naver.com%252Fnife0719%252F221035148567%26usg%3DAOvVaw058DPtXRm2iiUBQS9uXeR1
