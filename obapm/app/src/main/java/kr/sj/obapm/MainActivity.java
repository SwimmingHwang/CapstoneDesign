package kr.sj.obapm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;


public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_STORAGE =1111;
    private static final int REQUEST_TAKE_PHOTO =2222;
    private static final int REQUEST_IMAGE_CROP =3333;

    ImageView iv_cam;
    ImageButton btn_cap, btn_send;
    Uri imageUri;//,photoUri;
    String mCurrentPhotoPath;
    List<NameValuePair> aList ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#57AD56")));
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("음식입력") ;

        btn_cap = (ImageButton)findViewById(R.id.btn_capture);
        btn_send=(ImageButton)findViewById(R.id.btn_sendimg);
        iv_cam = (ImageView)findViewById(R.id.iv_cam);
        aList = new ArrayList<>();

        btn_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureCamera();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendImagetoServer("http://52.78.67.243:8000/web/base",aList);
            }
        });

        checkPermission();
    }

    public void sendImagetoServer(String url, List<NameValuePair> nameValuePairs){

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(url);

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            for(int index=0; index < nameValuePairs.size(); index++) {
                if(nameValuePairs.get(index).getName().equalsIgnoreCase("image")) {
                    // key값이 image일 경우 데이터 전송을 위해 FileBody 사용
                    builder.addPart(nameValuePairs.get(index).getName(), new FileBody(new File (nameValuePairs.get(index).getValue())));
                } else {
                    // 그냥 string 데이터
                    builder.addPart(nameValuePairs.get(index).getName(), new StringBody(nameValuePairs.get(index).getValue()));
                }
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost, localContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void captureCamera(){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                 if (resultCode == RESULT_OK) {
                     Uri resultUri = result.getUri();//uri of crop result
                     iv_cam.setImageURI(resultUri);
                     aList.add(new BasicNameValuePair("image",resultUri.toString()));
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
        CropImage.activity(imageUri).start(this);
    }
/*
    public void cropIamge(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(imageUri,"image/*");
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("aspectY",1);
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("return-data",true);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

*/
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
