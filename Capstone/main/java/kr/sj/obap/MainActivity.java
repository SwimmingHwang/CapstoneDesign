package kr.sj.obap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_STORAGE =1111;
    private static final int REQUEST_TAKE_PHOTO =2222;

    ActionBar ab;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment frag = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    frag = new HomeFragment();
                    break;
                case R.id.navigation_diary:
                    //mTextMessage.setText(R.string.title_notifications);
                    frag = new DiaryFragment();
                    break;
                case R.id.navigation_camera:
                    //mTextMessage.setText(R.string.title_dashboard);
                    frag = new CameraFragment();
                    ab.setTitle("음식입력") ;
                    break;
                case R.id.navigation_like:
                    //mTextMessage.setText(R.string.title_dashboard);
                    frag = new LikeFragment();
                    break;
                case R.id.navigation_mypage:
                    //mTextMessage.setText(R.string.title_dashboard);
                    frag = new MypageFragment();
                    break;

            }
            return loadFragment(frag);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ab= getSupportActionBar() ;
        checkPermission();
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_frag, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void checkPermission(){
        //http://g-y-e-o-m.tistory.com/47
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)this, new String[]{Manifest.permission.CAMERA},MY_PERMISSION_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity)this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_STORAGE);
        }
    }
}
