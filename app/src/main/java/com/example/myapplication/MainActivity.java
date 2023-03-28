package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    GridView gridView;
    String[] name = {"He'd have you all unravel at the",
            "Heed not the rabble",
            "Sound of screams but the",
            "Who scream"};
    int[] colorname = {R.color.color8, R.color.color9, R.color.color10, R.color.color11};
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

        gridView = findViewById(R.id.gridview);
        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getApplicationContext(), GridActivity.class);
                intent.putExtra("name", name[position]);
                intent.putExtra("image", colorname[position]);
                startActivity(intent);
            }
        });

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

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return colorname.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.grid_list, null);
            TextView name1 = view.findViewById(R.id.text);
            ImageView image = view.findViewById(R.id.image);
            name1.setText(name[position]);
            image.setImageResource(colorname[position]);
            return view;
        }
    }
}
