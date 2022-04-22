package com.example.wallpaper;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WallpapersListFragment extends Fragment implements WallpaperSelectListener {
    private RecyclerView wallpaperRecyclerView;
    private WallpaperGalleryRecyclerAdapter wallpaperGalleryRecyclerAdapter;
    private List<Wallpaper> wallpapers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            wallpapers = new ArrayList<>();
            for (int i = 1; i <= 12; i++)
                wallpapers.add(new Wallpaper("https://github.com/Deven990/Wallpapers/raw/main/" + i + ".jpg"));
            wallpaperGalleryRecyclerAdapter = new WallpaperGalleryRecyclerAdapter(this);
            wallpaperGalleryRecyclerAdapter.setWallpapers(wallpapers);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listwallpapers, container, false);

        wallpaperRecyclerView = view.findViewById(R.id.fragment_listwallpapers_recyclerView);
        wallpaperRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wallpaperRecyclerView.setAdapter(wallpaperGalleryRecyclerAdapter);

        return view;
    }

    private void setHomeScreenWallpaper(Bitmap bitmap) {
        try {
            WallpaperManager.getInstance(getContext()).setBitmap(bitmap);
            Toast.makeText(getContext(), "Wallpaper Set", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCroppedHomeScreenWallpaper(Bitmap bitmap) {
        try {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());

            int wallpaperHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
            int wallpaperWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

            Point start = new Point(0, 0);
            Point end = new Point(bitmap.getWidth(), bitmap.getHeight());

            if (bitmap.getWidth() > wallpaperWidth) {
                start.x = (bitmap.getWidth() - wallpaperWidth) / 2;
                end.x = start.x + wallpaperWidth;
            }

            if (bitmap.getHeight() > wallpaperHeight) {
                start.y = (bitmap.getHeight() - wallpaperHeight) / 2;
                end.y = start.y + wallpaperHeight;
            }

            wallpaperManager.setBitmap(bitmap, new Rect(start.x, start.y, end.x, end.y), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLockScreenWallpaper(Bitmap bitmap) {
        try {
            WallpaperManager.getInstance(getContext()).setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWallpaperSelect(Wallpaper wallpaper) {

        Glide.with(getContext())
                .asBitmap()
                .load(wallpaper.getImageUri())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        setHomeScreenWallpaper(resource);
//                        setCroppedHomeScreenWallpaper(resource);
//                        setLockScreenWallpaper(resource);
//                        Toast.makeText(getContext(), wallpaper.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
