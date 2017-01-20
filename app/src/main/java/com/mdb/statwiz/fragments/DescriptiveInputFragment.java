package com.mdb.statwiz.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdb.statwiz.R;

import java.util.ArrayList;


/**
 * This fragment implements list input interface for descriptive stats
 *
 */
public class DescriptiveInputFragment extends Fragment implements View.OnClickListener {
    public static final String LIST_KEY = "listKey";
    public EditText descriptiveInput;
    public SharedPreferences preferences;
    public String[] splitInputString;
    public ArrayList<Double> inputAsNumbers;
    private String inputString;
    private Button clearFields, space;
    private FloatingActionButton submitInput;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.descriptive_input, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());      //get data from shared preference to restore input values from inputs when fragment was last used
        inputString = preferences.getString(LIST_KEY, "");

        descriptiveInput = (EditText) layout.findViewById(R.id.editText);
        descriptiveInput.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        descriptiveInput.setText(inputString);

        space = (Button) layout.findViewById(R.id.space);
        clearFields = (Button) layout.findViewById(R.id.clear_field);
        submitInput = (FloatingActionButton) layout.findViewById(R.id.submit_input);

        space.setOnClickListener(this);
        clearFields.setOnClickListener(this);
        submitInput.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.space:        //space button to deal with compatability issues for certain numpads that do not have spaces
                descriptiveInput.setText(descriptiveInput.getText().toString() + " ");
                Selection.setSelection(descriptiveInput.getText(), descriptiveInput.length());
                break;
            case R.id.clear_field:      //button to clear field
                descriptiveInput.setText("");
                break;
            case R.id.submit_input:     //saves input in shared preference when submitted for computation
                inputString = descriptiveInput.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(LIST_KEY, inputString);
                editor.apply();

                splitInputString = inputString.split("\\s+");       //distinguish space seperated values in string list and convert into array

                inputAsNumbers = new ArrayList<Double>();
                try {
                    for (String input : splitInputString)
                        inputAsNumbers.add(Double.parseDouble(input));
                } catch (Exception e) {                                 //list is not formatted properly
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Incorrect number format!", Toast.LENGTH_LONG).show();
                    break;

                }

                double[] primitiveArray = new double[inputAsNumbers.size()];
                for (int i = 0; i < inputAsNumbers.size(); i++)
                    primitiveArray[i] = inputAsNumbers.get(i);
                Bundle bundle = new Bundle();
                bundle.putDoubleArray("inputAsNumbers", primitiveArray);
                DescriptiveOutputFragment descriptiveOutput = new DescriptiveOutputFragment();
                descriptiveOutput.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, descriptiveOutput)
                        .addToBackStack("inputToOutput").commit();

                break;
        }
    }

    @Override
    public void onDestroyView() {           //when changing fragments store input values on shared preference to be saved when fragment is entered agian
        inputString = descriptiveInput.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_KEY, inputString);
        editor.apply();
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
