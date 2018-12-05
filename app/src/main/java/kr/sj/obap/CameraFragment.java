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

    ImageView iv_cam;
    ImageButton  btn_send;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_camera,null);

        btn_send=(ImageButton)view.findViewById(R.id.btn_sendimg);
        iv_cam = (ImageView)view.findViewById(R.id.iv_cam);

        String str=  getArguments().getString("imageURI");
        Uri myUri = Uri.parse(str);
        Log.e("camac",myUri.toString());
        iv_cam.setImageURI(myUri);

        return view;
    }


}
