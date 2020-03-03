package com.example.livewallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.livewallpaper.CategoryViewHolder.WallpaperViewHolder;
import com.example.livewallpaper.model.WallpaperItem;
import com.example.livewallpaper.utils.utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.example.livewallpaper.utils.utils.CATEGORY_ID;
import static com.example.livewallpaper.utils.utils.CATEGORY_SELECTION;

public class ListWallpaperActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Query query;

    FirebaseRecyclerOptions<WallpaperItem> options;
    FirebaseRecyclerAdapter<WallpaperItem, WallpaperViewHolder> adapter;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wallpaper);


        MobileAds.initialize(this,
                "ca-app-pub-9028512770259391~2388554457");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewWallpaper);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        query = FirebaseDatabase.getInstance().getReference().child("Background")
                .orderByChild("categoryId").equalTo(CATEGORY_ID);

        options = new FirebaseRecyclerOptions.Builder<WallpaperItem>()
                .setQuery(query, WallpaperItem.class).build();

        adapter = new FirebaseRecyclerAdapter<WallpaperItem, WallpaperViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final WallpaperViewHolder holder, final int position, @NonNull final WallpaperItem model) {

                Picasso.get().load(model.getImageLink())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(model.getImageLink())
                                        .error(R.drawable.ic_error_black_24dp)
                                        .into(holder.imageView);
                            }
                        });

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        utils.CATEGORY_ID = adapter.getRef(position).getKey();
                        utils.CATEGORY_SELECTION = model.getCategoryId();
                        utils.selected_wallpaper = model;

                        Intent i = new Intent(ListWallpaperActivity.this, ViewWallpaperActivity.class);
                        startActivity(i);
                    }
                });


            }

            @NonNull
            @Override
            public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_wallpaper_item, parent, false);

                int height = parent.getMeasuredHeight()/2;
                v.setMinimumHeight(height);


                return new WallpaperViewHolder(v);
            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter!=null)
        {
            adapter.startListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!= null)
        {
            adapter.startListening();
        }
    }

    @Override
    protected void onDestroy() {
        if (adapter!=null)
        {
            adapter.stopListening();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (adapter!=null)
        {
            adapter.stopListening();
        }
        super.onStop();
    }

}
