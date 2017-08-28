package com.chengfu.recyclerviewplus.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ChengFu on 2017/8/22.
 * <p>
 * A recyclerView adapter contain set header and footer
 */

public abstract class HAFRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = -Integer.MAX_VALUE;
    private static final int TYPE_FOOTER_VIEW = -Integer.MAX_VALUE + 1;

    private RecyclerView mRecyclerView;
    private View mHeaderView;
    private View mFooterView;

    /**
     * has the header view
     *
     * @return has
     */
    public boolean hasHeader() {
        return mHeaderView != null;
    }

    /**
     * set the header view
     *
     * @param header
     */
    public void setHeaderView(View header) {
        setHeaderView(header, false);
    }

    public void setHeaderView(View header, boolean scrollToTop) {
        if (header == null || mHeaderView == header) {
            return;
        }
        mHeaderView = header;
        notifyItemInserted(0);
        if (scrollToTop && mRecyclerView != null) {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
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
                mRecyclerView.scrollToPosition(0);
            }
        }
    }

    /**
     * remove the header view
     */
    public void removeHeaderView() {
        if (mHeaderView != null) {
            notifyItemRemoved(0);
            mHeaderView = null;
        }
    }

    /**
     * has the footer view
     *
     * @return has
     */
    public boolean hasFooter() {
        return mFooterView != null;
    }

    /**
     * set the footer view
     *
     * @param footer
     */
    public void setFooterView(View footer) {
        setFooterView(footer, false);
    }

    /**
     * set the footer view
     *
     * @param footer
     * @param scrollToBottom
     */
    public void setFooterView(View footer, boolean scrollToBottom) {
        if (footer == null || mFooterView == footer) {
            return;
        }
        mFooterView = footer;
        notifyItemInserted(getItemCount() - 1);
        if (scrollToBottom) {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
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
            if (lastVisibleItemPosition == getItemCount() - 2) {
                mRecyclerView.scrollToPosition(getItemCount() - 1);
            }
        }
    }

    /**
     * remove the footer view
     */
    public void removeFooterView() {
        if (mFooterView != null) {
            notifyItemRemoved(getItemCount() - 1);
            mFooterView = null;
        }
    }

    public int getItemViewType1(int position) {
        return 0;
    }

    @Override
    public final int getItemViewType(int position) {
        if (hasHeader() && position == 0) {
            return TYPE_HEADER_VIEW;
        }
        if (hasFooter() && position == getItemCount() - 1) {
            return TYPE_FOOTER_VIEW;
        }
        return getItemViewType1(position - (hasHeader() ? 1 : 0));
    }

    public abstract int getItemCount1();

    @Override
    public final int getItemCount() {
        return (hasHeader() ? 1 : 0) + (hasFooter() ? 1 : 0) + getItemCount1();
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder1(ViewGroup parent, int viewType);

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER_VIEW) {
            return new HeaderViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER_VIEW) {
            return new FooterViewHolder(mFooterView);
        } else {
            return onCreateViewHolder1(parent, viewType);
        }
    }

    public abstract void onBindViewHolder1(RecyclerView.ViewHolder holder, int position);

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= (hasHeader() ? 1 : 0) && position < (hasHeader() ? 1 : 0) + getItemCount1()) {
            onBindViewHolder1(holder, position - (hasHeader() ? 1 : 0));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridManager = ((GridLayoutManager) manager);
            GridLayoutManager.SpanSizeLookup userSpanSizeLookup = gridManager.getSpanSizeLookup();
            gridManager.setSpanSizeLookup(new HAFSpanSizeLookup(this, gridManager, userSpanSizeLookup));
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            if ((holder.getLayoutPosition() == 0 && hasHeader()) || ((holder.getLayoutPosition() == getItemCount() - 1) && hasFooter())) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                p.setFullSpan(true);
            }
        }
    }

    public int getRealNotifyPosition(int position) {
        return position + (hasHeader() ? 1 : 0);
    }

    public int getRealAdapterPositions(int positions) {
        return positions - (hasHeader() ? 1 : 0);
    }

    public int getRealItemCount() {
        return getItemCount1();
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

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class HAFSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private HAFRecyclerViewAdapter adapter;
        private GridLayoutManager gridLayoutManager;
        private GridLayoutManager.SpanSizeLookup userSpanSizeLookup;

        public HAFSpanSizeLookup(HAFRecyclerViewAdapter adapter, GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup userSpanSizeLookup) {
            this.adapter = adapter;
            this.gridLayoutManager = gridLayoutManager;
            this.userSpanSizeLookup = userSpanSizeLookup;
        }

        @Override
        public int getSpanSize(int position) {
            if ((position == 0 && adapter.hasHeader()) || (position == adapter.getItemCount() - 1) && adapter.hasFooter()) {
                return gridLayoutManager.getSpanCount();
            } else if (userSpanSizeLookup != null) {
                return userSpanSizeLookup.getSpanSize(position - (adapter.hasHeader() ? 1 : 0));
            } else {
                return 1;
            }
        }
    }
}
