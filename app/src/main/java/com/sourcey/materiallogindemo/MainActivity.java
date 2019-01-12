package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Database.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("915a6c06d29a92d23310ab4214bde8af4769fa2c")
                .clientKey("ae9efdb28bfecc0787c79098a604f7bc7fb9640c")
                .server("http://13.233.115.16:80/parse/")
                .build()
        );

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL,true);

        //check if any user is logged in
        if (ParseUser.getCurrentUser() != null){

            boolean isPatient = ParseUser.getCurrentUser().getBoolean("isPatient");
            String name = ParseUser.getCurrentUser().getString("name");

            if (isPatient){

                Intent intentPatient = new Intent(getApplicationContext(), PatientActivity.class);
                intentPatient.putExtra("name", name);
                startActivity(intentPatient);

            } else {

                Intent intentDoctor = new Intent(getApplicationContext(), DoctorActivity.class);
                intentDoctor.putExtra("name", name);
                startActivity(intentDoctor);

            }

        } else {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }
    }

}
