package com.mercadolibretest.testmobile.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mercadolibretest.testmobile.R;
import com.mercadolibretest.testmobile.models.item.Picture;

import java.util.List;

public class AdapterPicture extends RecyclerView.Adapter {

    private List<? extends Picture> pictures;
    private AdapterPicture.OnPictureClickListener onPictureClickListener;
    private AdapterPicture.OnPictureLongClickListener onPictureLongClickListener;
    private Context context;


    public AdapterPicture(List<? extends Picture> pictures, Context context) {
        this.pictures = pictures;
        this.context = context;
    }

    static final class PictureHolder extends RecyclerView.ViewHolder {
        final ImageView imagePicture;
        final ProgressBar progressBar;

        private PictureHolder(final View view) {
            super(view);
            this.imagePicture = view.findViewById(R.id.imageViewPicture);
            this.progressBar = view.findViewById(R.id.progressBarPicture);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        RecyclerView.ViewHolder vh;
        final View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_picture, viewGroup, false);

        vh = new PictureHolder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder customViewHolder, final int i) {
        try {
            final Picture picture = pictures.get(i);

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y / 3;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, height);
            ((PictureHolder) customViewHolder).imagePicture.setLayoutParams(params);
            ((PictureHolder) customViewHolder).imagePicture.setScaleType(ImageView.ScaleType.FIT_CENTER);

            if (picture.getUrl() != null && !picture.getUrl().isEmpty()) {

                CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
                circularProgressDrawable.setStrokeWidth(20f);
                circularProgressDrawable.setCenterRadius(100f);
                circularProgressDrawable.start();

                Glide.with(context).load(picture.getUrl()).placeholder(circularProgressDrawable)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .error(context.getResources().getDrawable(R.drawable.default_item_image))
                        .into(((PictureHolder) customViewHolder).imagePicture);

            }


            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPictureClickListener.onPictureClick(picture);
                }
            };

            ((PictureHolder) customViewHolder).imagePicture.setOnClickListener(listener);

        } catch (Exception e) {
            Log.e("AdapterPicture",e.getMessage(),e);
        }


    }


    @Override
    public int getItemCount() {
        return (null != pictures ? pictures.size() : 0);
    }

    public interface OnPictureClickListener {
        void onPictureClick(Picture picture);
    }

    public interface OnPictureLongClickListener {
        boolean onPictureLongClick(Picture picture);
    }

    public AdapterPicture.OnPictureClickListener getOnItemClickListener() {
        return onPictureClickListener;
    }

    public void setOnPictureClickListener(AdapterPicture.OnPictureClickListener onPictureClickListener) {
        this.onPictureClickListener = onPictureClickListener;
    }

    public AdapterPicture.OnPictureLongClickListener getOnItemLongClickListener() {
        return onPictureLongClickListener;
    }

    public void setOnPictureLongClickListener(AdapterPicture.OnPictureLongClickListener onPictureLongClickListener) {
        this.onPictureLongClickListener = onPictureLongClickListener;
    }

}