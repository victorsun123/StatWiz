package com.mdb.statwiz;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.GeometricDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
import org.apache.commons.math3.stat.interval.BinomialConfidenceInterval;
import org.apache.commons.math3.util.DoubleArray;

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
        calculations.put("Variance", descStats.getVariance());
        calculations.put("Median", descStats.getPercentile(0.5));
        calculations.put("Q1", descStats.getPercentile(0.25));
        calculations.put("Q3", descStats.getPercentile(0.75));
        calculations.put("Range", ((double)calculations.get("Max") - (double) calculations.get("Min")));
        calculations.put("N", ((Long) descStats.getN()).doubleValue());
        HashMap<Double, Integer> freqs = new HashMap<Double, Integer>();
        for(Double value: values) {
            if (freqs.containsKey(value)) {
                freqs.put(value, (freqs.get(value) + 1));
            }
            else {
                freqs.put(value, 1);
            }
        }
        ArrayList<Double> modesArrayList = new ArrayList<Double>();
        Integer maxCount = 0;
        for(Double value: values) {
            if (freqs.get(value) > maxCount) {
                maxCount = freqs.get(value);
                modesArrayList.clear();
                modesArrayList.add(value);
            }
            else if (freqs.get(value) == maxCount) {
                if (!modesArrayList.contains(value))
                    modesArrayList.add(value);
            }
        }
        Object[] modes = modesArrayList.toArray();
        calculations.put("Mode", modes);
        calculations.put("Kurtosis", descStats.getKurtosis());
        calculations.put("Skewness", descStats.getSkewness());

        return calculations;
    }

    public static HashMap<String, Double> normalProbDist(double mean, double sd, double x)
    {
        HashMap<String, Double> calculations = normalProbDist(mean, sd, Double.NEGATIVE_INFINITY, x);
        double zScore = (x - mean)/sd;
        calculations.put("ZScore", zScore);

        return calculations;
    }

    public static HashMap<String, Double> normalProbDist(double mean, double sd, double x0, double x1)
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
        calculations.put("Probability", tDist.cumulativeProbability(p));

        return calculations;
    }

    public static HashMap<String, Double> inverseT(double degreesOfFreedom,double CI)
    {
        HashMap<String, Double> calculations = new HashMap<String, Double>();
        TDistribution tDist = new TDistribution(degreesOfFreedom);
        calculations.put("Probability", tDist.inverseCumulativeProbability(CI));

        return calculations;
    }

    public static HashMap<String, Double> chiSquareDist(double chiSquare, double degreesOfFreedom)
    {
        HashMap<String, Double> calculations = new HashMap<String, Double>();
        ChiSquaredDistribution chiDist = new ChiSquaredDistribution(degreesOfFreedom);
        calculations.put("Probability", chiDist.cumulativeProbability(chiSquare));

        return calculations;
    }

    public static HashMap<String, Double> binomDist(int numberOfTrials, double probablityOfSuccess, int x)
    {
        HashMap<String, Double> calculations = new HashMap<String, Double>();
        BinomialDistribution biDist = new BinomialDistribution(numberOfTrials, probablityOfSuccess);
        //P(X = x)
        calculations.put("Probability", biDist.probability(x));
        //P(X <= x)
        calculations.put("Cumulative Probability", biDist.cumulativeProbability(x) + (double) calculations.get("Probabilty"));
        calculations.put("Mean", biDist.getNumericalMean());

        return calculations;
    }

    public static HashMap<String, Double> geomDist(int numberOfTrials, double probablityOfSuccess, int x)
    {
        HashMap<String, Double> calculations = new HashMap<String, Double>();
        GeometricDistribution geoDist = new GeometricDistribution(probablityOfSuccess);
        //P(X = x)
        calculations.put("Probability", geoDist.probability(x));
        //P(X <= x)
        calculations.put("Cumulative Probability", geoDist.cumulativeProbability(x) + (double) calculations.get("Probabilty"));
        calculations.put("Mean", geoDist.getNumericalMean());

        return calculations;
    }

}
