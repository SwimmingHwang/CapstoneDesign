package kr.sj.obapm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView tv_Cal,tv_Carbo,tv_Prot,tv_Fat,tv_Vita,tv_Diet,tv_Calcium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_Cal=(TextView)findViewById(R.id.tv_cal);
        tv_Carbo=(TextView)findViewById(R.id.tv_carbo);
        tv_Prot=(TextView)findViewById(R.id.tv_prot);
        tv_Fat=(TextView)findViewById(R.id.tv_fat);
        tv_Vita=(TextView)findViewById(R.id.tv_vita);
        tv_Diet=(TextView)findViewById(R.id.tv_diet);
        tv_Calcium=(TextView)findViewById(R.id.tv_calcium);
        load_values();


    }

    private void load_values() {
        SQLiteDatabase db = SignupInfoActivity.dbHelper.getReadableDatabase() ;
        Cursor cursor = db.rawQuery(DBContract.SQL_SELECT_USER_NUTRIENT, null) ;

        if (cursor.moveToFirst()) {
            // 값 가져오기.
            float cal = cursor.getFloat(0) ;
            tv_Cal.setText(Float.toString(cal)) ;

            float carbo = cursor.getFloat(1) ;
            tv_Carbo.setText(Float.toString(carbo)) ;

            float prot = cursor.getFloat(2) ;
            tv_Prot.setText(Float.toString(prot)) ;

            float fat = cursor.getFloat(3) ;
            tv_Fat.setText(Float.toString(fat)) ;

            float vita = cursor.getFloat(4) ;
            tv_Vita.setText(Float.toString(vita)) ;

            float diet = cursor.getFloat(5) ;
            tv_Diet.setText(Float.toString(diet)) ;

            float calcium = cursor.getFloat(6) ;
            tv_Calcium.setText(Float.toString(calcium)) ;

            Log.e("nut",cal +" "+calcium);
        }
    }



}
