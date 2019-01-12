package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {

    public static ArrayList<String> patients = new ArrayList<>();
    public static ArrayAdapter arrayAdapter2;
    public static ListView listViewPatients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        listViewPatients = findViewById(R.id.listPatients);
        arrayAdapter2 = new ArrayAdapter(this, R.layout.text, patients);

        listViewPatients.setAdapter(arrayAdapter2);

        getPatients();

        listViewPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), MessagePatient.class);

                intent.putExtra("name", patients.get(position));

                startActivity(intent);

            }
        });

        Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();
        this.setTitle("Your Patients");
    }

    private void getPatients() {

        patients.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereEqualTo("isAttending", ParseUser.getCurrentUser().getString("name"));
        query.whereEqualTo("isPatient", true);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null){

                    if (objects.size() > 0){

                        for (ParseUser user: objects){

                            patients.add(user.getString("name"));

                        }

                        arrayAdapter2.notifyDataSetChanged();

                    }

                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doctor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.btnLogoutDoctor){

            logout();

        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(){

        try {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){

                        Toast.makeText(DoctorActivity.this, "logged out successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                    }
                }
            });
        } catch (Exception e){
            Log.i("error", "logout: "+ e.getMessage());
        }

    }

}
