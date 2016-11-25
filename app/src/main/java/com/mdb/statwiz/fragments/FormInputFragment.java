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
import com.mdb.statwiz.activities.MainActivity;
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
    private String functionName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.form_page, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.form_input_list);
        outputPager = (ViewPager) layout.findViewById(R.id.output_pager);
        left = (FloatingActionButton) layout.findViewById(R.id.form_input_left);
        right = (FloatingActionButton) layout.findViewById(R.id.form_input_right);

        functionName = getArguments().getString(MainActivity.FUNCTIONTYPE);

        // Initialize RecyclerView
        List<String> testList = new ArrayList<>();

        switch(functionName) {
            case "Normal PDF":
                testList.add("Mean");
                testList.add("Standard Deviation");
                testList.add("X-Value");
                break;
            case "Normal CDF":
                testList.add("Mean");
                testList.add("Standard Deviation");
                testList.add("Lower Bound");
                testList.add("Upper Bound");
                break;
            case "Inverse Normal":
                testList.add("Mean");
                testList.add("Standard Deviation");
                testList.add("Area");
                break;
            case "t PDF":
                testList.add("Degrees of Freedom");
                testList.add("X-Value");
                break;
            case "t CDF":
                testList.add("Degrees of Freedom");
                testList.add("Lower Bound");
                testList.add("Upper Bound");
                break;
            case "Inverse t":
                testList.add("Degrees of Freedom");
                testList.add("Area");
                break;
            case "Chi Squared PDF":
                testList.add("Degrees of Freedom");
                testList.add("X-Value");
                break;
            case "Chi Squared CDF":
                testList.add("Degrees of Freedom");
                testList.add("Lower Bound");
                testList.add("Upper Bound");
                break;
            case "Inverse Chi Squared":
                testList.add("Degrees of Freedom");
                testList.add("Area");
                break;
            case "F PDF":
                testList.add("Numerator Degrees of Freedom");
                testList.add("Denominator Degrees of Freedom");
                testList.add("X-Value");
                break;
            case "F CDF":
                testList.add("Numerator Degrees of Freedom");
                testList.add("Denominator Degrees of Freedom");
                testList.add("Lower Bound");
                testList.add("Upper Bound");
                break;
            case "Inverse F":
                testList.add("Numerator Degrees of Freedom");
                testList.add("Denominator Degrees of Freedom");
                testList.add("Area");
                break;
            case "Binomial PDF":
            case "Geometric PDF":
                testList.add("Numerator of Trials");
                testList.add("Probability of Success");
                testList.add("X-Value");
                break;
            case "Binomial CDF":
            case "Geometric CDF":
                testList.add("Numerator of Trials");
                testList.add("Probability of Success");
                testList.add("Lower Bound");
                testList.add("Upper Bound");
                break;
            case "Poisson PDF":
                testList.add("Lambda");
                testList.add("X-Value");
                break;
            case "Poisson CDF":
                testList.add("Lambda");
                testList.add("Lower Bound");
                testList.add("Upper Bound");
                break;
            case "Permutations":
            case "Combinations":
                testList.add("n");
                testList.add("r");
                break;
            case "Factorial":
                testList.add("n");
                break;

        }
       
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

        return layout;
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
