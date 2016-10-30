package com.theah64.gpix.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.theah64.gpix.R;
import com.theah64.gpix.models.Image;

import java.util.List;

/**
 * Created by theapache64 on 30/10/16.
 */

public class ImagesAdapter extends BaseRecyclerViewAdapter<ImagesAdapter.ViewHolder, Image> {

    public ImagesAdapter(List<Image> data, int layoutRowId, @Nullable Callback callback) {
        super(data, layoutRowId, callback);
    }

    @Override
    protected ImagesAdapter.ViewHolder getNewRow(View row) {
        return new ViewHolder(row);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ImagesAdapter.ViewHolder holder, Image model) {
        ImageLoader.getInstance().displayImage(model.getThumbImageUrl(), holder.ivImageThumb);
        holder.tvImageDimension.setText(String.format("%dx%d", model.getWidth(), model.getHeight()));
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView ivImageThumb;
        private final TextView tvImageDimension;

        ViewHolder(View itemView) {
            super(itemView);

            this.ivImageThumb = (ImageView) itemView.findViewById(R.id.ivImageThumb);
            this.tvImageDimension = (TextView) itemView.findViewById(R.id.tvImageDimension);
        }
    }
}
