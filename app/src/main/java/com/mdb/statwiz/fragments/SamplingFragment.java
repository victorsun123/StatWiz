package com.mdb.statwiz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdb.statwiz.R;
import com.mdb.statwiz.adapters.SamplingDefinitionsAdapter;

public class SamplingFragment extends Fragment {
    private RecyclerView samplingRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.sampling_fragment, container, false);
        samplingRecyclerView = (RecyclerView) layout.findViewById(R.id.sampling_recycler_view);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        samplingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        samplingRecyclerView.setAdapter(new SamplingDefinitionsAdapter(getActivity()));
    }
}
