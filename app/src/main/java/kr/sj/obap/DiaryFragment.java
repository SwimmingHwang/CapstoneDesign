package kr.sj.obap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DiaryFragment extends Fragment {
    boolean HEARTED =false;
    Button Bt_heart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fooddetail,null);
        Bt_heart= (Button)view.findViewById(R.id.bt_heart);
        Bt_heart.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!HEARTED){
                    Bt_heart.setSelected(true);
                    HEARTED=true;
                }
                else{
                    Bt_heart.setSelected(false);
                    HEARTED=false;
                }
            }
        });

        return view;
    }
}
