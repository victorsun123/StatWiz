package com.mdb.statwiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DescriptiveOutputActivity extends Fragment {

    public ArrayList<String> properties;
    public ArrayList<Double> values;
    private RecyclerView rv;
    private DescriptiveAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_descriptive_output, container, false);

        properties =  new ArrayList<String>();
        values = new ArrayList<Double>();

        rv = (RecyclerView) layout.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DescriptiveAdapter(getContext(), properties, values);
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, new DescriptiveInputActivity())
                        .commit();
            }
        });
        return layout;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
