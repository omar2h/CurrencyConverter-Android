package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    EditText in1, in2;
    Spinner spin1, spin2;


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

                if(spin1.getSelectedItemPosition()==spin2.getSelectedItemPosition()) {
                    spin2.setSelection((spin1.getSelectedItemPosition() + 1) % 2);
                    in1.setText("");
                    in2.setText("");
                    in1.requestFocus();
                }
                in1.setSelection(in1.getText().length());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spin1.getSelectedItemPosition()==spin2.getSelectedItemPosition()) {
                    spin1.setSelection((spin2.getSelectedItemPosition() + 1) % 2);
                    in1.setText("");
                    in2.setText("");
                    in2.requestFocus();
                }
                in2.setSelection(in2.getText().length());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    TextWatcher watcher1 = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            double n;
            String text = spin2.getSelectedItem().toString();
            in2.removeTextChangedListener(watcher2);
            if(text.equals("Egyptian Pound")) {
                if (!s.toString().equals("")) {
                    n = Double.parseDouble(s.toString());
                    n *= 15.90;
                    if(n%1==0)
                        in2.setText(String.format("%.0f", n));
                    else
                        in2.setText(String.format("%.2f", n));
                } else
                    in2.setText("");
            }
            else if (text.equals("United States Dollar")){
                if (!s.toString().equals("")) {
                    n = Double.parseDouble(s.toString());
                    n /= 15.90;
                    if(n%1==0)
                        in2.setText(String.format("%.0f", n));
                    else
                        in2.setText(String.format("%.2f", n));
                } else
                    in2.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            in2.addTextChangedListener(watcher2);
            String str = in1.getText().toString();
            if (str.isEmpty()) return;
            String str2 = PerfectDecimal(str, 9, 2);

            if (!str2.equals(str)) {
                in1.setText(str2);
                int pos = in1.getText().length();
                in1.setSelection(pos);
            }
        }
    };

    TextWatcher watcher2 = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            double n;
            String text = spin1.getSelectedItem().toString();
            in1.removeTextChangedListener(watcher1);
            if(text.equals("Egyptian Pound")) {
                if (!s.toString().equals("")) {
                    n = Double.parseDouble(s.toString());
                    n *= 15.90;
                    if(n%1==0)
                        in1.setText(String.format("%.0f", n));
                    else
                        in1.setText(String.format("%.2f", n));
                } else
                    in1.setText("");
            }
            else if (text.equals("United States Dollar")){
                if (!s.toString().equals("")) {
                    n = Double.parseDouble(s.toString());
                    n /= 15.90;
                    if(n%1==0)
                        in1.setText(String.format("%.0f", n));
                    else
                        in1.setText(String.format("%.2f", n));
                } else
                    in1.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            in1.addTextChangedListener(watcher1);
            String str = in2.getText().toString();
            if (str.isEmpty()) return;
            String str2 = PerfectDecimal(str, 9, 2);

            if (!str2.equals(str)) {
                in2.setText(str2);
                int pos = in2.getText().length();
                in2.setSelection(pos);
            }

        }

    };

    public String PerfectDecimal(String str, int MAX_BEFORE_POINT, int MAX_DECIMAL){
        if(str.charAt(0) == '.') str = "0"+str;
        int max = str.length();

        String rFinal = "";
        boolean after = false;
        int i = 0, up = 0, decimal = 0; char t;
        while(i < max){
            t = str.charAt(i);
            if(t != '.' && after == false){
                up++;
                if(up > MAX_BEFORE_POINT) return rFinal;
            }else if(t == '.'){
                after = true;
            }else{
                decimal++;
                if(decimal > MAX_DECIMAL)
                    return rFinal;
            }
            rFinal = rFinal + t;
            i++;
        }return rFinal;
    }


}