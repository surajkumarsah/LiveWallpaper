package com.example.livewallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.livewallpaper.Helper.SaveImageHelper;
import com.example.livewallpaper.utils.utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ViewWallpaperActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fabDownload,fabWallpaper;
    ImageView img;
    private Bitmap mBitmap;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);


        MobileAds.initialize(this, "ca-app-pub-9028512770259391~2388554457");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        Picasso.get().load(utils.selected_wallpaper.getImageLink()).into(img);

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
        img = (ImageView) findViewById(R.id.thumbImage);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBarLayout);
        fabDownload = (FloatingActionButton) findViewById(R.id.fab_download);
        fabWallpaper = (FloatingActionButton) findViewById(R.id.fab_wallpaper);

        collapsingToolbarLayout.setTitle(utils.CATEGORY_SELECTION);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
       if (id == R.id.share) {
          /*  try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plan");
                i.putExtra(Intent.ACTION_SEND, img);
                i.putExtra(Intent.EXTRA_TEXT, "Image");
                startActivity(Intent.createChooser(i, "Share with :"));
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Please, Try again...",Toast.LENGTH_SHORT).show();
            }
        }
           Bitmap icon = mBitmap;
           Intent share = new Intent(Intent.ACTION_SEND);
           share.setType("image/jpeg");

           ContentValues values = new ContentValues();
           values.put(MediaStore.Images.Media.TITLE, "title");
           values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
           Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                   values);


           OutputStream outstream;
           try {
               outstream = getContentResolver().openOutputStream(uri);
               icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
               outstream.close();
           } catch (Exception e) {
               System.err.println(e.toString());
           }

           share.putExtra(Intent.EXTRA_STREAM, uri);
           startActivity(Intent.createChooser(share, "Share Image"));
*/

           Bitmap b = BitmapFactory.decodeResource(getResources(),R.id.fab_download);
           Intent share = new Intent(Intent.ACTION_SEND);
           share.setType("image/jpeg");
           ByteArrayOutputStream bytes = new ByteArrayOutputStream();
           b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
           String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                   b, "Title", null);
           Uri imageUri =  Uri.parse(path);
           share.putExtra(Intent.EXTRA_STREAM, imageUri);
           startActivity(Intent.createChooser(share, "Select"));
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }
}
