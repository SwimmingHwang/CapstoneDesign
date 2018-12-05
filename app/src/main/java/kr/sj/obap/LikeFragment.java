package kr.sj.obap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeFragment extends Fragment {

    NetworkService networkService;
    private String myUrl = "http://52.78.67.243:8000";


    //@BindView(R.id.usermeal_tv1) TextView usermeal_tv1;
    @BindView(R.id.tv_breakfast) TextView tv_breakfast;
    @BindView(R.id.tv_lunch) TextView tv_lunch;
    @BindView(R.id.tv_dinner) TextView tv_dinner;

    //1 tv_breakfast
    public void usermeal_breakfast()
    {
        //GET

        Call<List<UserMeal>> usermealCall = networkService.get_usermeal();

        usermealCall.enqueue(new Callback<List<UserMeal>>() {
            @Override
            public void onResponse(Call<List<UserMeal>> call, Response<List<UserMeal>> response) {
                if(response.isSuccessful()) {
                    List<UserMeal> breakfastList = response.body();
                    String breakfast_txt = "";
                    for(UserMeal breakfast : breakfastList){
                        breakfast_txt += breakfast.getBreakfast() + "\n";

                    }
                    Log.i("breakfastList", breakfast_txt);
                    tv_breakfast.setText(breakfast_txt);
                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                }
            }

            @Override
            public void onFailure(Call<List<UserMeal>> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }

    //2 tv_lunch
    public void usermeal_lunch()
    {
        //GET

        Call<List<UserMeal>> usermealCall = networkService.get_usermeal();

        usermealCall.enqueue(new Callback<List<UserMeal>>() {
            @Override
            public void onResponse(Call<List<UserMeal>> call, Response<List<UserMeal>> response) {
                if(response.isSuccessful()) {
                    List<UserMeal> lunchList = response.body();
                    String lunch_txt = "";
                    for(UserMeal lunch : lunchList){
                        lunch_txt += lunch.getLunch() + "\n";

                    }
                    Log.i("lunchList", lunch_txt);
                    tv_lunch.setText(lunch_txt);
                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                }
            }

            @Override
            public void onFailure(Call<List<UserMeal>> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }
    //tv_dinner
    public void usermeal_dinner()
    {
        //GET

        Call<List<UserMeal>> usermealCall = networkService.get_usermeal();

        usermealCall.enqueue(new Callback<List<UserMeal>>() {
            @Override
            public void onResponse(Call<List<UserMeal>> call, Response<List<UserMeal>> response) {
                if(response.isSuccessful()) {
                    List<UserMeal> dinnerList = response.body();

                    String dinner_txt = "";
                    for(UserMeal dinner : dinnerList){
                        dinner_txt += dinner.getDinner() + "\n";

                    }
                    Log.i("dinnerList", dinner_txt);
                    tv_dinner.setText(dinner_txt);
                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                }
            }

            @Override
            public void onFailure(Call<List<UserMeal>> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_diary,null);

        //ButterKnife.bind(this);

        ImageView iv_cam=(ImageView) view.findViewById(R.id.iv_dycam);
        iv_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).captureCamera();
            }
        });
        ApplicationController application = ApplicationController.getInstance();
        //application.buildNetworkService("61fa624f.ngrok.io");
        application.buildNetworkService("52.78.67.243", 8000);
        networkService = ApplicationController.getInstance().getNetworkService();

        //usermeal_tv1=(TextView)view.findViewById(R.id.usermeal_tv1);

        usermeal_breakfast();
        tv_breakfast=(TextView)view.findViewById(R.id.tv_breakfast);
        usermeal_lunch();
        tv_lunch=(TextView)view.findViewById(R.id.tv_lunch);
        usermeal_dinner();
        tv_dinner=(TextView)view.findViewById(R.id.tv_dinner);

        return view;
    }

}
