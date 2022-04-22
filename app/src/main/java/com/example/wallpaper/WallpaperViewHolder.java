package com.example.wallpaper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class WallpaperViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private final ImageView image;
    private ImageView image1;

    public WallpaperViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.item_wallpaper_image);
    }

    public void bind(final Wallpaper wallpaper, final WallpaperSelectListener listener) {
        Glide.with(itemView).load(wallpaper.getImageUri()).into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWallpaperSelect(wallpaper);
            }
        });
    }
}
