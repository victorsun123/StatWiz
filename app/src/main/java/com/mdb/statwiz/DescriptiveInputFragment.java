package com.mdb.statwiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class DescriptiveInputFragment extends Fragment {
    private EditText et1, et2;
    private String list1, list2;
    public SharedPreferences preferences;
    public static final String LIST_KEY_1 = "listKey1";
    public static final String LIST_KEY_2 = "listKey2";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.descriptive_input, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        list1 = preferences.getString(LIST_KEY_1,"");
        list2 = preferences.getString(LIST_KEY_2,"");
        et1 = (EditText) layout.findViewById(R.id.editText);
        et2 = (EditText) layout.findViewById(R.id.editText2);
        et1.setText(list1);
        et2.setText(list2);
        //list1.split(",");





        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list1 = et1.getText().toString();
                list2 = et2.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(LIST_KEY_1, list1);
                editor.putString(LIST_KEY_2, list2);
                editor.apply();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new DescriptiveOutputFragment())
                        .commit();
            }
        });
        return layout;

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
