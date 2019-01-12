package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPills extends AppCompatActivity {

    private int id;
    public ImageView pillLogo;
    public Spinner freqSpinner;
    public ArrayList<String> frequency;
    public TextView timeFreq1,timeFreq2,timeFreq3, q1, q2, q0;
    public EditText quantity1, quantity2, quantity3, days, name;
    public AppCompatButton btnDone;
    public int t1, t2, t3, t4;
    int quantity, code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pills);

        hide();

        Intent intent = getIntent();

        pillLogo = findViewById(R.id.pillLogo);
        name = findViewById(R.id.inputName);
        freqSpinner = findViewById(R.id.inputFreqPill);
        timeFreq1 = findViewById(R.id.timeFreq1);
        timeFreq2 = findViewById(R.id.timeFreq2);
        timeFreq3 = findViewById(R.id.timeFreq3);
        q0 = findViewById(R.id.q0);
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        quantity1 = findViewById(R.id.quantityPill1);
        quantity2 = findViewById(R.id.quantityPill2);
        quantity3 = findViewById(R.id.quantityPill3);
        days = findViewById(R.id.inputDays);

        frequency = new ArrayList<>();

        id = intent.getIntExtra("id", 0);

        if (id == 1) pillLogo.setImageResource(R.drawable.one);
        if (id == 2) pillLogo.setImageResource(R.drawable.two);
        if (id == 3) pillLogo.setImageResource(R.drawable.three);
        if (id == 4) pillLogo.setImageResource(R.drawable.four);

        frequency.add("Once a day: 10:00 AM");
        frequency.add("Once a day: 4:00 PM");
        frequency.add("Once a day: 9:00 PM");
        frequency.add("Twice a day");
        frequency.add("3 times a day");

        quantity1.setEnabled(false);
        quantity2.setEnabled(false);
        quantity3.setEnabled(false);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, frequency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        freqSpinner.setAdapter(adapter);

        freqSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){

                    quantity1.setEnabled(true);
                    quantity2.setEnabled(false);
                    quantity3.setEnabled(false);
                    timeFreq2.setTextColor(getResources().getColor(R.color.monsoon));
                    timeFreq3.setTextColor(getResources().getColor(R.color.monsoon));
                    q1.setTextColor(getResources().getColor(R.color.monsoon));
                    q2.setTextColor(getResources().getColor(R.color.monsoon));
                    timeFreq1.setTextColor(Color.RED);
                    q0.setTextColor(getResources().getColor(R.color.primary_darker));

                    t1 = 1;
                    t2 = 0;
                    t3 = 0;

                    code = 0;

                }
                if (position == 1){

                    quantity1.setEnabled(false);
                    quantity2.setEnabled(true);
                    quantity3.setEnabled(false);
                    timeFreq1.setTextColor(getResources().getColor(R.color.monsoon));
                    timeFreq3.setTextColor(getResources().getColor(R.color.monsoon));
                    q0.setTextColor(getResources().getColor(R.color.monsoon));
                    q2.setTextColor(getResources().getColor(R.color.monsoon));
                    timeFreq2.setTextColor(Color.RED);
                    q1.setTextColor(getResources().getColor(R.color.primary_darker));

                    t1 = 0;
                    t2 = 1;
                    t3 = 0;

                    code = 1;

                }
                if (position == 2){

                    quantity1.setEnabled(false);
                    quantity2.setEnabled(false);
                    quantity3.setEnabled(true);
                    timeFreq2.setTextColor(getResources().getColor(R.color.monsoon));
                    timeFreq1.setTextColor(getResources().getColor(R.color.monsoon));
                    q1.setTextColor(getResources().getColor(R.color.monsoon));
                    q0.setTextColor(getResources().getColor(R.color.monsoon));
                    timeFreq3.setTextColor(Color.RED);
                    q2.setTextColor(getResources().getColor(R.color.primary_darker));

                    t1 = 0;
                    t2 = 0;
                    t3 = 1;

                    code = 2;

                }
                if (position == 3){

                    quantity1.setEnabled(true);
                    quantity2.setEnabled(false);
                    quantity3.setEnabled(true);
                    timeFreq1.setTextColor(Color.RED);
                    q0.setTextColor(getResources().getColor(R.color.primary_darker));
                    timeFreq3.setTextColor(Color.RED);
                    q2.setTextColor(getResources().getColor(R.color.primary_darker));
                    timeFreq2.setTextColor(getResources().getColor(R.color.monsoon));
                    q1.setTextColor(getResources().getColor(R.color.monsoon));

                    t1 = 1;
                    t2 = 0;
                    t3 = 1;

                    code = 3;

                }
                if (position == 4){

                    quantity1.setEnabled(true);
                    quantity2.setEnabled(true);
                    quantity3.setEnabled(true);
                    timeFreq2.setTextColor(Color.RED);
                    q1.setTextColor(getResources().getColor(R.color.primary_darker));
                    timeFreq1.setTextColor(Color.RED);
                    q0.setTextColor(getResources().getColor(R.color.primary_darker));
                    timeFreq3.setTextColor(Color.RED);
                    q2.setTextColor(getResources().getColor(R.color.primary_darker));

                    t1 = 1;
                    t2 = 1;
                    t3 = 1;

                    code = 4;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setTimeArray(View view){




        try {

            t4 = Integer.valueOf(days.getText().toString());

            if (code == 0){

                quantity = t4 * Integer.valueOf(quantity1.getText().toString());

            }

            if (code == 1){

                quantity = t4 * Integer.valueOf(quantity2.getText().toString());

            }

            if (code == 2){

                quantity = t4 * Integer.valueOf(quantity3.getText().toString());

            }

            if (code == 3){

                int k3 = Integer.valueOf(quantity3.getText().toString());
                int k1 = Integer.valueOf(quantity1.getText().toString());

                quantity = t4 * (k1 + k3);

            }

            if (code == 4){

                int k3 = Integer.valueOf(quantity3.getText().toString());
                int k1 = Integer.valueOf(quantity1.getText().toString());
                int k2 = Integer.valueOf(quantity2.getText().toString());

                quantity = t4 * (k1 + k2 + k3);

            }

            PatientActivity.pillArray[(id -1) * 5] = quantity;
            PatientActivity.pillArray[(id -1) * 5 + 1] = t1;
            PatientActivity.pillArray[(id -1) * 5 + 2] = t2;
            PatientActivity.pillArray[(id -1) * 5 + 3] = t3;
            PatientActivity.pillArray[(id -1) * 5 + 4] = t4;

            finish();

        } catch (Exception e){

            Toast.makeText(this, "Error: Please Enter valid responses", Toast.LENGTH_SHORT).show();

        }

    }

    public void hide() {

        findViewById(R.id.pillView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

        findViewById(R.id.pillLogo).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        finish();
    }
}
