package com.chengfu.recyclerviewplus;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chengfu.recyclerviewplus.adapter.HeaderAndFooterRecyclerViewAdapter;

/**
 * Created by cundong on 2015/10/22.
 * RecyclerView设置Header/Footer�?用到的工具类
 */
public class RecyclerViewUtils {

    /**
     * 设置HeaderView
     *
     * @param recyclerView
     * @param view
     */
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;
        if (headerAndFooterAdapter.getHeaderViewsCount() == 0) {
            headerAndFooterAdapter.addHeaderView(view);

            recyclerView.getLayoutManager();
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int firstVisibleItemPosition = -1;
            if (layoutManager instanceof LinearLayoutManager) {
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] firstPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                staggeredGridLayoutManager.findFirstVisibleItemPositions(firstPositions);
                firstVisibleItemPosition = findMin(firstPositions);
            }
            if (firstVisibleItemPosition == 0) {
                recyclerView.scrollToPosition(0);
            }

        }
    }

    private static int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static boolean hasHeaderView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return false;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;
        if (headerAndFooterAdapter.getHeaderView() == view) {
            return true;
        }
        return false;
    }

    /**
     * 设置FooterView
     *
     * @param recyclerView
     * @param view
     */
    public static void setFooterView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;
        if (headerAndFooterAdapter.getFooterViewsCount() == 0) {
            headerAndFooterAdapter.addFooterView(view);
        }
    }

    /**
     * 移除FooterView
     *
     * @param recyclerView
     */
    public static void removeFooterView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {

            int footerViewCounter = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount();
            if (footerViewCounter > 0) {
                View footerView = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).removeFooterView(footerView);
            }
        }
    }

    /**
     * 移除HeaderView
     *
     * @param recyclerView
     */
    public static void removeHeaderView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {

            int headerViewCounter = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                View headerView = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderView();
                ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).removeHeaderView(headerView);
            }
        }
    }

    /**
     * 请使用本方法替代RecyclerView.ViewHolder的getLayoutPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {

            int headerViewCounter = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - headerViewCounter;
            }
        }

        return holder.getLayoutPosition();
    }

    /**
     * 请使用本方法替代RecyclerView.ViewHolder的getAdapterPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {

            int headerViewCounter = ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getAdapterPosition() - headerViewCounter;
            }
        }

        return holder.getAdapterPosition();
    }
}