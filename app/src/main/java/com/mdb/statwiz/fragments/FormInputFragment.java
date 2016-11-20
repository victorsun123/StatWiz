package com.mdb.statwiz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdb.statwiz.R;
import com.mdb.statwiz.adapters.FormInputAdapter;
import com.mdb.statwiz.adapters.FormOutputPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sayan Paul on 11/17/2016.
 */

public class FormInputFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private FormInputAdapter mAdapter;
    private ViewPager outputPager;
    private FormOutputPagerAdapter outputPagerAdapter;
    private FloatingActionButton left, right;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.form_page, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.form_input_list);
        outputPager = (ViewPager) layout.findViewById(R.id.output_pager);
        left = (FloatingActionButton) layout.findViewById(R.id.form_input_left);
        right = (FloatingActionButton) layout.findViewById(R.id.form_input_right);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize RecyclerView
        List<String> testList = new ArrayList<>(1);
        testList.add("jkl;");
        testList.add("qwerty");
        mAdapter = new FormInputAdapter(getActivity(), testList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize ViewPager
        HashMap<String, Double> testOutputs = new HashMap<>();
        testOutputs.put("asdf", 123.0);
        testOutputs.put("jkl;", 456.0);
        outputPagerAdapter = new FormOutputPagerAdapter(getActivity().getSupportFragmentManager(), testOutputs);
        outputPager.setAdapter(outputPagerAdapter);

        // Initializes left/right buttons for ViewPager
        left.setOnClickListener(this);
        right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.form_input_left:
                outputPager.setCurrentItem(outputPager.getCurrentItem() - 1, true);
                break;
            case R.id.form_input_right:
                outputPager.setCurrentItem(outputPager.getCurrentItem() + 1, true);
                break;
        }
    }
}
