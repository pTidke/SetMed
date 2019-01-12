package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PatientActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView pill1, pill2, pill3, pill4;
    public static int[] pillArray = new int[20];
    public static TextView name1, time1, take1, name2, time2, take2, name3, time3, take3, name4, time4, take4;
    //public static SharedPreferences preferences1;

    public String pillString;

    AppCompatButton btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        //preferences1 = getSharedPreferences("com.sourcey.materiallogindemo", MODE_PRIVATE);


        pill1 = findViewById(R.id.pill1);
        pill2 = findViewById(R.id.pill2);
        pill3 = findViewById(R.id.pill3);
        pill4 = findViewById(R.id.pill4);
        btnSend = findViewById(R.id.btnSend);

        name1 = findViewById(R.id.name1);
        time1 = findViewById(R.id.time1);
        take1 = findViewById(R.id.take1);
        name2 = findViewById(R.id.name2);
        time2 = findViewById(R.id.time2);
        take2 = findViewById(R.id.take2);
        name3 = findViewById(R.id.name3);
        time3 = findViewById(R.id.time3);
        take3 = findViewById(R.id.take3);
        name4 = findViewById(R.id.name4);
        time4 = findViewById(R.id.time4);
        take4 = findViewById(R.id.take4);

        pill1.setOnClickListener(this);
        pill2.setOnClickListener(this);
        pill3.setOnClickListener(this);
        pill4.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();
        this.setTitle("Hey, " + StringUtils.capitalize(intent.getStringExtra("name")));

        //initializing the medicines array to send to arduino
        for (int i = 0; i < 20; i++){

            pillArray[i] = 0;

        }

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.patient, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.btnLogoutPatient){ logout(); }

        if (item.getItemId() == R.id.btnDoctor){ showDoctors(); }

        if (item.getItemId() == R.id.btnMessage){ getMessage(); }

        return super.onOptionsItemSelected(item);

    }

    private void getMessage() {

        Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
        startActivity(intent);

    }

    public void logout(){

        try {

            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){

                        Toast.makeText(PatientActivity.this, "logged out successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                    }
                }
            });

        } catch (Exception e){

        }

    }

    public void showDoctors(){

        //method to open the doctors activity
        try {

            Intent intent1 = new Intent(getApplicationContext(), DoctorList.class);

            if (ParseUser.getCurrentUser().getString("isAttending") == null){

                updateDoctors();

            }
            else {

                getDoctor();

            }

            startActivity(intent1);

        }
        catch (Exception e) {

        }

    }

    public static void getDoctor(){

        DoctorList.attendDoctor.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereEqualTo("name", ParseUser.getCurrentUser().getString("isAttending"));

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0){

                        for (ParseUser user: objects){

                            DoctorList.attendDoctor.add(user.getString("name"));
                            DoctorList.attendDoctor.add("           Speciality: " + user.getString("specialty"));
                            DoctorList.attendDoctor.add("           City: " + user.getString("city"));
                            DoctorList.attendDoctor.add("           Age: " + user.getInt("age"));
                            DoctorList.attendDoctor.add("           Phone: " + user.getInt("phone"));


                        }

                        DoctorList.arrayAdapter1.notifyDataSetChanged();

                    }

                }

            }
        });

    }

    public void updateDoctors() {

        DoctorList.doctors.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo("isPatient", false);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0){

                        for (ParseUser user: objects){

                            DoctorList.doctors.add(user.getString("name"));
                            DoctorList.doctors.add("           Speciality: " + user.getString("specialty"));
                            DoctorList.doctors.add("           City: " + user.getString("city"));
                            DoctorList.doctors.add("           Age: " + user.getInt("age"));
                            DoctorList.doctors.add("           Phone: " + user.getInt("phone"));


                        }

                        DoctorList.arrayAdapter.notifyDataSetChanged();

                    }

                }

            }
        });

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getApplicationContext(), AddPills.class);
        Intent intent1 = new Intent(getApplicationContext(), Bluetooth.class);


        if (v.getId() == R.id.pill1){

            Toast.makeText(this, "Pill 1 clicked", Toast.LENGTH_SHORT).show();
            intent.putExtra("id", 1);
            startActivity(intent);

        }

        if (v.getId() == R.id.pill2){

            Toast.makeText(this, "Pill 2 clicked", Toast.LENGTH_SHORT).show();
            intent.putExtra("id", 2);
            startActivity(intent);

        }

        if (v.getId() == R.id.pill3){

            Toast.makeText(this, "Pill 3 clicked", Toast.LENGTH_SHORT).show();
            intent.putExtra("id", 3);
            startActivity(intent);

        }

        if (v.getId() == R.id.pill4){

            Toast.makeText(this, "Pill 4 clicked", Toast.LENGTH_SHORT).show();
            intent.putExtra("id", 4);
            startActivity(intent);

        }

        if (v.getId() == R.id.btnSend){

            pillString = "";

            for (int i = 0; i< 20; i++){

                if ( i % 5 == 0 || i % 5 == 4){

                    if (pillArray[i] <= 9){

                        pillString += "0" + String.valueOf(pillArray[i]);

                    }

                    else {

                        pillString += String.valueOf(pillArray[i]);

                    }

                }

                else {

                    pillString += String.valueOf(pillArray[i]);

                }

                Log.i("data", String.valueOf(pillArray[i]));

            }

            intent1.putExtra("pills", pillString);
            Toast.makeText(this, pillString, Toast.LENGTH_SHORT).show();
            startActivity(intent1);

        }

    }
}
