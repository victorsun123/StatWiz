package com.mdb.statwiz;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by emaanhariri on 11/16/16.
 */

public class Calculator
{
    public Calculator() {}

    public static HashMap<String, Object> descriptiveStats(double[] values)
    {
        HashMap<String, Object> calculations = new HashMap<String, Object>();
        DescriptiveStatistics descStats = new DescriptiveStatistics(values);
        calculations.put("Mean", descStats.getMean());
        calculations.put("Max", descStats.getMax());
        calculations.put("Min", descStats.getMin());
        calculations.put("SD", descStats.getStandardDeviation());
        calculations.put("Median", descStats.getPercentile(0.5));
        calculations.put("Q1", descStats.getPercentile(0.25));
        calculations.put("Q3", descStats.getPercentile(0.75));
        calculations.put("Range", ((double)calculations.get("Max") - (double) calculations.get("Min")));
        calculations.put("N", ((Long) descStats.getN()).doubleValue());
        Frequency freq = new Frequency();
        for (double value: values) freq.addValue(value);
        double[] modes = {};
        List<Comparable<?>> modesAL = freq.getMode();


        return calculations;
    }

    public static HashMap<String, Double> normalDistProb(double mean, double sd, double x)
    {
        HashMap<String, Double> calculations = normalDistProb(mean, sd, Double.NEGATIVE_INFINITY, x);
        double zScore = (x - mean)/sd;
        calculations.put("ZScore", zScore);

        return calculations;
    }

    public static HashMap<String, Double> normalDistProb(double mean, double sd, double x0, double x1)
    {
        HashMap<String, Double> calculations = new HashMap<String, Double>();
        NormalDistribution normDist = new NormalDistribution(mean, sd);
        calculations.put("Probability", normDist.probability(x0, x1));

        return calculations;
    }

    public static HashMap<String, Double> inverseNorm(double mean, double sd, double cumDist)
    {
        HashMap<String, Double> calculations = new HashMap<String, Double>();
        NormalDistribution normDist = new NormalDistribution(mean, sd);
        calculations.put("Inverse Probability", normDist.inverseCumulativeProbability(cumDist));

        return calculations;
    }

    public static HashMap<String, Double> tDist(double degreesOfFreedom, double p)
    {
        HashMap<String, Double> calculations = new HashMap<String, Double>();
        TDistribution tDist = new TDistribution(degreesOfFreedom);
        calculations.put("Probability", tDist.inverseCumulativeProbability(p));

        return calculations;
    }

    public static HashMap<String, Double> chiSquareDist(double chiSquare, double degreesOfFreedom)
    {
        HashMap<String, Double> calculations = new HashMap<String, Double>();
        ChiSquaredDistribution chiDist = new ChiSquaredDistribution(degreesOfFreedom);
        calculations.put("Probability", chiDist.cumulativeProbability(chiSquare));

        return calculations;
    }
}
