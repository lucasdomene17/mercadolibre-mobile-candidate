package com.mercadolibretest.testmobile.adapters;

import android.content.Context;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mercadolibretest.testmobile.R;
import com.mercadolibretest.testmobile.models.search.Result;

import java.util.List;

public class AdapterItem extends RecyclerView.Adapter {

    private List<? extends Result> items;
    private AdapterItem.OnItemClickListener onItemClickListener;
    private AdapterItem.OnItemLongClickListener onItemLongClickListener;
    private Context context;
    private final int ITEM_VIEW_TYPE_BASIC = 0;
    private final int ITEM_VIEW_TYPE_FOOTER = 1;
    boolean value;


    public AdapterItem(List<? extends Result> items, Context context) {
        this.items = items;
        this.context = context;
    }

    static final class ItemListHolder extends RecyclerView.ViewHolder {
        final TextView titleItem;
        final TextView priceItem;
        final ImageView imageItem;
        final LinearLayout linearItem;

        private ItemListHolder(final View view) {
            super(view);
            this.titleItem = view.findViewById(R.id.textViewTitleItem);
            this.priceItem = view.findViewById(R.id.textViewPriceItem);
            this.imageItem = view.findViewById(R.id.imageViewItem);
            this.linearItem = view.findViewById(R.id.linearContent);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == ITEM_VIEW_TYPE_BASIC) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.row_item, viewGroup, false);

            vh = new ItemListHolder(v);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progress_bar, viewGroup, false);

            vh = new ProgressViewHolder(v);
        }

        return vh;
    }

    public void refreshAdapter(boolean value, List<Result> tempResults) {
        this.value = value;
        this.items = tempResults;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder customViewHolder, final int i) {
        if (customViewHolder instanceof ItemListHolder) {
            try {
                final Result item = items.get(i);

                if (item.getTitle() != null && !item.getTitle().isEmpty()) {
                    ((ItemListHolder) customViewHolder).titleItem.setText(item.getTitle());
                }
                if (item.getPrice() != null && item.getCurrencyId() != null && !item.getCurrencyId().isEmpty()) {
                    ((ItemListHolder) customViewHolder).priceItem.setText(item.getCurrencyId() + " " + String.valueOf(item.getPrice().doubleValue()));
                }
                if (item.getThumbnail() != null && !item.getThumbnail().isEmpty()) {
                    CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
                    circularProgressDrawable.setStrokeWidth(5f);
                    circularProgressDrawable.setCenterRadius(30f);
                    circularProgressDrawable.start();

                    Glide.with(context).load(item.getThumbnail()).placeholder(circularProgressDrawable)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                            .error(context.getResources().getDrawable(R.drawable.default_item_image))
                            .centerInside().into(((ItemListHolder) customViewHolder).imageItem);
                }


                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(item);
                    }
                };
                View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return onItemLongClickListener.onItemLongClick(item);
                    }
                };
                ((ItemListHolder) customViewHolder).linearItem.setOnClickListener(listener);

            } catch (Exception e) {
                Log.e("AdapterItem",e.getMessage(),e);
            }

        } else {
            if (!value) {
                ((ProgressViewHolder) customViewHolder).progressBar.setVisibility(View.VISIBLE);
                ((ProgressViewHolder) customViewHolder).progressBar.setIndeterminate(true);
            } else ((ProgressViewHolder) customViewHolder).progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) != null ? ITEM_VIEW_TYPE_BASIC : ITEM_VIEW_TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public interface OnItemClickListener {
        void onItemClick(Result item);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(Result item);
    }

    public AdapterItem.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterItem.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterItem.OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(AdapterItem.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        }
    }
}