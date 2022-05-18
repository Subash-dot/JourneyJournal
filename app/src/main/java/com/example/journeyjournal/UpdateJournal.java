package com.example.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class UpdateJournal extends AppCompatActivity {

    private TextView tvID;
    private TextView txtLatitude, txtLongitude;
    private EditText etTitle, etDescription;
    private Button btnUpdate, btnDelete;
    private Button btnMaps;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_journal);

        db = new DatabaseHelper(UpdateJournal.this);

        tvID = findViewById(R.id.tvID);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        String Id = getIntent().getStringExtra("idKey");
        String userID = getIntent().getStringExtra("userIDKey");

        tvID.setText(Id);

        String id = tvID.getText().toString();
        searchJournal(Integer.parseInt(id));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();

                Boolean checkUpdateJournal = db.updateJournal(Integer.parseInt(id), title, description);
                if (checkUpdateJournal==true){
                    Toast.makeText(UpdateJournal.this, "Journal Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateJournal.this, JournalActivity.class);
                    intent.putExtra("userIDKey", userID);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateJournal.this, "Journal not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkDeleteJournal = db.deleteJournal(Integer.parseInt(id));
                if (checkDeleteJournal == true) {
                    Toast.makeText(UpdateJournal.this, "Journal Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateJournal.this, JournalActivity.class);
                    intent.putExtra("userIDKey", userID);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateJournal.this, "Journal not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void searchJournal(int id) {
        String title = null, description = null, latitude = null, longitude = null;
        Cursor cursor = db.searchJournal(id);
        while (cursor.moveToNext()) {

            title = cursor.getString(2);
            description = cursor.getString(3);
            latitude = cursor.getString(4);
            longitude = cursor.getString(5);

            etTitle.setText(title);
            etDescription.setText(description);
            txtLatitude.setText(latitude);
            txtLongitude.setText(longitude);
        }
    }
}