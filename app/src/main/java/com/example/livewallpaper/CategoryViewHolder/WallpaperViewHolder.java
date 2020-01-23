package com.example.livewallpaper.CategoryViewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livewallpaper.R;

public class WallpaperViewHolder extends RecyclerView.ViewHolder
{

    public ImageView imageView;

    public WallpaperViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.wallpaper);

    }
}
