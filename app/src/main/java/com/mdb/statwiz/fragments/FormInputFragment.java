package com.mdb.statwiz.fragments;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mdb.statwiz.R;
import com.mdb.statwiz.activities.MainActivity;
import com.mdb.statwiz.adapters.FormInputAdapter;
import com.mdb.statwiz.adapters.FormOutputPagerAdapter;
import com.mdb.statwiz.utils.Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

/**
 * Created by Sayan Paul on 11/17/2016.
 */

public class FormInputFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private FormInputAdapter mAdapter;
    private ViewPager outputPager;
    private FormOutputPagerAdapter outputPagerAdapter;
    private FloatingActionButton left, right, calculateOutput;
    private String functionName;
    private List<String> testList;
    private LinkedHashMap<String, Double> testOutputs;
    private FormInputAdapter.FormInputViewHolder holder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.form_page, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.form_input_list);
        outputPager = (ViewPager) layout.findViewById(R.id.output_pager);
        left = (FloatingActionButton) layout.findViewById(R.id.form_input_left);
        right = (FloatingActionButton) layout.findViewById(R.id.form_input_right);
        calculateOutput = (FloatingActionButton) layout.findViewById(R.id.calculate_output);

        functionName = getArguments().getString(MainActivity.FUNCTIONTYPE);

        // Initialize RecyclerView
        testList = new ArrayList<>();

        switch (functionName) {
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
                testList.add("Number of Trials");
                testList.add("Probability of Success");
                testList.add("X-Value");
                break;
            case "Geometric PDF":
                testList.add("Probability of Success");
                testList.add("X-Value");
                break;
            case "Binomial CDF":
                testList.add("Number of Trials");
                testList.add("Probability of Success");
                testList.add("Lower Bound");
                testList.add("Upper Bound");
                break;
            case "Geometric CDF":
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
        testOutputs = new LinkedHashMap<>();
        testOutputs.put("output", 0.0);
        outputPagerAdapter = new FormOutputPagerAdapter(getActivity().getSupportFragmentManager(), testOutputs);
        outputPager.setAdapter(outputPagerAdapter);

        // Initializes left/right buttons for ViewPager
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        calculateOutput.setOnClickListener(this);
        return layout;
    }

    public void updateAdapter() {
        if (testOutputs.size() != 0) {
            outputPagerAdapter = new FormOutputPagerAdapter(getActivity().getSupportFragmentManager(), testOutputs);
            outputPager.setAdapter(outputPagerAdapter);
        }
    }

    public void calculateValues() {
        testOutputs = new LinkedHashMap<>();
        switch (functionName) {
            case "Normal PDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double mean = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double std = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    double x = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> normalPDF = Calculator.normalDistribution(mean, std, x);
                    testOutputs.put("Normal PDF", normalPDF.get("NormalPDF"));
                    testOutputs.put("Normal CDF", normalPDF.get("NormalCDF"));
                    testOutputs.put("Z-Score", normalPDF.get("ZScore"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Normal CDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double mean = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double std = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    double lowerBound = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(3);
                    double upperBound = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> normalCDF = Calculator.normalCDF(mean, std, lowerBound, upperBound);
                    testOutputs.put("Normal CDF", normalCDF.get("NormalCDF"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Inverse Normal":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double mean = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double std = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    double area = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> inverseNorm = Calculator.inverseNorm(mean, std, area);
                    testOutputs.put("Inverse Normal", inverseNorm.get("InverseNormal"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "t PDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double dof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double x = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> tPDF = Calculator.tDistribution(dof, x);
                    testOutputs.put("t PDF", tPDF.get("tPDF"));
                    testOutputs.put("t CDF", tPDF.get("tCDF"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "t CDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double dof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double lowerBound = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    double upperBound = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> tCDF = Calculator.tCDF(dof, lowerBound, upperBound);
                    testOutputs.put("t CDF", tCDF.get("tCDF"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Inverse t":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double dof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double area = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> inverseT = Calculator.inverseT(dof, area);
                    testOutputs.put("Inverse t", inverseT.get("Inverset"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Chi Squared PDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double dof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double x = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> chiSquarePDF = Calculator.chiSquareDist(dof, x);
                    testOutputs.put("Chi Squared PDF", chiSquarePDF.get("ChiSqPDF"));
                    testOutputs.put("Chi Squared CDF", chiSquarePDF.get("ChiSqCDF"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Chi Squared CDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double dof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double lowerBound = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    double upperBound = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> chiSquareCDF = Calculator.chiSquareCDF(dof, lowerBound, upperBound);
                    testOutputs.put("Chi Squared CDF", chiSquareCDF.get("ChiSqCDF"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Inverse Chi Squared":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double dof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double area = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> inverseChiSquare = Calculator.inverseChiSquare(dof, area);
                    testOutputs.put("Inverse Chi Squared", inverseChiSquare.get("InverseChiSq"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "F PDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double ndof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double ddof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    double x = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> FDistribution = Calculator.FDist(ndof, ddof, x);
                    testOutputs.put("F PDF", FDistribution.get("FPDF"));
                    testOutputs.put("F CDF", FDistribution.get("FCDF"));
                    testOutputs.put("Mean", FDistribution.get("Mean"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "F CDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double ndof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double ddof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    double lowerBound = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(3);
                    double upperBound = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> FCDF = Calculator.FCDF(ndof, ddof, lowerBound, upperBound);
                    testOutputs.put("F CDF", FCDF.get("FCDF"));
                    testOutputs.put("Mean", FCDF.get("Mean"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Inverse F":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double ndof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double ddof = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    double area = Double.parseDouble(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> inverseF = Calculator.inverseF(ndof, ddof, area);
                    testOutputs.put("Inverse F", inverseF.get("InverseF"));
                    testOutputs.put("Mean", inverseF.get("Mean"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Binomial PDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    int numTrials = Integer.parseInt(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double probability = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    int x = Integer.parseInt(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> binomialPDF = Calculator.binomialDist(numTrials, probability, x);
                    testOutputs.put("Binomial PMF", binomialPDF.get("BinomialPDF"));
                    testOutputs.put("Binomial CDF", binomialPDF.get("BinomialCDF"));
                    testOutputs.put("Mean", binomialPDF.get("Mean"));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Binomial CDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    int numTrials = Integer.parseInt(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    double probability = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    int lowerBound = Integer.parseInt(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(3);
                    int upperBound = Integer.parseInt(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> binomialCDF = Calculator.binomialCDF(numTrials, probability, lowerBound, upperBound);
                    testOutputs.put("Binomial CDF", binomialCDF.get("BinomialCDF"));
                    testOutputs.put("Mean", binomialCDF.get("Mean"));

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Geometric PDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double probability = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    int x = Integer.parseInt(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> geometricPDF = Calculator.geometricDist(probability, x);
                    testOutputs.put("Geometric PMF", geometricPDF.get("GeometricPDF"));
                    testOutputs.put("Geometric CDF", geometricPDF.get("GeometricCDF"));
                    testOutputs.put("Mean", geometricPDF.get("Mean"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;

            case "Geometric CDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double probability = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    int lowerBound = Integer.parseInt(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    int upperBound = Integer.parseInt(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> geometricCDF = Calculator.geometricCDF(probability, lowerBound, upperBound);
                    testOutputs.put("Geometric CDF", geometricCDF.get("GeometricCDF"));
                    testOutputs.put("Mean", geometricCDF.get("Mean"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Poisson PDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double mean = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    int x = Integer.parseInt(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> poissonPDF = Calculator.poissonDist(mean, x);
                    testOutputs.put("Poisson PMF", poissonPDF.get("PoissonPDF"));
                    testOutputs.put("Poisson CDF", poissonPDF.get("PoissonCDF"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Poisson CDF":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    double mean = Double.parseDouble(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    int lowerBound = Integer.parseInt(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
                    int upperBound = Integer.parseInt(holder.inputField.getText().toString());
                    LinkedHashMap<String, Double> poissonCDF = Calculator.poissonCDF(mean, lowerBound, upperBound);
                    testOutputs.put("Poisson CDF", poissonCDF.get("PoissonCDF"));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Permutations":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    long n = Integer.parseInt(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    long r = Integer.parseInt(holder.inputField.getText().toString());
                    testOutputs.put("Permutation", Calculator.permutation(n, r));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Combinations":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    long n = Integer.parseInt(holder.inputField.getText().toString());
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
                    long r = Integer.parseInt(holder.inputField.getText().toString());
                    testOutputs.put("Combination", Calculator.combination(n, r));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;
            case "Factorial":
                try {
                    holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
                    long n = Integer.parseInt(holder.inputField.getText().toString());
                    testOutputs.put("Permutation", Calculator.factorial(n));
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_LONG).show();
                }
                break;

        }
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
            case R.id.calculate_output:
                calculateValues();
                updateAdapter();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }
}
