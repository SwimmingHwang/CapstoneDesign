package kr.sj.obap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    TextView tv_Cal,tv_Carbo,tv_Prot,tv_Fat,tv_Vita,tv_Diet,tv_Calcium;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,null);

        tv_Cal=(TextView)view.findViewById(R.id.tv_cal);
        tv_Carbo=(TextView)view.findViewById(R.id.tv_carbo);
        tv_Prot=(TextView)view.findViewById(R.id.tv_prot);
        tv_Fat=(TextView)view.findViewById(R.id.tv_fat);
        tv_Vita=(TextView)view.findViewById(R.id.tv_vita);
        tv_Diet=(TextView)view.findViewById(R.id.tv_diet);
        tv_Calcium=(TextView)view.findViewById(R.id.tv_calcium);
        load_values();

        return view;
    }

    private void load_values() {
       /*
            // 값 가져오기.
            float cal = cursor.getFloat(0) ;
            tv_Cal.setText(Float.toString(cal)) ;

            float carbo = cursor.getFloat(1) ;
            tv_Carbo.setText(Float.toString(carbo)) ;

            float prot = cursor.getFloat(2) ;
            tv_Prot.setText(Float.toString(prot)) ;

            float fat = cursor.getFloat(3) ;
            tv_Fat.setText(Float.toString(fat)) ;

            float vita = cursor.getFloat(4) ;
            tv_Vita.setText(Float.toString(vita)) ;

            float diet = cursor.getFloat(5) ;
            tv_Diet.setText(Float.toString(diet)) ;

            float calcium = cursor.getFloat(6) ;
            tv_Calcium.setText(Float.toString(calcium));
            */
    }
}
