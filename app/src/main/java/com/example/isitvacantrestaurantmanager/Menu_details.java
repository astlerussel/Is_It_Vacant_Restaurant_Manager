package com.example.isitvacantrestaurantmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Menu_details extends AppCompatActivity {

    TextView Edit,Remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);
        Edit = findViewById(R.id.Edit);
        Remove = findViewById(R.id.Remove);
        Edit.setPaintFlags(Edit.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        Remove.setPaintFlags(Remove.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu_details.this, "Edit", Toast.LENGTH_SHORT).show();
            }
        });
        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu_details.this, "Remove", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
