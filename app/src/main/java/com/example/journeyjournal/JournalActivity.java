package com.example.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class JournalActivity extends AppCompatActivity {

    private Button btnAddJournal;

    ArrayList<JournalModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        String userID = getIntent().getStringExtra("userIDKey");

        //Toast.makeText(JournalActivity.this, "This is the.: "+userID, Toast.LENGTH_SHORT).show();

        btnAddJournal = findViewById(R.id.btnAddJournal);

        btnAddJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JournalActivity.this, AddJournalActivity.class);
                intent.putExtra("userIDKey", userID);
                startActivity(intent);
            }
        });

        RecyclerView QuestionList = (RecyclerView) findViewById(R.id.journals);
        QuestionList.setLayoutManager(new LinearLayoutManager(JournalActivity.this));

        Cursor cursor = new DatabaseHelper(JournalActivity.this).viewAllJournal(Integer.parseInt(userID));
        data = new ArrayList<>();


        if (cursor.getCount() == 0) {
            Toast.makeText(JournalActivity.this, "No Journal to show.", Toast.LENGTH_SHORT).show();
        }
        else {
            int count = 0;
            while(cursor.moveToNext())
            {
                JournalModel obj = new JournalModel(cursor.getInt(0),cursor.getInt(1), cursor.getString(2), cursor.getString(3));
                data.add(obj);
            }
        }

        JournalAdapter adapter = new JournalAdapter(data, new JournalAdapter.ItemClickListener() {
            @Override
            public void onItemClick(JournalModel j) {
                Intent intent;
                intent = new Intent(JournalActivity.this, UpdateJournal.class);
                String a = String.valueOf(j.getId());
                intent.putExtra("idKey", a);
                intent.putExtra("userIDKey", userID);
                startActivity(intent);
            }
        });

        QuestionList.setAdapter(adapter);
    }
}