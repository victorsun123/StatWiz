package com.mdb.statwiz;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Sayan Paul on 11/17/2016.
 */

public class FormInputAdapter extends RecyclerView.Adapter<FormInputAdapter.FormInputViewHolder> {
    List<String> inputs;

    public FormInputAdapter(List<String> inputs) {
        this.inputs = inputs;
    }

    @Override
    public void onBindViewHolder(FormInputViewHolder holder, int position) {
        holder.inputField.setText(inputs.get(position));
    }

    @Override
    public FormInputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_input_row, null, false);
        return new FormInputViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return inputs.size();
    }

    class FormInputViewHolder extends RecyclerView.ViewHolder {
        public TextInputEditText inputField;

        public FormInputViewHolder(View v) {
            super(v);

            inputField = (TextInputEditText) v.findViewById(R.id.form_input_field);
        }
    }
}
