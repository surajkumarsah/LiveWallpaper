package com.example.livewallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.livewallpaper.CategoryViewHolder.CategoryViewHolder;
import com.example.livewallpaper.model.CategoryItem;
import com.example.livewallpaper.utils.utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    DatabaseReference CategoryBackgroundReference;
    FirebaseRecyclerOptions<CategoryItem> options;

    FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder> adapter;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this,
                "ca-app-pub-9028512770259391~2388554457");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        CategoryBackgroundReference = FirebaseDatabase.getInstance().getReference().child("CategoryBackground");

        options = new FirebaseRecyclerOptions.Builder<CategoryItem>()
                .setQuery(CategoryBackgroundReference, CategoryItem.class).build();


        adapter = new FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position, @NonNull final CategoryItem model) {

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

                holder.textView.setText(model.getName());

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        utils.CATEGORY_ID = adapter.getRef(position).getKey();
                        utils.CATEGORY_SELECTION = model.getName();

                        Intent i = new Intent(MainActivity.this, ListWallpaperActivity.class);
                        startActivity(i);
                    }
                });

            }


    

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_category_item,parent,false);


                return new CategoryViewHolder(v);
            }
        };


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (adapter!=null)
        {
            adapter.startListening();
        }
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
