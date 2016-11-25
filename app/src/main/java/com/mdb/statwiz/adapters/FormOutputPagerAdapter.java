package com.mdb.statwiz.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mdb.statwiz.fragments.FormOutputFragment;
import com.mdb.statwiz.utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Sayan Paul on 11/19/2016.
 */

public class FormOutputPagerAdapter extends FragmentStatePagerAdapter {
    public List<Tuple<String, Double>> outputs;

    public FormOutputPagerAdapter(FragmentManager manager, HashMap<String, Double> outputs) {
        super(manager);
        this.outputs = new ArrayList<>(outputs.size());

        SortedSet<String> keys = new TreeSet<>(outputs.keySet());
        for (String key : keys) {
            this.outputs.add(new Tuple<>(key, outputs.get(key)));
        }
    }

    @Override
    public int getCount() {
        return outputs.size();
    }

    @Override
    public Fragment getItem(int position) {
        Tuple<String, Double> output = outputs.get(position);
        return FormOutputFragment.newInstance(output.key, output.value);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return outputs.get(position).key;
    }
}
