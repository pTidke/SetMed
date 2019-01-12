package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MessagePatient extends AppCompatActivity {

    public EditText prescribe;
    public AppCompatButton btnPres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_patient);

        this.setTitle("Add Prescription");

        final Intent intent = getIntent();
        prescribe = findViewById(R.id.et);
        btnPres = findViewById(R.id.btnPres);

        btnPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = intent.getStringExtra("name");

                ParseObject messages = new ParseObject("prescription");

                messages.put("doctor", ParseUser.getCurrentUser().getString("name"));
                messages.put("patient", name);
                messages.put("message", prescribe.getText().toString());

                messages.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e != null){

                            Toast.makeText(MessagePatient.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        else {

                            Toast.makeText(MessagePatient.this, "Sent", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                finish();

            }
        });
    }
}
