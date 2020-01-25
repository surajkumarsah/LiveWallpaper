package com.example.livewallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.livewallpaper.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {

    private EditText Username, Password;
    private Button submit;
    DatabaseReference loginRef;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);

        submit = findViewById(R.id.login);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });





    }

    private void validateData()
    {
        username = Username.getText().toString();
        password = Password.getText().toString();

        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(Login_Activity.this,"Please,Enter username.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(Login_Activity.this,"Please,Enter Password.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            searchDB();
        }

    }

    private void searchDB()
    {
        loginRef = FirebaseDatabase.getInstance().getReference();

        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("login").child(username).exists())
                {
                    Users users = dataSnapshot.child("login").child(username).getValue(Users.class);

                    if (users.getUser().equals(username))
                    {
                        if (users.getPassword().equals(password))
                        {
                            Intent intent = new Intent(Login_Activity.this,UpdateImage_Activity.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
