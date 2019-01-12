package com.sourcey.materiallogindemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class DoctorList extends AppCompatActivity {

    public static ArrayList<String> doctors = new ArrayList<>();
    public static ArrayAdapter arrayAdapter;
    public static ArrayAdapter arrayAdapter1;
    public static ListView listViewDoctors;
    public static ArrayList<String> attendDoctor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        listViewDoctors = findViewById(R.id.listDoctors);

        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, doctors);
        arrayAdapter1 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, attendDoctor);

        if (ParseUser.getCurrentUser().getString("isAttending") == null){

            this.setTitle("Doctors list");

            listViewDoctors.setAdapter(arrayAdapter);

            listViewDoctors.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    showAttendDialogue(position);

                    return false;
                }
            });

        } else {

            this.setTitle("Your Doctor");

            listViewDoctors.setAdapter(arrayAdapter1);

        }

    }


    public void showAttendDialogue(final int position){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(doctors.get((position/5)*5));

        builder.setMessage("Do you want to Attend this doctor?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                ParseUser.getCurrentUser().put("isAttending", doctors.get((position/5)*5));
                ParseUser.getCurrentUser().saveInBackground();
                finish();
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        final AlertDialog alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            }
        });

        alert.show();

    }

}
