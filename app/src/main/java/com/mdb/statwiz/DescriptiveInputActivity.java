package com.mdb.statwiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class DescriptiveInputActivity extends Fragment {
    private EditText et1, et2;
    private String list1, list2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_descriptive_input, container, false);
        et1 = (EditText) layout.findViewById(R.id.editText);
        et2 = (EditText) layout.findViewById(R.id.editText2);
        list1 = et1.getText().toString();
        list2 = et1.getText().toString();

        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DescriptiveOutputActivity.class);
                startActivity(intent);
            }
        });
        return layout;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
