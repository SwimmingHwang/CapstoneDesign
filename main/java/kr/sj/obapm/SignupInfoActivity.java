package kr.sj.obapm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class SignupInfoActivity extends AppCompatActivity {
    ImageButton btn_signupInfoEnd;

    public static DbSqliteOpenHelper dbHelper;
    private String id,pwd,name,gender;
    private int age;
    private float height,weight;

    private Spinner genderSpinner,ageSpinner,heightSpinner,weightSpinner;
    final ArrayList<String> genlist = new ArrayList<>();
    final ArrayList<Integer> agelist = new ArrayList<>();
    final ArrayList<Float> heightlist = new ArrayList<>();
    final ArrayList<Float> weightlist = new ArrayList<>();
    ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupinfo);

        //http://yoo-hyeok.tistory.com/15  액티비티간 데이터전송
        Intent receiveintent = getIntent();
        id=receiveintent.getStringExtra("ID");
        pwd=receiveintent.getStringExtra("PWD");
        name=receiveintent.getStringExtra("NAME");
        Log.e("signupinfo","id "+id+"  pwd "+pwd+"  name "+name);

        init_spinner();

        dbHelper = new DbSqliteOpenHelper(this);

        btn_signupInfoEnd = (ImageButton)findViewById(R.id.btn_signinfonext);
        btn_signupInfoEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertUserColumn(id, pwd, name, gender,age,height,weight);
                Log.e("btn_signupInfoEnd",id+"  "+pwd+"  "+ name+"  "+ gender+"  "+age+"  "+height+"  "+weight);
                dbHelper.insertUserNutColumn(1.1f,2.2f,3.3f,4.4f,5.5f,6.6f,7.8f,8.9f);
                Intent intent = new Intent(SignupInfoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init_spinner(){
        genderSpinner =(Spinner)findViewById(R.id.sp_gen) ;
        genlist.add("female");
        genlist.add("male");
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, genlist);
        genderSpinner.setAdapter(spinnerAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                gender = (String) genderSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ageSpinner =(Spinner)findViewById(R.id.sp_age) ;
        for(int i=1;i<100;i++)
            agelist.add(i);
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, agelist);
        ageSpinner.setAdapter(spinnerAdapter);
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                age = (int) ageSpinner.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });
        ageSpinner.setSelection(20);

    heightSpinner =(Spinner)findViewById(R.id.sp_h) ;
        for(float i=100;i<250;i++)
            heightlist.add(i);
    spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, heightlist);
        heightSpinner.setAdapter(spinnerAdapter);
        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                height = (float) heightSpinner.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        heightSpinner.setSelection(65);

        weightSpinner =(Spinner)findViewById(R.id.sp_w) ;
        for(float i=30;i<200;i++)
            weightlist.add(i);
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, weightlist);
        weightSpinner.setAdapter(spinnerAdapter);
        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                weight = (float) weightSpinner.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        weightSpinner.setSelection(30);
    }

}
