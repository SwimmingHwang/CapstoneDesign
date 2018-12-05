package kr.sj.obap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_STORAGE =1111;
    private static final int REQUEST_TAKE_PHOTO =2222;

    Uri imageUri;//,photoUri;
    String mCurrentPhotoPath;

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
                    ab.setTitle("") ;
                    break;
                case R.id.navigation_diary:
                    //mTextMessage.setText(R.string.title_notifications);
                    frag = new DiaryFragment();
                    ab.setTitle("식사 다이어리") ;
                    break;
                case R.id.navigation_camera:
                    //mTextMessage.setText(R.string.title_dashboard);
                    ab.setTitle("음식 입력") ;
                    captureCamera();
                    break;
                case R.id.navigation_like:
                    //mTextMessage.setText(R.string.title_dashboard);
                    frag = new LikeFragment();
                    ab.setTitle("저장 목록") ;
                    break;
                case R.id.navigation_mypage:
                    //mTextMessage.setText(R.string.title_dashboard);
                    frag = new MypageFragment();
                    ab.setTitle("마이페이지") ;
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

    public void captureCamera(){
        String state = Environment.getExternalStorageState();
        //외장 메모리 검사
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager())!=null){
                File photoFile=null;
                try{
                    photoFile = createImageFile();
                }catch (IOException ex){
                    Log.e("captureCameran Error",ex.toString());
                }
                if (photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imageUri=providerURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,providerURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }
        else{
            Toast.makeText(this,"저장 공간 접근 불가",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp+".jpg";
        File imageFile =null;
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/Pictures","obap");

        if(!storageDir.exists()){
            Log.i(" mcurrentPhotopath",storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath=imageFile.getAbsolutePath();

        return imageFile;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_TAKE_PHOTO:
                if(resultCode == Activity.RESULT_OK){
                    try{
                        Log.i("REQUEST_TAKE_PHOTO","OK");
                        galleryAddPic();
                        startCropImageActivity();
                    } catch (Exception e){
                        Log.i("REQUEST_TAKE_PHOTO",e.toString());
                    }
                }
                break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == Activity.RESULT_OK) {
                    Uri resultUri = result.getUri();//uri of crop result
                    Bundle bundle = new Bundle();
                    bundle.putString("imageURI", resultUri.toString());
                    Log.e("camac",resultUri.toString());
                    Fragment frag = new CameraFragment();
                    frag.setArguments(bundle);
                    loadFragment(frag);
                    //iv_cam.setImageURI(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Log.e("REQUEST_TAKE_PHOTO",error.toString());
                }

                break;
        }
    }

    private void galleryAddPic(){
        Log.i("galleryAddPic","Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        //photoUri=contentUri;
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this,"사진이 앨범에 저장되었습니다",Toast.LENGTH_SHORT).show();
    }

    private void startCropImageActivity() {
        Log.e("camac","크롭이미지");
        CropImage.activity(imageUri).start(this);
    }
}