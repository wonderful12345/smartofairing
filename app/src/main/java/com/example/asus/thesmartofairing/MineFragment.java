package com.example.asus.thesmartofairing;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    private SharedPreferences mSharedPreferences;
    private TextView tv_login;
    private View mView;
    private SimpleDraweeView mSimpleDraweeView;
    private GenericDraweeHierarchyBuilder builder;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(getContext());
        mView = inflater.inflate(R.layout.fragment_mine,container,false);
        initView();
        mSharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String c = mSharedPreferences.getString("boolean","");
        if (c.equals("true")){
            tv_login.setText("13794575196");
            tv_login.setClickable(false);
            GenericDraweeHierarchy hierarchy = builder.setPlaceholderImage(getActivity().getDrawable(R.drawable.image_login))
                    .setRoundingParams(RoundingParams.fromCornersRadius(40)).build();
            mSimpleDraweeView.setHierarchy(hierarchy);
        }else {
            tv_login.setText("点击登录");
            GenericDraweeHierarchy hierarchy = builder.setPlaceholderImage(getActivity().getDrawable(R.drawable.auser))
                    .build();
            mSimpleDraweeView.setHierarchy(hierarchy);
        }
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h = mSharedPreferences.getString("boolean","");
                if (h.equals("false")){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),Login.class);
                    startActivity(intent);
                    getActivity().finish();
                }

            }
        });
        // Inflate the layout for this fragment
        return mView;
    }

    private void initView() {
        tv_login = (TextView) mView.findViewById(R.id.tv_mine1);
        mSimpleDraweeView = (SimpleDraweeView) mView.findViewById(R.id.simpledraweeview_login);
        builder = new GenericDraweeHierarchyBuilder(getResources());


    }


}
