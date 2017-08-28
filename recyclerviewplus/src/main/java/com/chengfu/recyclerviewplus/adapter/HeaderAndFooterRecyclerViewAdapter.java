package com.chengfu.recyclerviewplus.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ChengFu on 2017/8/17.
 * <p>
 * A recyclerView adapter contain set header and footer
 */
public class HeaderAndFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = -Integer.MAX_VALUE;
    private static final int TYPE_FOOTER_VIEW = -Integer.MAX_VALUE + 1;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    private View mHeaderView;
    private View mFooterView;

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyItemRangeChanged((hasHeader() ? 1 : 0), mInnerAdapter.getItemCount());
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + (hasHeader() ? 1 : 0), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + (hasHeader() ? 1 : 0), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + (hasHeader() ? 1 : 0), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            notifyItemRangeChanged(fromPosition + (hasHeader() ? 1 : 0), toPosition + (hasHeader() ? 1 : 0) + itemCount);
        }
    };

    private HeaderAndFooterRecyclerViewAdapter() {
    }

    /**
     * set your adapter
     *
     * @param innerAdapter
     */
    public HeaderAndFooterRecyclerViewAdapter(RecyclerView.Adapter innerAdapter) {
        setAdapter(innerAdapter);
    }

    private void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {

        if (adapter != null) {
            if (!(adapter instanceof RecyclerView.Adapter))
                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
        }

        if (mInnerAdapter != null) {
            notifyItemRangeRemoved(hasHeader() ? 1 : 0, mInnerAdapter.getItemCount());
            mInnerAdapter.unregisterAdapterDataObserver(mDataObserver);
        }

        this.mInnerAdapter = adapter;
        mInnerAdapter.registerAdapterDataObserver(mDataObserver);
        notifyItemRangeInserted(hasHeader() ? 1 : 0, mInnerAdapter.getItemCount());
    }

    /**
     * get your adapter
     *
     * @return your adapter
     */
    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }

    /**
     * set the header view
     *
     * @param header
     */
    public void setHeaderView(View header) {
        if (header == null || mHeaderView == header) {
            return;
        }
        mHeaderView = header;
        notifyItemInserted(0);
    }

    /**
     * set the footer view
     *
     * @param footer
     */
    public void setFooterView(View footer) {
        if (footer == null || mFooterView == footer) {
            return;
        }
        mFooterView = footer;
        notifyItemInserted(getItemCount() - 1);

    }

    /**
     * has the header view
     *
     * @return has
     */
    public boolean hasHeader() {
        return mHeaderView != null;
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
     * get the header view
     *
     * @return header view
     */
    public View getHeaderView() {
        return mHeaderView;
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
     * get the footer view
     *
     * @return footer view
     */
    public View getFooterView() {
        return mFooterView;
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

    @Override
    public int getItemCount() {
        return (hasHeader() ? 1 : 0) + (hasFooter() ? 1 : 0) + mInnerAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader() && position == 0) {
            return TYPE_HEADER_VIEW;
        }
        if (hasFooter() && position == getItemCount() - 1) {
            return TYPE_FOOTER_VIEW;
        }
        return mInnerAdapter.getItemViewType(position - (hasHeader() ? 1 : 0));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER_VIEW) {
            return new HeaderViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER_VIEW) {
            return new FooterViewHolder(mFooterView);
        } else {
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= (hasHeader() ? 1 : 0) && position < (hasHeader() ? 1 : 0) + mInnerAdapter.getItemCount()) {
            mInnerAdapter.onBindViewHolder(holder, position - (hasHeader() ? 1 : 0));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            GridLayoutManager gridManager = ((GridLayoutManager) manager);
            GridLayoutManager.SpanSizeLookup userSpanSizeLookup = gridManager.getSpanSizeLookup();
            gridManager.setSpanSizeLookup(new HeaderAndFooterSpanSizeLookup(this, gridManager, userSpanSizeLookup));
        }
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

    public class HeaderSpaceHolder extends RecyclerView.ViewHolder {
        public HeaderSpaceHolder(View itemView) {
            super(itemView);
        }
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


    private class HeaderAndFooterSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private HeaderAndFooterRecyclerViewAdapter adapter;
        private GridLayoutManager gridLayoutManager;
        private GridLayoutManager.SpanSizeLookup userSpanSizeLookup;

        public HeaderAndFooterSpanSizeLookup(HeaderAndFooterRecyclerViewAdapter adapter, GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup userSpanSizeLookup) {
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
