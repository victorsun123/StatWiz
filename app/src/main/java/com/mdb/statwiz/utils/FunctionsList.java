package com.mdb.statwiz.utils;

import android.graphics.Color;

import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstraction for assigning each function to a category of functions
 */

public class FunctionsList {
    public static ColorGenerator generator = ColorGenerator.MATERIAL;
    public static int[] COLORS = {          //Material Design Color Scheme
            Color.parseColor("#f44336"),    //red
            Color.parseColor("#9C27B0"),    //purple
            Color.parseColor("#3F51B5"),    //indigo
            Color.parseColor("#03A9F4"),    //light blue
            Color.parseColor("#009688"),    //teal
            Color.parseColor("#8BC34A"),    //light green
            Color.parseColor("#FFEB3B"),    //yellow
            Color.parseColor("#FF9800"),    //orange
            Color.parseColor("#795548"),    //brown
            Color.parseColor("#607D8B"),    //blue grey
            Color.parseColor("#E91E63"),    //pink
            Color.parseColor("#673AB7"),    //deep purple
            Color.parseColor("#2196F3"),    //blue
            Color.parseColor("#00BCD4"),    //cyan
            Color.parseColor("#4CAF50"),    //green
            Color.parseColor("#CDDC39"),    //lime
            Color.parseColor("#FFC107"),    //amber
            Color.parseColor("#FF5722"),    //deep orange
            Color.parseColor("#9E9E9E"),    //gray
    };
    public static final Function[] DISTRIBUTIONS = {        //list of distribution functions
            new Function("Normal PDF", COLORS[0]),
            new Function("Normal CDF", COLORS[0]),
            new Function("Inverse Normal", COLORS[0]),
            new Function("t PDF", COLORS[1]),
            new Function("t CDF", COLORS[1]),
            new Function("Inverse t", COLORS[1]),
            new Function("Chi Squared PDF", COLORS[2]),
            new Function("Chi Squared CDF", COLORS[2]),
            new Function("Inverse Chi Squared", COLORS[2]),
            new Function("F PDF", COLORS[3]),
            new Function("F CDF", COLORS[3]),
            new Function("Inverse F", COLORS[3]),
            new Function("Binomial PDF", COLORS[4]),
            new Function("Binomial CDF", COLORS[4]),
            new Function("Geometric PDF", COLORS[5]),
            new Function("Geometric CDF", COLORS[5]),
            new Function("Poisson PDF", COLORS[6]),
            new Function("Poisson CDF", COLORS[6]),
    };

    public static final Function[] PROBABILITY = {          //list of probability functions
            new Function("Permutations", COLORS[7]),
            new Function("Combinations", COLORS[8]),
            new Function("Factorial", COLORS[9]),
    };

    public static List<Function> getFunctionsList(String functionType) {
        switch (functionType.toLowerCase()) {
            case "distributions":
                return Arrays.asList(DISTRIBUTIONS);
            case "probability":
                return Arrays.asList(PROBABILITY);
            default:
                return new ArrayList<>(0);
        }
    }
}
