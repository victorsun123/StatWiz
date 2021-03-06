package com.mdb.statwiz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mdb.statwiz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter Class for the Recycler View displaying descriptive data
 */

public class DescriptiveAdapter extends RecyclerView.Adapter<DescriptiveAdapter.CustomViewHolder> {

    private Context context;
    private List<String> properties;
    private List<String> values;

    /**
     * @param context
     * @param properties property type i.e. mean, median, standard deviation, etc.
     * @param values value of property
     */
    public DescriptiveAdapter(Context context, List<String> properties, ArrayList<String> values) {
        this.context = context;
        this.properties = properties;
        this.values = values;
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.descriptive_row_view,
                parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.property.setText(properties.get(position));
        holder.value.setText(values.get(position));
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView property, value;

        CustomViewHolder(View itemView) {
            super(itemView);
            property = (TextView) itemView.findViewById(R.id.property);
            value = (TextView) itemView.findViewById(R.id.value);
        }
    }
}
