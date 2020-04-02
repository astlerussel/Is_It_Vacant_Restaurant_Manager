package com.example.isitvacantrestaurantmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class Gg extends AppCompatActivity {
     GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gg);
        grid = findViewById(R.id.grid);
        grid.setAdapter(new ImageAdapter( this));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),FullScreen.class);
                intent.putExtra("id",position);
                startActivity(intent);
            }
        });
    }
}
