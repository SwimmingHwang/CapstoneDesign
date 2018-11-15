package kr.sj.obapm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    Button btn_signupEnd;
    String id,pwd,name;
    EditText etId,etPwd,etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        etId = (EditText) findViewById(R.id.et_id) ;
        etPwd = (EditText) findViewById(R.id.et_pwd) ;
        etName = (EditText) findViewById(R.id.et_name) ;


       btn_signupEnd = (Button)findViewById(R.id.btn_signupnext);
       btn_signupEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupInfoActivity.class);
                id= etId.getText().toString() ;
                pwd = etPwd.getText().toString() ;
                name = etName.getText().toString() ;
                intent.putExtra("ID",id);
                intent.putExtra("PWD",pwd);
                intent.putExtra("NAME",name);
                startActivity(intent);
            }
        });
    }



}
