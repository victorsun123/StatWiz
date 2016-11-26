package com.mdb.statwiz.utils;

import android.graphics.Color;

import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sayan Paul on 11/20/2016.
 */

public class FunctionsList {
    public static ColorGenerator generator = ColorGenerator.MATERIAL;
    public static int[] COLORS = {
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
    public static final Function[] DISTRIBUTIONS = {
            new Function("Normal PDF", new ArrayList<String>(0), COLORS[0]),
            new Function("Normal CDF", new ArrayList<String>(0), COLORS[0]),
            new Function("Inverse Normal", new ArrayList<String>(0), COLORS[0]),
            new Function("t PDF", new ArrayList<String>(0), COLORS[1]),
            new Function("t CDF", new ArrayList<String>(0), COLORS[1]),
            new Function("Inverse t", new ArrayList<String>(0), COLORS[1]),
            new Function("Chi Squared PDF", new ArrayList<String>(0), COLORS[2]),
            new Function("Chi Squared CDF", new ArrayList<String>(0), COLORS[2]),
            new Function("Inverse Chi Squared", new ArrayList<String>(0), COLORS[2]),
            new Function("F PDF", new ArrayList<String>(0), COLORS[3]),
            new Function("F CDF", new ArrayList<String>(0), COLORS[3]),
            new Function("Inverse F", new ArrayList<String>(0), COLORS[3]),
            new Function("Binomial PDF", new ArrayList<String>(0), COLORS[4]),
            new Function("Binomial CDF", new ArrayList<String>(0), COLORS[4]),
            new Function("Geometric PDF", new ArrayList<String>(0), COLORS[5]),
            new Function("Geometric CDF", new ArrayList<String>(0), COLORS[5]),
            new Function("Poisson PDF", new ArrayList<String>(0), COLORS[6]),
            new Function("Poisson CDF", new ArrayList<String>(0), COLORS[6]),
    };

    public static final Function[] PROBABILITY = {
            new Function("Permutations", new ArrayList<String>(0), COLORS[7]),
            new Function("Combinations", new ArrayList<String>(0), COLORS[8]),
            new Function("Factorial", new ArrayList<String>(0), COLORS[9]),
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
