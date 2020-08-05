package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    EditText in1, in2;
    Spinner spin1, spin2;
    String[] c = {"United States Dollar", "Egyptian Pound"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.currencies, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        in1 = findViewById(R.id.input1);
        in2 = findViewById(R.id.input2);



        in1.addTextChangedListener(watcher1);
        in2.addTextChangedListener(watcher2);

        spin1 = findViewById(R.id.spin1);
        spin1.setAdapter(adapter);

        spin2 = findViewById(R.id.spin2);
        spin2.setAdapter(adapter);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                in1.setText("");
                in2.setText("");
                spin2.setSelection((spin1.getSelectedItemPosition()+1)%2);
                in2.setFocusableInTouchMode(true);
                in2.requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                in1.setText("");
                in2.setText("");
                spin1.setSelection((spin2.getSelectedItemPosition()+1)%2);
                in1.setFocusableInTouchMode(true);
                in1.requestFocus();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    TextWatcher watcher1 = new TextWatcher() {

        double eg, us;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = spin2.getSelectedItem().toString();
            if(text.equals("Egyptian Pound")) {
                in2.removeTextChangedListener(watcher2);
                if (!s.toString().equals("")) {
                    eg = Double.parseDouble(s.toString());
                    us = eg * 15.90;
                    in2.setText(String.format("%.2f", us));
                } else
                    in2.setText("");
            }
            else{

                in2.removeTextChangedListener(watcher2);
                if (!s.toString().equals("")) {
                    us = Double.parseDouble(s.toString());
                    eg = us / 15.90;
                    in2.setText(String.format("%.2f", eg));
                } else
                    in2.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            in2.addTextChangedListener(watcher2);
        }
    };

    TextWatcher watcher2 = new TextWatcher() {

        double eg, us;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = spin1.getSelectedItem().toString();
            if (text == "United States Dollar") {
                in1.removeTextChangedListener(watcher1);
                if (!s.toString().equals("")) {
                    us = Double.parseDouble(s.toString());
                    eg = us / 15.90;
                    in1.setText(String.format("%.2f", eg));
                } else
                    in1.setText("");
            } else {
                in1.removeTextChangedListener(watcher1);
                if (!s.toString().equals("")) {
                    eg = Double.parseDouble(s.toString());
                    us = eg * 15.90;
                    in1.setText(String.format("%.2f", us));
                }
                else
                    in1.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            in1.addTextChangedListener(watcher1);

        }
    };


}