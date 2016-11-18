package com.mdb.statwiz;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import java.util.HashMap;


/**
 * Created by emaanhariri on 11/16/16.
 */

public class Calculator
{
    public Calculator() {}

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
