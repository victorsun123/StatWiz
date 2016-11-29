package com.mdb.statwiz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdb.statwiz.R;

import java.util.Locale;

/**
 * Created by Sayan Paul on 11/19/2016.
 */

public class FormOutputFragment extends Fragment {
    private static final String OUPUT_NAME_ARG = "OUTPUTNAME";
    private static final String OUTPUT_VALUE_ARG = "OUTPUTVALUE";
    private static final String POSITION_ARG = "POSITION";
    private static final String NUM_OUTPUTS_ARG = "NUMOUTPUTS";
    private static final String DEFAULT_OUTPUT_NAME = "Output";
    private static final Double DEFAULT_OUTPUT_VALUE = 0.0;
    public int position;
    public int numOutputs;
    private String outputName;
    private Double outputValue;

    public static FormOutputFragment newInstance(String outputName, Double outputValue, int position, int numOutputs) {
        FormOutputFragment instance = new FormOutputFragment();
        Bundle args = new Bundle();
        args.putString(OUPUT_NAME_ARG, outputName);
        args.putDouble(OUTPUT_VALUE_ARG, outputValue);
        args.putInt(POSITION_ARG, position);
        args.putInt(NUM_OUTPUTS_ARG, numOutputs);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        outputName = args.getString(OUPUT_NAME_ARG, DEFAULT_OUTPUT_NAME);
        outputValue = args.getDouble(OUTPUT_VALUE_ARG, DEFAULT_OUTPUT_VALUE);
        position = args.getInt(POSITION_ARG);
        numOutputs = args.getInt(NUM_OUTPUTS_ARG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.form_output_fragment, container, false);
        TextView outputNameLabel = (TextView) layout.findViewById(R.id.output_name);
        TextView outputValueLabel = (TextView) layout.findViewById(R.id.output_value);
        TextView positionLabel = (TextView) layout.findViewById(R.id.position);
        outputNameLabel.setText(outputName);
        outputValueLabel.setText(String.format(Locale.getDefault(), "%.4f", outputValue));
        positionLabel.setText((position + 1) + "/" + numOutputs);
        return layout;
    }
}
