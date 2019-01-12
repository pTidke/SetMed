package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.linkLogin) TextView _loginLink;
    @BindView(R.id.btnSignup) Button signUpButton;
    @BindView(R.id.inputName) EditText name;
    @BindView(R.id.inputUsername) EditText username;
    @BindView(R.id.inputPassword) EditText password;
    @BindView(R.id.roleSwitch) Switch role;
    @BindView(R.id.inputPhone) EditText phone;
    @BindView(R.id.inputCity) EditText city;
    @BindView(R.id.inputAge) EditText age;
    @BindView(R.id.inputSpeciality) EditText speciality;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);


        role.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!role.isChecked()){

                    speciality.setEnabled(true);

                }
                else if (role.isChecked()){

                    speciality.setEnabled(false);

                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser user = new ParseUser();

                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.put("name", StringUtils.capitalize(name.getText().toString()));
                user.put("isPatient", role.isChecked());
                user.put("age", Integer.valueOf(String.valueOf(age.getText())));
                user.put("city", StringUtils.capitalize(city.getText().toString()));
                user.put("phone", Double.parseDouble(String.valueOf(phone.getText())));
                user.put("specialty", StringUtils.capitalize(speciality.getText().toString()));

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){

                            Toast.makeText(SignupActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                            Intent intentDoctor = new Intent(getApplicationContext(), DoctorActivity.class);
                            Intent intentPatient = new Intent(getApplicationContext(), PatientActivity.class);

                            if (role.isChecked()){

                                intentPatient.putExtra("name", StringUtils.capitalize(name.getText().toString()));
                                startActivity(intentPatient);

                            }
                            else if (!role.isChecked()){

                                intentDoctor.putExtra("name", StringUtils.capitalize(name.getText().toString()));
                                startActivity(intentDoctor);

                            }


                        }
                        else {

                            Toast.makeText(SignupActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

        hide();

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void hide() {

        findViewById(R.id.signupView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

        findViewById(R.id.signupLogo).setOnTouchListener(new View.OnTouchListener() {
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
        moveTaskToBack(true);
    }

}