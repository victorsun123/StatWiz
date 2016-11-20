package com.mdb.statwiz.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sayan Paul on 11/20/2016.
 */

public class FunctionsList {
    public static final Function[] DISTRIBUTIONS = {
            new Function("Normal PDF", new ArrayList<String>(0)),
            new Function("Normal CDF", new ArrayList<String>(0)),
            new Function("Inverse Normal", new ArrayList<String>(0)),
            new Function("t PDF", new ArrayList<String>(0)),
            new Function("t CDF", new ArrayList<String>(0)),
            new Function("Inverse t", new ArrayList<String>(0)),
            new Function("Binomial PDF", new ArrayList<String>(0)),
            new Function("Binomial CDF", new ArrayList<String>(0)),
            new Function("Poisson PDF", new ArrayList<String>(0)),
            new Function("Poisson CDF", new ArrayList<String>(0)),
            new Function("Chi Squared PDF", new ArrayList<String>(0)),
            new Function("Chi Squared CDF", new ArrayList<String>(0)),
            new Function("Inverse Chi Squared", new ArrayList<String>(0)),
            new Function("Geometric PDF", new ArrayList<String>(0)),
            new Function("Geometric CDF", new ArrayList<String>(0)),
            new Function("F PDF", new ArrayList<String>(0)),
            new Function("F CDF", new ArrayList<String>(0)),
            new Function("Inverse F", new ArrayList<String>(0))
    };

    public static List<Function> getFunctionsList(String functionType) {
        switch (functionType.toLowerCase()) {
            case "distributions":
                return Arrays.asList(DISTRIBUTIONS);
            default:
                return new ArrayList<>(0);
        }
    }
}
