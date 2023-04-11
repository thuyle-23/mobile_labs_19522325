package com.example.mobile_labs_19522325;

import static com.example.mobile_labs_19522325.PersonalSalary.dependenceCost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextInputEditText txtInputName, txtInputGrossSal;
    ArrayList<PersonalSalary> arr;
    Button btnCalc;
    ListView listViewPersonalSal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arr = new ArrayList<PersonalSalary>();
        txtInputName = (TextInputEditText) findViewById(R.id.txtInputName);
        txtInputGrossSal = (TextInputEditText) findViewById(R.id.txtInputGrossSal);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        listViewPersonalSal = (ListView) findViewById(R.id.listViewPersonalSal);

        ArrayAdapter<PersonalSalary> adapter = new ArrayAdapter<PersonalSalary>(
                MainActivity.this, android.R.layout.simple_list_item_1, arr
        );
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalSalary ps = new PersonalSalary();

                ps.setFullName(txtInputName.getText().toString());
                double grossSalary = Long.parseLong(txtInputGrossSal.getText().toString());
                double a = (double) (grossSalary - grossSalary * 0.105);
                if ( a <= dependenceCost)
                    ps.setGrossSalary(a);
                else
                    ps.setGrossSalary((double)(a - (a - dependenceCost) * 0.05));
                arr.add(ps);
                adapter.notifyDataSetChanged();

                //Clear all text field when submit button
                txtInputName.setText("");
                txtInputGrossSal.setText("");

                //Hide keyboard on button click
                txtInputName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                txtInputGrossSal.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        listViewPersonalSal.setAdapter(adapter);
    }
}