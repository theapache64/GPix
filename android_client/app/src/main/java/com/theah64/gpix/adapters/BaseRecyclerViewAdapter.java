package com.theah64.gpix.adapters;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;


import java.util.List;

/**
 * Created by shifar on 15/9/16.
 */
public abstract class BaseRecyclerViewAdapter<V extends RecyclerView.ViewHolder, M> extends RecyclerView.Adapter<V> {
    
    private final Callback callback;

    @LayoutRes
    private final int layoutRowId;
    private List<M> data;
    private LayoutInflater inflater;

    BaseRecyclerViewAdapter(final List<M> data, int layoutRowId, @Nullable Callback callback) {
        this.data = data;
        this.layoutRowId = layoutRowId;
        this.callback = callback;
    }

    public final List<M> getData() {
        return this.data;
    }

    public final void setData(List<M> data) {
        this.data = data;
    }

    public Callback getCallback() {
        return callback;
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        final View row = inflater.inflate(layoutRowId, parent, false);
        return getNewRow(row);
    }

    protected abstract V getNewRow(View row);

    public interface Callback {
        void onItemClick(int position);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        onBindViewHolder(holder, data.get(position));
    }

    public abstract void onBindViewHolder(V holder, M model);
}

