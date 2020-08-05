package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText egEdit, usEdit;
    private Boolean machine_changed_edittext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        egEdit = findViewById(R.id.input1);
        usEdit = findViewById(R.id.input2);

        egEdit.addTextChangedListener(egWatcher);
        usEdit.addTextChangedListener(usWatcher);
    }

    TextWatcher egWatcher = new TextWatcher() {

        double eg, us;
        boolean flag = false;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            usEdit.removeTextChangedListener(usWatcher);
            if (!s.toString().equals("")) {
                flag = true;
                eg = Double.parseDouble(s.toString());
                us = eg*15.90;
                usEdit.setText(String.format("%.2f",us));
            }
            else
                usEdit.setText("");

        }

        @Override
        public void afterTextChanged(Editable s) {
            usEdit.addTextChangedListener(usWatcher);
        }
    };

    TextWatcher usWatcher = new TextWatcher() {

        double eg, us;
        boolean flag = false;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            egEdit.removeTextChangedListener(egWatcher);
            if (!s.toString().equals("")) {
                flag = true;
                us = Double.parseDouble(s.toString());
                eg = us / 15.90;
                egEdit.setText(String.format("%.2f",eg));
            }
            else
                egEdit.setText("");
        }

        @Override
        public void afterTextChanged(Editable s) {
            egEdit.addTextChangedListener(egWatcher);

        }
    };
}