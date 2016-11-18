package com.mdb.statwiz;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sayan Paul on 11/17/2016.
 */

public class FormInputFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FormInputAdapter mAdapter;
    private String functionName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.form_input_page, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.form_input_list);
        functionName = getArguments().getString(MainActivity.FUNCTIONNAME, "");
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> testList = new ArrayList<>(1);
        testList.add("jkl;");
        mAdapter = new FormInputAdapter(testList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
