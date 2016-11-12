package com.mdb.statwiz;

import java.util.List;

/**
 * Created by Sayan Paul on 11/12/2016.
 */

public class Function {
    public String name;
    public List<String> inputs;
    public List<String> outputs;

    public Function(String name, List<String> inputs, List<String> outputs) {
        this.name = name;
        this.inputs = inputs;
        this.outputs = outputs;
    }

}
