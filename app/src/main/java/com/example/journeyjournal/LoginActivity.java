package com.example.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView btnRegister;
    private Button btnFacebook;
     FirebaseAuth mAuth;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(LoginActivity.this);

        etUsername = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_signup);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = 0;
                String b = null;
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(!username.isEmpty() && !password.isEmpty()) {
                    Cursor cursor = db.searchUserNameAndPassword(username, password);
                    while (cursor.moveToNext()) {
                        a = a + 1;
                        b = cursor.getString(0);
                    }
                    if(a==1) {
                        Intent intent;
                        intent = new Intent(LoginActivity.this, JournalActivity.class);
                        //Toast.makeText(LoginActivity.this, "UserIDKey.: "+b, Toast.LENGTH_SHORT).show();
                        intent.putExtra("userIDKey", b);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(LoginActivity.this, "Username Password does not matched.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please Enter your username and password", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}