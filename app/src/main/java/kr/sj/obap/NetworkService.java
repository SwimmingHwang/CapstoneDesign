package kr.sj.obap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

//Call Interface


public interface NetworkService {
    @POST("/obap/foods/")
    Call<Food> post_food(@Body Food food);

    @PATCH("/obap/foods/{pk}/")
    Call<Food> patch_food(@Path("pk") int pk, @Body Food food);

    @DELETE("/obap/foods/{pk}/")
    Call<Food> delete_food(@Path("pk") int pk);

    @GET("/obap/foods/")
    Call<List<Food>> get_food();


    //usermeal
    @GET("/obap/usermeals/")
    Call<List<UserMeal>> get_usermeal();

    @GET("/obap/foods/{pk}/")
    Call<Food> get_pk_food(@Path("pk") int pk);




}


