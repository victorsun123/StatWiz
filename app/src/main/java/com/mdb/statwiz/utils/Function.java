package com.mdb.statwiz.utils;

import java.util.List;

/**
 * Created by Sayan Paul on 11/12/2016.
 */

public class Function {
    public String name;
    public List<String> inputs;
    public int color;

    public Function(String name, List<String> inputs, int color) {
        this.name = name;
        this.inputs = inputs;
        this.color = color;
    }
}
