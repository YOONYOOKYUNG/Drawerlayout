package com.cookandroid.windowairfresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class Main_Fragment3 extends Fragment {
    ViewPager2 viewpager;
    private Button windowadd;

    public Main_Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup)  inflater.inflate(R.layout.activity_main_fragment3, container, false);
        viewpager = getActivity().findViewById(R.id.viewpager);
        windowadd = (Button) view.findViewById(R.id.windowadd);
        windowadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WindowlistActivity.class));
            }
        });
        return view;}}
