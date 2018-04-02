package com.chengfu.recyclerviewplus;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chengfu.recyclerviewplus.adapter.HAFRecyclerViewAdapter;

/**
 * Created by ChengFu on 2017/8/17.
 * <p>
 * a util for recyclerView to set header adn footer
 */
public class RecyclerViewHeaderUtils {

    /**
     * the recyclerView has header view
     *
     * @param recyclerView
     * @return if the recyclerView has header view,return true,else return false
     */
    public static boolean hasHeaderView(RecyclerView recyclerView) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HAFRecyclerViewAdapter)) {
            return false;
        }

        HAFRecyclerViewAdapter hafRecyclerViewAdapter = (HAFRecyclerViewAdapter) outerAdapter;
        return hafRecyclerViewAdapter.hasHeader();
    }

    /**
     * set the recyclerView header view
     *
     * @param recyclerView
     * @param view
     */
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        setHeaderView(recyclerView, view, false);
    }

    /**
     * set the recyclerView header view
     *
     * @param recyclerView
     * @param view
     * @param scrollToTop
     */
    public static void setHeaderView(RecyclerView recyclerView, View view, boolean scrollToTop) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HAFRecyclerViewAdapter)) {
            return;
        }

        HAFRecyclerViewAdapter hafRecyclerViewAdapter = (HAFRecyclerViewAdapter) outerAdapter;
        hafRecyclerViewAdapter.setHeaderView(view);

        if (scrollToTop) {
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


    /**
     * remove the recyclerView header view
     *
     * @param recyclerView
     */
    public static void removeHeaderView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null && !(outerAdapter instanceof HAFRecyclerViewAdapter)) {
            return;
        }
        HAFRecyclerViewAdapter hafRecyclerViewAdapter = (HAFRecyclerViewAdapter) outerAdapter;
        hafRecyclerViewAdapter.removeHeaderView();
    }

    /**
     * the recyclerView has footer view
     *
     * @param recyclerView
     * @return if the recyclerView has footer view,return true,else return false
     */
    public static boolean hasFooterView(RecyclerView recyclerView) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HAFRecyclerViewAdapter)) {
            return false;
        }

        HAFRecyclerViewAdapter hafRecyclerViewAdapter = (HAFRecyclerViewAdapter) outerAdapter;
        return hafRecyclerViewAdapter.hasFooter();
    }

    /**
     * set recyclerView has footer view
     *
     * @param recyclerView
     * @param view
     */
    public static void setFooterView(RecyclerView recyclerView, View view) {
        setFooterView(recyclerView, view, false);
    }

    /**
     * set recyclerView has footer view
     *
     * @param recyclerView
     * @param view
     * @param scrollToBottom
     */
    public static void setFooterView(RecyclerView recyclerView, View view, boolean scrollToBottom) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HAFRecyclerViewAdapter)) {
            return;
        }

        HAFRecyclerViewAdapter hafRecyclerViewAdapter = (HAFRecyclerViewAdapter) outerAdapter;
        hafRecyclerViewAdapter.setFooterView(view);

        if (scrollToBottom) {
            recyclerView.getLayoutManager();
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int lastVisibleItemPosition = -1;
            if (layoutManager instanceof LinearLayoutManager) {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] firstPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                staggeredGridLayoutManager.findLastVisibleItemPositions(firstPositions);
                lastVisibleItemPosition = findMax(firstPositions);
            }
            if (lastVisibleItemPosition == outerAdapter.getItemCount() - 2) {
                recyclerView.scrollToPosition(outerAdapter.getItemCount() - 1);
            }
        }

    }

    /**
     * remove recyclerView has footer view
     *
     * @param recyclerView
     */
    public static void removeFooterView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null && !(outerAdapter instanceof HAFRecyclerViewAdapter)) {
            return;
        }
        HAFRecyclerViewAdapter hafRecyclerViewAdapter = (HAFRecyclerViewAdapter) outerAdapter;
        hafRecyclerViewAdapter.removeFooterView();
    }


    /**
     * replace the RecyclerView.ViewHolder.getLayoutPosition()
     *
     * @param recyclerView
     * @param holder
     * @return layout position
     */
    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HAFRecyclerViewAdapter) {
            return holder.getLayoutPosition() - (((HAFRecyclerViewAdapter) outerAdapter).hasHeader() ? 1 : 0);
        }
        return holder.getLayoutPosition();
    }

    /**
     * replace the RecyclerView.ViewHolder.getAdapterPosition()
     *
     * @param recyclerView
     * @param holder
     * @return adapter position
     */
    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HAFRecyclerViewAdapter) {
            return holder.getAdapterPosition() - (((HAFRecyclerViewAdapter) outerAdapter).hasHeader() ? 1 : 0);
        }

        return holder.getAdapterPosition();
    }

    /**
     * get the rel adapter item count
     *
     * @param recyclerView
     * @return rel ttem count
     */
    public static int getRelItemCount(RecyclerView recyclerView) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HAFRecyclerViewAdapter) {
            return outerAdapter.getItemCount() - (((HAFRecyclerViewAdapter) outerAdapter).hasHeader() ? 1 : 0) - (((HAFRecyclerViewAdapter) outerAdapter).hasFooter() ? 1 : 0);
        }

        return outerAdapter.getItemCount();
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

    private static int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}