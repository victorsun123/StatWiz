package com.mdb.statwiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

/**
 * Created by Sayan Paul on 11/12/2016.
 */

public class FunctionViewAdapter extends RecyclerView.Adapter<FunctionViewAdapter.FunctionViewHolder> {

    Context context;
    List<Function> functionsList;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    public FunctionViewAdapter(Context context, List<Function> functions) {
        this.context = context;
        this.functionsList = functions;
    }

    @Override
    public void onBindViewHolder(FunctionViewHolder holder, int position) {
        Function f = functionsList.get(position);
        String letter = String.valueOf(f.name.charAt(0));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        holder.letterView.setImageDrawable(drawable);
        holder.itemName.setText(f.name);
    }

    @Override
    public FunctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.function_row_view, null, false);
        return new FunctionViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return functionsList.size();
    }

    class FunctionViewHolder extends RecyclerView.ViewHolder {
        public ImageView letterView;
        public TextView itemName;

        public FunctionViewHolder(View v) {
            super(v);

            letterView = (ImageView) v.findViewById(R.id.function_item_letter);
            itemName = (TextView) v.findViewById(R.id.function_item_name);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
                    FormInputFragment formInputFragment = new FormInputFragment();

                    String functionName = ((TextView) v.findViewById(R.id.function_item_name)).getText().toString();
                    Bundle args = new Bundle();
                    args.putString(MainActivity.FUNCTIONNAME, functionName);
                    formInputFragment.setArguments(args);

                    manager.beginTransaction().replace(R.id.fragment_container, formInputFragment).commit();
                }
            });
        }
    }
}
