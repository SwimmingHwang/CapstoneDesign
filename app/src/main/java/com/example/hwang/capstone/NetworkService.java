package com.example.hwang.capstone;
import java.util.List;

import okhttp3.internal.Version;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

//Call Interface


public interface NetworkService {
    @POST("/web/foods/")
    Call<Food> post_food(@Body Food food);

    @PATCH("/web/foods/{pk}/")
    Call<Food> patch_food(@Path("pk") int pk, @Body Food food);

    @DELETE("/web/foods/{pk}/")
    Call<Food> delete_food(@Path("pk") int pk);

    @GET("/web/foods/")
    Call<List<Food>> get_food();

    @GET("/web/foods/{pk}/")
    Call<Food> get_pk_food(@Path("pk") int pk);


}


