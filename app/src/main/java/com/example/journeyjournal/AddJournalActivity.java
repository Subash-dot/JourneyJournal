package com.example.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddJournalActivity extends AppCompatActivity {

    private Button btnAddJournal, btnLoc;
    private EditText etTitle;
    private EditText etDescription;
    private TextView txtLatitude, txtLongitude;

    DatabaseHelper db;

    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        btnAddJournal = findViewById(R.id.btnAddJournal);
        btnLoc = findViewById(R.id.btnLoc);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);

        db = new DatabaseHelper(this);

        String userID = getIntent().getStringExtra("userIDKey");
        //Toast.makeText(AddJournalActivity.this, "UserID is.: "+userID, Toast.LENGTH_SHORT).show();

        gpsTracker = new GpsTracker(AddJournalActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Toast.makeText(AddJournalActivity.this, ""+latitude+","+longitude, Toast.LENGTH_SHORT).show();
            txtLatitude.setText(Double.toString(latitude));
            txtLongitude.setText(Double.toString(longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }

        btnAddJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etTitle.getText().toString().isEmpty()) {
                    Toast.makeText(AddJournalActivity.this, "Title Empty", Toast.LENGTH_SHORT).show();
                } else if (etDescription.getText().toString().isEmpty()) {
                    Toast.makeText(AddJournalActivity.this, "Description Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Double latitude = Double.valueOf(txtLatitude.getText().toString());
                    Double longitude = Double.valueOf(txtLongitude.getText().toString());
                    Boolean insertJournal = db.insertJournal(Integer.parseInt(userID), etTitle.getText().toString(), etDescription.getText().toString(), latitude, longitude);
                    if(insertJournal == true) {
                        Toast.makeText(AddJournalActivity.this, "New Journal Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddJournalActivity.this, JournalActivity.class);
                        intent.putExtra("userIDKey", userID);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddJournalActivity.this, "Failed to Add Journal", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(AddJournalActivity.this);
                if(gpsTracker.canGetLocation()){
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
                    Toast.makeText(AddJournalActivity.this, ""+latitude+","+longitude, Toast.LENGTH_SHORT).show();
                    txtLatitude.setText(Double.toString(latitude));
                    txtLongitude.setText(Double.toString(longitude));
                }else{
                    gpsTracker.showSettingsAlert();
                }
            }
        });
    }
}
























