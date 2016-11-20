package com.mdb.statwiz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorsun on 11/13/16.
 */

public class DescriptiveAdapter extends RecyclerView.Adapter<DescriptiveAdapter.CustomViewHolder> {

    private Context context;
    private List<String> properties;
    private List<String> values;

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
        holder.value.setText((values.get(position)));
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView property, value;

        public CustomViewHolder(View itemView) {
            super(itemView);
            property = (TextView) itemView.findViewById(R.id.property);
            value = (TextView) itemView.findViewById(R.id.value);
        }
    }
}
