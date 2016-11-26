package com.mdb.statwiz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdb.statwiz.R;

/**
 * Created by Sayan Paul on 11/25/2016.
 */

public class SamplingDefinitionsAdapter extends RecyclerView.Adapter<SamplingDefinitionsAdapter.DefinitionViewHolder> {

    private String[] terms;
    private String[] definitions;

    public SamplingDefinitionsAdapter(Context context) {
        terms = context.getResources().getStringArray(R.array.terms);
        definitions = context.getResources().getStringArray(R.array.definitions);
    }

    @Override
    public DefinitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sampling_row_view,
                parent, false);
        return new DefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DefinitionViewHolder holder, int position) {
        holder.term.setText(terms[position]);
        holder.definition.setText(definitions[position]);
    }

    @Override
    public int getItemCount() {
        return terms.length;
    }

    class DefinitionViewHolder extends RecyclerView.ViewHolder {
        TextView term, definition;

        DefinitionViewHolder(View v) {
            super(v);
            term = (TextView) v.findViewById(R.id.term);
            definition = (TextView) v.findViewById(R.id.definition);
        }
    }
}
