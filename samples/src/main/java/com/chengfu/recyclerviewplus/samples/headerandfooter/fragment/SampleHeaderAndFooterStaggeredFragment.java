package com.chengfu.recyclerviewplus.samples.headerandfooter.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengfu.recyclerviewplus.samples.R;
import com.chengfu.recyclerviewplus.samples.headerandfooter.adapter.StaggeredColorItemsAdapter;
import com.chengfu.recyclerviewplus.samples.headerandfooter.utils.DensityUtil;

/**
 * Created by ChengFu on 2017/8/18.
 */

public class SampleHeaderAndFooterStaggeredFragment extends Fragment {

    private StaggeredColorItemsAdapter colorItemsAdapter;
    private TextView headerView;
    private RecyclerView recyclerView;
    private TextView footerView;

    public static SampleHeaderAndFooterStaggeredFragment newInstance() {
        SampleHeaderAndFooterStaggeredFragment fragment = new SampleHeaderAndFooterStaggeredFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_header_and_footer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        colorItemsAdapter = new StaggeredColorItemsAdapter(getActivity(), 20);
//        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(colorItemsAdapter);
        recyclerView.setAdapter(colorItemsAdapter);

        headerView = new TextView(getActivity());
        headerView.setBackgroundColor(Color.parseColor("#FF4081"));
        RecyclerView.LayoutParams l = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 150));
        headerView.setLayoutParams(l);
        headerView.setTextColor(Color.WHITE);
        headerView.setTextSize(20);
        headerView.setGravity(Gravity.CENTER);
        headerView.setText(R.string.header);
        colorItemsAdapter.setHeaderView(headerView, true);

        footerView = new TextView(getActivity());
        footerView.setBackgroundColor(Color.parseColor("#FF4081"));
        RecyclerView.LayoutParams l1 = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 100));
        footerView.setLayoutParams(l1);
        footerView.setTextColor(Color.WHITE);
        footerView.setTextSize(20);
        footerView.setGravity(Gravity.CENTER);
        footerView.setText(R.string.footer);
        colorItemsAdapter.setFooterView(footerView, true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_header) {
            colorItemsAdapter.setHeaderView(headerView, true);
            return true;
        } else if (id == R.id.action_remove_header) {
            colorItemsAdapter.removeHeaderView();
            return true;
        } else if (id == R.id.action_add_footerer) {
            colorItemsAdapter.setFooterView(footerView, true);
            return true;
        } else if (id == R.id.action_remove_footer) {
            colorItemsAdapter.removeFooterView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
