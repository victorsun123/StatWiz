package com.mdb.statwiz.utils;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.GeometricDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * Created by emaanhariri on 11/16/16.
 */

public class Calculator {

    public static LinkedHashMap<String, Object> descriptiveStats(double[] values) {
        LinkedHashMap<String, Object> calculations = new LinkedHashMap<String, Object>();
        DescriptiveStatistics descStats = new DescriptiveStatistics(values);
        calculations.put("Mean", descStats.getMean());
        calculations.put("Max", descStats.getMax());
        calculations.put("Min", descStats.getMin());
        calculations.put("Standard Deviation", descStats.getStandardDeviation());
        calculations.put("Median", descStats.getPercentile(50.0));
        calculations.put("Q1", descStats.getPercentile(25.0));
        calculations.put("Q3", descStats.getPercentile(75.0));
        calculations.put("IQR", ((double) calculations.get("Q3") - (double) calculations.get("Q1")));
        calculations.put("Range", ((double) calculations.get("Max") - (double) calculations.get("Min")));
        calculations.put("Count", ((Long) descStats.getN()).doubleValue());
        calculations.put("Standard Error", (double) calculations.get("Standard Deviation") / Math.sqrt((double) calculations.get("Count")));
        calculations.put("Variance", descStats.getVariance());
        calculations.put("Kurtosis", descStats.getKurtosis());
        calculations.put("Skewness", descStats.getSkewness());
        LinkedHashMap<Double, Integer> freqs = new LinkedHashMap<Double, Integer>();
        for (Double value : values) {
            if (freqs.containsKey(value)) {
                freqs.put(value, (freqs.get(value) + 1));
            } else {
                freqs.put(value, 1);
            }
        }
        ArrayList<Double> modesArrayList = new ArrayList<Double>();
        Integer maxCount = 0;
        for (Double value : values) {
            if (freqs.get(value) > maxCount) {
                maxCount = freqs.get(value);
                modesArrayList.clear();
                modesArrayList.add(value);
            } else if (freqs.get(value) == maxCount) {
                if (!modesArrayList.contains(value))
                    modesArrayList.add(value);
            }
        }
        Object[] modes = modesArrayList.toArray();
        String modesAsString = "";
        for (Object o : modes) {
            modesAsString += o.toString() + ", ";
        }
        calculations.put("Mode", modesAsString.substring(0, modesAsString.length() - 2));

        return calculations;
    }


    public static LinkedHashMap<String, Double> normalDistribution(double mean, double sd, double x) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        NormalDistribution normDist = new NormalDistribution(mean, sd);
        calculations.put("NormalPDF", normDist.density(x));
        calculations.put("NormalCDF", normDist.cumulativeProbability(x));
        double zScore = (x - mean) / sd;
        calculations.put("ZScore", zScore);

        return calculations;
    }

    public static LinkedHashMap<String, Double> normalCDF(double mean, double sd, double x0, double x1) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        NormalDistribution normDist = new NormalDistribution(mean, sd);
        calculations.put("NormalCDF", normDist.probability(x0, x1));

        return calculations;

    }

    public static LinkedHashMap<String, Double> inverseNorm(double mean, double sd, double p) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        NormalDistribution normDist = new NormalDistribution(mean, sd);
        calculations.put("InverseNormal", normDist.inverseCumulativeProbability(p));

        return calculations;
    }

    public static LinkedHashMap<String, Double> tDistribution(double degreesOfFreedom, double x) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        TDistribution tDist = new TDistribution(degreesOfFreedom);
        calculations.put("tPDF", tDist.density(x));
        calculations.put("tCDF", tDist.cumulativeProbability(x));


        return calculations;
    }

    public static LinkedHashMap<String, Double> tCDF(double degreesOfFreedom, double x0, double x1) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        TDistribution tDist = new TDistribution(degreesOfFreedom);
        calculations.put("tCDF", tDist.probability(x0, x1));

        return calculations;
    }


    public static LinkedHashMap<String, Double> inverseT(double degreesOfFreedom, double p) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        TDistribution tDist = new TDistribution(degreesOfFreedom);
        calculations.put("Inverset", tDist.inverseCumulativeProbability(p));

        return calculations;
    }


    public static LinkedHashMap<String, Double> chiSquareDist(double degreesOfFreedom, double x) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        ChiSquaredDistribution chiDist = new ChiSquaredDistribution(degreesOfFreedom);
        calculations.put("ChiSqPDF", chiDist.density(x));
        calculations.put("ChiSqCDF", chiDist.cumulativeProbability(x));

        return calculations;
    }

    public static LinkedHashMap<String, Double> chiSquareCDF(double degreesOfFreedom, double x0, double x1) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        ChiSquaredDistribution chiDist = new ChiSquaredDistribution(degreesOfFreedom);
        calculations.put("ChiSqCDF", chiDist.probability(x0, x1));

        return calculations;
    }

    public static LinkedHashMap<String, Double> inverseChiSquare(double degreesOfFreedom, double p) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        ChiSquaredDistribution chiDist = new ChiSquaredDistribution(degreesOfFreedom);
        calculations.put("InverseChiSq", chiDist.inverseCumulativeProbability(p));

        return calculations;
    }

    public static LinkedHashMap<String, Double> binomialDist(int numberOfTrials, double probabilityOfSuccess, int x) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        BinomialDistribution biDist = new BinomialDistribution(numberOfTrials, probabilityOfSuccess);
        //P(X = x)
        calculations.put("BinomialPDF", biDist.probability(x));
        //P(X <= x)
        calculations.put("BinomialCDF", biDist.cumulativeProbability(x));
        calculations.put("Mean", biDist.getNumericalMean());

        return calculations;
    }

    public static LinkedHashMap<String, Double> binomialCDF(int numberOfTrials, double probabilityOfSuccess, int x) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        BinomialDistribution biDist = new BinomialDistribution(numberOfTrials, probabilityOfSuccess);

        calculations.put("BinomialCDF", biDist.cumulativeProbability(x));
        calculations.put("Mean", biDist.getNumericalMean());

        return calculations;
    }


    public static LinkedHashMap<String, Double> geometricDist(double probabilityOfSuccess, int x) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        GeometricDistribution geoDist = new GeometricDistribution(probabilityOfSuccess);
        calculations.put("GeometricPDF", geoDist.probability(x));
        calculations.put("GeometricCDF", geoDist.cumulativeProbability(x));
        calculations.put("Mean", geoDist.getNumericalMean());

        return calculations;
    }

    public static LinkedHashMap<String, Double> geometricCDF(double probabilityOfSuccess, int x0, int x1) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        GeometricDistribution geoDist = new GeometricDistribution(probabilityOfSuccess);
        calculations.put("GeometricCDF", geoDist.cumulativeProbability(x0, x1));
        calculations.put("Mean", geoDist.getNumericalMean());

        return calculations;
    }


    public static LinkedHashMap<String, Double> poissonDist(double mean, int x) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        PoissonDistribution poissonDist = new PoissonDistribution(mean);
        calculations.put("PoissonPDF", poissonDist.probability(x));
        calculations.put("PoissonCDF", poissonDist.cumulativeProbability(x));

        return calculations;
    }

    public static LinkedHashMap<String, Double> poissonCDF(double mean, int x0, int x1) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        PoissonDistribution poissonDist = new PoissonDistribution(mean);
        calculations.put("PoissonCDF", poissonDist.cumulativeProbability(x0, x1));

        return calculations;
    }


    public static LinkedHashMap<String, Double> FDist(double numeratordf, double denominatordf, double x) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        FDistribution FDist = new FDistribution(numeratordf, denominatordf);
        calculations.put("FPDF", FDist.density(x));
        calculations.put("FCDF", FDist.cumulativeProbability(x));
        calculations.put("Mean", FDist.getNumericalMean());

        return calculations;
    }

    public static LinkedHashMap<String, Double> FCDF(double numeratordf, double denominatordf, double x0, double x1) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        FDistribution FDist = new FDistribution(numeratordf, denominatordf);
        calculations.put("FCDF", FDist.probability(x0, x1));
        calculations.put("Mean", FDist.getNumericalMean());

        return calculations;
    }

    public static LinkedHashMap<String, Double> inverseF(double numeratordf, double denominatordf, double p) {
        LinkedHashMap<String, Double> calculations = new LinkedHashMap<String, Double>();
        FDistribution FDist = new FDistribution(numeratordf, denominatordf);
        calculations.put("InverseF", FDist.inverseCumulativeProbability(p));
        calculations.put("Mean", FDist.getNumericalMean());

        return calculations;
    }


    public static double factorial(long n) {
        if (n == 0 || n == 1) return 1;
        long result = 1;
        for (long i = 1; i <= n; i++) result *= i;
        return (double) result;
    }

    public static double permutation(long n, long r) {
        if (r == 0) return 1;
        if (r == 1) return n;
        if (n == r) return factorial(n);
        if (n < r) return 0;
        return (double) factorial(n) / factorial(n - r);
    }

    public static double combination(long n, long r) {
        if (n < r) return 0;
        if (r == 0 || n == r) return 1;
        if (r == 1 || n == (r + 1)) return n;
        return (double) permutation(n, r) / factorial(r);
    }

}
