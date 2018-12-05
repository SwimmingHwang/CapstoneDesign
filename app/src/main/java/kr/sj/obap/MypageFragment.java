package kr.sj.obap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MypageFragment extends Fragment {

    private ListView foodlikelistview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mypage,null);

        foodlikelistview = (ListView)view.findViewById(R.id.lv_foodlikelist);
        setFoodList();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFoodRate();
    }


    private void setFoodList(){
        FoodListAdapter mAdapter = new FoodListAdapter();
        for(int i =0;i<10;i++){
            mAdapter.addItem(ContextCompat.getDrawable(getContext(),R.drawable.tt),"name"+i,i);
        }
        foodlikelistview.setAdapter(mAdapter);
    }

    private void getFoodRate(){
        FoodLikeItem item;
        for(int i =0;i<10;i++){
            item = (FoodLikeItem)foodlikelistview.getAdapter().getItem(i);
            float rate = item.getRate();
            Log.e("rate",i+ "i  ="+ rate);
        }
    }
}
