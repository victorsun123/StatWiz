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
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This fragment displays all the computation interfaces input interfaces for distribution and probability, links it with the output
 * view pager, and keeps track of function currently comptued so that all the function input pages can be abstracted under one fragment
 */

public class FormInputFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private RecyclerView mRecyclerView;
    private FormInputAdapter mAdapter;
    private ViewPager outputPager;      //view pager displaying results
    private FormOutputPagerAdapter outputPagerAdapter;
    private FloatingActionButton left, right, calculateOutput;
    private String functionName;    //function currently selected
    private List<String> inputList;     //list of parameter names
    private LinkedHashMap<String, Double> testOutputs;          //hashmap for storing outputs to be displayed
    private FormInputAdapter.FormInputViewHolder holder;        //view holder for each input parameter

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
        inputList = new ArrayList<>();

        //switch statement determining which input parameters to include in input form
        switch (functionName) {
            case "Normal PDF":
                inputList.add("Mean");
                inputList.add("Standard Deviation");
                inputList.add("X-Value");
                break;
            case "Normal CDF":
                inputList.add("Mean");
                inputList.add("Standard Deviation");
                inputList.add("Lower Bound");
                inputList.add("Upper Bound");
                break;
            case "Inverse Normal":
                inputList.add("Mean");
                inputList.add("Standard Deviation");
                inputList.add("Area");
                break;
            case "t PDF":
                inputList.add("Degrees of Freedom");
                inputList.add("X-Value");
                break;
            case "t CDF":
                inputList.add("Degrees of Freedom");
                inputList.add("Lower Bound");
                inputList.add("Upper Bound");
                break;
            case "Inverse t":
                inputList.add("Degrees of Freedom");
                inputList.add("Area");
                break;
            case "Chi Squared PDF":
                inputList.add("Degrees of Freedom");
                inputList.add("X-Value");
                break;
            case "Chi Squared CDF":
                inputList.add("Degrees of Freedom");
                inputList.add("Lower Bound");
                inputList.add("Upper Bound");
                break;
            case "Inverse Chi Squared":
                inputList.add("Degrees of Freedom");
                inputList.add("Area");
                break;
            case "F PDF":
                inputList.add("Numerator Degrees of Freedom");
                inputList.add("Denominator Degrees of Freedom");
                inputList.add("X-Value");
                break;
            case "F CDF":
                inputList.add("Numerator Degrees of Freedom");
                inputList.add("Denominator Degrees of Freedom");
                inputList.add("Lower Bound");
                inputList.add("Upper Bound");
                break;
            case "Inverse F":
                inputList.add("Numerator Degrees of Freedom");
                inputList.add("Denominator Degrees of Freedom");
                inputList.add("Area");
                break;
            case "Binomial PDF":
                inputList.add("Number of Trials");
                inputList.add("Probability of Success");
                inputList.add("X-Value");
                break;
            case "Binomial CDF":
                inputList.add("Number of Trials");
                inputList.add("Probability of Success");
                inputList.add("Lower Bound");
                inputList.add("Upper Bound");
                break;
            case "Geometric PDF":
                inputList.add("Probability of Success");
                inputList.add("X-Value");
                break;
            case "Geometric CDF":
                inputList.add("Probability of Success");
                inputList.add("Lower Bound");
                inputList.add("Upper Bound");
                break;
            case "Poisson PDF":
                inputList.add("Lambda");
                inputList.add("X-Value");
                break;
            case "Poisson CDF":
                inputList.add("Lambda");
                inputList.add("Lower Bound");
                inputList.add("Upper Bound");
                break;
            case "Permutations":
            case "Combinations":
                inputList.add("n");
                inputList.add("r");
                break;
            case "Factorial":
                inputList.add("n");
                break;

        }

        mAdapter = new FormInputAdapter(getActivity(), inputList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize ViewPager
        testOutputs = new LinkedHashMap<>();
        testOutputs.put("output", 0.0);
        outputPagerAdapter = new FormOutputPagerAdapter(getActivity().getSupportFragmentManager(), testOutputs);
        outputPager.setAdapter(outputPagerAdapter);
        outputPager.addOnPageChangeListener(this);

        // Initializes left/right buttons for ViewPager
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        calculateOutput.setOnClickListener(this);
        setLeftRightButtonVisibility();
        return layout;
    }

    //Updates adapter with new output values in testOutputs
    public void updateAdapter() {
        if (testOutputs.size() != 0) {      //if calculation does not go through, don't display anything
            outputPagerAdapter = new FormOutputPagerAdapter(getActivity().getSupportFragmentManager(), testOutputs);
            outputPager.setAdapter(outputPagerAdapter);
        }
    }

    /**
     * switch statement based on function name to determine which calculations to be made
     */
    public void calculateValues() {
        testOutputs = new LinkedHashMap<>();
        try {
            switch (functionName) {
                case "Normal PDF":
                    normalPDF();
                    break;
                case "Normal CDF":
                    normalCDF();
                    break;
                case "Inverse Normal":
                    inverseNorm();
                    break;
                case "t PDF":
                    tPDF();
                    break;
                case "t CDF":
                    tCDF();
                    break;
                case "Inverse t":
                    inverseT();
                    break;
                case "Chi Squared PDF":
                    chiSquaredPDF();
                    break;
                case "Chi Squared CDF":
                    chiSquaredCDF();
                    break;
                case "Inverse Chi Squared":
                    inverseChiSquared();
                    break;
                case "F PDF":
                    fPDF();
                    break;
                case "F CDF":
                    fCDF();
                    break;
                case "Inverse F":
                    inverseF();
                    break;
                case "Binomial PDF":
                    binomialPDF();
                    break;
                case "Binomial CDF":
                    binomialCDF();
                    break;
                case "Geometric PDF":
                    geometricPDF();
                    break;
                case "Geometric CDF":
                    geometricCDF();
                    break;
                case "Poisson PDF":
                    poissonPDF();
                    break;
                case "Poisson CDF":
                    poissonCDF();
                    break;
                case "Permutations":
                    permutation();
                    break;
                case "Combinations":
                    combination();
                    break;
                case "Factorial":
                    factorial();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Improper values!", Toast.LENGTH_SHORT).show();
        }
    }

    //Implement interface for results view pager
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
                setLeftRightButtonVisibility();
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
        setLeftRightButtonVisibility();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void setLeftRightButtonVisibility() {
        if (outputPager.getCurrentItem() == 0)
            left.setVisibility(View.GONE);
        else
            left.setVisibility(View.VISIBLE);
        if (outputPager.getCurrentItem() == outputPagerAdapter.getCount() - 1)
            right.setVisibility(View.GONE);
        else
            right.setVisibility(View.VISIBLE);
    }

    /**
    * Methods for each function that retrieves input parameters, computes from calculator function and sends output to view pager
    */

    public void normalPDF() {
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
    }

    public void normalCDF() {
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
    }

    public void inverseNorm() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double mean = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double std = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
        double area = Double.parseDouble(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> inverseNorm = Calculator.inverseNorm(mean, std, area);
        testOutputs.put("Inverse Normal", inverseNorm.get("InverseNormal"));
    }

    public void tPDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double dof = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double x = Double.parseDouble(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> tPDF = Calculator.tDistribution(dof, x);
        testOutputs.put("t PDF", tPDF.get("tPDF"));
        testOutputs.put("t CDF", tPDF.get("tCDF"));
    }

    public void tCDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double dof = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double lowerBound = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
        double upperBound = Double.parseDouble(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> tCDF = Calculator.tCDF(dof, lowerBound, upperBound);
        testOutputs.put("t CDF", tCDF.get("tCDF"));
    }

    public void inverseT() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double dof = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double area = Double.parseDouble(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> inverseT = Calculator.inverseT(dof, area);
        testOutputs.put("Inverse t", inverseT.get("Inverset"));
    }

    public void chiSquaredPDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double dof = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double x = Double.parseDouble(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> chiSquarePDF = Calculator.chiSquareDist(dof, x);
        testOutputs.put("Chi Squared PDF", chiSquarePDF.get("ChiSqPDF"));
        testOutputs.put("Chi Squared CDF", chiSquarePDF.get("ChiSqCDF"));
    }

    public void chiSquaredCDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double dof = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double lowerBound = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
        double upperBound = Double.parseDouble(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> chiSquareCDF = Calculator.chiSquareCDF(dof, lowerBound, upperBound);
        testOutputs.put("Chi Squared CDF", chiSquareCDF.get("ChiSqCDF"));
    }

    public void inverseChiSquared() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double dof = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double area = Double.parseDouble(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> inverseChiSquare = Calculator.inverseChiSquare(dof, area);
        testOutputs.put("Inverse Chi Squared", inverseChiSquare.get("InverseChiSq"));
    }

    public void fPDF() {
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
    }

    public void fCDF() {
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
    }

    public void inverseF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double ndof = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double ddof = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
        double area = Double.parseDouble(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> inverseF = Calculator.inverseF(ndof, ddof, area);
        testOutputs.put("Inverse F", inverseF.get("InverseF"));
        testOutputs.put("Mean", inverseF.get("Mean"));
    }

    public void binomialPDF() {
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

    }

    public void binomialCDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        int numTrials = Integer.parseInt(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        double probability = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
        int lowerBound = Integer.parseInt(holder.inputField.getText().toString()) - 1;
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(3);
        int upperBound = Integer.parseInt(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> binomialCDF = Calculator.binomialCDF(numTrials, probability, lowerBound, upperBound);
        testOutputs.put("Binomial CDF", binomialCDF.get("BinomialCDF"));
        testOutputs.put("Mean", binomialCDF.get("Mean"));

    }

    public void geometricPDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double probability = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        int x = Integer.parseInt(holder.inputField.getText().toString()) - 1;
        LinkedHashMap<String, Double> geometricPDF = Calculator.geometricDist(probability, x);
        testOutputs.put("Geometric PMF", geometricPDF.get("GeometricPDF"));
        testOutputs.put("Geometric CDF", geometricPDF.get("GeometricCDF"));
        testOutputs.put("Mean", geometricPDF.get("Mean"));
    }


    public void geometricCDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double probability = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        int lowerBound = Integer.parseInt(holder.inputField.getText().toString()) - 2;
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
        int upperBound = Integer.parseInt(holder.inputField.getText().toString()) - 1;
        LinkedHashMap<String, Double> geometricCDF = Calculator.geometricCDF(probability, lowerBound, upperBound);
        testOutputs.put("Geometric CDF", geometricCDF.get("GeometricCDF"));
        testOutputs.put("Mean", geometricCDF.get("Mean"));

    }

    public void poissonPDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double mean = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        int x = Integer.parseInt(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> poissonPDF = Calculator.poissonDist(mean, x);
        testOutputs.put("Poisson PMF", poissonPDF.get("PoissonPDF"));
        testOutputs.put("Poisson CDF", poissonPDF.get("PoissonCDF"));
    }

    public void poissonCDF() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        double mean = Double.parseDouble(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        int lowerBound = Integer.parseInt(holder.inputField.getText().toString()) - 1;
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(2);
        int upperBound = Integer.parseInt(holder.inputField.getText().toString());
        LinkedHashMap<String, Double> poissonCDF = Calculator.poissonCDF(mean, lowerBound, upperBound);
        testOutputs.put("Poisson CDF", poissonCDF.get("PoissonCDF"));
    }

    public void permutation() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        long n = Integer.parseInt(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        long r = Integer.parseInt(holder.inputField.getText().toString());
        testOutputs.put("Permutation", Calculator.permutation(n, r));
    }

    public void combination() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        long n = Integer.parseInt(holder.inputField.getText().toString());
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(1);
        long r = Integer.parseInt(holder.inputField.getText().toString());
        testOutputs.put("Combination", Calculator.combination(n, r));
    }

    public void factorial() {
        holder = (FormInputAdapter.FormInputViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        long n = Integer.parseInt(holder.inputField.getText().toString());
        testOutputs.put("Factorial", Calculator.factorial(n));
    }
}
