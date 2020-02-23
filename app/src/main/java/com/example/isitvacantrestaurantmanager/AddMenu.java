package com.example.isitvacantrestaurantmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddMenu extends AppCompatActivity {
    Button Add_top_dishes,Add_Food_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        Add_top_dishes = findViewById(R.id.Add_Top_Dishes);
        Add_Food_items = findViewById(R.id.Add_Food_items);
        Add_top_dishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),TopDishes.class));
            }
        });
        Add_Food_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddMenu.this, "Add Food Items", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
