package com.example.asus.thesmartofairing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {

    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;
    private StoreAdapt mStoreAdapt;
    private List<String> tv_list;
    private List<String> iv_list1;
    private List<String> iv_list2;
    private List<String> iv_list3;
    private Okhttphelper mOkhttphelper = Okhttphelper.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       /* if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_store,container,false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }*/
        Fresco.initialize(this.getActivity());
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_store);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider_layout);
        tv_list = new ArrayList<>();
        iv_list1 = new ArrayList<>();
        iv_list2 = new ArrayList<>();
        iv_list3 = new ArrayList<>();
        initSlider();
        // Inflate the layout for this fragment
        initRecyclerView();

        return view;
    }

    private void httpclientset() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://192.168.1.107:8080/app/get_data.json");
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity,"utf-8");
                        parseGson(response);
                    }else{
                        httpGet.abort();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseGson(String response) {
        Gson gson = new Gson();
        List<StoreGson> storeList = gson.fromJson(response,new TypeToken<List<StoreGson>>(){}.getType());
        for (StoreGson store: storeList){
            tv_list.add(store.getName());
            iv_list1.add(store.getUrione());
            iv_list2.add(store.getUritwo());
            iv_list3.add(store.getUrithree());
        }
    }


    private void initRecyclerView() {
        String url = "http://192.168.1.107:8080/app/get_data.json";
        mOkhttphelper.get(url, new BaseCallBack<List<StoreGson>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, List<StoreGson> storeGsons) {
                initData(storeGsons);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initData(List<StoreGson> storeGsons) {
        mStoreAdapt = new StoreAdapt(this.getActivity(),storeGsons);
        mRecyclerView.setAdapter(mStoreAdapt);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
    /*private void initRecyclerView(View view) {
        httpclientset();
        mStoreAdapt = new StoreAdapt(this.getActivity(),tv_list,iv_list1,iv_list2,iv_list3);
        mRecyclerView.setAdapter(mStoreAdapt);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }*/

        private void initSlider(){
        TextSliderView textSliderView = new TextSliderView(this.getActivity());
        textSliderView.description("秒杀")
                .image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(StoreFragment.this.getActivity(),"秒杀",Toast.LENGTH_LONG).show();
            }
        });

        TextSliderView textSliderView1 = new TextSliderView(this.getActivity());
        textSliderView1.description("女主角上场")
                .image("http://www.91baihui.com/images//20111018/83ff140f8e124bbe.jpg");
        textSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(StoreFragment.this.getActivity(),"女主角上场",Toast.LENGTH_LONG).show();
            }
        });

        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());
        textSliderView2.description("帆布态度")
                .image("http://sc.jb51.net/uploads/allimg/140305/10-140305220335391.jpg");
        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(StoreFragment.this.getActivity(),"帆布态度",Toast.LENGTH_LONG).show();
            }
        });

        TextSliderView textSliderView3 = new TextSliderView(this.getActivity());
        textSliderView3.description("轻松淘")
                .image("http://pic2.ooopic.com/11/72/89/06b1OOOPICe1.jpg");
        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(StoreFragment.this.getActivity(),"轻松淘",Toast.LENGTH_LONG).show();
            }
        });

        mSliderLayout.addSlider(textSliderView);
        mSliderLayout.addSlider(textSliderView1);
        mSliderLayout.addSlider(textSliderView2);
        mSliderLayout.addSlider(textSliderView3);


        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.setDuration(3000);


        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
