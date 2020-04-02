package com.example.isitvacantrestaurantmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class FullScreen extends AppCompatActivity {
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        image = findViewById(R.id.image);
       // getSupportActionBar().setTitle("Full Screen");
        Intent intent = getIntent();
        int position= intent.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);
        image.setImageResource(imageAdapter.imageArray[position]);
    }
}
