package kr.sj.obap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraFragment extends Fragment {

    private static final int REQUEST_TAKE_PHOTO =2222;

    ImageView iv_cam;
    ImageButton btn_cap, btn_send;
    Uri imageUri;//,photoUri;
    String mCurrentPhotoPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_camera,null);
        btn_cap = (ImageButton)view.findViewById(R.id.btn_capture);
        btn_send=(ImageButton)view.findViewById(R.id.btn_sendimg);
        iv_cam = (ImageView)view.findViewById(R.id.iv_cam);

        btn_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureCamera();
            }
        });

        return view;
    }

    private void captureCamera(){
        String state = Environment.getExternalStorageState();
        //외장 메모리 검사
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!=null){
                File photoFile=null;
                try{
                    photoFile = createImageFile();
                }catch (IOException ex){
                    Log.e("captureCameran Error",ex.toString());
                }
                if (photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(this.getContext(), getActivity().getPackageName(), photoFile);
                    imageUri=providerURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,providerURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }
        else{
            Toast.makeText(this.getContext(),"저장 공간 접근 불가",Toast.LENGTH_SHORT).show();
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
                    iv_cam.setImageURI(resultUri);
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
        if(getActivity()!=null)
            getActivity().sendBroadcast(mediaScanIntent);
        Toast.makeText(this.getContext(),"사진이 앨범에 저장되었습니다",Toast.LENGTH_SHORT).show();
    }

    private void startCropImageActivity() {
        Log.e("camac","크롭이미지");
        CropImage.activity(imageUri).start(this.getContext(),this);
    }
}
