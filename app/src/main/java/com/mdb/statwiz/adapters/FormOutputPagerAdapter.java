package com.mdb.statwiz.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mdb.statwiz.fragments.FormOutputFragment;
import com.mdb.statwiz.utils.Tuple;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Adapter for Recycler view for ViewPager outputting calculation results
 */

public class FormOutputPagerAdapter extends FragmentStatePagerAdapter {
    public List<Tuple<String, Double>> outputs;

    public FormOutputPagerAdapter(FragmentManager manager, LinkedHashMap<String, Double> outputs) {
        super(manager);
        this.outputs = new ArrayList<>(outputs.size());

        for (Entry<String, Double> e : outputs.entrySet())
            this.outputs.add(new Tuple<>(e.getKey(), e.getValue()));
    }

    @Override
    public int getCount() {
        return outputs.size();
    }

    @Override
    public Fragment getItem(int position) {
        Tuple<String, Double> output = outputs.get(position);
        return FormOutputFragment.newInstance(output.key, output.value, position, getCount());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return outputs.get(position).key;
    }
}
