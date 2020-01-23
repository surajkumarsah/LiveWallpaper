package com.example.livewallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.livewallpaper.Helper.SaveImageHelper;
import com.example.livewallpaper.utils.utils;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.UUID;

public class ViewWallpaperActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fabDownload,fabWallpaper;
    ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        initialize();

        Picasso.get().load(utils.selected_wallpaper.getImageLink()).into(i);

        fabWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(utils.selected_wallpaper.getImageLink()).into(target);
            }
        });

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = UUID.randomUUID().toString() + ".png";

                AlertDialog.Builder b = new AlertDialog.Builder(ViewWallpaperActivity.this);

                b.setMessage("Downloading...");
                AlertDialog alertDialog = b.create();
                alertDialog.show();

                Picasso.get().load(utils.selected_wallpaper.getImageLink()).into(new SaveImageHelper(getBaseContext(),alertDialog,getApplicationContext().getContentResolver(),fileName,"Image"));
            }
        });

    }

    private void initialize()
    {
        i = (ImageView) findViewById(R.id.thumbImage);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBarLayout);
        fabDownload = (FloatingActionButton) findViewById(R.id.fab_download);
        fabWallpaper = (FloatingActionButton) findViewById(R.id.fab_wallpaper);

        collapsingToolbarLayout.setTitle(utils.CATEGORY_SELECTION);
    }
}
