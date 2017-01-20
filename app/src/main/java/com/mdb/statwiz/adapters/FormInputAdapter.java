package com.mdb.statwiz.adapters;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdb.statwiz.R;

import java.util.List;

/*
 * Adapter for recycler view for input form for distributions
 */

public class FormInputAdapter extends RecyclerView.Adapter<FormInputAdapter.FormInputViewHolder> {
    List<String> inputs;
    Context context;

    public FormInputAdapter(Context context, List<String> inputs) {
        this.inputs = inputs;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(FormInputViewHolder holder, int position) {
        holder.inputLayout.setHint(inputs.get(position));
    }

    @Override
    public FormInputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.form_input_row, parent, false);
        return new FormInputViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return inputs.size();
    }


    public class FormInputViewHolder extends RecyclerView.ViewHolder {
        public TextInputEditText inputField;
        public TextInputLayout inputLayout;

        public FormInputViewHolder(View v) {
            super(v);

            inputField = (TextInputEditText) v.findViewById(R.id.form_input_field);
            inputLayout = (TextInputLayout) v.findViewById(R.id.form_input_field_layout);
        }
    }
}
