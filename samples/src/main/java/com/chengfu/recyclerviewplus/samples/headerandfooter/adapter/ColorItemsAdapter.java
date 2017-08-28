package com.chengfu.recyclerviewplus.samples.headerandfooter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengfu.recyclerviewplus.adapter.HAFRecyclerViewAdapter;
import com.chengfu.recyclerviewplus.samples.R;
import com.chengfu.recyclerviewplus.samples.headerandfooter.utils.ValueInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengFu on 2017/8/18.
 */

public class ColorItemsAdapter extends HAFRecyclerViewAdapter {

    private int[] colors;
    private boolean test = false;
    private List<Integer> list;

    public ColorItemsAdapter(Context context, int numberOfItems) {
        colors = new int[numberOfItems];

        int startColor = ContextCompat.getColor(context, R.color.wisteria);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endColor = ContextCompat.getColor(context, R.color.emerald);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        ValueInterpolator interpolatorR = new ValueInterpolator(0, numberOfItems - 1, endR, startR);
        ValueInterpolator interpolatorG = new ValueInterpolator(0, numberOfItems - 1, endG, startG);
        ValueInterpolator interpolatorB = new ValueInterpolator(0, numberOfItems - 1, endB, startB);

        list = new ArrayList<>();
        for (int i = 0; i < numberOfItems; ++i) {
            colors[i] = Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i));
            list.add(colors[i]);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder1(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_list, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder1(RecyclerView.ViewHolder holder, int position) {
        SampleViewHolder viewHolder = (SampleViewHolder) holder;
        viewHolder.mainLayout.setBackgroundColor(list.get(position));
        viewHolder.text.setText(viewHolder.itemView.getResources().getString(R.string.item) + (position + 1));
    }

    @Override
    public int getItemCount1() {
        return list.size();
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {
        public View mainLayout;
        TextView text;

        public SampleViewHolder(View view) {
            super(view);
            mainLayout = view.findViewById(R.id.layout);
            text = view.findViewById(R.id.text);
        }
    }

    public void kk() {
        list.remove(0);
        notifyItemRemoved(getRealNotifyPosition(0));
    }
}