package com.example.hwang.capstone;
/*
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hwang.capstone.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Version;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTakePhotoIntent();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }

            ((ImageView)findViewById(R.id.photo)).setImageBitmap(rotate(bitmap, exifDegree));
        }
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      ///* prefix
                ".jpg",        // /* suffix
                storageDir    //      /* directory
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }
}
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//import okhttp3.internal.Version;

import okhttp3.internal.Version;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    //public final String TAG = "KJH";
    private NetworkService networkService;
    @BindView(R.id.tv1) TextView tv1;
    @BindView(R.id.tv2) TextView tv2;
    @BindView(R.id.tv3) TextView tv3;
    @BindView(R.id.tv4) TextView tv4;
////    @BindView(R.id.tv5) TextView tv5;
////    @BindView(R.id.tv6) TextView tv6;
////    @BindView(R.id.tv7) TextView tv7;
////    @BindView(R.id.tv8) TextView tv8;
////    @BindView(R.id.tv9) TextView tv9;

    @OnClick(R.id.bt1)
    public void bt1_Click()
    {
        //GET

        Call<List<Food>> foodCall = networkService.get_food();

        foodCall.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if(response.isSuccessful()) {
                    List<Food> foodList = response.body();
                    String food_txt = "";
                    for(Food food : foodList){
                        food_txt += food.getFood() + "\n";
                    }

                    tv1.setText(food_txt);
                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }

    @OnClick(R.id.bt2)
    public void bt2_Click(){
        //POST

        Food food = new Food();
        food.setFood("bt2 set ver");
        Call<Food> postCall = networkService.post_food(food);
        postCall.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if( response.isSuccessful()) {
                    tv2.setText("등록");
                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                }

            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }

    @OnClick(R.id.bt3)
    public void bt3_click(){
        //PATCH
        Food food = new Food();
        food.setFood("bt3 set Ver");
        Call<Food> patchCall = networkService.patch_food(1, food);
        patchCall.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if( response.isSuccessful()) {
                    tv3.setText("업데이트");
                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }

    @OnClick(R.id.bt4)
    public void bt4_click(){
        //DELETE
        Call<Food> deleteCall = networkService.delete_food(1);
        deleteCall.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if( response.isSuccessful()) {
                    tv4.setText("삭제");
                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }
//
//    @OnClick(R.id.bt5)
//    public void bt5_click(){
//        //Restaurant GET
//        Call<List<Restaurant>> getCall = networkService.get_restaruant();
//        getCall.enqueue(new Callback<List<Restaurant>>() {
//            @Override
//            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
//                if( response.isSuccessful()) {
//                    List<Restaurant> restaurantList = response.body();
//
//                    String restaurant_txt = "";
//                    for(Restaurant restaurant : restaurantList){
//                        restaurant_txt += restaurant.getName() +
//                                restaurant.getAddress() +
//                                restaurant.getCategory() +
//                                restaurant.getWeather() +
//                                restaurant.getDistance() +
//                                restaurant.getDescription() +
//                                "\n";
//                    }
//
//                    tv5.setText(restaurant_txt);
//                } else {
//                    int StatusCode = response.code();
//                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
//    @OnClick(R.id.bt6)
//    public void bt6_click(){
//        //Restaurant POST
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("음식점1");
//        restaurant.setAddress("장소1");
//        restaurant.setCategory(3);
//        restaurant.setWeather(3);
//        restaurant.setDistance(3);
//        restaurant.setDescription("설명1");
//
//        Call<Restaurant> postCall = networkService.post_restaruant(restaurant);
//        postCall.enqueue(new Callback<Restaurant>() {
//            @Override
//            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
//                if( response.isSuccessful()) {
//                    tv6.setText("등록");
//                } else {
//                    int StatusCode = response.code();
//                    try {
//                        Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Restaurant> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
//    @OnClick(R.id.bt7)
//    public void bt7_click(){
//        //Restaurant PATCH
//        //Full or partial patch available
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("이름22");
//        restaurant.setAddress("장소22");
//        restaurant.setCategory(3);
//        restaurant.setWeather(1);
//        restaurant.setDistance(2);
//        restaurant.setDescription("장소22");
//
//        Call<Restaurant> patchCall = networkService.patch_restaruant(1, restaurant);
//        patchCall.enqueue(new Callback<Restaurant>() {
//            @Override
//            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
//                if( response.isSuccessful()) {
//                    tv7.setText("업데이트");
//                } else {
//                    int StatusCode = response.code();
//                    try {
//                        Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Restaurant> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
//    @OnClick(R.id.bt8)
//    public void bt8_click(){
//        //Restaurant DELETE
//        Call<Restaurant> deleteCall = networkService.delete_restaruant(2);
//        deleteCall.enqueue(new Callback<Restaurant>() {
//
//            @Override
//            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
//                if ( response.isSuccessful()) {
//                    tv8.setText("삭제");
//                } else {
//                    int StatusCode = response.code();
//                    try {
//                        Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Restaurant> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }
//
//    @OnClick(R.id.bt9)
//    public void bt9_click(){
//        Call<List<Restaurant>> get_weather_pk_Call = networkService.get_weather_pk_restaruant(1);
//        get_weather_pk_Call.enqueue(new Callback<List<Restaurant>>() {
//            @Override
//            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
//                if( response.isSuccessful()) {
//                    List<Restaurant> restaurantList = response.body();
//
//                    String restaurant_txt = "";
//                    for(Restaurant restaurant : restaurantList){
//                        restaurant_txt += restaurant.getName() +
//                                restaurant.getAddress() +
//                                restaurant.getCategory() +
//                                restaurant.getWeather() +
//                                restaurant.getDistance() +
//                                restaurant.getDescription() +
//                                "\n";
//                    }
//
//                    tv9.setText(restaurant_txt);
//                } else {
//                    int StatusCode = response.code();
//                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
//                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
//            }
//        });
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ApplicationController application = ApplicationController.getInstance();
        //application.buildNetworkService("61fa624f.ngrok.io");
        application.buildNetworkService("52.78.67.243", 8000);
        networkService = ApplicationController.getInstance().getNetworkService();
    }
}

