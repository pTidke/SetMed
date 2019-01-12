package com.sourcey.materiallogindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    TextView messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        this.setTitle("Prescriptions");
        messageText = findViewById(R.id.messageText);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("prescription");

        query.whereEqualTo("patient", ParseUser.getCurrentUser().getString("name"));

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if ( e == null){

                    if (objects.size() > 0){

                        for (ParseObject object: objects){

                            //Toast.makeText(MessagesActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            messageText.setText("[" + object.getUpdatedAt().toLocaleString() + "]\n" + object.getString("message") + "\n");

                        }

                    }

                }
                else {

                    Toast.makeText(MessagesActivity.this, "error", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}
