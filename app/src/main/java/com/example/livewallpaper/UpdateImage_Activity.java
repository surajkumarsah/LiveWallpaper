package com.example.livewallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.livewallpaper.model.GalleryView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateImage_Activity extends AppCompatActivity {

    private DatabaseReference WallpaperRef, CategoryRef;
    private ProgressDialog loadingBar;

    private Button AddWallpaper, AddCategory;
    private EditText cid, imageLink, name, wallpaperId, wallpaperCid,imgLinkWallpaper;
    String c_id, image_Link, Name, wallpaper_Id, wallpaper_Cid,img_LinkWallpaper;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image_);


        MobileAds.initialize(this, "ca-app-pub-9028512770259391~2388554457");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AddWallpaper = (Button) findViewById(R.id.addWallpaper);
        AddCategory = (Button) findViewById(R.id.addCategory);
        cid = (EditText) findViewById(R.id.cId);
        imageLink = (EditText) findViewById(R.id.imgLink);
        name = (EditText) findViewById(R.id.name);
        wallpaperId = (EditText) findViewById(R.id.wallpaperId);
        wallpaperCid = (EditText) findViewById(R.id.cIdwallpaper);
        imgLinkWallpaper = (EditText) findViewById(R.id.imgLinkwallpaper);


        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });


        loadingBar = new ProgressDialog(this);

        AddWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateStudentAccount();
            }
        });

    }

    private void validateStudentAccount()
    {
        wallpaper_Id = wallpaperId.getText().toString();
        wallpaper_Cid = wallpaperCid.getText().toString();
        img_LinkWallpaper = imgLinkWallpaper.getText().toString();


        if (TextUtils.isEmpty(wallpaper_Id)) {
            Toast.makeText(UpdateImage_Activity.this, "Type wallpaperId", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(wallpaper_Cid)) {
            Toast.makeText(UpdateImage_Activity.this, "CategoryId", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(img_LinkWallpaper)) {
            Toast.makeText(UpdateImage_Activity.this, "ImageLink", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.show();
            AddWallpaperToDB();
        }
    }

    private void AddWallpaperToDB()
    {
        WallpaperRef = FirebaseDatabase.getInstance().getReference();

        WallpaperRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> categoryDetails = new HashMap<>();

                categoryDetails.put("imageLink", img_LinkWallpaper);
                categoryDetails.put("categoryId", wallpaper_Cid);

                WallpaperRef.child("Background").child(wallpaper_Id).updateChildren(categoryDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    loadingBar.dismiss();
                                    Toast.makeText(UpdateImage_Activity.this, "Successfully Data Updated.", Toast.LENGTH_SHORT).show();
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(UpdateImage_Activity.this, "Sorry, Data is not updated.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void validate() {
        c_id = cid.getText().toString();
        image_Link = imageLink.getText().toString();
        Name = name.getText().toString();

        if (TextUtils.isEmpty(c_id)) {
            Toast.makeText(UpdateImage_Activity.this, "Type Cid", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(image_Link)) {
            Toast.makeText(UpdateImage_Activity.this, "Image Link", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Name)) {
            Toast.makeText(UpdateImage_Activity.this, "Type Name", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.show();
            AddCategoryToDB();
        }
    }

    private void AddCategoryToDB() {
        CategoryRef = FirebaseDatabase.getInstance().getReference();

        CategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> categoryDetails = new HashMap<>();

                categoryDetails.put("imageLink", image_Link);
                categoryDetails.put("name", Name);

                CategoryRef.child("CategoryBackground").child(c_id).updateChildren(categoryDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    loadingBar.dismiss();
                                    Toast.makeText(UpdateImage_Activity.this, "Successfully Data Updated.", Toast.LENGTH_SHORT).show();
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(UpdateImage_Activity.this, "Sorry, Data is not updated.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}