package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GridActivity extends AppCompatActivity {
ImageView img;
TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        txt=(TextView)findViewById(R.id.text);
        img=(ImageView)findViewById(R.id.image);

        Intent intent = getIntent();
        txt.setText(intent.getStringExtra("name"));
        img.setImageResource(intent.getIntExtra("image", 0));
    }
}