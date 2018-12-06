package kr.sj.obap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;

import static kr.sj.obap.R.id.webView;

public class MypageFragment extends Fragment {

    private WebView mWebView;
    private ListView foodlikelistview;


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_mypage,null);
        String myUrl = "http://52.78.67.243:8000";

        //websetting은 캐시, 자바스크립트 등의 기능 설정
//        WebSettings webSettings =  mWebView.getSettings();
//        webSettings.setAppCacheEnabled(false);
        //ButterKnife.bind(this);

        // 웹뷰 셋
        mWebView = (WebView)view.findViewById(webView);
        mWebView.setNetworkAvailable(true);
        //mWebView.loadUrl("http://www.pois.co.kr/mobile/login.do");

        mWebView.loadUrl(myUrl + "/obap/"); // 접속 URL
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());

        //// Sets whether the DOM storage API is enabled.
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //캐시 지우는 용
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.clearFormData();

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                Toast.makeText(getContext(),"click되었습니다", Toast.LENGTH_LONG).show();
                return false;
            }
        });


        //흰화면 없애는 용
        //mWebView.setBackgroundColor(0x00000000);
        //mWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        //foodlikelistview = (ListView)view.findViewById(R.id.lv_foodlikelist);
       // setFoodList();
        return view;
    }

    //webview------------------------

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
    //------------------------------------------------------

    /*
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
    }*/
}
