package com.mdb.statwiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FunctionViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FunctionViewAdapter mAdapter;
    private String functionType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.function_view_fragment, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.function_list_recycler_view);
        functionType = getArguments().getString(MainActivity.FUNCTIONTYPE, "");
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new FunctionViewAdapter(getActivity(), FunctionsList.getFunctionsList(functionType));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
