package com.example.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister;
    private TextView btnLogin;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(RegisterActivity.this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(!username.isEmpty() && !password.isEmpty()){
                    searchUsername(username,password);
                }else{
                    Toast.makeText(RegisterActivity.this, "Enter Username and password", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void searchUsername(String username, String password) {
        int a = 0;
        Cursor cursor = db.searchUserName(username);
        while (cursor.moveToNext()) {
            a = a + 1;
        }
        if (a > 0) {
            Toast.makeText(RegisterActivity.this, "Username already Used.", Toast.LENGTH_SHORT).show();
        }
        else {
            insertUsernamePassword(username, password);
        }
    }

    private void insertUsernamePassword(String username, String password) {
        Boolean checkinsertdata = db.insertUser(username, password);
         if (checkinsertdata == true){
            Toast.makeText(RegisterActivity.this, "New User Registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        else {
            Toast.makeText(RegisterActivity.this, "New User not Registered", Toast.LENGTH_SHORT).show();
        }
    }

}