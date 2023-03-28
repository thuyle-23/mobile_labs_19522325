package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.list_view);
        String[] itemList = new String[]{
                "", "", ""
        };
        String[] strView = new String[]{"He'd have you all unravel at the",
                "Heed not the rabble",
                "Sound of screams but the",
                "Who scream"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, itemList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position == 0) {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.color1));
                } else if (position == 1) {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.color4));
                } else if (position == 2) {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.color6));
                }
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
    }
}
