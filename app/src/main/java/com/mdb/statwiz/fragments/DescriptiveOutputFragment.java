package com.mdb.statwiz.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdb.statwiz.R;
import com.mdb.statwiz.adapters.DescriptiveAdapter;
import com.mdb.statwiz.utils.Calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * This fragment gathers input data, sends to calculator class to compute, and outputs results
 */

public class DescriptiveOutputFragment extends Fragment {
    //properties to be called from calculator hashmap
    private static final String[] DOUBLE_STATS = {"Median", "Mean", "Standard Deviation", "Standard Error", "Variance", "Min", "Max", "Range", "Q1", "Q3", "IQR", "Kurtosis", "Skewness"};
    //properties to be displayed on outputpage
    private static final String[] PROPERTIES = {"Count", "Median", "Mean", "Standard Deviation", "Standard Error", "Variance", "Min", "Max", "Range", "Q1", "Q3", "IQR", "Kurtosis", "Skewness", "Mode(s)",};

    public ArrayList<String> values;
    private RecyclerView resultsRV;
    private DescriptiveAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.descriptive_output, container, false);

        Bundle args = getArguments();
        double[] data = args.getDoubleArray("inputAsNumbers");

        LinkedHashMap<String, Object> descriptiveFunction = Calculator.descriptiveStats(data);      //send inputs to calculator function to implement Apache calculations
        values = new ArrayList<>();                                                                 //arraylist containing output values
        values.add(Integer.toString(((Double) descriptiveFunction.get("Count")).intValue()));       //count and mode calculations are retrieved separately due to different output formatting
        DecimalFormat dfFormat = new DecimalFormat("#.####");
        for (String stat : DOUBLE_STATS)                                                            //retrieve values for all other properties
            values.add(dfFormat.format(descriptiveFunction.get(stat)));
        values.add((String) descriptiveFunction.get("Mode"));

        resultsRV = (RecyclerView) layout.findViewById(R.id.sampling_recycler_view);
        resultsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DescriptiveAdapter(getContext(), Arrays.asList(PROPERTIES), values);
        resultsRV.setAdapter(adapter);

        FloatingActionButton returnToInput = (FloatingActionButton) layout.findViewById(R.id.return_to_input);
        returnToInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new DescriptiveInputFragment())
                        .addToBackStack("outputToInput").commit();
            }
        });

        return layout;
    }
}
